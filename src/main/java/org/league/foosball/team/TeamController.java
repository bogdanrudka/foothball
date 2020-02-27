package org.league.foosball.team;

import org.league.foosball.persistence.entity.Player;
import org.league.foosball.persistence.entity.Team;
import org.league.foosball.persistence.repository.TeamRepository;
import org.league.foosball.persistence.repository.PlayerRepository;
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
    List<Team> getAllByUser(@RequestParam Long playerId){
        if(playerId!= null){
            return teamRepository.findDistinctByLeft_IdOrRight_Id(playerId, playerId);
        }else{
            return teamRepository.findAll();
        }
    }

    @PostMapping
    public Long saveTeam(@RequestBody CreateTeamDto team) {
        List<Player> players = playerRepository.findAllById(List.of(team.getLeft(), team.getRight()));
        return teamRepository.save(Team.builder()
                .left(players.get(0))
                .right(players.get(1))
                .build()
        ).getId();
    }

}
