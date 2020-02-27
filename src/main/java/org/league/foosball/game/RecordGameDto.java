package org.league.foosball.game;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
class RecordGameDto {
    private Long team1;
    private Long team2;
    private Integer scoreTeam1;
    private Integer scoreTeam2;
}
