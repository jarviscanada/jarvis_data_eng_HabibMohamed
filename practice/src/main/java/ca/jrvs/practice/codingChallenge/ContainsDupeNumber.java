package ca.jrvs.practice.codingChallenge;

import java.util.HashMap;

/**
 * Finds out if an array has duplicate values.
 * Takes O(n) time, as the worst case sees the duplicate
 * of a number occurring at the last index.
 */
public class ContainsDupeNumber {

  public boolean containsDuplicate(int[] nums){

    HashMap<Integer, Integer> numsMap = new HashMap<Integer, Integer>();

    for(int i : nums){

      if(numsMap.containsKey(i)){
        return true;
      }

      numsMap.put(i, 1);

    }

    return false;
  }

}
