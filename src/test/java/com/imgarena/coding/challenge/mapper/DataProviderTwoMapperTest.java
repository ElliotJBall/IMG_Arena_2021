package com.imgarena.coding.challenge.mapper;

import static com.imgarena.coding.challenge.TestUtils.OBJECT_MAPPER;
import static com.imgarena.coding.challenge.TestUtils.loadJsonFile;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.imgarena.coding.challenge.exception.MappingException;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

/**
 * {@link DataProviderTwoMapper} test class.
 *
 * @author Elliot Ball
 */
class DataProviderTwoMapperTest {

  private final DataProviderTwoMapper mapper = new DataProviderTwoMapper(OBJECT_MAPPER);

  @Test
  void testCanConvertValidJson() throws Exception {
    // Given I have a valid JSON sample from the data provider 2
    var json = loadJsonFile("data-source-2.json");

    // When I run this through the mapper
    var golfTournament = mapper.convert(json);

    // Then I have correctly mapped the required fields to the tournament domain model
    assertThat(golfTournament).isNotNull();
    assertThat(golfTournament.getExternalId()).isEqualTo("southWestInvitational");
    assertThat(golfTournament.getExternalSource()).isEqualTo("DATA-PROVIDER-2");
    assertThat(golfTournament.getCourseName()).isEqualTo("Happy Days Golf Club");
    assertThat(golfTournament.getName()).isEqualTo("South West Invitational");
    assertThat(golfTournament.getCountryName()).isEqualTo("United States Of America");
    assertThat(golfTournament.getStartDate()).isEqualTo(LocalDate.of(2021, 12, 1));
    assertThat(golfTournament.getEndDate()).isEqualTo(LocalDate.of(2021, 12, 2));
    assertThat(golfTournament.getRounds()).isEqualTo(2);
  }

  @Test
  void testNullJsonPayloadResultsInEmptyRecord() {
    // Given I have a null JSON payload
    JsonNode json = null;

    // When I run this through the mapper
    var golfTournament = mapper.convert(json);

    // Then an empty golf tournament domain model is returned
    assertThat(golfTournament).isNotNull();
  }

  @Test
  void testInvalidJsonPayloadThrowsMappingException() throws Exception {
    // Given I have an invalid JSON payload
    var json = loadJsonFile("data-source-2.json");
    ((ObjectNode) json).put("rounds", "ABC"); // Simulate invalid rounds

    // When I run this through the mapper
    // Then a MappingException is thrown
    assertThatThrownBy(() -> mapper.convert(json)).isInstanceOf(MappingException.class);
  }
}