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

  /**
   * Check if there is a mapper configured to handle the given data provider. Returns true if a
   * mapper exists for the given data source id.
   *
   * @param dataSourceId the data provider id
   * @return true if a mapper exists for the given data provider id, else false
   */
  public boolean canHandleDataSource(final String dataSourceId) {
    return dataProviderMappers.containsKey(dataSourceId);
  }

  /**
   * Converts the incoming golf tournament JSON for a given data provider into the {@link
   * GolfTournament} domain model.
   *
   * @param dataProviderId the data provider id
   * @param json the data provider golf tournament JSON
   * @return populated golf tournament optional if a mapper exists for the given data source id,
   * else empty optional
   */
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
    log.debug("Using mapper: {} for data provider id: {}", mapper, dataProviderId);

    // Convert to the domain model
    var golfTournament = mapper.convert(json);
    log.debug("Converted JSON: {} into golf tournament domain model: {}", json, golfTournament);

    // Determine if we have a new entity or an existing one
    repository.findByExternalIdAndExternalSource(golfTournament.getExternalId(),
            golfTournament.getExternalSource())
        .ifPresent(existing -> {
          log.debug("Updating existing tournament with foreign id: {} and foreign source: {}",
              existing.getExternalId(), existing.getExternalSource());

          golfTournament.setId(existing.getId());
        });

    // Persist and return
    return Optional.of(repository.save(golfTournament));
  }

  /**
   * Fetch all golf tournaments from the database.
   *
   * @return a list of all golf tournaments in the database
   */
  public List<GolfTournament> findAll() {
    return repository.findAll();
  }
}