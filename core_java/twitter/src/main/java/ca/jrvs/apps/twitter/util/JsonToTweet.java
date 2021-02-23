package ca.jrvs.apps.twitter.util;

import ca.jrvs.apps.twitter.model.Tweet;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class JsonToTweet {

  /**
   *
   * Takes a JSON string, ideally from a HTTP response
   * from Twitter API, and maps the fields to the getters
   * defined in class Tweet, generating a Tweet object.
   *
   * @param response
   * @param clazz
   * @return Tweet tweet
   * @throws IOException
   */
  public static Tweet fromJsonToModel(String response, Class clazz) throws IOException {

    ObjectMapper mapper = new ObjectMapper();

    mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    return (Tweet) mapper.readValue(response,clazz);

  }

}
