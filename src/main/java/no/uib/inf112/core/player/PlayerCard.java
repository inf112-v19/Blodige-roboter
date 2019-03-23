package no.uib.inf112.core.player;

import no.uib.inf112.core.map.cards.Card;
import org.jetbrains.annotations.NotNull;

public class PlayerCard implements Comparable<PlayerCard> {
    private Card card;
    private AbstractPlayer player;

    public PlayerCard(Card card, AbstractPlayer player) {
        this.card = card;
        this.player = player;
    }

    public Card getCard() {
        return card;
    }

    public AbstractPlayer getPlayer() {
        return player;
    }

    @Override
    public int compareTo(@NotNull PlayerCard playerCard) {
        return card.compareTo(playerCard.card);
    }
}
