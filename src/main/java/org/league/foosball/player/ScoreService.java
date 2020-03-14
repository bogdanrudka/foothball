package org.league.foosball.player;

import lombok.extern.slf4j.Slf4j;
import org.league.foosball.persistence.repository.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
class ScoreService {


    private PlayerRepository playerRepository;
    private ScoreRepository scoreRepository;
    private TeamRepository teamRepository;

    public ScoreService(PlayerRepository playerRepository, ScoreRepository scoreRepository, TeamRepository teamRepository) {
        this.playerRepository = playerRepository;
        this.scoreRepository = scoreRepository;
        this.teamRepository = teamRepository;
    }

    public List<ScoreDto> calculateScoreByPlayer(Set<Long> playerId) {
        log.info("Calculating scored for players {}", playerId);
        return playerRepository.findScoresByPlayerId(playerId).stream()
                .map(score -> ScoreDto.builder()
                        .playerId(score.getId())
                        .lastName(score.getLastName())
                        .firstName(score.getFistName())
                        .loses(score.getLoses())
                        .total(score.getWins() + score.getLoses())
                        .wins(score.getWins())
                        .score(score.getGoals())
                        .build()
                )
                .sorted(Comparator.comparing(ScoreDto::getScore).reversed())
                .collect(Collectors.toList());
    }
}
