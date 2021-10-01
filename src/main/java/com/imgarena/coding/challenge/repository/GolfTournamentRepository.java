package com.imgarena.coding.challenge.repository;

import com.imgarena.coding.challenge.domain.GolfTournament;
import java.util.List;
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
   * @param externalSource the external data provider source identifier
   * @return the {@link GolfTournament} if present, else empty optional
   */
  Optional<GolfTournament> findByExternalIdAndExternalSource(String externalId,
      String externalSource);

  /**
   * Fetch all {@link GolfTournament} from the database.
   *
   * @return all the {@link GolfTournament}'s
   */
  @Override
  List<GolfTournament> findAll();
}
