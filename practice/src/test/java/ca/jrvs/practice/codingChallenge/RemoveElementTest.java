package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class RemoveElementTest {

  RemoveElement removeElement;

  @Before
  public void setUp() throws Exception {
    removeElement = new RemoveElement();
  }

  @Test
  public void removeElement() {

    int[] nums = {0,1,2,2,3,0,4,2};

    int length = removeElement.removeElement(nums, 2);

    assertEquals(5, length);

  }
}