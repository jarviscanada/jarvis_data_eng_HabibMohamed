package ca.jrvs.practice.codingChallenge;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Finds all the duplicate characters in a string.
 * This takes O(n) time, as the string is iterated through
 * the entirety of its length.
 */
public class DupeChar {

  public Set<Character> dupeCharacters(String str){

    HashMap<Character, Integer> charCount = new HashMap<Character, Integer>();
    char[] chars = str.toLowerCase().replace(" ", "").toCharArray();
    Set<Character> dupes = new HashSet<Character>();

    for(char c : chars){

      if(!charCount.containsKey(c)){
        charCount.put(c, 1);
      }else{
        charCount.put(c, charCount.get(c)+1);
      }
    }

    for(Character c : charCount.keySet()){
      if(charCount.get(c) > 1){
        dupes.add(c);
      }
    }

    return dupes;

  }

}
