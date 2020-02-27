package org.league.foosball.persistence.entity;

import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "TEAM")
@Table(name = "TEAM")
public class Team {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private Player left;
    @ManyToOne
    private Player right;
}
