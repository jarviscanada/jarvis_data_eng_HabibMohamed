package ca.jrvs.apps.twitter.impl;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.JsonToTweet;
import java.io.IOException;
import java.net.URI;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
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

  /**
   * Constructor generates a TwitterDao object with
   * the requirement of passing in a httpHelper object,
   * usually a TwitterHttpHelper object.
   *
   * @param httpHelper
   */
  @Autowired
  public TwitterDao(HttpHelper httpHelper){this.httpHelper = httpHelper;}

  /**
   * Using uri and method, either posts or gets
   * and generate JSON string of response
   *
   * @param uri
   * @param method
   * @return String responseJSON
   * @throws IOException
   */
  private String getJson(URI uri, String method) throws IOException{

    HttpResponse response = null;

    if (method == "post"){
      response = httpHelper.httpPost(uri);
    }else{
      response = httpHelper.httpGet(uri);
    }

    if (response.getStatusLine().getStatusCode() != HTTP_OK){
      throw new RuntimeException("Http response is not OK", e);
    }

    String responseJson = EntityUtils.toString(response.getEntity());

    return responseJson;

  }

  @Override
  public Tweet create(Tweet entity) {

    //Building request
    String tweetText = entity.getText();
    String tweetLon = String.valueOf(entity.getCoordinates().getCoordinates()[0]);
    String tweetLat = String.valueOf(entity.getCoordinates().getCoordinates()[1]);

    URI postURI = URI.create(API_BASE_URI + POST_PATH + QUERY_SYM + "status" + EQUAL
        + tweetText + AMPERSAND + "long" + EQUAL + tweetLon + AMPERSAND + "lat" +
        EQUAL + tweetLat);

    Tweet responseTweet = null;

    //Sending request
    try {

      String responseJson = getJson(postURI, "post");

      responseTweet = JsonToTweet.fromJsonToModel(responseJson, Tweet.class);


    }catch (IOException e){
      throw new RuntimeException("There is a problem with the http request.", e);
    }

    return responseTweet;

  }

  @Override
  public Tweet findById(String s) {

    //Building request
    URI getURI = URI.create(API_BASE_URI + SHOW_PATH + QUERY_SYM + "id" + EQUAL + s);

    Tweet responseTweet = null;

    //Sending request
    try{

      String responseJson = getJson(getURI, "get");

      responseTweet = JsonToTweet.fromJsonToModel(responseJson, Tweet.class);

    }catch (IOException e){
      throw new RuntimeException("There is a problem with the http request.", e);
    }

    return responseTweet;

  }

  @Override
  public Tweet deleteById(String s) {

    //Building request
    URI deleteURI = URI.create(API_BASE_URI + DELETE_PATH + "/" + s + ".json");

    Tweet responseTweet = null;

    //Sending request
    try{

      String responseJson = getJson(deleteURI, "post");

      responseTweet = JsonToTweet.fromJsonToModel(responseJson, Tweet.class);

    }catch(IOException e){
      throw new RuntimeException("There is a problem with the http request.", e);
    }

    return responseTweet;

  }
}
