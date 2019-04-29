package no.uib.inf112.core.multiplayer.jsonClasses;

import no.uib.inf112.core.map.cards.Card;
import no.uib.inf112.core.map.cards.MovementCard;

import java.util.ArrayList;
import java.util.List;

public class SelectedCardsDto {
    public final List<CardDto> cards;

    public SelectedCardsDto(List<Card> cards) {
        this.cards = mapToDto(cards);
    }

    public static List<CardDto> mapToDto(List<Card> cards) {
        List<CardDto> result = new ArrayList<>();
        for (Card card :
                cards) {
            result.add(new CardDto(card.getAction(), card.getPriority()));
        }
        return result;
    }

    public static List<Card> mapFromDto(List<CardDto> drawnCards) {
        List<Card> result = new ArrayList<>();
        for (CardDto card :
                drawnCards) {
            result.add(new MovementCard(card.movement, card.priority));
        }
        return result;
    }
}
