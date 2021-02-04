package ca.jrvs.apps.twitter;

import static org.junit.Assert.*;

import ca.jrvs.apps.twitter.impl.TwitterDao;
import ca.jrvs.apps.twitter.impl.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.TweetBuilder;
import org.junit.Before;
import org.junit.Test;

public class TwitterDaoTest {

  TwitterDao twitterDao;
  private TwitterHttpHelper httpHelper;

  @Before
  public void setUp() throws Exception {

    TwitterHttpHelper httpHelper =
        new TwitterHttpHelper(System.getenv("consumerKey"), System.getenv("consumerSecret"),
            System.getenv("accessToken"), System.getenv("tokenSecret"));

    twitterDao = new TwitterDao(httpHelper);

  }

  @Test
  public void create() {
    Tweet regularTweet = TweetBuilder.TweetBuild("Wheres%20Johnny....%23lol", 165.234f, 65.2391f);

    Tweet newTweet = twitterDao.create(regularTweet);

    assertNotNull(regularTweet);

    assertEquals("Wheres Johnny....#lol", newTweet.getText());
    assertEquals(2, newTweet.getCoordinates().getCoordinates().length);
    assertEquals(165.234f, newTweet.getCoordinates().getCoordinates()[0], 0.0001);
    assertEquals(65.2391f, newTweet.getCoordinates().getCoordinates()[1], 0.0001);
    assertEquals("lol", newTweet.getEntities().getHashtags()[0].getText());

  }

  @Test
  public void findById() {

    Tweet newTweet = twitterDao.findById("1356702014982545413");

    assertEquals("Wheres Josephine....#lol", newTweet.getText());
    assertEquals("Tue Feb 02 20:32:21 +0000 2021", newTweet.getCreated_at());
    assertEquals(2, newTweet.getCoordinates().getCoordinates().length);
    assertEquals(165.234f, newTweet.getCoordinates().getCoordinates()[0], 0.0001);
    assertEquals(65.2391f, newTweet.getCoordinates().getCoordinates()[1], 0.0001);
    assertEquals("lol", newTweet.getEntities().getHashtags()[0].getText());
    assertEquals("1356702014982545413", newTweet.getId_str());
    assertEquals(20, newTweet.getEntities().getHashtags()[0].getIndices()[0]);
    assertEquals(24, newTweet.getEntities().getHashtags()[0].getIndices()[1]);

  }

  @Test
  public void deleteById() {

    Tweet deleteTweet = twitterDao.deleteById("1356702014982545413");

    assertEquals("Wheres Josephine....#lol", deleteTweet.getText());
    assertEquals("Tue Feb 02 20:32:21 +0000 2021", deleteTweet.getCreated_at           ());
    assertEquals(2, deleteTweet.getCoordinates().getCoordinates().length);
    assertEquals(165.234f, deleteTweet.getCoordinates().getCoordinates()[0], 0.0001);
    assertEquals(65.2391f, deleteTweet.getCoordinates().getCoordinates()[1], 0.0001);
    assertEquals("lol", deleteTweet.getEntities().getHashtags()[0].getText());
    assertEquals("1356702014982545413", deleteTweet.getId_str());
    assertEquals(20, deleteTweet.getEntities().getHashtags()[0].getIndices()[0]);
    assertEquals(24, deleteTweet.getEntities().getHashtags()[0].getIndices()[1]);

  }
}