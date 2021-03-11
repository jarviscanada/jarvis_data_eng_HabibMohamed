package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import ca.jrvs.practice.codingChallenge.helper.ListNode;
import org.junit.Before;
import org.junit.Test;

public class LinkedListCycleTest {

  LinkedListCycle cycle;

  @Before
  public void setUp() throws Exception {

    cycle = new LinkedListCycle();

  }

  @Test
  public void hasCycle() {

    ListNode first = new ListNode(4);
    ListNode second = new ListNode(2);
    ListNode third = new ListNode(8);
    ListNode last = new ListNode(10);

    first.next = second;
    second.next = third;
    third.next = last;
    last.next = second;

    assertTrue(cycle.hasCycle(first));

  }
}