package ca.jrvs.apps.twitter.impl;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import java.io.IOException;
import java.net.URI;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TwitterHttpHelper implements HttpHelper {

  private OAuthConsumer consumer;
  private HttpClient httpClient;
  final Logger logger = LoggerFactory.getLogger(TwitterHttpHelper.class);

  /**
   * Constructor sets up OAuthConsumer object with keys and secrets, along with HTTP client
   *
   * @param consumerKey
   * @param consumerSecret
   * @param accessToken
   * @param tokenSecret
   */
  public TwitterHttpHelper(String consumerKey, String consumerSecret, String accessToken, String tokenSecret){

    consumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);
    consumer.setTokenWithSecret(accessToken, tokenSecret);

    httpClient = HttpClientBuilder.create().build();

  }

  /**
   * Default constructor, does similar work as the one above.
   */
  public TwitterHttpHelper(){

    String consumerKey = System.getenv("consumerKey");
    String consumerSecret = System.getenv("consumerSecret");
    String accessToken = System.getenv("accessToken");
    String tokenSecret = System.getenv("tokenSecret");

    consumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);
    consumer.setTokenWithSecret(accessToken, tokenSecret);

    httpClient = HttpClientBuilder.create().build();

  }

  /**
   * Signs a request with Authorization header containing
   * Twitter API keys and secrets
   *
   * @param req
   */
  private void authorize(Object req){

    try {
      consumer.sign(req);
    }catch (OAuthMessageSignerException e){
      logger.error("Something went wrong with the signature", e);
    }catch(OAuthExpectationFailedException e){
      logger.error("Something went wrong with HTTP header Expect", e);
    }catch(OAuthCommunicationException e){
      logger.error("Something went wrong, most likely a SSL certificate issue", e);
    }

  }

  @Override
  public HttpResponse httpPost(URI uri) throws IOException{

    HttpPost request = new HttpPost(uri);

    authorize(request);

    return httpClient.execute(request);

  }

  @Override
  public HttpResponse httpGet(URI uri) throws IOException{

    HttpGet request = new HttpGet(uri);

    authorize(request);

    return httpClient.execute(request);
  }
}
