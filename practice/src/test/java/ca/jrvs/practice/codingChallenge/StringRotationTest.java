package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class StringRotationTest {

  StringRotation stringRotation;

  @Before
  public void setUp() {
    stringRotation = new StringRotation();
  }

  @Test
  public void rotateString() {

    String first = "jklmnop";
    String second = "mnopjkl";

    assertTrue(stringRotation.rotateString(first, second));

    first = "";
    second = "";

    assertTrue(stringRotation.rotateString(first,second));

  }
}