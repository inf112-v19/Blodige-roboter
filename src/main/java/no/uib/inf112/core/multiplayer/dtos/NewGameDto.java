package no.uib.inf112.core.multiplayer.dtos;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NewGameDto {
    public final String map;
    public final List<PlayerDto> players;
    public int userId;

    public NewGameDto(String mapFileName, @NotNull List<PlayerDto> players, @NotNull Integer hostId) {
        map = mapFileName;
        this.players = players;
        userId = hostId;
    }
}
