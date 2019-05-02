package no.uib.inf112.core.multiplayer.dtos;

import no.uib.inf112.core.map.cards.Card;
import no.uib.inf112.core.map.cards.MovementCard;
import no.uib.inf112.core.player.IPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A static class containing methods mapping between real instances and dtos
 */
public class DtoMapper {

    /**
     * Maps a list of Cards to a corresponding list of CardDto's
     *
     * @param cards list of Cards
     * @return the new list of dto's
     */
    @NotNull
    public static List<CardDto> mapToDto(@NotNull List<Card> cards) {
        List<CardDto> result = new ArrayList<>();
        for (Card card : cards) {
            result.add(new CardDto(card.getAction(), card.getPriority()));
        }
        return result;
    }

    /**
     * Maps a list of CardDto's to a corresponding list of MovementCards
     *
     * @param cards list of cards
     * @return the new list of cards
     */
    @NotNull
    public static List<Card> mapFromDto(@NotNull List<CardDto> cards) {
        List<Card> result = new ArrayList<>();
        for (CardDto card : cards) {
            result.add(new MovementCard(card.movement, card.priority));
        }
        return result;
    }

    /**
     * Select IPlayer.MAX_PLAYER_CARDS random cards from the given list of CardDto's
     *
     * @param drawnCards cards to draw from
     * @return list of selected cards
     */
    @NotNull
    public static List<CardDto> drawRandomCards(@NotNull List<CardDto> drawnCards) {
        Collections.shuffle(drawnCards);
        return drawnCards.subList(0, IPlayer.MAX_PLAYER_CARDS);
    }
}
