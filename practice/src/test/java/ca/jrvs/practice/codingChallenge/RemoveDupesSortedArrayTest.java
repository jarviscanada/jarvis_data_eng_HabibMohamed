package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class RemoveDupesSortedArrayTest {

  RemoveDupesSortedArray dupesSortedArray;

  @Before
  public void setUp() throws Exception {
    dupesSortedArray = new RemoveDupesSortedArray();
  }

  @Test
  public void removeDupes() {
    int[] nums = {0,1,1,1,2,3,4,5,6};

    assertEquals(7, dupesSortedArray.removeDupes(nums));
  }
}