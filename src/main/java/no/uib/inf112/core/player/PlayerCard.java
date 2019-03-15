package no.uib.inf112.core.player;

import no.uib.inf112.core.map.cards.Card;
import org.jetbrains.annotations.NotNull;

public class PlayerCard implements Comparable<PlayerCard> {
    private Card card;
    private Player player;

    public PlayerCard(Card card, Player player) {
        this.card = card;
        this.player = player;
    }

    public Card getCard() {
        return card;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public String toString() {
        return "PlayerCard{" +
                "player= " + player +
                ", card= " + card +
                '}';
    }

    @Override
    public int compareTo(@NotNull PlayerCard playerCard) {
        return card.compareTo(playerCard.card);
    }
}
