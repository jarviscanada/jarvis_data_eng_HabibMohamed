package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SwapNumberTest {

  SwapNumber swapNumber;

  @Before
  public void setUp() throws Exception {

    swapNumber = new SwapNumber();

  }

  @Test
  public void swapNumbers() {

    int x = 6;
    int y = 7;

    int[] nums = {x,y};

    nums = swapNumber.swapNumbers(nums);

    assertEquals(y, nums[0]);
    assertEquals(x, nums[1]);

  }
}