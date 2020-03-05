package org.league.foosball.persistence.repository;

import org.league.foosball.persistence.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface GameRepository extends JpaRepository<Game, Long> {
}
