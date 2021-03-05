package ca.jrvs.practice.codingChallenge;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Implements a stack using two queues
 *
 * This operates in O(n) time, with n being
 * the maximum number of integers added to the queue.
 * This is due to the pop function moving out all the numbers
 * it contains in order to have the newest one at the head.
 */
public class StackViaQueues {

  private Queue<Integer> queue1;
  private Queue<Integer> queue2;

  public StackViaQueues() {

    queue1 = new LinkedList<Integer>();
    queue2 = new LinkedList<Integer>();

  }


  public void push(int x) {

    if(queue1.isEmpty() && queue2.isEmpty()){
      queue1.add(x);
      return;
    }

    queue2.add(x);

    while(!queue1.isEmpty()){
      queue2.add(queue1.remove());
    }

    while(!queue2.isEmpty()){
      queue1.add(queue2.remove());
    }

  }


  public int pop() {

    return queue1.remove();

  }


  public int top() {

    return queue1.peek();

  }


  public boolean empty() {

    return queue1.isEmpty();

  }

}
