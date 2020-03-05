package org.league.foosball.player;

import org.league.foosball.exception.ResourceNotFountException;
import org.league.foosball.persistence.entity.*;
import org.league.foosball.persistence.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
class ScoreService {


    private PlayerRepository playerRepository;
    private ScoreRepository scoreRepository;
    private TeamRepository teamRepository;

    public ScoreService(PlayerRepository playerRepository, ScoreRepository scoreRepository, TeamRepository teamRepository) {
        this.playerRepository = playerRepository;
        this.scoreRepository = scoreRepository;
        this.teamRepository = teamRepository;
    }

    public ScoreDto calculateScoreByPlayer(Long playerId) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(ResourceNotFountException::new);

        if (player == null) {
            throw new ResourceNotFountException();
        }

        List<Team> teams = teamRepository.findAllByPlayers(player);
        List<Score> scores =
                scoreRepository.findAllByTeam_IdIn(teams.stream().map(Team::getId).collect(Collectors.toList()));

        Integer totalGoals = Math.toIntExact(scores.stream()
                .filter(Score::getWin)
                .map(Score::getScore)
                .count());

        ScoreDto result = new ScoreDto();
        result.setWins(totalGoals);

        return result;
    }
}
