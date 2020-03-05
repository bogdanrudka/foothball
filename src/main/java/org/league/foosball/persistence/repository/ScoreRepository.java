package org.league.foosball.persistence.repository;

import org.league.foosball.persistence.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScoreRepository extends JpaRepository<Score, Long> {
    List<Score> findAllByTeam_IdIn(List<Long> id);
}
