package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import ca.jrvs.practice.codingChallenge.helper.ListNode;
import org.junit.Before;
import org.junit.Test;

public class MiddleNodeTest {

  MiddleNode middleNode;

  @Before
  public void setUp() throws Exception {
    middleNode = new MiddleNode();
  }

  @Test
  public void middleNode() {

    ListNode head  = new ListNode(1);
    ListNode second = new ListNode(2);
    ListNode third = new ListNode(3);
    ListNode fourth = new ListNode(4);
    ListNode fifth = new ListNode(5);
    ListNode last = new ListNode(6);

    head.next = second;
    second.next = third;
    third.next = fourth;
    fourth.next = fifth;
    fifth.next = last;

    ListNode resultNode = middleNode.middleNode(head);

    assertEquals(fourth, resultNode);

  }
}