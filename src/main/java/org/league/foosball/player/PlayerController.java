package org.league.foosball.player;

import org.league.foosball.persistence.entity.Player;
import org.league.foosball.persistence.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/players")
public class PlayerController {

    private PlayerRepository playerRepository;

    @Autowired
    public PlayerController(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @GetMapping
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    @PostMapping
    public Long savePlayer(@RequestBody Player player) {
        return playerRepository.save(player).getId();
    }
}
