package no.uib.inf112.core.player;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;

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
    public int compareTo(@NotNull PlayerCard playerCard) {
        return this.card.compareTo(playerCard.card);
    }
}
