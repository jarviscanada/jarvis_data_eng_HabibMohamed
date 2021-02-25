package ca.jrvs.practice.codingChallenge;

import java.util.LinkedList;
import java.util.stream.Collectors;

/**
 * Removes dupe nodes from linked list.
 * Takes O(n) time, with n being the size of
 * the linked list, due to a travesal through the
 * linked list being necessary to grab distinct nodes.
 *
 */
public class DupeLinkedNodes {

  public LinkedList<Integer> duplicateRemoval(LinkedList<Integer> integers){

    return integers.stream()
        .distinct()
        .collect(Collectors.toCollection(LinkedList<Integer>::new));

  }

}
