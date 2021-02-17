package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class AtoiTest {

  Atoi atoi;

  @Before
  public void setUp() throws Exception {
    atoi = new Atoi();
  }

  @Test
  public void stringToInt() {

    int val1 = atoi.stringToInt("-42");
    int val2 = atoi.stringToInt("34a34");
    int val3 = atoi.stringToInt("-+");

    assertEquals(-42, val1);
    assertEquals(34, val2);
    assertEquals(0, val3);

  }
}