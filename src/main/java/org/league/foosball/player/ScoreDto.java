package org.league.foosball.player;

import lombok.*;

import javax.persistence.criteria.CriteriaBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScoreDto {
    private Integer total;
    private Integer totalPlayer;
    private Integer wins;
    private Integer loses;
    private Double score;
}
