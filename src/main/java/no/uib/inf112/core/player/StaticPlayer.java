package no.uib.inf112.core.player;

import com.badlogic.gdx.graphics.Color;
import no.uib.inf112.core.map.MapHandler;
import no.uib.inf112.core.map.cards.Card;
import no.uib.inf112.core.map.cards.Movement;
import no.uib.inf112.core.map.cards.MovementCard;
import no.uib.inf112.core.map.tile.Attribute;
import no.uib.inf112.core.map.tile.api.Tile;
import no.uib.inf112.core.util.ComparableTuple;
import no.uib.inf112.core.util.Direction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * A player that only spins
 *
 * @author Elg
 */
public class StaticPlayer extends AbstractPlayer {
    /**
     * @param x         Start x position
     * @param y         Start y position
     * @param direction Start direction
     * @param map       Current map
     */
    public StaticPlayer(int x, int y, @NotNull Direction direction, @NotNull MapHandler map) {
        super(x, y, direction, map, Color.GREEN);
    }

    @Override
    public ComparableTuple<Card, IPlayer> getNextCard(int id) {
        return new ComparableTuple<>(new MovementCard(Movement.RIGHT_TURN, 0), this);
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
