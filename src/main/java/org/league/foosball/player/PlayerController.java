package org.league.foosball.player;

import org.league.foosball.persistence.entity.Player;
import org.league.foosball.persistence.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/players")
public class PlayerController {

    private PlayerRepository playerRepository;
    private ScoreService scoreService;

    @Autowired
    public PlayerController(PlayerRepository playerRepository, ScoreService scoreService) {
        this.playerRepository = playerRepository;
        this.scoreService = scoreService;
    }

    @GetMapping
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    @PostMapping
    public Long savePlayer(@RequestBody Player player) {
        return playerRepository.save(player).getId();
    }

    @GetMapping("/scores")
    List<ScoreDto> getScore(@RequestParam Set<Long> ids) {
        return scoreService.calculateScoreByPlayer(ids);
    }
}
