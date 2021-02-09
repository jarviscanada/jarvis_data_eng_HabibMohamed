package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class EqualMapsTest {

  Map<String, Integer> map1;
  Map<String, Integer> map2;
  Map<String, Integer> map3;

  EqualMaps equalMaps;

  @Before
  public void setUp() throws Exception {

    map1 = new HashMap<>();
    map1.put("jeff" , 20);
    map1.put("max", 32);
    map1.put("joe", 47);

    map2 = new HashMap<>();
    map2.put("park", 98);
    map2.put("wise", 76);
    map2.put("zack", 45);

    map3 = new HashMap<>();
    map3.put("jeff" , 20);
    map3.put("max", 32);
    map3.put("joe", 47);

    equalMaps = new EqualMaps();

  }

  @Test
  public void isMapsEqual() {

    assertEquals(false, equalMaps.isMapsEqual(map1, map2));
    assertEquals(true, equalMaps.isMapsEqual(map1, map3));

  }
}