package org.league.foosball.team;

import org.league.foosball.exception.ResourceNotFountException;
import org.league.foosball.persistence.entity.*;
import org.league.foosball.persistence.repository.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/teams")
public class TeamController {

    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;

    public TeamController(TeamRepository teamRepository, PlayerRepository playerRepository) {
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
    }


    @GetMapping
    List<Team> getAllByUser(@RequestParam(required = false) Long playerId) {
        if (playerId != null) {
            return teamRepository.findAllByPlayers(playerRepository.findById(playerId).orElseThrow(
                    ResourceNotFountException::new));
        } else {
            return teamRepository.findAll();
        }
    }

    @PostMapping
    public Long saveTeam(@RequestBody CreateTeamDto team) {
        List<Player> players = playerRepository.findAllById(List.of(team.getLeft(), team.getRight()));
        Team build = Team.builder()
                .players(players)
                .build();
        return teamRepository.save(build).getId();
    }

}
