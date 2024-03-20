package controller;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.time.DateTimeException;
import java.time.format.DateTimeParseException;

public class CLIParserTest {

  @Test
  void validateArgs_Success() throws FileNotFoundException{
    CLIParser cliParser = new CLIParser();
    String[] args = {"src/test/resources/cookie_log.csv", "-d", "2024-03-18"};

    assertTrue(cliParser.validateArgs(args));
  }

  @Test
  void validateArgs_InvalidArgsLength() {
    CLIParser cliParser = new CLIParser();
    String[] args = {"src/test/resources/cookie_log.csv", "-d"};

    assertThrows(IllegalArgumentException.class, () -> cliParser.validateArgs(args));
  }

  @Test
  void validateArgs_MissingDashD() {
    CLIParser cliParser = new CLIParser();
    String[] args = {"src/test/resources/cookie_log.csv", "2024-03-18"};

    assertThrows(IllegalArgumentException.class, () -> cliParser.validateArgs(args));
  }

  @Test
  void validateArgs_NotDashD() {
    CLIParser cliParser = new CLIParser();
    String[] args = {"src/test/resources/cookie_log.csv", "-e", "2024-03-18"};

    assertThrows(IllegalArgumentException.class, () -> cliParser.validateArgs(args));
  }

  @Test
  void validateArgs_InvalidDateFormat() {
    CLIParser cliParser = new CLIParser();
    String[] args = {"src/test/resources/cookie_log.csv", "-d", "18-03-2024"};

    assertThrows(DateTimeParseException.class, () -> cliParser.validateArgs(args));
  }

  @Test
  void validateArgs_InvalidLogFileFormat() {
    CLIParser cliParser = new CLIParser();
    String[] args = {"src/test/resources/cookie_log.txt", "-d", "2024-03-18"};

    assertThrows(IllegalArgumentException.class, () -> cliParser.validateArgs(args));
  }

  @Test
  void validateArgs_LogFileNotFound() {
    CLIParser cliParser = new CLIParser();
    String[] args = {"nonexistentfile.csv", "-d", "2024-03-18"};

    assertThrows(FileNotFoundException.class, () -> cliParser.validateArgs(args));
  }
}
