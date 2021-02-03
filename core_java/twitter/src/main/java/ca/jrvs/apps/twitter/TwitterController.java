package ca.jrvs.apps.twitter;

import ca.jrvs.apps.twitter.controller.Controller;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import ca.jrvs.apps.twitter.util.TweetBuilder;
import java.util.List;

public class TwitterController implements Controller {

  private final static String COORD_SEP = ":";
  private final static String COMMA = ",";

  private Service service;

  public TwitterController(Service service){this.service = service;}

  @Override
  public Tweet postTweet(String[] args) {

    if (args.length != 3){
      throw new IllegalArgumentException("Usage: TwitterAppCLI post \"text\" \"longitude:latitude\"");
    }

    String text = args[1];
    String coordinates = args[2];

    String[] coords_arr = coordinates.split(COORD_SEP);

    if (coords_arr.length != 2 || text.isEmpty()){
      throw new IllegalArgumentException("Please provide non-empty arguments.\nUsage:"
          + " TwitterAppCLI post \"text\" \"longitude:latitude\"");
    }

    float lon;
    float lat;

    try{

      lon = Float.parseFloat(coords_arr[0]);
      lat = Float.parseFloat(coords_arr[1]);

    }catch (Exception e){
      throw new IllegalArgumentException("Please provide valid coordinates.\nUsage: TwitterAppCLI post \"text\" \"longitude:latitude\"");
    }

    Tweet theTweet = TweetBuilder.TweetBuild(text, lon, lat);

    return service.postTweet(theTweet);
  }

  @Override
  public Tweet showTweet(String[] args) {

    if (args.length != 2){
      throw new IllegalArgumentException("Usage: TwitterAppCLI show \"id\"");
    }

    String id = args[1];

    if (id.isEmpty()){
      throw new IllegalArgumentException("Please provide non-empty id value.\nUsage: TwitterAppCLI show \"id\"");
    }

    return service.showTweet(id, null);
  }

  @Override
  public List<Tweet> deleteTweet(String[] args) {

    if(args.length < 2){
      throw new IllegalArgumentException("Usage: TwitterAppCLI delete \"id\",...,\"id\"");
    }

    String comma_Ids = args[1];
    String[] ids = comma_Ids.split(COMMA);

    if(ids.length < 1 || ids[0].isEmpty()){
      throw new IllegalArgumentException("Please provide a list of ids separated by commas. (min. 1)\n"
          + "Usage: TwitterAppCLI delete \"id\",...,\"id\"");
    }

    return service.deleteTweets(ids);
  }
}
