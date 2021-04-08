package com.digio.services;

import static com.digio.Utility.ATTRIBUTES_COMPARATOR;
import static java.util.stream.Collectors.toMap;

import com.digio.models.ApacheLogAttributes;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Summary -
 * Functional interface that exposes a method to accept a log line and build a map of it. Key of the map returned will 
 * Ip address or Url depending upon the keyGroupNumber. Value of the map will contain information of count and the latest
 * accessed date.
 */
public interface LogParser {
  Map<String, ApacheLogAttributes> buildMap(String logLine, Map<String, ApacheLogAttributes> map,
                                            int keyGroupNumber);

  /**
   * Summary - This default method accepts a map and sorts the map by Count and then by Date when the log was inserted.
   * @param map - Map of String and Log Attributes
   * @return - LinkedHashMap.
   */
  default LinkedHashMap<String, ApacheLogAttributes> sortMap(Map<String, ApacheLogAttributes> map) {
    return map.entrySet().stream()
          .sorted(Collections.reverseOrder(Map.Entry.comparingByValue(ATTRIBUTES_COMPARATOR)))
          .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                LinkedHashMap::new));
  }
}
