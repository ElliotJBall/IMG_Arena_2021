package com.imgarena.coding.challenge.repository;

import com.imgarena.coding.challenge.domain.GolfTournament;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

/**
 * {@link GolfTournament} repository class.
 *
 * @author Elliot Ball
 */
public interface GolfTournamentRepository extends CrudRepository<GolfTournament, Long> {

  /**
   * Lookup a {@link GolfTournament} by a given external id.
   *
   * @param externalId the external data provider identifier
   * @return the {@link GolfTournament} if present, else empty optional
   */
  Optional<GolfTournament> findByExternalId(String externalId);

}
