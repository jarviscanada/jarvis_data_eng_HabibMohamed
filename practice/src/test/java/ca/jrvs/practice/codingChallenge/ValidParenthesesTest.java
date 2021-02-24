package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ValidParenthesesTest {

  ValidParentheses validP;

  @Before
  public void setUp() throws Exception {

    validP = new ValidParentheses();

  }

  @Test
  public void isValid(){

    String s = "(]";

    assertFalse(validP.isValid(s));

    s = "()";

    assertTrue(validP.isValid(s));

  }
}