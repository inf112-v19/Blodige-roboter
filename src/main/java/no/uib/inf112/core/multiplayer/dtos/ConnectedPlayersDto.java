package no.uib.inf112.core.multiplayer.dtos;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ConnectedPlayersDto {
    public final List<PlayerDto> players;

    public ConnectedPlayersDto(@NotNull List<PlayerDto> players) {
        this.players = players;
    }
}
