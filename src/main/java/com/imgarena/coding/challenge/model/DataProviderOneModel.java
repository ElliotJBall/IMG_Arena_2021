package com.imgarena.coding.challenge.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.time.LocalDate;
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

  @JsonFormat( shape = Shape.STRING, pattern = "dd/MM/yy" )
  LocalDate startDate();

  @JsonFormat( shape = Shape.STRING, pattern = "dd/MM/yy" )
  LocalDate endDate();

  int roundCount();
}
