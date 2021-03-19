package ca.jrvs.practice.codingChallenge;

/**
 * Goes through a string of letters and assigns
 * numbers next to them based on their position
 * in the alphabet.
 * Takes O(n) time due to traversing through the string.
 */
public class LetterNumber {

  public String letterWithNumbers(String original){

    StringBuilder sb = new StringBuilder();

    char[] chars = original.toCharArray();

    for(char c : chars){

      int position = 0;

      if(c > 64 && c < 91){
        position = c - 64 + 26;
      }else{
        position = c - 96;
      }

      sb.append(c);
      sb.append(String.valueOf(position));

    }

    return sb.toString();

  }

}
