package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ContainsDupeNumberTest {

  ContainsDupeNumber containsDupeNumber;

  @Before
  public void setUp() throws Exception {
    containsDupeNumber = new ContainsDupeNumber();
  }

  @Test
  public void containsDuplicate() {

    int[] nums = {1,2,3,6,7,8,9,3,4,2,1};

    assertTrue(containsDupeNumber.containsDuplicate(nums));

  }
}