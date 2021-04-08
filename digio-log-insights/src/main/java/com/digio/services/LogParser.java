package com.digio.services;

import static com.digio.Utility.ATTRIBUTES_COMPARATOR;
import static java.util.stream.Collectors.toMap;

import com.digio.models.ApacheLogAttributes;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public interface LogParser {
  Map<String, ApacheLogAttributes> buildMap(String logLine, Map<String, ApacheLogAttributes> map,
                                            int keyGroupNumber);
  
  default LinkedHashMap<String, ApacheLogAttributes> sortMap(Map<String, ApacheLogAttributes> map) {
    return map.entrySet().stream()
          .sorted(Collections.reverseOrder(Map.Entry.comparingByValue(ATTRIBUTES_COMPARATOR)))
          .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                LinkedHashMap::new));
  }
}
