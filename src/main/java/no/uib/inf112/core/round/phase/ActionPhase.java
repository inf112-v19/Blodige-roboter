package no.uib.inf112.core.round.phase;

import no.uib.inf112.core.map.MapHandler;
import no.uib.inf112.core.map.tile.Attribute;
import no.uib.inf112.core.map.tile.TileType;
import no.uib.inf112.core.map.tile.api.ActionTile;
import no.uib.inf112.core.map.tile.api.Tile;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author Elg
 */
public class ActionPhase extends AbstractPhase {

    private final TileType tileType;
    private final long totalRunTime; //delay till next phase

    /**
     * @param tileType
     * @param totalRunTime
     */
    public ActionPhase(@NotNull TileType tileType, int totalRunTime) {
        super(totalRunTime);
        if (tileType.getImplClass() == null || !(ActionTile.class.isAssignableFrom(tileType.getImplClass()))) {
            throw new IllegalArgumentException("Given tile type '" + tileType + "' cannot run actions");
        } else if (tileType.getAttributes().contains(Attribute.ACTIVE_ONLY_ON_STEP)) {
            throw new IllegalArgumentException("Given tile can not have its own phase, as it is activated when stepped on");
        }

        this.tileType = tileType;
        this.totalRunTime = totalRunTime;
    }

    @Override
    public void startPhase(@NotNull MapHandler map) {
        for (int x = 0; x < map.getMapWidth(); x++) {
            for (int y = 0; y < map.getMapHeight(); y++) {
                List<Tile> tiles = map.getAllTiles(x, y);

                for (Tile tile : tiles) {
                    //get a tile of the correct type
                    if (tile != null && tile.getTileType() == tileType) {
                        ActionTile actionTile = (ActionTile) tile;
                        for (Tile otherTile : tiles) {

                            if (!otherTile.equals(tile) && actionTile.canDoAction(otherTile)
                                    && actionTile.action(otherTile)) {
                                //noinspection unchecked checked in actionTile.canDoAction
                                actionTile.getActionSound().play();
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Phase{" + "tileType=" + tileType +
                ", totalRunTime=" + totalRunTime +
                '}';
    }
}
