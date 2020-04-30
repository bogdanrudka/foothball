package org.league.foosball.score;

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
    private Long score;
    private Double pivot;
}
