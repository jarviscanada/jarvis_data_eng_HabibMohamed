package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import ca.jrvs.practice.codingChallenge.helper.ListNode;
import org.junit.Before;
import org.junit.Test;

public class ReverseLinkedListTest {

  ReverseLinkedList reverseLinkedList;

  @Before
  public void setUp() throws Exception {
    reverseLinkedList = new ReverseLinkedList();
  }

  @Test
  public void reverseList() {

    ListNode head = new ListNode(4);
    ListNode next = new ListNode(5);
    ListNode last = new ListNode(6);

    head.next = next;
    next.next = last;

    ListNode newHead = reverseLinkedList.reverseList(head);

    assertEquals(last,newHead);
    assertEquals(next, newHead.next);
    assertEquals(head, newHead.next.next);

  }
}