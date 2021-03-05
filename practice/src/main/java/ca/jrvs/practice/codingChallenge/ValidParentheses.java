package ca.jrvs.practice.codingChallenge;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Checks if a sequence of parentheses is valid
 *
 * Takes O(n) time, with n being the number of characters in a given String.
 * This is due to the loop going through each character of the string.
 */
public class ValidParentheses {

  public boolean isValid(String s){

    Stack paraStack = new Stack<String>();

    Map<String, String> paraMap = new HashMap<String,String>();
    paraMap.put(")", "(");
    paraMap.put("}","{");
    paraMap.put("]","[");

    String[] characters = s.chars()
        .mapToObj(c -> String.valueOf((char) c))
        .toArray(String[] :: new);

    for (String chara : characters){

      if(paraMap.containsValue(chara)){
        paraStack.push(chara);
      }else{
        if(paraStack.empty() ||
            !paraStack.peek().equals(paraMap.get(chara))){
          return false;
        }else{
          if (!paraStack.empty()) {
            paraStack.pop();
          }
        }

      }

    }

    return paraStack.empty();

  }

}
