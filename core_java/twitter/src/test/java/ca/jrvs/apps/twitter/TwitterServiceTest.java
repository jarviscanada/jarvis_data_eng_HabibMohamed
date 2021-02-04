package ca.jrvs.apps.twitter;

import static org.junit.Assert.*;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.impl.TwitterDao;
import ca.jrvs.apps.twitter.impl.TwitterHttpHelper;
import ca.jrvs.apps.twitter.impl.TwitterService;
import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class TwitterServiceTest {

  private CrdDao dao;
  private TwitterHttpHelper httpHelper;
  private TwitterService service;
  private Tweet testTweet;
  private String tweetId = "";
  private String expectedText1 = "Howdy yall#goodnight";
  private String expectedText2 = "Howdy yall#goodday";



  @Before
  public void setUp() throws Exception {

    httpHelper = new TwitterHttpHelper(System.getenv("consumerKey"), System.getenv("consumerSecret"),
        System.getenv("accessToken"), System.getenv("tokenSecret"));

    dao = new TwitterDao(httpHelper);

    service = new TwitterService(dao);

    testTweet = new Tweet();
    testTweet.setText("Howdy%20yall%23goodnight");



    Coordinates coordinates = new Coordinates();
    coordinates.setCoordinates(new float[]{100.23f, -82.456f});
    testTweet.setCoordinates(coordinates);

    tweetId = "1357029353046102018";
  }

  @Test
  public void postTweet() {

    Tweet resultTweet = service.postTweet(testTweet);

    assertEquals(resultTweet.getText(), expectedText1);
    assertEquals(resultTweet.getCoordinates().getCoordinates()[0],
        testTweet.getCoordinates().getCoordinates()[0], 0.0001);
    assertEquals(resultTweet.getCoordinates().getCoordinates()[1],
        testTweet.getCoordinates().getCoordinates()[1], 0.0001);


  }

  @Test
  public void showTweet() {

    Tweet resultTweet = service.showTweet(tweetId, null);

    assertEquals(resultTweet.getText(), expectedText2);
    assertEquals(resultTweet.getCoordinates().getCoordinates()[0],
        testTweet.getCoordinates().getCoordinates()[0], 0.0001);
    assertEquals(resultTweet.getCoordinates().getCoordinates()[1],
        testTweet.getCoordinates().getCoordinates()[1], 0.0001);

  }

  @Test
  public void deleteTweets() {

    List<Tweet> deletedTweets = service.deleteTweets(new String[]{tweetId});

    for (Tweet tweet : deletedTweets){

      assertEquals(tweet.getText(), expectedText2);
      assertEquals(tweet.getCoordinates().getCoordinates()[0],
          testTweet.getCoordinates().getCoordinates()[0], 0.0001);
      assertEquals(tweet.getCoordinates().getCoordinates()[1],
          testTweet.getCoordinates().getCoordinates()[1], 0.0001);

    }

  }
}