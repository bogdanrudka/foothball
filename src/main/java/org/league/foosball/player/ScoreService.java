package org.league.foosball.player;

import org.league.foosball.exception.ResourceNotFountException;
import org.league.foosball.persistence.entity.*;
import org.league.foosball.persistence.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
class ScoreService {


    private PlayerRepository playerRepository;
    private GameRepository gameRepository;
    private TeamRepository teamRepository;

    public ScoreService(PlayerRepository playerRepository, GameRepository gameRepository, TeamRepository teamRepository) {
        this.playerRepository = playerRepository;
        this.gameRepository = gameRepository;
        this.teamRepository = teamRepository;
    }

    public ScoreDto calculateScore(Long id) {
        Player player = playerRepository.findById(id)
                .orElseThrow(ResourceNotFountException::new);
        Set<Long> teams = teamRepository.findDistinctByLeft_IdOrRight_Id(player.getId(), player.getId())
                .stream()
                .map(Team::getId)
                .collect(Collectors.toSet());
        List<Game> games = gameRepository.findAllByTeam1_IdInOrTeam2_IdIn(teams, teams);
        return;
    }
}
