package ca.jrvs.practice.codingChallenge;

public class Atoi {

  /**
   * Takes in a string and returns the number it represents.
   * Integer overflow yields a return of Integer.MAX or Integer.MIN.
   * Non-number string yields a return of 0.
   *
   * This should operate within O(n) time, with n being
   * the number of characters in the string. This is due to the
   * worst case involving going through each character in the given
   * string.
   *
   * @param s
   * @return an integer value
   */
  public int stringToInt(String s) {

    Character[] chars = s.chars()
        .mapToObj(c -> (char) c)
        .toArray(Character[]::new);

    if (chars.length == 0 || isNotValidChar(chars[0])) {
      return 0;
    }

    StringBuilder stringBuilder = new StringBuilder();
    boolean gotSign = false;
    boolean noMoreSpace = false;

    for (char c : chars) {

      if (isNotValidChar(c)) {
        break;
      }

      if (Character.isSpaceChar(c)) {
        if (!noMoreSpace) {
          continue;
        } else {
          break;
        }
      }

      if (c == 45 || c == 43) {
        if (gotSign) {
          break;
        }

      }

      stringBuilder.append(c);
      noMoreSpace = true;
      gotSign = true;

    }

    String prepared_Num = stringBuilder.toString();

    try {
      int num_check = Integer.parseInt(prepared_Num);

      return num_check;

    } catch (NumberFormatException e) {

      if(!prepared_Num.matches("(\\+|-)?[0-9]+")){
        return 0;
      }

      int revised_Num = 0;

      if (prepared_Num.contains("-")) {
        revised_Num = Integer.MIN_VALUE;
      }else{
        revised_Num = Integer.MAX_VALUE;
      }

      return revised_Num;
    }

  }



  private boolean isNotValidChar(char c) {

    return (!Character.isDigit(c) &&
        !Character.isSpaceChar(c) &&
        c != '+' && c != '-');

  }

}
