package com.imgarena.coding.challenge.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.imgarena.coding.challenge.domain.GolfTournament;
import com.imgarena.coding.challenge.exception.MappingException;

/**
 * Mapper marker interface.
 *
 * @param <T> the data provider DTO type
 * @author Elliot Ball
 */
public interface GolfTournamentMapper<T> {

  /**
   * Get the unique identifier for a given data provider.
   */
  String getDataProviderId();

  /**
   * Convert the incoming object form the data provider into our {@link GolfTournament} domain
   * model.
   *
   * @param json the incoming data provider golf tournament JSON
   * @return the populated {@link GolfTournament} domain model
   * @throws MappingException thrown if error during conversion of JSON -> DTO -> Domain process
   */
  GolfTournament convert(JsonNode json) throws MappingException;

  /**
   * Parses the incoming JSON into the representing DTO.
   *
   * Note:
   * Can skip this step entirely and act directly on the JsonNode with pointers if you wanted.
   *
   * @param json the incoming JSON from the data provider
   * @return the parsed {@link T} DTO
   * @throws MappingException thrown if failure to convert data provider JSON payload to the custom
   * DTO
   */
  T parseToDto(JsonNode json) throws MappingException;

  /**
   * The class the mapper handles.
   *
   * @return the class this mapper handles
   */
  Class<T> getClazz();
}
