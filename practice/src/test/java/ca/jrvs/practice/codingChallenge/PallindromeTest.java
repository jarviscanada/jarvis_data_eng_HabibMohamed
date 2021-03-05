package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PallindromeTest {

  private Pallindrome pallindrome;

  @Before
  public void setUp(){
    pallindrome = new Pallindrome();
  }

  @Test
  public void isPallindrome(){

    String s = "A man, a plan, a canal: Panama";

    assertTrue(pallindrome.isPallindrome(s));

  }

}
