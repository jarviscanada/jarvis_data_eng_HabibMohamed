package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CountPrimesTest {

  CountPrimes countPrimes;

  @Before
  public void setUp() throws Exception {
    countPrimes = new CountPrimes();
  }

  @Test
  public void primeCounter() {

    int limit = 10;
    assertEquals(4, countPrimes.primeCounter(limit));

    limit = 100;

    assertEquals(25, countPrimes.primeCounter(limit));

    limit = 1000;
    assertEquals(168, countPrimes.primeCounter(limit));

    limit = 10000;

    assertEquals(1229, countPrimes.primeCounter(limit));



  }
}