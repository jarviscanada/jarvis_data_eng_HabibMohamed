package ca.jrvs.apps.twitter;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class TwitterDao implements CrdDao<Tweet, String> {

  private static final String API_BASE_URI = "https://api.twitter.com";
  private static final String POST_PATH = "/1.1/statuses/update.json";
  private static final String SHOW_PATH = "/1.1/statuses/show.json";
  private static final String DELETE_PATH = "/1.1/statuses/destroy";

  private static final String QUERY_SYM = "?";
  private static final String AMPERSAND = "&";
  private static final String EQUAL = "=";

  private static final int HTTP_OK = 200;

  private HttpHelper httpHelper;

  final Logger logger = LoggerFactory.getLogger(TwitterDao.class);

  @Autowired
  public TwitterDao(HttpHelper httpHelper){this.httpHelper = httpHelper;}

  public static Tweet fromJsonToModel(String response, Class clazz) throws IOException{

    ObjectMapper mapper = new ObjectMapper();

    mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    return (Tweet) mapper.readValue(response,clazz);

  }

  @Override
  public Tweet create(Tweet entity) {

    String tweetText = entity.getText();
    String tweetLon = String.valueOf(entity.getCoordinates().getCoordinates()[0]);
    String tweetLat = String.valueOf(entity.getCoordinates().getCoordinates()[1]);

    URI postURI = URI.create(API_BASE_URI + POST_PATH + QUERY_SYM + "status" + EQUAL
        + tweetText + AMPERSAND + "long" + EQUAL + tweetLon + AMPERSAND + "lat" +
        EQUAL + tweetLat);

    try {
      HttpResponse response = httpHelper.httpPost(postURI);

      String responseJson = EntityUtils.toString(response.getEntity());

      Tweet responseTweet = fromJsonToModel(responseJson, Tweet.class);

      return responseTweet;

    }catch (IOException e){
      logger.error(e.getMessage(), e);
    }

    return null;
  }

  @Override
  public Tweet findById(String s) {

    URI getURI = URI.create(API_BASE_URI + SHOW_PATH + QUERY_SYM + "id" + EQUAL + s);

    try{

      HttpResponse response = httpHelper.httpGet(getURI);

      String responseJson = EntityUtils.toString(response.getEntity());

      Tweet responseTweet = fromJsonToModel(responseJson, Tweet.class);

      return responseTweet;

    }catch (IOException e){
      logger.error(e.getMessage(), e);
    }

    return null;
  }

  @Override
  public Tweet deleteById(String s) {

    URI deleteURI = URI.create(API_BASE_URI + DELETE_PATH + "/" + s + ".json");

    try{

      HttpResponse response = httpHelper.httpPost(deleteURI);

      String responseJson = EntityUtils.toString(response.getEntity());

      Tweet responseTweet = fromJsonToModel(responseJson, Tweet.class);

      return responseTweet;

    }catch(IOException e){
      logger.error(e.getMessage(), e);
    }

    return null;
  }
}
