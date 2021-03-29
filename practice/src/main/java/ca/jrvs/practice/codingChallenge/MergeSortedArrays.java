package ca.jrvs.practice.codingChallenge;

/**
 * Merges two sorted array into the bigger one.
 * Takes O(m+n) time, with m and n being the length of
 * the bigger array. This is due to the bigger array being traversed
 * through in its entirety.
 */
public class MergeSortedArrays {

  public void merge(int[] nums1, int m, int[] nums2, int n){

    int tail1 = m-1;
    int tail2 = n-1;
    int index = m + n - 1;

    while(tail1 >= 0 && tail2 >= 0){

      nums1[index--] = nums1[tail1] >= nums2[tail2] ? nums1[tail1--] : nums2[tail2--];

    }


    int remain = -1;
    int[] numsRemain;

    if(tail1 < 0){
      remain = tail2;
      numsRemain = nums2;
    }else{
      remain = tail1;
      numsRemain = nums1;
    }

    while(remain >= 0){
      nums1[index--] = numsRemain[remain--];
    }

  }

}
