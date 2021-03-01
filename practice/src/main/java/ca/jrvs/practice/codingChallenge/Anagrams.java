package ca.jrvs.practice.codingChallenge;

import java.util.HashMap;
import java.util.Map;

public class Anagrams {

  /**
   * Checks if two Strings are anagrams
   *
   * Takes O(n) time, with n being the length
   * of the strings. This is due to the loop going
   * through the whole string to keep track of the
   * number of occurrences for each character.
   *
   * @param s
   * @param t
   * @return true if anagram, false otherwise
   */
  public boolean isAnagram(String s, String t){

    if(s.length() != t.length()){
      return false;
    }

    Map<Character, Integer> sChar = new HashMap<Character, Integer>();
    Map<Character, Integer> tChar = new HashMap<Character, Integer>();

    for(int i = 0; i < s.length(); i++){

      char sCh = s.charAt(i);
      char tCh = t.charAt(i);

      updateDict(sChar, sCh);
      updateDict(tChar, tCh);

    }

    if(sChar.equals(tChar)){
      return true;
    }
    return false;
  }

  private void updateDict(Map<Character, Integer> map, char theChar){

    if(map.containsKey(theChar)){

      int currentVal = map.get(theChar);
      map.put(theChar, currentVal+1);

    }else{
      map.put(theChar, 1);
    }

  }

}
