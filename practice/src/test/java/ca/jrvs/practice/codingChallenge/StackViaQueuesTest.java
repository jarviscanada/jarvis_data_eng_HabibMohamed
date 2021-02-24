package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class StackViaQueuesTest {

  StackViaQueues stackViaQueues;

  @Before
  public void setUp() throws Exception {

    stackViaQueues = new StackViaQueues();

  }

  @Test
  public void stackViaQueue() {

    stackViaQueues.push(3);
    stackViaQueues.push(4);

    assertEquals(4, stackViaQueues.top());

    stackViaQueues.push(5);

    assertFalse(stackViaQueues.empty());

    assertEquals(5, stackViaQueues.pop());

  }

}