package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import java.util.LinkedList;
import org.junit.Before;
import org.junit.Test;

public class DupeLinkedNodesTest {

  DupeLinkedNodes dupes;

  @Before
  public void setUp() {
    dupes = new DupeLinkedNodes();
  }

  @Test
  public void duplicateRemoval() {

    LinkedList<Integer> ints = new LinkedList<Integer>();
    ints.add(5);
    ints.add(7);
    ints.add(8);
    ints.add(5);

    int priorLen = ints.size();

    ints = dupes.duplicateRemoval(ints);

    assertNotEquals(priorLen, ints.size());
    assertEquals(3, ints.size());

  }
}