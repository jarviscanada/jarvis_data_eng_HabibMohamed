package ca.jrvs.apps.twitter.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Arrays;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Entities {

  private Hashtag[] hashtags;
  private UserMentions[] user_mentions;

  public Hashtag[] getHashtags() {
    return hashtags;
  }

  public void setHashtags(Hashtag[] hashtags) {
    this.hashtags = hashtags;
  }

  public UserMentions[] getUser_mentions() {
    return user_mentions;
  }

  public void setUser_mentions(UserMentions[] userMentions) {
    this.user_mentions = userMentions;
  }

  @Override
  public String toString() {
    return "{"
        + "                        \"hashtags\":" + Arrays.toString(hashtags)
        + ",                         \"user_mentions\":" + Arrays.toString(user_mentions)
        + "}";
  }
}
