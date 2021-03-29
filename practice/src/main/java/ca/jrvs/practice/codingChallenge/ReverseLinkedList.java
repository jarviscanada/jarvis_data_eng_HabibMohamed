package ca.jrvs.practice.codingChallenge;

import ca.jrvs.practice.codingChallenge.helper.ListNode;
import java.util.ArrayDeque;

/**
 * Reverses a linked list using a stack.
 * Takes O(n) time, with n being the length of
 * the list. This is due to the list being traversed.
 */
public class ReverseLinkedList {

  public ListNode reverseList(ListNode head){

    if(head == null){
      return null;
    }

    ArrayDeque<ListNode> stack = new ArrayDeque<ListNode>();

    while(head.next != null){

      stack.push(head);
      head = head.next;

    }

    ListNode newHead = head;

    while(!stack.isEmpty()){

      head.next = stack.pop();
      head = head.next;

    }
    head.next = null;

    return newHead;

  }

}
