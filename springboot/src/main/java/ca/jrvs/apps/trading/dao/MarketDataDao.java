package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public class MarketDataDao implements CrudRepository<IexQuote, String> {

  private static final String IEX_STOCK = "/stock";
  private static final String IEX_MARKET = "/market";
  private static final String IEX_SYMBOLS = "%s";
  private static final String IEX_TYPES = "types=quote";
  private static final String IEX_TOKEN = "token=";

  private static final String AMPERSAND = "&";
  private static final String QUERY = "?";

  private final String IEX_BATCH_URL;
  private final String IEX_SINGLE_URL;

  private HttpClientConnectionManager httpCCM;

  private Logger logger = LoggerFactory.getLogger(MarketDataDao.class);

  public MarketDataDao(HttpClientConnectionManager httpCCM,
      MarketDataConfig marketDataConfig){

    this.httpCCM = httpCCM;

    IEX_BATCH_URL = marketDataConfig.getHost()  + IEX_STOCK
        + IEX_MARKET + "/batch" + QUERY +
        "symbols=" +
        IEX_SYMBOLS + AMPERSAND +
        IEX_TYPES + AMPERSAND + IEX_TOKEN
        + marketDataConfig.getToken();

    IEX_SINGLE_URL = marketDataConfig.getHost() + IEX_STOCK
        + "/" + IEX_SYMBOLS
        + "/quote" + QUERY
        + IEX_TOKEN + marketDataConfig.getToken();
  }



  private Optional<String> executeHttpGet(String url){

    HttpClient client = getHttpClient();
    URI getURI = URI.create(url);

    try {
      HttpResponse response = client.execute(new HttpGet(getURI));

      int status = response.getStatusLine().getStatusCode();

      if (status != 200 ){

        if (status == 404)
          return Optional.empty();
        else
          throw new DataRetrievalFailureException("The status of the response is " + String.valueOf(status));
      }

      String json = EntityUtils.toString(response.getEntity());

      return Optional.of(json);

    }catch (IOException e){
      throw new DataRetrievalFailureException("Something went wrong with the Http request");
    }

  }

  private CloseableHttpClient getHttpClient(){
    return HttpClients.custom()
        .setConnectionManager(httpCCM)
        .setConnectionManagerShared(true)
        .build();
  }

  @Override
  public Optional<IexQuote> findById(String s) {

    if(s.length() > 5 || s.length() < 3 || s.matches(".*[0-9]+.*")){
      throw new IllegalArgumentException("Symbol must be between 3-5 characters and have no numbers");
    }

    String url = String.format(IEX_SINGLE_URL, s);

    Optional<String> json = executeHttpGet(url);

    ObjectMapper mapper = new ObjectMapper();
    mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    try {
      IexQuote quote = (IexQuote) mapper.readValue(json.get(), IexQuote.class);

      return Optional.of(quote);
    } catch (IOException e) {
      throw new DataRetrievalFailureException("Couldn't convert JSON to object");
    }

  }

  @Override
  public List<IexQuote> findAllById(Iterable<String> iterable) {

    iterable.forEach( s -> {
          if (s.length() > 5 || s.length() < 3 || s.matches(".*[0-9]*.*")){
            throw new IllegalArgumentException("Symbol must be between 3-5 characters and have no numbers");
          }
        }
    );

    String ids = StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.joining(","));

    String url = String.format(IEX_BATCH_URL, ids);

    Optional<String> json = executeHttpGet(url);

    JSONObject jsonObject = new JSONObject(json.get());

    Iterator<String> keys = jsonObject.keys();

    ObjectMapper mapper = new ObjectMapper();
    mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
    ObjectReader reader = mapper.reader().withRootName("quote");

    List<IexQuote> quotes = new ArrayList<IexQuote>();

    do{

      String newJson = jsonObject.get(keys.next()).toString();

      try {
        IexQuote quote = (IexQuote) reader.forType(IexQuote.class).readValue(newJson);
        quotes.add(quote);

      } catch (IOException e) {
        throw new DataRetrievalFailureException("Couldn't convert JSON to object");
      }

    } while(keys.hasNext());

//    System.out.println(jsonObject.toString());
//    System.out.println(json.get());

    return quotes;

  }

  @Override
  public long count() {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public void deleteById(String s) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public void delete(IexQuote iexQuote) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public void deleteAll(Iterable<? extends IexQuote> iterable) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public void deleteAll() {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public Iterable<IexQuote> findAll() {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public boolean existsById(String s) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public <S extends IexQuote> S save(S s) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public <S extends IexQuote> Iterable<S> saveAll(Iterable<S> iterable) {
    throw new UnsupportedOperationException("Not implemented");
  }
}
