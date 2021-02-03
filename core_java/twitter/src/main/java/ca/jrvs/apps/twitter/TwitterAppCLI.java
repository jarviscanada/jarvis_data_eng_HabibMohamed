package ca.jrvs.apps.twitter;

import ca.jrvs.apps.twitter.controller.Controller;
import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import java.util.List;

public class TwitterAppCLI {

  private static HttpHelper httpHelper;
  private static CrdDao dao;
  private static Service service;
  private static Controller controller;


  public static void run(String[] args){

    if (args[0].equalsIgnoreCase("post")){

      Tweet postedTweet = controller.postTweet(args);
      System.out.println(postedTweet.toString());

    } else if (args[0].equalsIgnoreCase("show")){

      Tweet fetchedTweet = controller.showTweet(args);
      System.out.println(fetchedTweet.toString());

    } else if (args[0].equalsIgnoreCase("delete")){

      List<Tweet> deletedTweets = controller.deleteTweet(args);

      deletedTweets.stream()
          .map(tweet -> tweet.toString())
          .forEach(System.out::println);

    }else{

      throw new IllegalArgumentException("TwitterAppCLI Usage:\n"
          + "TwitterAppCLI post \"text\" \"longitude:latitude\""
          + "TwitterAppCLI show \"id\""
          + "TwitterAppCLI delete \"id\",...,\"id\"");

    }

  }

  public static void main(String[] args) {

    httpHelper = new TwitterHttpHelper(System.getenv("consumerKey"), System.getenv("consumerSecret"),
        System.getenv("accessToken"), System.getenv("tokenSecret"));

    dao = new TwitterDao(httpHelper);

    service = new TwitterService(dao);

    controller = new TwitterController(service);

    run(args);

  }

}
