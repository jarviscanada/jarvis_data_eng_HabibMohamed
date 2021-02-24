package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class QueueViaStacksTest {

  QueueViaStacks queueViaStacks;
  
  @Before
  public void setUp() throws Exception {
    
    queueViaStacks = new QueueViaStacks();
    
  }
  
  @Test
  public void queueViaStacks(){

    queueViaStacks.push(3);
    queueViaStacks.push(4);

    assertEquals(3, queueViaStacks.peek());

    queueViaStacks.push(5);

    assertFalse(queueViaStacks.empty());

    assertEquals(3, queueViaStacks.pop());
    
  }
  
}