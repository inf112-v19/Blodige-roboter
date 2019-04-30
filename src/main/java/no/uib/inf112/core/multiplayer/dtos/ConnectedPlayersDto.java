package no.uib.inf112.core.multiplayer.dtos;

import java.util.List;

public class ConnectedPlayersDto {
    public final List<PlayerDto> players;

    public ConnectedPlayersDto(List<PlayerDto> players) {
        this.players = players;
    }
}
