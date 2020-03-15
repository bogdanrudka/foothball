package org.league.foosball.game;

import org.league.foosball.persistence.entity.*;
import org.league.foosball.persistence.repository.*;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/games")
public class GameController {

    private GameRepository gameRepository;
    private TeamRepository teamRepository;

    public GameController(GameRepository gameRepository, TeamRepository teamRepository) {
        this.gameRepository = gameRepository;
        this.teamRepository = teamRepository;
    }

    @GetMapping
    public List<Game> getAll(@RequestParam Set<Long> players) {
        if (players.isEmpty()) {
            return gameRepository.findAll();
        } else {
            return gameRepository.findAllMatchingGames(players);
        }
    }

    @PostMapping
    @Transactional
    public Long recordGame(@RequestBody RecordGameDto record) {
        List<Team> teams = teamRepository.findAllById(List.of(record.getTeam1(), record.getTeam2()));

        if (teams.size() != 2) {
            throw new IllegalStateException();
        }

        Map<Long, Team> groupedTeams = teams.stream().collect(
                Collectors.toMap(Team::getId, Function.identity()));

        Game game = new Game();
        boolean winTeam1 = record.getScoreTeam1() > record.getScoreTeam2();
        game.getScores().add(Score.builder()
                .team(groupedTeams.get(record.getTeam1()))
                .score(record.getScoreTeam1())
                .win(winTeam1)
                .build());

        game.getScores().add(Score.builder()
                .team(groupedTeams.get(record.getTeam2()))
                .score(record.getScoreTeam2())
                .win(!winTeam1)
                .build());

        return gameRepository.save(game).getId();
    }
}
