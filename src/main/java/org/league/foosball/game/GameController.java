package org.league.foosball.game;

import org.league.foosball.persistence.entity.Game;
import org.league.foosball.persistence.entity.Team;
import org.league.foosball.persistence.repository.GameRepository;
import org.league.foosball.persistence.repository.TeamRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
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
    public List<Game> getAll() {
        return gameRepository.findAll();
    }

    @PostMapping
    public Long recordGame(@RequestBody RecordGameDto record){
        List<Team> teams = teamRepository.findAllById(List.of(record.getTeam1(), record.getTeam2()));

        if (teams.size() != 2) {
            throw new IllegalStateException();
        }

        Map<Long, Team> groupedTeams = teams.stream().collect(
                Collectors.toMap(Team::getId, Function.identity()));

        Game game = new Game();
        game.setScoreTeam1(record.getScoreTeam1());
        game.setScoreTeam2(record.getScoreTeam2());

        game.setTeam_1(groupedTeams.get(record.getTeam1()));
        game.setTeam_2(groupedTeams.get(record.getTeam2()));

        return gameRepository.save(game).getId();
    }
}
