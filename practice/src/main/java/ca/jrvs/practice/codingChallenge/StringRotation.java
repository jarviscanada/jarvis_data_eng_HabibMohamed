package ca.jrvs.practice.codingChallenge;

public class StringRotation {

  /**
   * Checks if a string is a rotation of another.
   * This takes O(n) time, with n is the length of
   * the strings. This is due to the worst case rotating
   * a string n times.
   *
   * @param a
   * @param b
   * @return true if rotation, false otherwise
   */
  public boolean rotateString(String a, String b){

    if(a.length() != b.length()) {
      return false;
    }

    int length = a.length();

     do{

       if (a.compareTo(b) == 0){
         return true;
       }
       a = shift(a);
       length--;

     }while (length > 0);

    return false;
  }

  private String shift(String s){
    return s.substring(1, s.length()) + s.substring(0,1);
  }

}
