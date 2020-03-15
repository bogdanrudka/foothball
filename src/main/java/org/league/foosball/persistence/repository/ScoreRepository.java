package org.league.foosball.persistence.repository;

import org.league.foosball.persistence.entity.Score;
import org.league.foosball.persistence.projection.ScoreTableProjection;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface ScoreRepository extends JpaRepository<Score, Long> {
    List<Score> findAllByTeam_IdIn(List<Long> id);

    @Query("SELECT new org.league.foosball.persistence.projection.ScoreTableProjection(" +
           "p.id, p.firstName, p.lastName, sum(s.score), " +
           "sum(case when s.win = true then 1 else 0 end), " +
           "count(s)" +
           ") " +
           "FROM PLAYERS p " +
           "inner join TEAM t on p member of t.players " +
           "inner join SCORES s on t = s.team " +
           "WHERE s.active = true " +
           "group by p.firstName, p.lastName, p.id")
    @Cacheable("findAllMatchingGames")
    List<ScoreTableProjection> calculateScores();
}
