package org.league.foosball.persistence.entity;

import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "SCORES")
@Table(name = "SCORES")
public class Score {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private Team team;
    @ManyToOne
    private Game game;
    private Integer score;
    private Boolean win;
}
