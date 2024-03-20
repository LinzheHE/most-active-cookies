package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeParseException;
import java.util.*;

/**
 * Class for searching the most active cookies for a given date
 */
public class Handler {
  private Integer currMax = 0;

  /**
   * Key method to get the list of most active cookies for a given date
   * @param filePath: String, the file path from the args
   * @param dateStr: String, the given date from the args
   * @return: a List of most active cookies for the given date, order does not matter
   * @throws IOException: when cannot process the file
   */
  public List<String> getMostActiveCookies(String filePath, String dateStr) throws IOException {
    List<String> cookiesForDate = getCookiesForDate(filePath, dateStr);

    if (cookiesForDate.size() == 0) {
      return new ArrayList<>();
    } else {
      HashMap<String, Integer> cookieFreqForDate = buildCookieFreqMap(cookiesForDate);
      return findCookies(cookieFreqForDate, currMax);
    }
  }

  /**
   * Helper method to get the List of all cookies for the given date
   * @param filePath: String, the file path from the args
   * @param dateStr: String, the given date from the args
   * @return: the List of all cookies for the given date
   * @throws IOException when cannot process the file
   */
  private List<String> getCookiesForDate(String filePath, String dateStr) throws IOException {
    List<String> cookieListForDate = new ArrayList<>();
    LocalDate targetDate = LocalDate.parse(dateStr);

    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      // skip the first line
      reader.readLine();

      String line;
      while ((line = reader.readLine()) != null) {
        String[] parts = line.split(",");

        try {
          // Parse timestamp to OffsetDateTime
          OffsetDateTime timestamp = OffsetDateTime.parse(parts[1]);

          // Convert timestamp to UTC time zone
          OffsetDateTime timestampUtc = timestamp.withOffsetSameInstant(ZoneOffset.UTC);

          // Extract date part from timestamp
          LocalDate timestampDate = timestampUtc.toLocalDate();

          if (timestampDate.equals(targetDate)) {
            cookieListForDate.add(parts[0]);
          } else if (timestampDate.isBefore(targetDate)) {
            break;
          }
        } catch (DateTimeParseException e) {
          System.err.println("Error parsing timestamp: " + e.getMessage());
          throw e;
        }
      }
    }

    return cookieListForDate;
  }

  /**
   * Helper method for getting the <cookie, frequency> map
   * @param cookiesForDate: the List of all cookies for the given date
   * @return: a <cookie, frequency> map, keys are the cookies on that date, values are the frequencies of the cookies on that date
   */
  private HashMap<String, Integer> buildCookieFreqMap(List<String> cookiesForDate) {
    HashMap<String, Integer> cookieFreq = new HashMap<>();

    for (String cookie : cookiesForDate) {
      cookieFreq.put(cookie, cookieFreq.getOrDefault(cookie, 0) + 1);
      currMax = Math.max(currMax, cookieFreq.get(cookie));
    }

    return cookieFreq;
  }

  /**
   * Helper method to get the List of cookies with the highest freq
   * @param cookieFreqForDate: <cookie, frequency> map for the given date
   * @param freq: the recorded highest frequency
   * @return: List of cookies with the highest freq
   */
  private List<String> findCookies(HashMap<String, Integer> cookieFreqForDate, Integer freq) {
    List<String> mostActiveCookies = new ArrayList<>();

    for (Map.Entry<String, Integer> entry : cookieFreqForDate.entrySet()) {
      if (entry.getValue() == freq) {
        mostActiveCookies.add(entry.getKey());
      }
    }

    return mostActiveCookies;
  }
}
