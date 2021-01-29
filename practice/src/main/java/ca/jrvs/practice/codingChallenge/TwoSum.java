package ca.jrvs.practice.codingChallenge;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TwoSum {

  /**
   * Gets a pair of numbers that sum to a given target number.
   * This should operate within O(n) time.
   * The map is creatable within O(n) time, while each number
   * is iterated through once to see if the number has its sum partner.
   * This happens in O(n) time as well.
   *
   * @param nums
   * @param target
   * @return Sum pair, [-1,-2] if nothing found.
   */
  public int[] getTwoSum(int[] nums, int target) {

    Map<Integer, Integer> numsMap = IntStream.range(0, nums.length).boxed()
        .collect(Collectors.toMap(n -> nums[n], Function.identity()));

    int[] realisticArray = Arrays.stream(nums).filter(n -> n < target).toArray();

    for (int number : realisticArray) {

      int prospectivePair = target - number;

      if (numsMap.containsKey(prospectivePair)) {
        return new int[]{numsMap.get(number), numsMap.get(prospectivePair)};
      }

    }

    return new int[]{-1, -2};

  }


}
