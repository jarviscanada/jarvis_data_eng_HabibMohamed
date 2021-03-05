package ca.jrvs.practice.codingChallenge;

/**
 * Checks if a given string is a pallindrome.
 *
 * This should operate at a worst case O(n),
 * with n being the length of the string.
 * This is due to the string being processed before
 * the pallindrome check happens.
 */
public class Pallindrome {

  public boolean isPallindrome(String s){

    s = s.toLowerCase().replace(" ", "");

    Character[] characters = s.chars()
        .mapToObj(i -> (char) i)
        .filter(c -> Character.isAlphabetic(c) || Character.isDigit(c))
        .toArray(Character[]::new);

    int start = 0;
    int end = characters.length - 1;

    while (start < end){

      if(characters[start] != characters[end]){
        return false;
      }

      start++;
      end--;

    }

    return true;

  }

}
