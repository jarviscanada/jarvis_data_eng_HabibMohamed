package ca.jrvs.apps.twitter;

import static ca.jrvs.apps.twitter.util.TweetBuilder.TweetBuild;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.TweetBuilder;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TwitterDaoUnitTest {

  @Mock
  HttpHelper mockHelper;

  @InjectMocks
  TwitterDao dao;

  private String tweet = "{\n"
      + "\"created_at\":\"Tue Feb 02 20:56:14 +0000 2021\",\n"
      + " \"id\":1356708027643092996,\n"
      + " \"id_str\":\"1356708027643092996\",\n"
      + " \"text\":\"Whats good yall #blessed\",\n"
      + " \"entities\":{\n"
      + "     \"hashtags\":[],"
      + "     \"user_mentions\":[]"
      + " },\n"
      + " \"coordinates\":{\n"
      + "     \"coordinates\":[-100.8729, 32.7621],\n \"type\":\"Point\"},\n"
      + " \"retweet_count\":0,\n"
      + " \"favorite_count\":0,\n "
      + "\"favorited\":false,\n"
      + " \"retweeted\":false\n"
      + "}";

  @Test
  public void create() throws Exception {

    String text = "Whats good yall #blessed";
    Float lon = -100.8729f;
    Float lat = 32.7621f;

    when(mockHelper.httpPost(isNotNull())).thenThrow(new RuntimeException("httpPost fail"));

    try{
      dao.create(TweetBuild(text, lon, lat));
      fail();
    } catch (RuntimeException e){
      assertTrue(true);
    }

    when(mockHelper.httpPost(isNotNull())).thenReturn(null);
    TwitterDao spyDao = Mockito.spy(dao);
    Tweet expectedTweet = spyDao.fromJsonToModel(tweet, Tweet.class);

    Tweet actualTweet = TweetBuild(text, lon, lat);

    assertEquals(expectedTweet.getText(), actualTweet.getText());
    assertEquals(expectedTweet.getCoordinates().getCoordinates()[0],
        actualTweet.getCoordinates().getCoordinates()[0], 0.0001);

  }

  @Test
  public void findById() throws  Exception{

    String id = "1356708027643092996";

    when(mockHelper.httpGet(isNotNull())).thenThrow(new RuntimeException("httpGet fail"));

    try{
      dao.findById(id);
      fail();
    }catch (RuntimeException e){
      assertTrue(true);
    }

    when(mockHelper.httpGet(isNotNull())).thenReturn(null);
    TwitterDao spyDao = Mockito.spy(dao);
    Tweet expectedTweet = spyDao.fromJsonToModel(tweet, Tweet.class);

    assertEquals(32.7621f,
        expectedTweet.getCoordinates().getCoordinates()[1], 0.0001);
    assertEquals("Point", expectedTweet.getCoordinates().getType());


  }

  @Test
  public void deleteById() throws Exception {

    String id = "1356708027643092996";

    when(mockHelper.httpPost(isNotNull())).thenThrow(new RuntimeException("httpGet fail"));

    try{
      dao.deleteById(id);
      fail();
    }catch (RuntimeException e){
      assertTrue(true);
    }

    when(mockHelper.httpPost(isNotNull())).thenReturn(null);
    TwitterDao spyDao = Mockito.spy(dao);
    Tweet expectedTweet = spyDao.fromJsonToModel(tweet, Tweet.class);

    assertEquals("1356708027643092996", expectedTweet.getId_str());

  }


}
