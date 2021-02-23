package ca.jrvs.practice.codingChallenge;

public class EvenOrOdd {

  /**
   * This checks whether a number is even or odd. Return a string appropriately.
   *
   * This method uses the modulo operation to determine evenness/oddness.
   * This evaluates in O(1) time, due to modulo being an arithmetic expression.
   *
   * @param number
   * @return "even" or "odd"
   */
  public String isEvenOrOdd(int number){

    if(number % 2 == 0) return "even";

    return "odd";

  }

}
