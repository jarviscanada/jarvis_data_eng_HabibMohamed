package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class FibonnaciTest {

  Fibonnaci fibonnaci;

  @Before
  public void setUp() throws Exception {

   fibonnaci = new Fibonnaci();

  }

  @Test
  public void fibonnaciSequence() {

    assertEquals(1, fibonnaci.FibonnaciSequence(1));
    assertEquals(2, fibonnaci.FibonnaciSequence(3));
    assertEquals(3, fibonnaci.FibonnaciSequence(4));

  }

  @Test
  public void climbingStairs(){

    assertEquals(2, fibonnaci.climbingStairs(2));
    assertEquals(3, fibonnaci.climbingStairs(3));

  }

}