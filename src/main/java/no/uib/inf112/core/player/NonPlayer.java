package no.uib.inf112.core.player;

import com.badlogic.gdx.graphics.Color;
import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.map.MapHandler;
import no.uib.inf112.core.map.cards.Card;
import no.uib.inf112.core.map.tile.Attribute;
import no.uib.inf112.core.map.tile.api.Tile;
import no.uib.inf112.core.util.ComparableTuple;
import no.uib.inf112.core.util.Direction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public class NonPlayer extends AbstractPlayer {

    private Card[] deck;

    public NonPlayer(int x, int y, @NotNull Direction direction, @NotNull MapHandler map) {
        super(x, y, direction, map, Color.BLUE);
    }


    @Override
    public ComparableTuple<Card, IPlayer> getNextCard(int id) {
        if (deck == null) {
            deck = GameGraphics.getRoboRally().getDeck().draw(MAX_PLAYER_CARDS);
        }
        return new ComparableTuple<>(deck[id], this);
    }


    @Override
    public void clean(@NotNull Tile tile) {
        poweredDown = false;
    }

    @Nullable
    @Override
    public List<Attribute> requiredAttributes() {
        return null;
    }

    @Nullable
    @Override
    public List<Class<? extends Tile>> requiredSuperClasses() {
        return null;
    }

    @Override
    public boolean canDoAction(@NotNull Tile tile) {
        return false;
    }
}
