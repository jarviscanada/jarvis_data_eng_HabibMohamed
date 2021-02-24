package ca.jrvs.practice.codingChallenge;

import java.util.Stack;

/**
 * Implements a Queue using two Stacks.
 *
 * Operates in O(n) time, with n being the maximum
 * number of integers inserted into queue.
 * This is due to push moving all integers out of the stack
 * in order to place the newest integer at the bottom.
 *
 */
public class QueueViaStacks {

  private Stack<Integer> mainStack;
  private Stack<Integer> tempStack;

  public QueueViaStacks(){

    mainStack = new Stack<Integer>();
    tempStack = new Stack<Integer>();

  }

  public void push(int x){

    if(mainStack.empty() && tempStack.empty()){
      mainStack.push(x);
      return;
    }

    while(!mainStack.empty()){

      tempStack.push(mainStack.pop());

    }

    mainStack.push(x);

    while(!tempStack.empty()){

      mainStack.push(tempStack.pop());

    }

  }

  public int pop(){

    return mainStack.pop();

  }

  public int peek(){

    return mainStack.peek();

  }

  public boolean empty(){

    return mainStack.empty();

  }

}
