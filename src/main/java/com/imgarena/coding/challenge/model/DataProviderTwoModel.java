package com.imgarena.coding.challenge.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.time.Instant;
import org.immutables.value.Value;

/**
 * Data source two DTO.
 *
 * @author Elliot Ball
 */
@Value.Immutable
@JsonDeserialize(builder = ImmutableDataProviderTwoModel.Builder.class)
public interface DataProviderTwoModel {

  @JsonProperty("tournamentUUID")
  String tournamentUuid();

  String golfCourse();

  String competitionName();

  String hostCountry();

  Instant epochStart();

  Instant epochFinish();

  int rounds();

  int playerCount();
}
