package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class FindMaxMinTest {

  FindMaxMin findMaxMin;

  @Before
  public void setUp() throws Exception {

    findMaxMin = new FindMaxMin();

  }

  @Test
  public void findStats() {

    Integer[] arr = {3,5,6,7,2,5,78,99,1};
    List<Integer> ints = new ArrayList<Integer>(Arrays.asList(arr));

    assertEquals(1, findMaxMin.findMin(ints));
    assertEquals(99, findMaxMin.findMax(ints));

  }

}