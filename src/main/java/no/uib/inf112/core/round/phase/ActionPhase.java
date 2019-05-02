package no.uib.inf112.core.round.phase;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.map.MapHandler;
import no.uib.inf112.core.map.tile.Attribute;
import no.uib.inf112.core.map.tile.TileType;
import no.uib.inf112.core.map.tile.api.ActionTile;
import no.uib.inf112.core.map.tile.api.MovableTile;
import no.uib.inf112.core.map.tile.api.Tile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Elg
 */
public class ActionPhase extends AbstractPhase {

    private final TileType tileType;
    private final boolean movableTile;
    @Nullable
    private final TiledMapTileLayer layer;

    private Set<ActionTile<?>> tiles;


    /**
     * @param tileType     The type of tile to run the actions of
     * @param totalRunTime The total run time in milliseconds
     * @throws IllegalArgumentException If {@code tileType} does not have an implementation class
     * @throws IllegalArgumentException If the implementation class of {@code tileType} does not implement {@link ActionTile}
     * @throws IllegalArgumentException If the implementation class of {@code tileType} has the attribute {@link Attribute#ACTIVE_ONLY_ON_STEP}
     */
    public ActionPhase(@NotNull TileType tileType, int totalRunTime) {
        super(totalRunTime);
        if (tileType.getImplClass() == null || !(ActionTile.class.isAssignableFrom(tileType.getImplClass()))) {
            throw new IllegalArgumentException("Given tile type '" + tileType + "' cannot run actions");
        } else if (tileType.getAttributes().contains(Attribute.ACTIVE_ONLY_ON_STEP)) {
            throw new IllegalArgumentException("Given tile can not have its own phase, as it is activated when stepped on");
        }

        this.tileType = tileType;

        movableTile = MovableTile.class.isAssignableFrom(tileType.getImplClass());
        layer = GameGraphics.getRoboRally().getCurrentMap().getLayer(tileType.getLayerName());

    }

    @Override
    public void startPhase(@NotNull MapHandler map, int phaseNr) {
        Set<ActionTile<?>> foundTiles = new HashSet<>();
        if (tiles == null) {
            for (int x = 0; x < map.getMapWidth(); x++) {
                for (int y = 0; y < map.getMapHeight(); y++) {

                    List<Tile> tiles;
                    if (layer == null) {
                        tiles = map.getAllTiles(x, y);
                    } else {
                        tiles = Collections.singletonList(map.getTile(layer, x, y));
                    }

                    for (Tile tile : tiles) {
                        //get a tile of the correct type
                        if (tile != null && tile.getTileType() == tileType) {
                            ActionTile<?> actionTile = (ActionTile<?>) tile;
                            if (movableTile) {
                                runActionOnTiles(actionTile, tiles);
                            } else {
                                foundTiles.add(actionTile);
                            }
                        }
                    }
                }
            }
            if (movableTile) {
                return;
            } else {
                tiles = foundTiles;
            }
        }

        for (ActionTile tile : tiles) {
            runActionOnTiles(tile, map.getAllTiles(tile.getX(), tile.getY()));
        }
    }

    private void runActionOnTiles(@NotNull ActionTile tile, List<Tile> tiles) {
        for (Tile otherTile : tiles) {
            if (!tile.equals(otherTile) && tile.canDoAction(otherTile)) {
                //noinspection unchecked checked in actionTile.canDoAction
                boolean success = tile.action(otherTile);
                if (success) {
                    tile.getActionSound().play();
                }
            }
        }

    }
}
