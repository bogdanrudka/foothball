package org.league.foosball.persistence.repository;

import org.league.foosball.persistence.entity.Player;
import org.league.foosball.persistence.projection.ScoreTableProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface PlayerRepository extends JpaRepository<Player, Long> {

    @Query("SELECT new org.league.foosball.persistence.projection.ScoreTableProjection(" +
           "p.id, p.firstName, p.lastName, sum(s.score), " +
           "sum(case when s.win = true then 1 else 0 end), " +
           "sum(case when s.win = false then 1 else 0 end)" +
           ") " +
           "FROM PLAYERS p " +
           "inner join TEAM t on p member of t.players " +
           "inner join SCORES s on t = s.team " +
           "WHERE  p.id in :ids " +
           "group by p.firstName, p.lastName, p.id")
    List<ScoreTableProjection> findScoresByPlayerId(@Param("ids") Set<Long> ids);
}
