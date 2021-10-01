package com.imgarena.coding.challenge.mapper;

import com.imgarena.coding.challenge.exception.MappingException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Shared mapping utilities.
 *
 * @author Elliot Ball
 */
public final class MapperUtils {

  private static final Logger log = LoggerFactory.getLogger(MapperUtils.class);

  // FIXME: Maybe we should throw our own custom mapping exception here so we don't persist a
  //  tournament with dodgy data?

  public static LocalDate parseDate(final String dateString,
      final DateTimeFormatter formatter) {
    try {
      return LocalDate.parse(dateString, formatter);
    } catch (DateTimeParseException e) {
      log.error("Failed to parse a date value: {} expected format: {}", dateString, formatter, e);
      throw new MappingException(e);
    }
  }

  public static Integer parseRounds(final String roundsString) {
    try {
      return Integer.valueOf(roundsString);
    } catch (NumberFormatException e) {
      log.error("Failed to parse numeric value from given round: {}", roundsString);
      throw new MappingException(e);
    }
  }

}
