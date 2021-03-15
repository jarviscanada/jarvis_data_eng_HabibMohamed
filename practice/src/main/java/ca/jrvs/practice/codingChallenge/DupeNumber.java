package ca.jrvs.practice.codingChallenge;

import java.util.HashSet;
import java.util.Set;

/**
 * Finds the dupe in a list of numbers.
 * Takes O(n) time as the dupe could be at the
 * end of the list.
 */
public class DupeNumber {

  public int findDupeNumber(int[] ints){

    Set<Integer> numbers = new HashSet<Integer>();

    for (int i : ints){
      if(numbers.contains(i)){
        return i;
      }else{
        numbers.add(i);
      }
    }
    return -1;

  }

}
