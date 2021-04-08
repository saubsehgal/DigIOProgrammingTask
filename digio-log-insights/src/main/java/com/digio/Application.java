package com.digio;

import static com.digio.Utility.IP_ADDRESS_REGEX_GROUP;
import static com.digio.Utility.URL_REGEX_GROUP;

import com.digio.models.ApacheLogAttributes;
import com.digio.services.LogParser;
import com.digio.services.LogParserImpl;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Application {
  
  InputStream getFileFromResourceAsStream(String fileName) {
    ClassLoader classLoader = getClass().getClassLoader();
    InputStream inputStream = classLoader.getResourceAsStream(fileName);
    if (inputStream == null) {
      throw new IllegalArgumentException("file not found! " + fileName);
    } else {
      return inputStream;
    }

  }
  
  void programmingTask(InputStream is) {
    Map<String, ApacheLogAttributes> ipMap = new HashMap<>();
    Map<String, ApacheLogAttributes> urlMap = new HashMap<>();
    LogParser logParser = new LogParserImpl();
    try (InputStreamReader inputStreamReader =
               new InputStreamReader(is, StandardCharsets.UTF_8);
         BufferedReader reader = new BufferedReader(inputStreamReader)) {
      String line;
      while ((line = reader.readLine()) != null) {
        ipMap = logParser.buildMap(line,ipMap, IP_ADDRESS_REGEX_GROUP);
        urlMap = logParser.buildMap(line,urlMap, URL_REGEX_GROUP);
      }
      ipMap = logParser.sortMap(ipMap);
      urlMap = logParser.sortMap(urlMap);

      System.out.println("The number of unique IP addresses - " + ipMap.size());
      System.out.println("The top 3 most visited URLs - ");
      urlMap.keySet().stream().limit(3).forEach(System.out::println);
      System.out.println("The top 3 most active IP addresses - ");
      ipMap.keySet().stream().limit(3).forEach(System.out::println);
      
    } catch (IOException e) {
      e.printStackTrace();
    }

  }
  
  
  public static void main(String[] args) {
    Application app = new Application();
    String fileName = args.length > 0 ? args[0] : "programming-task-example-data.log";
    InputStream is = app.getFileFromResourceAsStream(fileName);
    app.programmingTask(is);
  }
}
