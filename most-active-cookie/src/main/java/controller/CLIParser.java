package controller;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Class for validating the command
 */
public class CLIParser {

  /**
   * Key method for calling all helper validation methods
   * @param args: the args received from the command line
   * @return: true if all validation pass
   * @throws IllegalArgumentException
   * @throws DateTimeParseException
   * @throws FileNotFoundException
   */
  public boolean validateArgs(String[] args) throws IllegalArgumentException, DateTimeParseException, FileNotFoundException {
    validateCommandFormat(args);
    validateLogFile(args[0]);
    validateDateFormat(args[2]);
    return true;
  }

  /**
   * Helper method for validating the command's format
   * @param args: the args received from the command line
   * @throws IllegalArgumentException: when the args length is not 3, or when -d is not at the correct place
   */
  private void validateCommandFormat(String[] args) throws IllegalArgumentException {
    if (args.length != 3) {
      throw new IllegalArgumentException("3 args expected.");
    }

    if (!args[1].equals("-d")) {
      throw new IllegalArgumentException("-d is expected but not found in the correct place.");
    }
  }

  /**
   * Helper method for validating the file path
   * @param filePath: the file path from the args
   * @throws IllegalArgumentException: when it is not .csv
   * @throws FileNotFoundException: when the file is not found
   */
  private void validateLogFile(String filePath) throws IllegalArgumentException, FileNotFoundException {
    if (!filePath.endsWith(".csv")) {
      throw new IllegalArgumentException("Log file must be in CSV format.");
    }

    if (!new java.io.File(filePath).exists()) {
      throw new FileNotFoundException("Log file not found.");
    }
  }

  /**
   * Helper method for validating the date
   * @param date: date string from the args
   * @throws DateTimeParseException: when the date is not in the YYYY-MM-DD format
   */
  private void validateDateFormat(String date) throws DateTimeParseException {
    try {
      LocalDate.parse(date); // ISO-8601 standard format by default; YYYY-MM-DD is valid
    } catch (DateTimeParseException e) {
      throw new DateTimeParseException("Invalid date format.", e.getParsedString(), e.getErrorIndex());
    }
  }
}
