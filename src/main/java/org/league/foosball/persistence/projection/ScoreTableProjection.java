package org.league.foosball.persistence.projection;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ScoreTableProjection {
    private Long id;
    private String fistName;
    private String lastName;
    private Long goals;
    private Long wins;
    private Long loses;
}
