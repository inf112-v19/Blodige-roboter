package no.uib.inf112.core.player;

import com.badlogic.gdx.graphics.Color;
import no.uib.inf112.core.map.MapHandler;
import no.uib.inf112.core.map.cards.Card;
import no.uib.inf112.core.map.cards.MovementCard;
import no.uib.inf112.core.multiplayer.dtos.CardDto;
import no.uib.inf112.core.util.ComparableTuple;
import no.uib.inf112.core.util.Direction;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class OnlinePlayer extends AbstractPlayer {

    private final int id;
    /**
     * @param x         Start x position
     * @param y         Start y position
     * @param direction Start direction
     * @param map       Current map
     */
    private Card[] cards = new Card[IPlayer.MAX_PLAYER_CARDS];

    public OnlinePlayer(int x, int y, @NotNull Direction direction, @NotNull MapHandler map, @NotNull ComparableTuple<String, Color> color, int id) {
        super(x, y, direction, map, color);
        this.id = id;
    }

    @Override
    public ComparableTuple<Card, IPlayer> getNextCard(int id) {
        if (id >= IPlayer.MAX_PLAYER_CARDS) {
            throw new IllegalArgumentException("Asked for card ID higher than max");
        } else if (id < 0) {
            throw new IllegalArgumentException("Asked for card ID less than zero");
        }
        return new ComparableTuple<>(cards[id], this);
    }

    public void setCards(List<CardDto> cards) {
        for (int i = 0; i < cards.size(); i++) {
            if (i < getHealth()) {
                CardDto cardDto = cards.get(i);
                this.cards[i] = new MovementCard(cardDto.movement, cardDto.priority);
            }
        }
    }

    @Override
    public int getId() {
        return id;
    }
}
