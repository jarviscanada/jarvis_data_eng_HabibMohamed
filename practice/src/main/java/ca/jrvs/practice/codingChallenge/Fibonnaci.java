package ca.jrvs.practice.codingChallenge;

import java.util.stream.IntStream;

public class Fibonnaci {

  /**
   * This gets the order-th number in the Fibonnaci Sequence
   * This takes O(n) time to execute, with n being the order
   * This is because the while loop executes roughly around the amount that order is.
   * Everything in the while loop are operations that take O(1) time.
   *
   * @param order
   * @return fib, the fibonnaci sequence number
   */
  public int FibonnaciSequence(int order){

    int first_num = 0;
    int second_num = 1;

    if (order == 0) return first_num;
    if (order == 1) return second_num;

    int incremetor = 1;
    int fib = 0;

    while (incremetor < order){

      fib = first_num + second_num;
      first_num = second_num;
      second_num = fib;

      incremetor++;

    }

    return fib;

  }

  /**
   * This is a twist on the Fibonnaci Sequence, where we find how many
   * strategies there are to climbing a given number of stairs.
   *
   * This takes O(n) time due to the while loop executing
   * the same number of times as there are stairs in the worst case.
   *
   *
   * @param stairs
   * @return num_of_strats, the number of stair-climbing strategies.
   */
  public int climbingStairs(int stairs){

    int one_step = 1;
    int two_steps = 2;

    if (stairs == 1) return one_step;
    if (stairs == 2) return two_steps;

    int incremetor = 2;
    int num_of_strats = 0;

    while (incremetor < stairs){

      num_of_strats = one_step + two_steps;
      one_step = two_steps;
      two_steps = num_of_strats;

      incremetor++;

    }

    return num_of_strats;

  }

}
