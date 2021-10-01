package com.imgarena.coding.challenge.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imgarena.coding.challenge.domain.GolfTournament;
import com.imgarena.coding.challenge.exception.MappingException;
import com.imgarena.coding.challenge.model.DataProviderOneModel;
import java.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Data source one mapper. Handles the conversion of a {@link DataProviderOneModel} into the {@link
 * GolfTournament} domain entity.
 *
 * @author Elliot Ball
 */
@Component
public class DataProviderOneMapper implements GolfTournamentMapper<DataProviderOneModel> {

  private static final Logger log = LoggerFactory.getLogger(DataProviderOneMapper.class);

  private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yy");

  private final ObjectMapper objectMapper;

  public DataProviderOneMapper(final ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  public String getDataProviderId() {
    return "DATA-PROVIDER-1";
  }

  @Override
  public GolfTournament convert(final JsonNode json) throws MappingException {
    var incoming = this.parseToDto(json);

    var tournament = new GolfTournament();

    if (incoming == null) {
      return tournament;
    }

    tournament.setExternalId(incoming.tournamentId());
    tournament.setExternalSource(this.getDataProviderId());

    tournament.setCourseName(incoming.courseName());
    tournament.setName(incoming.tournamentName());
    tournament.setCountryCode(incoming.countryCode());
    tournament.setRounds(MapperUtils.parseRounds(incoming.roundCount()));

    // Attempt to parse dates
    tournament.setStartDate(MapperUtils.parseDate(incoming.startDate(), DATE_FORMAT));
    tournament.setEndDate(MapperUtils.parseDate(incoming.endDate(), DATE_FORMAT));

    log.debug("Created GolfTournament: {} from JSON: {}", tournament, json);

    return tournament;
  }

  @Override
  public DataProviderOneModel parseToDto(final JsonNode json) throws MappingException {
    try {
      return objectMapper.convertValue(json, this.getClazz());
    } catch (IllegalArgumentException e) {
      log.error("Failed to convert the data provider JSON: {} into designated DTO: {}", json,
          this.getClazz());
      throw new MappingException(e);
    }
  }

  @Override
  public Class<DataProviderOneModel> getClazz() {
    return DataProviderOneModel.class;
  }
}