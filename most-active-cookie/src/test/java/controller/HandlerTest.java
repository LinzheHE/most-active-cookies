package controller;

import java.time.format.DateTimeParseException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;

public class HandlerTest {

  @Test
  void getMostActiveCookies_Success() {
    Handler handler = new Handler();
    String filePath = "src/test/resources/cookie_log.csv";
    String dateStr = "2018-12-09";

    assertDoesNotThrow(() -> {
      List<String> mostActiveCookies = handler.getMostActiveCookies(filePath, dateStr);
      assertEquals(1, mostActiveCookies.size());
      assertTrue(mostActiveCookies.contains("AtY0laUfhglK3lC7"));
    });
  }

  @Test
  void getMostActiveCookies_Success_DiffTimeZone() {
    Handler handler = new Handler();
    String filePath = "src/test/resources/cookie_log_notUTC.csv";
    String dateStr = "2018-12-09";

    assertDoesNotThrow(() -> {
      List<String> mostActiveCookies = handler.getMostActiveCookies(filePath, dateStr);
      assertEquals(1, mostActiveCookies.size());
      assertTrue(mostActiveCookies.contains("SAZuXPGUrfbcn5UA"));
    });
  }

  @Test
  void getMostActiveCookies_NotExistDate() {
    Handler handler = new Handler();
    String filePath = "src/test/resources/cookie_log.csv";
    String dateStr = "2024-03-01"; // Invalid date

    assertDoesNotThrow(() -> {
      List<String> mostActiveCookies = handler.getMostActiveCookies(filePath, dateStr);
      assertNotNull(mostActiveCookies);
      assertTrue(mostActiveCookies.isEmpty());
    });
  }

  @Test
  void getMostActiveCookies_WrongTimeStampFormat() {
    Handler handler = new Handler();
    String filePath = "src/test/resources/timestamp_wrong_format.csv";
    String dateStr = "2018-12-09";

    assertThrows(DateTimeParseException.class, () -> handler.getMostActiveCookies(filePath, dateStr));
  }

  @Test
  void getMostActiveCookies_FileNotFound() {
    Handler handler = new Handler();
    String filePath = "nonexistentfile.csv";
    String dateStr = "2024-03-18";

    assertThrows(IOException.class, () -> handler.getMostActiveCookies(filePath, dateStr));
  }
}
