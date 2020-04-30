package org.league.foosball.persistence.repository;

import org.league.foosball.persistence.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {
}
