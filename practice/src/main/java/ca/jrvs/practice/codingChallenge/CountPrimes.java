package ca.jrvs.practice.codingChallenge;


import java.util.Arrays;

/**
 * Finds out how many prime numbers are within
 * the range of 0 to the given number.
 * This takes roughly O(log n) time, as the outer
 * loop takes roughly O(log n - 1) time, and the inner
 * loop takes O(log n) time.
 */
public class CountPrimes {

  public int primeCounter(int n){

    if(n <= 2){
      return 0;
    }

    boolean[] primeTable = new boolean[n];
    Arrays.fill(primeTable, true);
    int primeCount = n-2;

    for(int i = 2; (i*i) < n; i++){

      if(primeTable[i]){

        for(int j = i; (j*i) < n; j++){

          if(!primeTable[i*j]){
            continue;
          }

          primeTable[i*j] = false;
          primeCount--;

        }

      }

    }

    return primeCount;

  }

}
