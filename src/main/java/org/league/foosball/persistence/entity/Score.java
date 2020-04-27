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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;
    private Integer score;
    private Boolean win;
    private Boolean active;
}
