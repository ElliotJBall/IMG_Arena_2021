package com.imgarena.coding.challenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception for when an error occurs during the mapping process. Custom exception to coerce
 * downstream errors into a generic bad request.
 *
 * @author Elliot Ball
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class MappingException extends RuntimeException {

  public MappingException(final Throwable cause) {
    super(cause);
  }
}
