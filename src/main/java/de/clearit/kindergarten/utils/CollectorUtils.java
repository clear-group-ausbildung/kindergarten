package de.clearit.kindergarten.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;

/**
 * This class contains utility methods for {@link Collector}s.
 *
 */
public class CollectorUtils {

  private CollectorUtils() {
    super();
  }

  /**
   * Returns a Collector to transform a one-element sized List to its single
   * element. If the List is has zero or more than one element, an exception is
   * thrown.
   * 
   * @return the first element of the collection or an exception
   */
  public static <T> Collector<T, List<T>, T> singletonCollector() {
    return Collector.of(ArrayList::new, List::add, (left, right) -> {
      left.addAll(right);
      return left;
    }, list -> {
      if (list.size() != 1) {
        throw new IllegalStateException();
      }
      return list.get(0);
    });
  }

}
