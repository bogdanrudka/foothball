package org.league.foosball.team;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTeamDto {
    private Long left;
    private Long right;
}
