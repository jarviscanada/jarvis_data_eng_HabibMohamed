package ca.jrvs.apps.twitter;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import ca.jrvs.apps.twitter.impl.TwitterController;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import ca.jrvs.apps.twitter.util.TweetBuilder;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TwitterControllerUnitTest {

  @Mock
  Service service;

  @InjectMocks
  TwitterController controller;

  @Test
  public void postTweet() {

    when(service.postTweet(any())).thenReturn(TweetBuilder.TweetBuild("unittest" , 179f, 89f));

    try{

      controller.postTweet(new String[]{"0"});
      fail();

    } catch (IllegalArgumentException e){
      assertTrue(true);
    }

    try{

      controller.postTweet(new String[]{"post", "", "0"});
      fail();

    } catch (IllegalArgumentException e){
      assertTrue(true);
    }

    try{

      controller.postTweet(new String[]{"post", "jeff man", "fg:gf"});
      fail();

    } catch (IllegalArgumentException e){
      assertTrue(true);
    }

    Tweet postedTweet = controller.postTweet(new String[]{"post", "unittest", "179:89"});

    assertEquals(postedTweet.getText(), "unittest");
    assertEquals(postedTweet.getCoordinates().getCoordinates()[0], 179f, 0.001);
    assertEquals(postedTweet.getCoordinates().getCoordinates()[1], 89, 0.001);

  }

  @Test
  public void showTweet() {

    when(service.showTweet(any(), any())).thenReturn(TweetBuilder.TweetBuild("unittest" , 179f, 89f));

    try{

      controller.showTweet(new String[]{"1"});
      fail();

    } catch (IllegalArgumentException e){
      assertTrue(true);
    }

    try{

      controller.showTweet(new String[]{"show", ""});
      fail();

    } catch (IllegalArgumentException e){
      assertTrue(true);
    }


    Tweet postedTweet = controller.showTweet(new String[]{"show", "172237387492368836"});

    assertEquals(postedTweet.getText(), "unittest");
    assertEquals(postedTweet.getCoordinates().getCoordinates()[0], 179f, 0.001);
    assertEquals(postedTweet.getCoordinates().getCoordinates()[1], 89, 0.001);

  }

  @Test
  public void deleteTweet() {

    List<Tweet> tweets = new ArrayList<Tweet>();
    tweets.add(TweetBuilder.TweetBuild("unittest" , 179f, 89f));
    tweets.add(TweetBuilder.TweetBuild("unittest2" , -179f, -89f));

    when(service.deleteTweets(any())).thenReturn(tweets);

    try{

      controller.deleteTweet(new String[]{"21"});
      fail();

    } catch (IllegalArgumentException e){
      assertTrue(true);
    }

    try{

      controller.deleteTweet(new String[]{"delete", ""});
      fail();

    } catch (IllegalArgumentException e){
      assertTrue(true);
    }


    List<Tweet> deletedTweets = controller.deleteTweet(new String[]{"show", "172237387492368836,625187291862128"});

    assertEquals(deletedTweets.get(0).getText(), tweets.get(0).getText());
    assertEquals(deletedTweets.get(0).getCoordinates().getCoordinates()[0],
        tweets.get(0).getCoordinates().getCoordinates()[0], 0.001);
    assertEquals(deletedTweets.get(0).getCoordinates().getCoordinates()[1],
        tweets.get(0).getCoordinates().getCoordinates()[1], 0.001);

    assertEquals(deletedTweets.get(1).getText(), tweets.get(1).getText());
    assertEquals(deletedTweets.get(1).getCoordinates().getCoordinates()[1],
        tweets.get(1).getCoordinates().getCoordinates()[1], 0.001);
    assertEquals(deletedTweets.get(1).getCoordinates().getCoordinates()[1],
        tweets.get(1).getCoordinates().getCoordinates()[1], 0.001);

  }
}