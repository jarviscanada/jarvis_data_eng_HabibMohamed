package ca.jrvs.apps.twitter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.impl.TwitterService;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.TweetBuilder;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TwitterServiceUnitTest {

  @Mock
  CrdDao dao;

  @InjectMocks
  TwitterService service;

  @Test
  public void postTweet(){

    when(dao.create(any())).thenReturn(TweetBuilder.TweetBuild("hiya", 50.0f, 60.02f));

    try {

      service.postTweet(TweetBuilder.TweetBuild("yoyo", 190.5f, 987.2f));
      fail();

    }catch (IllegalArgumentException e){
      assertTrue(true);
    }

    Tweet newTweet = service.postTweet(TweetBuilder.TweetBuild("hiya", 50.0f, 60.02f));

    assertEquals("hiya", newTweet.getText());
    assertEquals(50.0f, newTweet.getCoordinates().getCoordinates()[0], 0.0001);
    assertEquals(60.02f, newTweet.getCoordinates().getCoordinates()[1], 0.0001);

  }

  @Test
  public void showTweet(){

    when(dao.findById(any())).thenReturn(TweetBuilder.TweetBuild("hiya", 50.0f, 60.02f));

    try {

      service.showTweet("1234567890123456789g", null);
      fail();

    }catch (IllegalArgumentException e){

      assertTrue(true);

    }

    Tweet newTweet = service.showTweet("12345678901234567890", null);

    assertEquals("hiya", newTweet.getText());
    assertEquals(50.0f, newTweet.getCoordinates().getCoordinates()[0], 0.0001);
    assertEquals(60.02f, newTweet.getCoordinates().getCoordinates()[1], 0.0001);

  }

  @Test
  public void deleteTweet(){

    when(dao.deleteById(any())).thenReturn(TweetBuilder.TweetBuild("hiya", 50.0f, 60.02f));

    String[] ids = new String[]{"1r34s67l9012345e789g", "12345678901234567890"};
    String[] ids2 = new String[]{"12345678901234567890"};

    try {

      service.deleteTweets(ids);
      fail();

    }catch (IllegalArgumentException e){

      assertTrue(true);

    }

    List<Tweet> newTweets = service.deleteTweets(ids2);
    Tweet newTweet = newTweets.get(0);

    assertEquals("hiya", newTweet.getText());
    assertEquals(50.0f, newTweet.getCoordinates().getCoordinates()[0], 0.0001);
    assertEquals(60.02f, newTweet.getCoordinates().getCoordinates()[1], 0.0001);

  }

}
