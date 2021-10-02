package com.imgarena.coding.challenge.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imgarena.coding.challenge.exception.MappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Shared abstract golf tournament mapper implementing common logic between all mappers.
 *
 * @author Elliot Ball
 */
public abstract class AbstractGolfTournamentMapper<T> implements GolfTournamentMapper<T> {

  private static final Logger log = LoggerFactory.getLogger(AbstractGolfTournamentMapper.class);

  private final ObjectMapper objectMapper;

  public AbstractGolfTournamentMapper(final ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public T parseToDto(final JsonNode json) throws MappingException {
    try {
      return objectMapper.convertValue(json, this.getClazz());
    } catch (IllegalArgumentException e) {
      log.error("Failed to convert the data provider JSON: {} into designated DTO: {}", json,
          this.getClazz(), e);
      throw new MappingException(e);
    }
  }
}
