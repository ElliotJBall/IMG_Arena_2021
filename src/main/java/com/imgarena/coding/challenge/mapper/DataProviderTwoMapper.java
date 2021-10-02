package com.imgarena.coding.challenge.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imgarena.coding.challenge.domain.GolfTournament;
import com.imgarena.coding.challenge.exception.MappingException;
import com.imgarena.coding.challenge.model.DataProviderTwoModel;
import java.time.ZoneId;
import org.springframework.stereotype.Component;

/**
 * Data source two mapper. Handles the conversion of a {@link DataProviderTwoModel} into the {@link
 * GolfTournament} domain entity.
 *
 * @author Elliot Ball
 */
@Component
public class DataProviderTwoMapper extends AbstractGolfTournamentMapper<DataProviderTwoModel> {

  public DataProviderTwoMapper(final ObjectMapper objectMapper) {
    super(objectMapper);
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

    return tournament;
  }

  @Override
  public Class<DataProviderTwoModel> getClazz() {
    return DataProviderTwoModel.class;
  }
}
