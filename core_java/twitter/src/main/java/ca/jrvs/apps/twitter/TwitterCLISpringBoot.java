package ca.jrvs.apps.twitter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "ca.jrvs.apps.twitter")
public class TwitterCLISpringBoot implements CommandLineRunner {

  private TwitterAppCLI app;

  @Autowired
  public TwitterCLISpringBoot(TwitterAppCLI app) { this.app = app; }

  public static void main(String[] args) {

    if(args.length < 1){
      throw new IllegalArgumentException("TwitterAppCLI Usage:\n"
          + "TwitterAppCLI post \"text\" \"longitude:latitude\""
          + "TwitterAppCLI show \"id\""
          + "TwitterAppCLI delete \"id\",...,\"id\"");
    }

    SpringApplication app = new SpringApplication(TwitterCLISpringBoot.class);

    app.setWebApplicationType(WebApplicationType.NONE);
    app.run(args);
  }


  @Override
  public void run(String... args) throws Exception {
    app.run(args);
  }
}
