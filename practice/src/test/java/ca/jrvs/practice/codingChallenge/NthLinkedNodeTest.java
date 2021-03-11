package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import ca.jrvs.practice.codingChallenge.helper.ListNode;
import org.junit.Before;
import org.junit.Test;

public class NthLinkedNodeTest {

  NthLinkedNode nthLinkedNode;

  @Before
  public void setUp() throws Exception {

    nthLinkedNode = new NthLinkedNode();

  }

  @Test
  public void nthListNode() {

    ListNode head  = new ListNode(1);
    ListNode second = new ListNode(2);
    ListNode toBeRemoved = new ListNode(31);
    ListNode last = new ListNode(3);

    head.next = second;
    second.next = toBeRemoved;
    toBeRemoved.next = last;

    nthLinkedNode.nthListNode(head, 2);

    assertEquals(second, head.next);
    assertEquals(last, second.next);


  }
}