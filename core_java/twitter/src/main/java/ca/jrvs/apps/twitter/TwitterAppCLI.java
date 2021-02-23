package ca.jrvs.apps.twitter;

import ca.jrvs.apps.twitter.controller.Controller;
import ca.jrvs.apps.twitter.model.Tweet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TwitterAppCLI {

  private Controller controller;

  @Autowired
  public TwitterAppCLI(Controller controller){ this.controller = controller; }


  public void run(String[] args){

    //Pseudo menu control based on first argument passed in
    if (args[0].equalsIgnoreCase("post")){

      Tweet postedTweet = controller.postTweet(args);
      System.out.println(postedTweet.toString());

    } else if (args[0].equalsIgnoreCase("show")){

      Tweet fetchedTweet = controller.showTweet(args);
      System.out.println(fetchedTweet.getSpecializedMessage());

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

}
