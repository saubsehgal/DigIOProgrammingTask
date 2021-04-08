package com.digio;

import static com.digio.Utility.DATE_FORMAT;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.digio.models.ApacheLogAttributes;
import com.digio.services.LogParser;
import com.digio.services.LogParserImpl;
import java.text.ParseException;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class LogParserImplTest {
  static String logLine = "177.71.128.21 - - [10/Jul/2018:22:21:28 +0200] \"GET /intranet-analytics/ HTTP/1.1\" "
        + "200 3574 \"-\" \"Mozilla/5.0 (X11; U; Linux x86_64; fr-FR) AppleWebKit/534.7 (KHTML, like Gecko) "
        + "Epiphany/2.30.6 Safari/534.7\"";

  LogParser logParser = new LogParserImpl();

  private static Stream<Map<String, ApacheLogAttributes>> ipAddressMaps() {
    Map<String, ApacheLogAttributes> ipMap = new HashMap<>();
    ipMap.put("177.71.128.21",
          new ApacheLogAttributes(1, Date.from(Instant.now())));
    return Stream.of(new LinkedHashMap<>(),ipMap);
  }

  private static Stream<Map<String, ApacheLogAttributes>> urlAddressMaps() {
    Map<String, ApacheLogAttributes> ipMap = new HashMap<>();
    ipMap.put("/intranet-analytics/",
          new ApacheLogAttributes(1, Date.from(Instant.now())));
    return Stream.of(new LinkedHashMap<>(),ipMap);
  }

  @DisplayName("Build Ip address map")
  @ParameterizedTest
  @MethodSource("ipAddressMaps")
  void testIpAddressMap(Map<String, ApacheLogAttributes> ipMap) {
    Map<String, ApacheLogAttributes> outputMap = logParser.buildMap(logLine,ipMap,1);
    assertEquals(1, outputMap.size());
  }

  @DisplayName("Build Url address map")
  @ParameterizedTest
  @MethodSource("urlAddressMaps")
  void testUrlMap(Map<String, ApacheLogAttributes> ipMap) {
    Map<String, ApacheLogAttributes> outputMap = logParser.buildMap(logLine,ipMap,6);
    assertEquals(1, outputMap.size());
  }

  @DisplayName("Test Ip map count and date")
  @Test
  void testIpAddressMapAttributes() {
    Map<String, ApacheLogAttributes> ipMap = new HashMap<>();
    Date date = Date.from(Instant.now());
    ipMap.put("177.71.128.21",
          new ApacheLogAttributes(1, date));
    Map<String, ApacheLogAttributes> outputMap = logParser.buildMap(logLine,ipMap,1);
    assertEquals(1, outputMap.size());
    assertEquals(2, outputMap.get("177.71.128.21").getCount());
    assertEquals(date, outputMap.get("177.71.128.21").getDate());
  }

  @DisplayName("Test Url map count and date")
  @Test
  void testUrlMapAttributes() {
    Map<String, ApacheLogAttributes> ipMap = new HashMap<>();
    Date date = Date.from(Instant.now());
    ipMap.put("/intranet-analytics/",
          new ApacheLogAttributes(1, date));
    Map<String, ApacheLogAttributes> outputMap = logParser.buildMap(logLine,ipMap,6);
    assertEquals(1, outputMap.size());
    assertEquals(2, outputMap.get("/intranet-analytics/").getCount());
    assertEquals(date, outputMap.get("/intranet-analytics/").getDate());
  }

  @DisplayName("Log line not in format")
  @Test
  void testInvalidLogLine() {
    Map<String, ApacheLogAttributes> ipMap = new HashMap<>();
    String log = "invalid";
    Map<String, ApacheLogAttributes> outputMap = logParser.buildMap(log,ipMap,6);
    assertEquals(0, outputMap.size());
  }

  @DisplayName("Sort the map by count and then by date")
  @Test
  void testSortingOrder() throws ParseException {
    Map<String, ApacheLogAttributes> ipMap = new HashMap<>();
    Date dateBefore = DATE_FORMAT.parse("10/Jul/2018:22:21:28 +0200");
    Date dateAfter = Date.from(Instant.now());

    ipMap.put("/intranet-analytics/",
          new ApacheLogAttributes(4, dateBefore));
    ipMap.put("/home/",
          new ApacheLogAttributes(4, dateAfter));
    ipMap.put("/faq/",
          new ApacheLogAttributes(3, dateAfter));
    Map<String, ApacheLogAttributes> outputMap = logParser.sortMap(ipMap);
    assertEquals(3, outputMap.size());
    assertEquals("/home/", outputMap.keySet().stream().findFirst().isPresent()
          ? outputMap.keySet().stream().findFirst().get() : "");
  }
}