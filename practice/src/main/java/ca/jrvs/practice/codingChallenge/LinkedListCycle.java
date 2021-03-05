package ca.jrvs.practice.codingChallenge;

import ca.jrvs.practice.codingChallenge.helper.ListNode;

/**
 * Utilizes the tortoise and hare algorithm
 * to find out if a cycle is in a given linked
 * list.
 * This algorithm has been proven to run in a worst
 * case of O(n).
 */
public class LinkedListCycle {


  public boolean hasCycle(ListNode head) {
    if (head == null || head.next == null) {
      return false;
    }

    ListNode another = head;

    while (another != null) {

      head = head.next;

      if (another.next != null) {
        another = another.next.next;
      } else {
        break;
      }

      if (head == another) {
        return true;
      }

    }

    return false;
  }

}
