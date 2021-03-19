package ca.jrvs.practice.codingChallenge;

/**
 * Uses bitwise XOR (exclusive or) operator
 * to switch the values of two variables.
 * Takes O(1) time as the operations are done
 * in trivial time.
 */
public class SwapNumber {

  public int[] swapNumbers(int[] nums){

    nums[0] ^= nums[1];
    nums[1] ^= nums[0];
    nums[0] ^= nums[1];

    return nums;

  }

}
