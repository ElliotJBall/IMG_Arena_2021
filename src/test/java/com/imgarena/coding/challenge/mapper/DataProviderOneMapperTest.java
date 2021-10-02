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
 * {@link DataProviderOneMapper} test class.
 *
 * @author Elliot Ball
 */
class DataProviderOneMapperTest {

  private final DataProviderOneMapper mapper = new DataProviderOneMapper(OBJECT_MAPPER);

  @Test
  void testCanConvertValidJson() throws Exception {
    // Given I have a valid JSON sample from the data provider 1
    var json = loadJsonFile("data-source-1.json");

    // When I run this through the mapper
    var golfTournament = mapper.convert(json);

    // Then I have correctly mapped the required fields to the tournament domain model
    assertThat(golfTournament).isNotNull();
    assertThat(golfTournament.getExternalId()).isEqualTo("174638");
    assertThat(golfTournament.getExternalSource()).isEqualTo("DATA-PROVIDER-1");
    assertThat(golfTournament.getCourseName()).isEqualTo("Sunnydale Golf Course");
    assertThat(golfTournament.getName()).isEqualTo("Women's Open Championship");
    assertThat(golfTournament.getCountryName()).isEqualTo("United Kingdom");
    assertThat(golfTournament.getStartDate()).isEqualTo(LocalDate.of(2021, 7, 9));
    assertThat(golfTournament.getEndDate()).isEqualTo(LocalDate.of(2021, 7, 13));
    assertThat(golfTournament.getRounds()).isEqualTo(4);
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
    var json = loadJsonFile("data-source-1.json");
    ((ObjectNode) json).put("startDate", "009/07/21"); // Simulate invalid date

    // When I run this through the mapper
    // Then a MappingException is thrown
    assertThatThrownBy(() -> mapper.convert(json)).isInstanceOf(MappingException.class);
  }
}