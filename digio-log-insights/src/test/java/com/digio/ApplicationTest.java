package com.digio;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.digio.services.LogParserImpl;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ApplicationTest {
  
  @Mock
  private LogParserImpl logParser;

  Application application;

  @BeforeEach
  void init() {
    application = new Application();
  }

  @DisplayName("File found")
  @Test
  void getFileFromResourceAsStreamFileFoundSuccessfullyTest() {
    assertDoesNotThrow(
        () -> application.getFileFromResourceAsStream("programming-task-example-data.log"));
  }

  @DisplayName("File not found")
  @Test
  void getFileFromResourceAsStreamFileNotFoundTest() {
    assertThrows(IllegalArgumentException.class, 
        () -> application.getFileFromResourceAsStream("File_Does_Not_Exist"));
  }

  @DisplayName("Run task successfully")
  @Test
  void programmingTaskTest() {
    InputStream targetStream = new ByteArrayInputStream("initialString".getBytes());
    assertDoesNotThrow(
        () -> application.programmingTask(targetStream));
  }
}