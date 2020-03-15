package org.league.foosball.persistence.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "GAMES")
@Table(name = "GAMES")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Team> teams = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Score> scores = new ArrayList<>();
    private Boolean played = false;
}
