package ca.jrvs.apps.twitter.util;

import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import com.google.gdata.util.common.base.PercentEscaper;

public class TweetBuilder {

  /**
   * Takes a body of text, longitude, latitude, and formulates
   * a Tweet objects housing them.
   *
   * @param text
   * @param lon
   * @param lat
   * @return Tweet theTweet
   */
  public static Tweet TweetBuild(String text, float lon, float lat){

    PercentEscaper percentEscaper = new PercentEscaper("", false);

    Coordinates coordinates = new Coordinates();
    coordinates.setCoordinates(new float[]{lon, lat});

    Tweet theTweet = new Tweet();

    theTweet.setText(percentEscaper.escape(text));
    theTweet.setCoordinates(coordinates);

    return theTweet;
  }

}
