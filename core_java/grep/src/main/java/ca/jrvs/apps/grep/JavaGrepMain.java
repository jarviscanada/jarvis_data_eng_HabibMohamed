package ca.jrvs.apps.grep;

import java.nio.file.Files;
import java.nio.file.Paths;
import org.apache.log4j.BasicConfigurator;

public class JavaGrepMain {

  public static void main(String[] args) {

    if (args.length != 3) {
      throw new IllegalArgumentException(
          "To use this application, enter: JavaGrep regex rootPath outFile");
    }

    if (!Files.exists(Paths.get(args[1]))){
      throw new IllegalArgumentException("Please enter an existing directory");
    }

    BasicConfigurator.configure();

    JavaGrepImp javaGrepImplement = new JavaGrepImp();
    javaGrepImplement.setRegex(args[0]);
    javaGrepImplement.setRootPath(args[1]);
    javaGrepImplement.setOutFile(args[2]);

    try {
      javaGrepImplement.process();
    } catch (Exception e) {
      javaGrepImplement.logger.error(e.getMessage(), e);
    }

  }

}
