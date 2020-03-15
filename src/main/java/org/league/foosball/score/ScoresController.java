package org.league.foosball.score;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/scores")
public class ScoresController {
    private ScoreService scoreService;

    public ScoresController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @GetMapping
    List<ScoreDto> getScore() {
        return scoreService.calculateScoreByPlayer();
    }
}
