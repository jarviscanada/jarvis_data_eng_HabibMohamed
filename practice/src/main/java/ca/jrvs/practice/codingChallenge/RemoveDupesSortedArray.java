package ca.jrvs.practice.codingChallenge;

/**
 * Removes dupes from a sorted array.
 * Takes O(n^2) time, as dupes have to
 * be shuffled to the back such that the order
 * is maintained.
 */
public class RemoveDupesSortedArray {

  public int removeDupes(int[] nums){

    int trueLen = nums.length;
    int currentIndex = 1;

    while(currentIndex < trueLen){

      if(nums[currentIndex] == nums[currentIndex-1]){

        for(int j = currentIndex; j < trueLen-1; j++){
          int temp = nums[j+1];
          nums[j+1]  = nums[j];
          nums[j] = temp;
        }

        trueLen--;

      }else{
        currentIndex++;
      }

    }

    return trueLen;

  }

}
