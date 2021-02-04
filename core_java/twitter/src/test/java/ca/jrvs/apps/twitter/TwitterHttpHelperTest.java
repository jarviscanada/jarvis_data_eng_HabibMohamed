package ca.jrvs.apps.twitter;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.http.util.EntityUtils;
import org.junit.Before;
import org.junit.Test;

public class TwitterHttpHelperTest {

  private TwitterHttpHelper httpHelper;

  @Before
  public void setUp() throws Exception {

    TwitterHttpHelper httpHelper =
        new TwitterHttpHelper(System.getenv("consumerKey"), System.getenv("consumerSecret"),
            System.getenv("accessToken"), System.getenv("tokenSecret"));

  }

  @Test
  public void httpPost() {

    try{
      URI postURI = new URI("https://api.twitter.com/1.1/statuses/update.json?status=Howdy");
      System.out.println(httpHelper.httpPost(postURI));
    }catch(URISyntaxException | IOException e){

    }

  }

  @Test
  public void httpGet() {

    try{
      URI getURI = new URI("https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=HabibMo26619210");
      System.out.println(EntityUtils.toString(httpHelper.httpGet(getURI).getEntity()));
    }catch(URISyntaxException | IOException e){

    }

  }
}