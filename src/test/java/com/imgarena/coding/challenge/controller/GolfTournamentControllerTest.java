package com.imgarena.coding.challenge.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.imgarena.coding.challenge.TestUtils;
import com.imgarena.coding.challenge.repository.GolfTournamentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

/**
 * {@link GolfTournamentController} test class.
 *
 * @author Elliot Ball
 */
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ComponentScan(basePackages = "com.imgarena.coding.challenge")
class GolfTournamentControllerTest {

  private static final String DATA_PROVIDER_HEADER = "X-DATA-SOURCE-ID";

  private static final String DATA_PROVIDER_ONE_ID = "DATA-PROVIDER-1";
  private static final String DATA_PROVIDER_TWO_ID = "DATA-PROVIDER-2";

  private static final String INVALID_DATA_PROVIDER_ID = "SOME-INVALID-DATA-PROVIDER-ID";

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private GolfTournamentController controller;

  @Autowired
  private GolfTournamentRepository repository;

  @Test
  void testThisWorks() throws Exception {
    mockMvc.perform(get("/golf/tournaments")).andExpect(status().isOk());
  }

  @AfterEach
  void teardown() {
    // Quickly just reset state after each test
    repository.deleteAll();
  }

  @Test
  void testCanProcessDataProviderOnePayload() throws Exception {
    var json = TestUtils.loadJsonFile("data-source-1.json");

    mockMvc.perform(post("/golf/tournaments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json.toString())
            .header(DATA_PROVIDER_HEADER, DATA_PROVIDER_ONE_ID))
        .andExpect(status().isOk());

    assertThat(
        repository.findByExternalIdAndExternalSource("174638", DATA_PROVIDER_ONE_ID)).isPresent();
  }

  @Test
  void testCanProcessDataProviderTwoPayload() throws Exception {
    var json = TestUtils.loadJsonFile("data-source-2.json");

    mockMvc.perform(post("/golf/tournaments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json.toString())
            .header(DATA_PROVIDER_HEADER, DATA_PROVIDER_TWO_ID))
        .andExpect(status().isOk());

    assertThat(
        repository.findByExternalIdAndExternalSource("southWestInvitational",
            DATA_PROVIDER_TWO_ID)).isPresent();
  }

  @Test
  void testCanResendSamePayloadAndExistingRecordIsUpdated() throws Exception {
    var json = TestUtils.loadJsonFile("data-source-1.json");

    mockMvc.perform(post("/golf/tournaments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json.toString())
            .header(DATA_PROVIDER_HEADER, DATA_PROVIDER_ONE_ID))
        .andExpect(status().isOk());

    assertThat(repository.findAll().size()).isEqualTo(1);

    // Update a field on the payload, change the country code
    ((ObjectNode) json).put("countryCode", "HR"); // Croatia

    mockMvc.perform(post("/golf/tournaments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json.toString())
            .header(DATA_PROVIDER_HEADER, DATA_PROVIDER_ONE_ID))
        .andExpect(status().isOk());

    assertThat(repository.findAll().size()).isEqualTo(1);

    var optGolfTournament =
        repository.findByExternalIdAndExternalSource("174638", DATA_PROVIDER_ONE_ID);

    assertThat(optGolfTournament).isPresent();
    assertThat(optGolfTournament.get().getCountryName()).isEqualTo("Croatia");
  }

  @Test
  void testParsedGolfTournamentIsReturnedOnSuccessfulProcess() throws Exception {
    var json = TestUtils.loadJsonFile("data-source-1.json");

    mockMvc.perform(post("/golf/tournaments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json.toString())
            .header(DATA_PROVIDER_HEADER, DATA_PROVIDER_ONE_ID))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.externalId", is("174638")))
        .andExpect(jsonPath("$.externalSource", is(DATA_PROVIDER_ONE_ID)))
        .andExpect(jsonPath("$.courseName", is("Sunnydale Golf Course")))
        .andExpect(jsonPath("$.name", is("Women's Open Championship")))
        .andExpect(jsonPath("$.countryName", is("United Kingdom")))
        .andExpect(jsonPath("$.startDate", is("2021-07-09")))
        .andExpect(jsonPath("$.endDate", is("2021-07-13")))
        .andExpect(jsonPath("$.rounds", is(4)));
  }

  @Test
  void testReceive400WhenPassingInvalidDataProviderId() throws Exception {
    // Mock empty node
    var json = (JsonNode) objectMapper.createObjectNode();

    mockMvc.perform(post("/golf/tournaments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json.toString())
            .header(DATA_PROVIDER_HEADER, INVALID_DATA_PROVIDER_ID))
        .andExpect(status().isBadRequest());
  }

  @Test
  void testReceive400WhenPassingInvalidJsonForValidDataProvider() throws Exception {
    var json = TestUtils.loadJsonFile("data-source-1.json");
    ((ObjectNode) json).put("startDate", "0999/07/21"); // Invalidate the date

    mockMvc.perform(post("/golf/tournaments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json.toString())
            .header(DATA_PROVIDER_HEADER, DATA_PROVIDER_ONE_ID))
        .andExpect(status().isBadRequest());
  }
}