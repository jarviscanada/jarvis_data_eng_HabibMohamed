package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TwoSumTest {

  private TwoSum twoSum;

  @Before
  public void setUp() throws Exception {

    twoSum = new TwoSum();

  }

  @Test
  public void getTwoSum() {

    int[] checkArray1 = {0,1};
    int[] checkArray2 = {0,5};
    int[] inputArray1 = {2,7,11,15};
    int[] inputArray2 = {3,5,7,9,10,16,23};

    assertArrayEquals(checkArray1, twoSum.getTwoSum(inputArray1, 9));
    assertArrayEquals(checkArray2, twoSum.getTwoSum(inputArray2, 19));


  }
}