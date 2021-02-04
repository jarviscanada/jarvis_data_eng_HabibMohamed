package ca.jrvs.apps.twitter.util;

import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;

public class TweetBuilder {

  public static Tweet TweetBuild(String text, float lon, float lat){

    Coordinates coordinates = new Coordinates();
    coordinates.setCoordinates(new float[]{lon, lat});

    Tweet theTweet = new Tweet();

    theTweet.setText(text);
    theTweet.setCoordinates(coordinates);

    return theTweet;
  }

}
