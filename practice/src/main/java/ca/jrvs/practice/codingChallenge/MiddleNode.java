package ca.jrvs.practice.codingChallenge;

import ca.jrvs.practice.codingChallenge.helper.ListNode;

/**
 * Finds the middle node in a linked list.
 * Takes O(n) time, as we need to traverse
 * through the list to find the middle.
 */
public class MiddleNode {

  public ListNode middleNode(ListNode head){

    ListNode middle = head;
    int index = 0;

    while(head.next != null){

      index++;

      if(index % 2 != 0){
        middle = middle.next;
      }

      head = head.next;

    }

    return middle;

  }

}
