package ca.jrvs.apps.twitter.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Arrays;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserMentions {

  private long id;
  private String id_str;
  private int[] indices;
  private String name;
  private String screen_name;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getId_str() {
    return id_str;
  }

  public void setId_str(String id_str) {
    this.id_str = id_str;
  }

  public int[] getIndices() {
    return indices;
  }

  public void setIndices(int[] indices) {
    this.indices = indices;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getScreen_name() {
    return screen_name;
  }

  public void setScreen_name(String screen_name) {
    this.screen_name = screen_name;
  }

  @Override
  public String toString() {
    return "UserMentions{" +
        "id=" + id +
        ", id_str='" + id_str + '\'' +
        ", indices=" + Arrays.toString(indices) +
        ", name='" + name + '\'' +
        ", screen_name='" + screen_name + '\'' +
        '}';
  }
}

