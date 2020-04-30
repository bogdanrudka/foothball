package org.league.foosball.persistence.projection;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.league.foosball.persistence.entity.Player;

import java.util.List;

@Data
@AllArgsConstructor
public class GamesUsersProjection {
    private Long gameId;
    private List<Player> t1Players;
    private String t1Score;
    private List<Player> t2Players;
    private String t2Score;
}
