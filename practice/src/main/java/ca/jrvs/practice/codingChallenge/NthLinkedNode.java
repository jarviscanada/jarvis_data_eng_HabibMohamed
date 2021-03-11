package ca.jrvs.practice.codingChallenge;

import ca.jrvs.practice.codingChallenge.helper.ListNode;

/**
 * Finds the nth node from the end and deletes it.
 * Takes O(n) time, with n being the size of the list.
 * This is due to the traversal through the list to find
 * its size.
 *
 */
public class NthLinkedNode {

  public ListNode nthListNode(ListNode head, int n) {

    int size = 1;
    ListNode next = head.next;

    while (next != null) {

      size++;
      next = next.next;

    }

    if (size == 1) {
      return null;
    }

    int index = (size - n) % size;
    ListNode pre = null;

    if (index == 0) {
      return head.next;
    }

    next = head;

    for (int i = 0; i < size; i++) {
      if (i + 1 == index) {
        pre = next;
        break;
      }
      next = next.next;
    }

    ListNode post = null;

    if (pre.next != null && pre.next.next != null) {
      post = pre.next.next;
    }
    pre.next = post;

    return head;

  }

}
