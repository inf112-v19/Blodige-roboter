package no.uib.inf112.core.multiplayer.dtos;

import no.uib.inf112.core.map.cards.Card;
import no.uib.inf112.core.map.cards.MovementCard;
import no.uib.inf112.core.player.IPlayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SelectedCardsDto {
    public final List<CardDto> cards;
    public boolean poweredDown;

    public SelectedCardsDto(boolean poweredDown, List<Card> cards) {
        this.cards = mapToDto(cards);
        this.poweredDown = poweredDown;
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

    public static List<CardDto> drawRandomCards(List<CardDto> drawnCards) {
        Collections.shuffle(drawnCards);
        return drawnCards.subList(0, IPlayer.MAX_PLAYER_CARDS);
    }
}
