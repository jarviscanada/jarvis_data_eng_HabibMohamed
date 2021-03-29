package ca.jrvs.apps.twitter.impl;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.util.StringUtils;

@org.springframework.stereotype.Service
public class TwitterService implements Service {

  private CrdDao dao;
  
  final static int MAX_TEXT_LENGTH = 140;
  final static int MAX_LAT = 90;
  final static int MIN_LAT = -90;
  final static int MAX_LON = 180;
  final static int MIN_LON = -180;


  /**
   * Constructor generates a TwitterService object
   * that requires a CrdDao object to be passed in,
   * most likely a TwitterDao object.
   *
   * @param dao
   */
  public TwitterService(CrdDao dao){this.dao = dao;}

  /**
   * Checks if text and coordinates do not violate
   * Twitter constraints.
   *
   * @param tweet
   * @return true if valid, false otherwise
   */
  private boolean validateTweet(Tweet tweet){

    String text = tweet.getText();
    float lon = tweet.getCoordinates().getCoordinates()[0];
    float lat = tweet.getCoordinates().getCoordinates()[1];

    return text.length() <= MAX_TEXT_LENGTH && 
      (lon <= MAX_LON && lon >= MIN_LON) && 
      (lat <= MAX_LAT && lat >= MIN_LAT);

  }

  /**
   * Checks if id is numerical sequence
   *
   * @param id
   * @return true if numerical sequence, false otherwise
   */
  private boolean validateId(String id){

    return id.matches("[0-9]+");

  }

  private void filterByField(Tweet tweet, String[] fields){

    StringBuilder messageBuilder = new StringBuilder();

    messageBuilder.append("{\n");

    for (String field : fields){

      messageBuilder.append("\"" + field + "\": ");

      Method methodName = null;

      try{
        methodName = Tweet.class.getMethod("get" + StringUtils.capitalize(field));
      }catch (NoSuchMethodException e){
        throw new RuntimeException("The field " + field + " is an invalid field.", e);
      }

      Object result = null;
       try{
         result = methodName.invoke(tweet);
       } catch (IllegalAccessException e) {
         throw new RuntimeException("Permissions error.", e);
       } catch (InvocationTargetException e){
         throw new RuntimeException("Problem with method invocation.", e);
       }


      messageBuilder.append("\"" + result.toString() + "\"\n,");


    }

    messageBuilder.append("}");

    tweet.setSpecializedMessage(messageBuilder.toString());

  }

  @Override
  public Tweet postTweet(Tweet tweet) {

    if(!validateTweet(tweet)){
      throw new IllegalArgumentException("Tweet must be no longer than 140 characters. "
          + "Longitude is between (-180,180) inclusive and Latitude is between (-90, 90) inclusive");
    }

    return (Tweet) dao.create(tweet);

  }

  @Override
  public Tweet showTweet(String id, String[] fields) {

    if(!validateId(id)){
      throw new IllegalArgumentException("The ID " + id + " is invalid");
    }

    Tweet tweet = (Tweet) dao.findById(id);

    if (fields == null){
      tweet.setSpecializedMessage(tweet.toString());
    }else{
      filterByField(tweet, fields);
    }

    return tweet;
  }

  @Override
  public List<Tweet> deleteTweets(String[] ids) {

    List<Tweet> deletedTweets = new ArrayList<Tweet>();

    //Goes through each id passed in the ids array
    Arrays.stream(ids)
        .forEach(
            id -> {
              if(!validateId(id)){
                throw new IllegalArgumentException("The ID " + id + " is invalid");
              }
              deletedTweets.add((Tweet) dao.deleteById(id));
            }
        );

    return deletedTweets;
  }
}
