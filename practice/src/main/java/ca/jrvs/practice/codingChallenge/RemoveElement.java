package ca.jrvs.practice.codingChallenge;

/**
 * "Removes" elements from an array by placing
 * them at the end of the array and decreasing the
 * array length to reflect that. Returns the updated
 * array length. Takes O(n) time, as in the worst case,
 * there are no instances of the element and the whole
 * array is iterated through.
 */
public class RemoveElement {

  public int removeElement(int[] nums, int val){

    int trueLen = nums.length;
    int currentIndex = 0;

    while(currentIndex < trueLen){

      if(nums[currentIndex] == val){
        int temp = nums[currentIndex];
        nums[currentIndex] = nums[trueLen-1];
        nums[trueLen-1] = temp;

        trueLen--;
      }else{
        currentIndex++;
      }

    }

    return trueLen;

  }

}
