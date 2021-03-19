package ca.jrvs.practice.codingChallenge;

/**
 * Finds the missing number within an array.
 * Takes O(n) time, with n being the range,
 * as it is necessary to traverse through the
 * array.
 */
public class MissingNumber {

  public int missingNumber(int[] nums) {

    int range = nums.length + 1;

    int[] trueRange = new int[range];

    int missing = 0;

    for (int i : nums) {

      trueRange[i] = i;

    }

    for (int i = 0; i < trueRange.length; i++) {
      if (trueRange[i] == 0 && i != 0) {
        missing = i;
        break;
      }
    }

    return missing;

  }

}
