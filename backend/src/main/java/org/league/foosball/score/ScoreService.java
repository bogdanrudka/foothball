package org.league.foosball.score;

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
    private GameRepository gameRepository;

    public ScoreService(PlayerRepository playerRepository, ScoreRepository scoreRepository, TeamRepository teamRepository, GameRepository gameRepository) {
        this.playerRepository = playerRepository;
        this.scoreRepository = scoreRepository;
        this.teamRepository = teamRepository;
        this.gameRepository = gameRepository;
    }

    public List<ScoreDto> calculateScoreByPlayer() {
        return scoreRepository.calculateScores().stream()
                .map(score -> ScoreDto.builder()
                        .playerId(score.getId())
                        .lastName(score.getLastName())
                        .firstName(score.getFistName())
                        .total(score.getTotal())
                        .wins(score.getWins())
                        .score(score.getGoals())
                        .pivot((score.getWins() * (100d / score.getTotal())))
                        .build()
                )
                .sorted(Comparator.comparing(ScoreDto::getScore).reversed())
                .collect(Collectors.toList());
    }
}
