package no.uib.inf112.core.player;

import com.badlogic.gdx.graphics.Color;
import no.uib.inf112.core.map.MapHandler;
import no.uib.inf112.core.map.cards.Card;
import no.uib.inf112.core.map.cards.MovementCard;
import no.uib.inf112.core.multiplayer.jsonClasses.CardDto;
import no.uib.inf112.core.util.ComparableTuple;
import no.uib.inf112.core.util.Direction;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class OnlinePlayer extends AbstractPlayer {

    private final int id;
    /**
     * @param x         Start x position
     * @param y         Start y position
     * @param direction Start direction
     * @param map       Current map
     */
    Card[] cards = new Card[IPlayer.MAX_PLAYER_CARDS];

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
        List<MovementCard> result = new ArrayList<>();
        for (CardDto card :
                cards) {
            result.add(new MovementCard(card.movement, card.priority));
        }
        //To array places cards in the provided array
        result.toArray(this.cards);
    }

    public int getId() {
        return id;
    }
}
