package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class StringIsDigitCheckTest {

  StringIsDigitCheck digitCheck;

  @Before
  public void setUp() throws Exception {
    digitCheck = new StringIsDigitCheck();
  }

  @Test
  public void isDigit() {

    String line1 = "34677";
    String line2 = ".234";
    String line3 = "-456";
    String line4 = "34,232";
    String line5 = "p23op";

    assertTrue(digitCheck.isDigit(line1));
    assertFalse(digitCheck.isDigit(line2));
    assertFalse(digitCheck.isDigit(line3));
    assertFalse(digitCheck.isDigit(line4));
    assertFalse(digitCheck.isDigit(line5));

  }
}