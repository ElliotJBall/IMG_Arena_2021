package com.imgarena.coding.challenge.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imgarena.coding.challenge.domain.GolfTournament;
import com.imgarena.coding.challenge.exception.MappingException;
import com.imgarena.coding.challenge.model.DataProviderTwoModel;
import java.time.ZoneId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Data source two mapper. Handles the conversion of a {@link DataProviderTwoModel} into the {@link
 * GolfTournament} domain entity.
 *
 * @author Elliot Ball
 */
@Component
public class DataProviderTwoMapper implements GolfTournamentMapper<DataProviderTwoModel> {

  private static final Logger log = LoggerFactory.getLogger(DataProviderTwoMapper.class);

  private final ObjectMapper objectMapper;

  public DataProviderTwoMapper(final ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public String getDataProviderId() {
    return "DATA-PROVIDER-2";
  }

  @Override
  public GolfTournament convert(final JsonNode json) throws MappingException {
    var dto = this.parseToDto(json);

    var tournament = new GolfTournament();

    if (dto == null) {
      return tournament;
    }

    tournament.setExternalId(dto.tournamentUuid());
    tournament.setExternalSource(this.getDataProviderId());

    tournament.setCourseName(dto.golfCourse());
    tournament.setName(dto.competitionName());
    tournament.setCountryName(dto.hostCountry());
    tournament.setRounds(dto.rounds());

    // Just assuming UTC here, I think using systemDefault would lead to odd behaviour with
    // my local timezone (BST) outside a container and UTC inside one
    tournament.setStartDate(dto.epochStart().atZone(ZoneId.of("UTC")).toLocalDate());
    tournament.setEndDate(dto.epochFinish().atZone(ZoneId.of("UTC")).toLocalDate());

    log.debug("Created GolfTournament: {} from JSON: {}", tournament, json);

    return tournament;
  }

  @Override
  public DataProviderTwoModel parseToDto(final JsonNode json) throws MappingException {
    try {
      return objectMapper.convertValue(json, this.getClazz());
    } catch (IllegalArgumentException e) {
      log.error("Failed to convert the data provider JSON: {} into designated DTO: {}", json,
          this.getClazz(), e);
      throw new MappingException(e);
    }
  }

  @Override
  public Class<DataProviderTwoModel> getClazz() {
    return DataProviderTwoModel.class;
  }
}
