package no.uib.inf112.core.multiplayer.jsonClasses;

import java.util.List;

public class NewGameDto {
    public final String map;
    public final List<PlayerDto> players;
    public int userId = -1;

    public NewGameDto(String mapFileName, List<PlayerDto> players, Integer hostId) {
        map = mapFileName;
        this.players = players;
        userId = hostId;
    }
}
