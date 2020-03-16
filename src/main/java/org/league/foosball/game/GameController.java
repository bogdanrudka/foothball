package org.league.foosball.game;

import org.league.foosball.exception.ResourceNotFountException;
import org.league.foosball.persistence.entity.*;
import org.league.foosball.persistence.repository.GameRepository;
import org.league.foosball.persistence.repository.TeamRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.accepted;

@RestController
@RequestMapping("/games")
public class GameController {

    private GameRepository gameRepository;
    private TeamRepository teamRepository;
    private EntityManagerFactory sessionFactory;

    public GameController(GameRepository gameRepository, TeamRepository teamRepository, EntityManagerFactory sessionFactory) {
        this.gameRepository = gameRepository;
        this.teamRepository = teamRepository;
        this.sessionFactory = sessionFactory;
    }

    @GetMapping
    public List<Game> getAll(@RequestParam Set<Long> players, @RequestParam(required = false) Boolean played) {
        if (players.isEmpty()) {
            return gameRepository.findAll();
        }
        if (played != null) {
            return gameRepository.findAllMatchingGamesAndActive(players, played);

        } else {
            return gameRepository.findAllMatchingGames(players);
        }
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity updateGame(@PathVariable Long id, @RequestBody RecordGameDto record) {
        Game game = gameRepository.findById(id)
                .orElseThrow(ResourceNotFountException::new);
        Map<Long, Score> collect = game.getScores().stream()
                .collect(Collectors.toMap(score -> score.getTeam().getId(), Function.identity()));
        Score score1 = collect.get(record.getIdTeam1());
        score1.setScore(record.getScoreTeam1());
        score1.setWin(record.getScoreTeam1() > record.getScoreTeam2());

        Score score2 = collect.get(record.getIdTeam2());
        score2.setScore(record.getScoreTeam1());
        score2.setWin(record.getScoreTeam1() < record.getScoreTeam2());

        game.setPlayed(true);

        gameRepository.saveAndFlush(game);
        sessionFactory.getCache().evictAll();
        return accepted().build();
    }

    @PostMapping
    @Transactional
    public Long recordGame(@RequestBody RecordGameDto record) {
        List<Team> teams = teamRepository.findAllById(List.of(record.getIdTeam1(), record.getIdTeam2()));

        if (teams.size() != 2) {
            throw new IllegalStateException();
        }

        Map<Long, Team> groupedTeams = teams.stream().collect(
                Collectors.toMap(Team::getId, Function.identity()));

        Game game = new Game();
        boolean winTeam1 = record.getScoreTeam1() > record.getScoreTeam2();
        game.getScores().add(Score.builder()
                .team(groupedTeams.get(record.getIdTeam1()))
                .score(record.getScoreTeam1())
                .win(winTeam1)
                .build());

        game.getScores().add(Score.builder()
                .team(groupedTeams.get(record.getIdTeam2()))
                .score(record.getScoreTeam2())
                .win(!winTeam1)
                .build());

        return gameRepository.save(game).getId();
    }
}
