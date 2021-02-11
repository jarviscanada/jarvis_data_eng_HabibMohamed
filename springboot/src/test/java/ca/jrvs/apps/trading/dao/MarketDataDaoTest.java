package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.*;

import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.junit.Before;
import org.junit.Test;

public class MarketDataDaoTest {

  private MarketDataDao dao;

  @Before
  public void setUp() throws Exception {

    PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
    cm.setMaxTotal(50);
    cm.setDefaultMaxPerRoute(50);

    MarketDataConfig marketDataConfig = new MarketDataConfig("https://cloud.iexapis.com/stable",
        System.getenv("IEX_PUB_TOKEN"));

    dao = new MarketDataDao(cm, marketDataConfig);

  }

  @Test
  public void findByIdWorks() {

    String company = "twtr";

    IexQuote quote = dao.findById(company).get();

    assertNotNull(quote);
    assertEquals("Twitter Inc", quote.getCompanyName());



  }

  @Test(expected = IllegalArgumentException.class)
  public void findbyIdFails(){
    String company2 = "twtr23";
    dao.findById(company2);
  }

  @Test
  public void findAllById() {

    List<String> companies  = new ArrayList<>();
    companies.add("aapl");
    companies.add("twtr");

    List<IexQuote> quotes = dao.findAllById(companies);

    assertEquals(2, quotes.size());
    assertEquals("Twitter Inc", quotes.get(1).getCompanyName());
    assertEquals("Apple Inc", quotes.get(0).getCompanyName());

  }

  @Test(expected = IllegalArgumentException.class)
  public void findAllByIdFail() {
    dao.findAllById(Arrays.asList("a2pals", "twtr"));
  }
}