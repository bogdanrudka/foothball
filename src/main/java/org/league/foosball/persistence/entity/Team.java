package org.league.foosball.persistence.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "TEAM")
@Table(name = "TEAMS")
public class Team {
    @Id
    @GeneratedValue
    private Long id;
    @OneToMany
    private List<Player> players;
}
