package org.league.foosball.player;

import lombok.*;

import javax.persistence.criteria.CriteriaBuilder;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScoreDto {
    private Long id;
    private Long playerId;
    private String firstName;
    private String lastName;
    private Long total;
    private Integer totalPlayer;
    private Long wins;
    private Long loses;
    private Long score;
}
