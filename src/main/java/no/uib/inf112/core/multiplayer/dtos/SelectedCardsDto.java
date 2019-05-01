package no.uib.inf112.core.multiplayer.dtos;

import no.uib.inf112.core.map.cards.Card;

import java.util.List;

public class SelectedCardsDto {
    public final List<CardDto> cards;
    public boolean poweredDown;

    public SelectedCardsDto(boolean poweredDown, List<Card> cards) {
        this.cards = DtoMapper.mapToDto(cards);
        this.poweredDown = poweredDown;
    }
}
