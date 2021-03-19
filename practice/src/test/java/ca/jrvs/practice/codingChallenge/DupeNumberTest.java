package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class DupeNumberTest {

  DupeNumber dupeNumber;

  @Before
  public void setUp() throws Exception {
    dupeNumber = new DupeNumber();
  }

  @Test
  public void findDupeNumber() {
    int[] nums = {2,3,7,81,43,23,3,6,9,0,11};

    assertEquals(3, dupeNumber.findDupeNumber(nums));
  }
}