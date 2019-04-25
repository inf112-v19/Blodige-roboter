package no.uib.inf112.core.player;

import com.badlogic.gdx.graphics.Color;
import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.map.MapHandler;
import no.uib.inf112.core.map.cards.Card;
import no.uib.inf112.core.util.ComparableTuple;
import no.uib.inf112.core.util.Direction;
import org.jetbrains.annotations.NotNull;


public class NonPlayer extends AbstractPlayer {

    private Card[] deck;

    public NonPlayer(int x, int y, @NotNull Direction direction, @NotNull MapHandler map) {
        super(x, y, direction, map, new ComparableTuple<>("Blue", Color.BLUE));
    }


    @Override
    public ComparableTuple<Card, IPlayer> getNextCard(int id) {
        if (deck == null) {
            deck = GameGraphics.getRoboRally().getDeck().draw(MAX_PLAYER_CARDS);
        }
        return new ComparableTuple<>(deck[id], this);
    }

}
