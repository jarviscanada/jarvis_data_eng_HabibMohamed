# Introduction

The Java Grep application is meant to simulate the functionality of the Linux command grep. It will 
recursively search the given directory for files, and then search each file for lines that match
the given regular expression. The results are then output to the given file. This project was mainly
coded in Java 8, with Maven being used for dependency management. The application largely made use
of streams and lambda functions, as well as Paths and Files objects from the java.nio library.
The IDE used to work on this project was IntelliJ Ultimate, and the project was deployed via Docker.

# Quick Start 
There are 2 ways to use this application.

1) Classpath/Class Files
 
```bash
mvn clean compile

java -cp target.classes ca.jrvs.apps.grep.JavaGrepMain regex rootPath outFile

``` 

2) Jar File

```bash
mvn clean compile

java -cp target/grep.*.jar ca.jrvs.apps.greps.JavaGrepMain regex rootPath outFile
```

3) Docker

```bash
docker run grep regex rootPath outFile
```

# Implementation

## Pseudocode

```java
start method process
	listFiles on directory rootPath
		for each file readLines
			if line containsPattern(regex) writeToFile(line) 
```

## Performance Issue

This project initially made use of Lists as a way to store data, but due to running out of
heap space for low memory JVMs, the project was refactored to utilize Streams exclusively to
move data around.

# Testing

The testing of this program largely consisted of functional and performance testing. The functions
were tested in terms of providing manual test cases. Performance was tested by making sure the
application could run successfully on small heap sizes of around 5 MB.

# Deployment

The application was made into an image via a Dockerfile and then pushed to DockerHub for
public access.

# Improvement

1) Give users an option to provide character encoding they want
2) Add functionality to search for specific files instead of file content
3) Incorporate more modern functionality for file writing, like Files.write
