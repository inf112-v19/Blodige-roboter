package no.uib.inf112.core.multiplayer.dtos;

import no.uib.inf112.core.map.cards.Card;

import java.util.List;

public class StartRoundDto {
    public final List<PlayerDto> players;
    public final List<CardDto> drawnCards;

    public StartRoundDto(List<PlayerDto> players, List<Card> drawnCards) {
        this.players = players;
        this.drawnCards = SelectedCardsDto.mapToDto(drawnCards);
    }

    public List<Card> getCards() {
        return SelectedCardsDto.mapFromDto(drawnCards);
    }

}
