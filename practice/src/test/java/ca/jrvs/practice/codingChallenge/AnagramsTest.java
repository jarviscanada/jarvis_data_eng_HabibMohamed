package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class AnagramsTest {

  Anagrams anagrams;

  @Before
  public void setUp() {

    anagrams = new Anagrams();

  }

  @Test
  public void isAnagram() {

    String first = "racecar";
    String second = "carrbce";

    assertFalse(anagrams.isAnagram(first, second));
  }
}