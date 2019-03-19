package no.uib.inf112.core.player;

import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.map.MapHandler;
import no.uib.inf112.core.map.cards.Card;
import org.jetbrains.annotations.NotNull;


public class NonPlayer extends Player {

    private Card[] deck;

    public NonPlayer(int x, int y, @NotNull Direction direction, MapHandler map) {
        super(x, y, direction, map);
    }


    @Override
    public PlayerCard getNextCard(int id) {
        if (deck == null) {
            deck = GameGraphics.getRoboRally().getDeck().draw(MAX_PLAYER_CARDS);
        }
        return new PlayerCard(deck[id], this);
    }
}
