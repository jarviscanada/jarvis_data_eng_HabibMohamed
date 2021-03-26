package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import java.util.Set;
import org.junit.Before;
import org.junit.Test;

public class DupeCharTest {

  DupeChar dupeChar;

  @Before
  public void setUp() throws Exception {
    dupeChar = new DupeChar();
  }

  @Test
  public void dupeCharacters() {

    String string = "Here's a bunch of letters";

    Set<Character> dupes = dupeChar.dupeCharacters(string);

    assertEquals(5, dupes.size());
    assertTrue(dupes.contains("h".charAt(0)));

  }
}