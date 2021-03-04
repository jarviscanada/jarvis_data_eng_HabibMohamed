package ca.jrvs.practice.codingChallenge;

/**
 * Checks if a string contains only digits.
 * Takes O(n) time, with n being the length of
 * the string. This is due to the match function
 * going through the string to see if it upholds the
 * regex rule.
 */
public class StringIsDigitCheck {

  public boolean isDigit(String str){
    return str.matches("[0-9]*");
  }

}
