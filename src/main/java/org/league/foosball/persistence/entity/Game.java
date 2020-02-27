package org.league.foosball.persistence.entity;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "GAME")
@Table(name = "GAME")
public class Game {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private Team team1;
    @ManyToOne
    private Team team2;
    private Integer scoreTeam1;
    private Integer scoreTeam2;
}
