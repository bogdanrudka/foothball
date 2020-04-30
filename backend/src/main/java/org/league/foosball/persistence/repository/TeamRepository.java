package org.league.foosball.persistence.repository;

import org.league.foosball.persistence.entity.Player;
import org.league.foosball.persistence.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {
    List<Team> findAllByPlayers(Player player);
}
