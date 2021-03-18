package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MergeSortedArraysTest {

  MergeSortedArrays mergeSortedArrays;

  @Before
  public void setUp() throws Exception {
    mergeSortedArrays = new MergeSortedArrays();
  }

  @Test
  public void merge() {

    int[] nums1 = {0};
    int[] nums2 = {1};

    mergeSortedArrays.merge(nums1, 0, nums2, 1);

    assertEquals(1, nums1.length);

  }
}