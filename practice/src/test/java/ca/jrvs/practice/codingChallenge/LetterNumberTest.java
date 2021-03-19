package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class LetterNumberTest {

  LetterNumber letterNumber;

  @Before
  public void setUp() throws Exception {

    letterNumber = new LetterNumber();

  }

  @Test
  public void letterWithNumbers() {

    String line = "zAZa";

    assertEquals("z26A27Z52a1", letterNumber.letterWithNumbers(line));

  }
}