package org.league.foosball.persistence.repository;

import org.league.foosball.persistence.entity.Game;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface GameRepository extends JpaRepository<Game, Long> {

    @Query("SELECT g FROM GAMES g inner join SCORES s on s member of g.scores " +
           "inner join TEAM t on t = s.team " +
           "inner join PLAYERS p on p member of t.players " +
           "where p.id in :ids")
    List<Game> findAllByPlayersIds(@Param("ids") Set<Long> playerIds);

    //TODO REALLY sloooooow query
    @Query("SELECT g FROM GAMES g inner join TEAM t on t member of g.teams " +
           "WHERE t in ( " +
           "SELECT t from TEAM t inner join PLAYERS p on p member of t.players " +
           "where p.id in :ids group by t.id having count(t) = 2" +
           ") group by g.id having count(g) = 2")
    @Cacheable("findAllMatchingGames")
    List<Game> findAllMatchingGames(@Param("ids") Set<Long> playerIds);

    //TODO REALLY sloooooow query
    @Query("SELECT g FROM GAMES g inner join TEAM t on t member of g.teams " +
           "WHERE g.played = :played and t in ( " +
           "SELECT t from TEAM t inner join PLAYERS p on p member of t.players " +
           "where p.id in :ids group by t.id having count(t) = 2" +
           ") group by g.id having count(g) = 2")
    @Cacheable("findAllMatchingGames")
    List<Game> findAllMatchingGamesAndActive(@Param("ids") Set<Long> playerIds, @Param("played") Boolean played);
}
