package ca.jrvs.apps.grep;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaGrepImp implements JavaGrep {

  final Logger logger = LoggerFactory.getLogger(JavaGrep.class);

  private String regex;
  private String rootPath;
  private String outFile;

  @Override
  public void process() throws IOException {

    Stream<Path> allTheFiles = listFiles(rootPath);

    //Goes through each file in root directory, grabs their lines, and checks for pattern match
    allTheFiles.forEach(aPath ->
        {
          try {
            readLines(aPath.toFile())
                .filter(f -> containsPattern(f))
                .forEach(f -> {
                      try {
                        writeToFile(f);
                      } catch (IOException e) {
                        logger.error("Error with file writing", e);
                      }
                    }
                );//All matches are written to result file
          } catch (IOException e) {
            logger.error("Error with line reading", e);
          }
        }
    );

  }

  @Override
  public Stream<Path> listFiles(String rootDir) throws IOException {

    //Set up the stream and path respectively
    Path path = Paths.get(rootDir);

    //Files.walk will check at practically all directory level below the given directory
    Stream<Path> walk = Files.walk(path);

    return walk.filter(Files::isRegularFile);
    
  }


  @Override
  public Stream<String> readLines(File inputFile) throws IOException {

    //Checking if file is valid
    if (!inputFile.isFile()) {
      throw new IllegalArgumentException("This is not a file");
    }

    Stream<String> fileLines;

    //File lines are read and collected into a Stream
    BufferedReader bfread = Files.newBufferedReader(inputFile.toPath());
    fileLines = bfread.lines();

    return fileLines;

  }

  @Override
  public boolean containsPattern(String line) {
    return line.matches(regex); //Simple regex matching
  }

  @Override

  public void writeToFile(String lines) throws IOException {

    //Simple file writing of the results
    File file = new File(outFile);
    FileWriter fw = new FileWriter(file, true);
    fw.write(lines + "\n");
    fw.close();

  }

  @Override
  public String getRegex() {
    return regex;
  }

  @Override
  public void setRegex(String regex) {
    this.regex = regex;
  }

  @Override
  public String getRootPath() {
    return rootPath;
  }

  @Override
  public void setRootPath(String rootPath) {
    this.rootPath = rootPath;
  }

  @Override
  public String getOutFile() {
    return outFile;
  }

  @Override
  public void setOutFile(String outFile) {
    this.outFile = outFile;
  }

}
