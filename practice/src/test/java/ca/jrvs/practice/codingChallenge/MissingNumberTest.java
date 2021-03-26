package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MissingNumberTest {

  MissingNumber missingNumber;

  @Before
  public void setUp() throws Exception {
    missingNumber = new MissingNumber();
  }

  @Test
  public void missingNumber() {

    int[] nums = {4,5,7,2,0,1,6};

    assertEquals(3, missingNumber.missingNumber(nums));

  }
}