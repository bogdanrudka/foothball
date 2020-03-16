package org.league.foosball.game;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
class RecordGameDto {
    private Long gameId;
    private Long idTeam1;
    private Long idTeam2;
    private Integer scoreTeam1;
    private Integer scoreTeam2;
}
