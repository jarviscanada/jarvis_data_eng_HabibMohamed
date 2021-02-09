package ca.jrvs.practice.codingChallenge;

import java.util.Map;

public class EqualMaps {


  /**
   * This checks whether two maps have the same key/value pairs
   * This utilizes Collections.equals to do the job
   *
   * This should take roughly O(n) time
   * Retrieval with maps takes O(1) time. Getting all the keys takes O(n) time.
   * The implementation of Collections.equals seems to go through each key/value pair
   * of the map it was called on, and runs Objects.equals against the parameter map's
   * key/value pair, whether it exists or not.
   *
   * @param map1
   * @param map2
   * @param <K>
   * @param <V>
   * @return true if maps have same key/value pairs or false otherwise
   */
  public <K,V> boolean isMapsEqual (Map<K,V> map1, Map<K,V> map2){

    return map1.equals(map2) ? true : false;

  }

}
