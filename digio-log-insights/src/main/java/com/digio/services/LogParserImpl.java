package com.digio.services;

import static com.digio.Utility.DATE_FORMAT;
import static com.digio.Utility.DATE_REGEX_GROUP;
import static com.digio.Utility.PATTERN;

import com.digio.models.ApacheLogAttributes;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;

public class LogParserImpl implements LogParser {
  
  @Override
  public Map<String, ApacheLogAttributes> buildMap(String logLine, Map<String, ApacheLogAttributes> ipMap,
                                                   int keyGroupNumber) {
    final Matcher matcher = PATTERN.matcher(logLine);

    if (matcher.matches()) {
      if (ipMap.containsKey(matcher.group(keyGroupNumber))) {
        ApacheLogAttributes apacheLogAttributes = ipMap.get(matcher.group(keyGroupNumber));
        apacheLogAttributes.setCount(apacheLogAttributes.getCount() + 1);
        try {
          Date date = DATE_FORMAT.parse(matcher.group(DATE_REGEX_GROUP));
          if (date.after(apacheLogAttributes.getDate())) {
            apacheLogAttributes.setDate(date);
          }
        } catch (ParseException e) {
          System.out.println("Error parsing the date");
        }
      } else {
        try {
          Date date = DATE_FORMAT.parse(matcher.group(DATE_REGEX_GROUP));
          ipMap.put(matcher.group(keyGroupNumber), new ApacheLogAttributes(1, date));
        } catch (ParseException e) {
          System.out.println("Error parsing the date");
        }
      }
    }
    return ipMap;
  }
}
