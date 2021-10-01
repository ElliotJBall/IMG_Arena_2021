package com.imgarena.coding.challenge.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.imgarena.coding.challenge.domain.GolfTournament;
import com.imgarena.coding.challenge.mapper.GolfTournamentMapper;
import com.imgarena.coding.challenge.repository.GolfTournamentRepository;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Golf tournament service.
 *
 * @author Elliot Ball
 */
@Service
public class GolfTournamentService {

  private static final Logger log = LoggerFactory.getLogger(GolfTournamentService.class);

  private final Map<String, GolfTournamentMapper<?>> dataProviderMappers;
  private final GolfTournamentRepository repository;

  public GolfTournamentService(
      final List<GolfTournamentMapper<?>> mappers,
      final GolfTournamentRepository repository) {
    this.dataProviderMappers = mappers.stream()
        .collect(Collectors.toMap(GolfTournamentMapper::getDataProviderId,
            Function.identity()));
    this.repository = repository;
  }

  public boolean canHandleDataSource(final String dataSourceId) {
    return dataProviderMappers.containsKey(dataSourceId);
  }

  public Optional<GolfTournament> convert(final String dataProviderId,
      final JsonNode json) {
    if (!dataProviderMappers.containsKey(dataProviderId)) {
      log.warn(
          "Unknown data provider id, no mapper able to handle data provider id: {}",
          dataProviderId);
      return Optional.empty();
    }

    // Fetch the mapper for the given data provider
    var mapper = dataProviderMappers.get(dataProviderId);

    log.debug("Using mapper: {} to convert incoming JSON: {}", mapper, json);

    // Convert to the domain model
    var golfTournament = mapper.convert(json);

    // Determine if we have a new entity or an existing one
    repository.findByExternalId(golfTournament.getExternalId())
        .ifPresent(existing -> golfTournament.setId(existing.getId()));

    // Persist and return
    return Optional.of(repository.save(golfTournament));
  }
}