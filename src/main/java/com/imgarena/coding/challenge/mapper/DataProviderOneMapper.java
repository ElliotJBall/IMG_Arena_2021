package com.imgarena.coding.challenge.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imgarena.coding.challenge.domain.GolfTournament;
import com.imgarena.coding.challenge.exception.MappingException;
import com.imgarena.coding.challenge.model.DataProviderOneModel;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Component;

/**
 * Data source one mapper. Handles the conversion of a {@link DataProviderOneModel} into the {@link
 * GolfTournament} domain entity.
 *
 * @author Elliot Ball
 */
@Component
public class DataProviderOneMapper extends AbstractGolfTournamentMapper<DataProviderOneModel> {

  private static final Map<String, Locale> ISO_CODE_TO_COUNTRY_NAME = new HashMap<>();

  public DataProviderOneMapper(final ObjectMapper objectMapper) {
    super(objectMapper);

    Arrays.stream(Locale.getAvailableLocales())
        .forEach(iso -> ISO_CODE_TO_COUNTRY_NAME.put(iso.getCountry(), iso));
  }

  public String getDataProviderId() {
    return "DATA-PROVIDER-1";
  }

  @Override
  public GolfTournament convert(final JsonNode json) throws MappingException {
    var dto = this.parseToDto(json);

    var tournament = new GolfTournament();
    tournament.setExternalId(dto.tournamentId());
    tournament.setExternalSource(this.getDataProviderId());

    tournament.setCourseName(dto.courseName());
    tournament.setName(dto.tournamentName());
    tournament.setCountryName(DataProviderOneMapper.parseCountryName(dto.countryCode()));
    tournament.setRounds(dto.roundCount());

    tournament.setStartDate(dto.startDate());
    tournament.setEndDate(dto.endDate());

    return tournament;
  }

  private static String parseCountryName(final String countryCode) {
    // The requirement around fetching the country is a little vague, not sure what we want
    // (country name or something such as the ISO code...) I'll go with country name for this challenge
    // but 'United States Of America' doesn't resolve to a Locale for me, looked for external libraries
    // that may help (https://github.com/TakahikoKawasaki/nv-i18n) but same problem.
    return Optional.ofNullable(ISO_CODE_TO_COUNTRY_NAME.get(countryCode.toUpperCase(Locale.ROOT)))
        .map(Locale::getDisplayCountry).orElse("UNKNOWN");
  }

  @Override
  public Class<DataProviderOneModel> getClazz() {
    return DataProviderOneModel.class;
  }
}