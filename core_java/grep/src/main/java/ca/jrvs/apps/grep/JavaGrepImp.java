package ca.jrvs.apps.grep;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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

    List<String> matchedLines = new ArrayList<String>();

    List<File> allTheFiles = listFiles(rootPath);

    //Goes through each file in root directory, grabs their lines, and checks for pattern match
    for (File aFile : allTheFiles) {

      List<String> linesOfAFile = readLines(aFile);

      linesOfAFile
          .stream()
          .filter(f -> containsPattern(f))
          .forEach(matchedLines::add);

    }

    //All matches are written to result file
    writeToFile(matchedLines);

  }

  @Override
  public List<File> listFiles(String rootDir) throws IOException {

    //Set up the List and path respectively
    List<File> files;
    Path path = Paths.get(rootDir);

    //Files.walk will check at practically all directory level below the given directory
    try (Stream<Path> walk = Files.walk(path)) {

      files = walk.filter(Files::isRegularFile)
          .map(f -> f.toFile())
          .collect(Collectors.toList());

    }

    return files;
  }

  @Override
  public List<String> readLines(File inputFile) throws IOException {

    //Checking if file is valid
    if (!inputFile.isFile()) {
      throw new IllegalArgumentException("This is not a file");
    }

    List<String> fileLines;

    //File lines are read and collected into a List
    try (BufferedReader bfread = Files.newBufferedReader(inputFile.toPath())) {

      fileLines = bfread.lines()
          .collect(Collectors.toList());

    }

    return fileLines;

  }

  @Override
  public boolean containsPattern(String line) {
    return line.matches(regex); //Simple regex matching
  }

  @Override
  public void writeToFile(List<String> lines) throws IOException {

    Files.write(Paths.get(outFile), lines); //Simple file writing of the results

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
