package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EvenOrOddTest {

  private EvenOrOdd evenOrOdd;

  @Before
  public void setUp() throws Exception {

    evenOrOdd = new EvenOrOdd();

  }

  @Test
  public void isEvenOrOdd() {

    String isEven = "even";
    String isOdd = "odd";

    Assert.assertEquals(isOdd, evenOrOdd.isEvenOrOdd(1));
    Assert.assertEquals(isEven, evenOrOdd.isEvenOrOdd(2));

    Assert.assertEquals(isEven, evenOrOdd.isEvenOrOdd(-2));
    Assert.assertEquals(isOdd, evenOrOdd.isEvenOrOdd(-1));

    Assert.assertEquals(isEven, evenOrOdd.isEvenOrOdd(0));

  }
}