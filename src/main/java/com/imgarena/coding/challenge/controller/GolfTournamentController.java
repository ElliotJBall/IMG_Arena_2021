package com.imgarena.coding.challenge.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.imgarena.coding.challenge.domain.GolfTournament;
import com.imgarena.coding.challenge.exception.MappingException;
import com.imgarena.coding.challenge.service.GolfTournamentService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Golf tournaments controller class.
 *
 * @author Elliot Ball
 */
@RestController
@RequestMapping("golf-tournaments")
public class GolfTournamentController {

  private final GolfTournamentService service;

  public GolfTournamentController(final GolfTournamentService service) {
    this.service = service;
  }

  /**
   * POST method that takes an incoming golf-tournament from a number of data providers. A custom
   * header X-DATA-SOURCE-ID is provided (This is just some dummy id, could be an API key or
   * something more 'advanced') to determine which mapping strategy must be used.
   *
   * @param dataSourceId the unique data provider identifier
   * @param json the data provider golf tournament JSON payload
   * @return the golf tournament domain model we parsed
   */
  @PostMapping(path = "/")
  public ResponseEntity<GolfTournament> createGolfTournament(
      @RequestHeader(value = "X-DATA-SOURCE-ID") final String dataSourceId,
      @RequestBody final JsonNode json) {

    if (!service.canHandleDataSource(dataSourceId)) {
      return ResponseEntity.badRequest().build();
    }

    return ResponseEntity.of(service.convert(dataSourceId, json));
  }

  /**
   * Get all endpoint, exists purely so we can quickly verify that the data was successfully
   * ingested as part of the demo.
   *
   * @return all {@link GolfTournament}'s
   */
  @GetMapping(path = "/")
  public ResponseEntity<List<GolfTournament>> findAll() {
    return ResponseEntity.ok(service.findAll());
  }

  /**
   * As we only have one controller I'll use this error handling method, in the real world would
   * likely use a separate class with @ControllerAdvice to consolidate exceptions.
   */
  @ExceptionHandler(MappingException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST,
      reason = "Failed to map incoming JSON to DTO/domain model")
  public void handleErrors() {
  }
}
