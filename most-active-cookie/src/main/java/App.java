import java.util.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.format.DateTimeParseException;
import controller.CLIParser;
import controller.Handler;

public class App {
  public static void main(String[] args) {
    // validate the args
    CLIParser parser = new CLIParser();

    try {
      parser.validateArgs(args);
    } catch (IllegalArgumentException | DateTimeParseException | FileNotFoundException e) {
      System.out.println("Error: " + e.getMessage());
      return;
    }

    // get the most active cookies
    Handler handler = new Handler();
    String filePath = args[0];
    String dateStr = args[2];

    try {
      List<String> cookies = handler.getMostActiveCookies(filePath, dateStr);

      if (cookies.size() == 0) {
        System.out.println("No logs for the input date.");
      } else {
        for (String cookie : cookies) {
          System.out.println(cookie);
        }
      }
    } catch (IOException e) {
      System.out.println("Error reading log file: " + e.getMessage());
    }
  }
}
