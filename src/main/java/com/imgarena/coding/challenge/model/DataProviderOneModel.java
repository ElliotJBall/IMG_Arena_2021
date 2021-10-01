package com.imgarena.coding.challenge.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

/**
 * Data source one DTO.
 *
 * @author Elliot Ball
 */
@Value.Immutable
@JsonDeserialize(builder = ImmutableDataProviderOneModel.Builder.class)
public interface DataProviderOneModel {

  String tournamentId();

  String tournamentName();

  String forecast();

  String courseName();

  String countryCode();

  String startDate();

  String endDate();

  String roundCount();
}
