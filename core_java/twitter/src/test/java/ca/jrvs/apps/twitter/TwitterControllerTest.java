package ca.jrvs.apps.twitter;

import static org.junit.Assert.*;

import ca.jrvs.apps.twitter.controller.Controller;
import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.impl.TwitterController;
import ca.jrvs.apps.twitter.impl.TwitterDao;
import ca.jrvs.apps.twitter.impl.TwitterHttpHelper;
import ca.jrvs.apps.twitter.impl.TwitterService;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import com.google.gdata.util.common.base.PercentEscaper;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class TwitterControllerTest {

  private Controller twitterController;
  private Service twitterService;
  private CrdDao dao;
  private HttpHelper httpHelper;
  private PercentEscaper percentEscaper = new PercentEscaper("", false);

  @Before
  public void setUp() throws Exception {

    httpHelper = new TwitterHttpHelper(System.getenv("consumerKey"), System.getenv("consumerSecret"),
        System.getenv("accessToken"), System.getenv("tokenSecret"));
    dao = new TwitterDao(httpHelper);
    twitterService = new TwitterService(dao);
    twitterController = new TwitterController(twitterService);

  }

  @Test
  public void postTweet() {

    String text = "just do it! #niketing";

    String args[] = new String[]{"post", percentEscaper.escape(text), "45.67:87.21"};

    Tweet theTweet = twitterController.postTweet(args);

    assertEquals(theTweet.getText(), text);
    assertEquals(theTweet.getCoordinates().getCoordinates()[0], 45.67f, 0.01);
    assertEquals(theTweet.getCoordinates().getCoordinates()[1], 87.21f, 0.01);

  }

  @Test
  public void showTweet() {

    String id = "1357058921865900034";

    String args[] = new String[]{"show", id};

    Tweet aTweet = twitterController.showTweet(args);

    assertEquals(aTweet.getText(), "just do it! #niketing");
    assertEquals(aTweet.getCoordinates().getCoordinates()[0], 45.67f, 0.01);
    assertEquals(aTweet.getCoordinates().getCoordinates()[1], 87.21f, 0.01);
    assertEquals(aTweet.getEntities().getHashtags()[0].getText(), "niketing");

  }

  @Test
  public void deleteTweet() {

    String id1 = "1357058921865900034";
    String id2 = "1357032762105077765";

    String args[] = new String[]{"delete", id1+","+id2};

    List<Tweet> tweets = twitterController.deleteTweet(args);

    Tweet tweet1 = tweets.get(0);
    Tweet tweet2 = tweets.get(1);

    assertEquals(tweet1.getText(), "just do it! #niketing");
    assertEquals(tweet1.getCoordinates().getCoordinates()[0], 45.67f, 0.01);
    assertEquals(tweet1.getCoordinates().getCoordinates()[1], 87.21f, 0.01);
    assertEquals(tweet1.getEntities().getHashtags()[0].getText(), "niketing");

    assertEquals(tweet2.getText(), "Howdy yall#goodnight");
    assertEquals(tweet2.getCoordinates().getCoordinates()[0], 100.23f, 0.01);
    assertEquals(tweet2.getCoordinates().getCoordinates()[1], -82.456f, 0.01);
    assertEquals(tweet2.getCreated_at(), "Wed Feb 03 18:24:26 +0000 2021");

  }
}