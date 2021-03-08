package ca.jrvs.practice.codingChallenge;

import java.util.Collections;
import java.util.List;

/**
 * Finds maximum and minimum in List
 * Takes O(n) time, as one traversal through
 * List is all it takes to find them.
 */
public class FindMaxMin {

  public int findMax(List<Integer> integers){
    return Collections.max(integers);
  }

  public int findMin(List<Integer> integers){
    return Collections.min(integers);
  }

}
