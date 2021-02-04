package ca.jrvs.apps.twitter.impl;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TwitterService implements Service {

  private CrdDao dao;

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

    return text.length() <= 140 && (lon <= 180 && lon >= -180) && (lat <= 90 && lat >= -90);

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

    return (Tweet) dao.findById(id);
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
