package com;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.digio.Application;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ApplicationComponentTest {

  private final ByteArrayOutputStream out = new ByteArrayOutputStream();
  private final ByteArrayOutputStream err = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;
  private final PrintStream originalErr = System.err;
  private final String expectedOutput = "The number of unique IP addresses - 11\n"
        + "The top 3 most visited URLs - \n"
        + "/docs/manage-websites/\n"
        + "/temp-redirect\n"
        + "/moved-permanently\n"
        + "The top 3 most active IP addresses - \n"
        + "168.41.191.40\n"
        + "50.112.00.11\n"
        + "177.71.128.21"
        + "\n";

  @BeforeEach
   void setStreams() {
    System.setOut(new PrintStream(out));
    System.setErr(new PrintStream(err));
  }

  @AfterEach
   void restoreInitialStreams() {
    System.setOut(originalOut);
    System.setErr(originalErr);
  }
  
  @Test
  void main() {
    Application.main(new String[]{});
    assertEquals(expectedOutput, out.toString());
  }
}