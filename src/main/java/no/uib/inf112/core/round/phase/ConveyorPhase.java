package no.uib.inf112.core.round.phase;

import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.map.MapHandler;
import no.uib.inf112.core.map.tile.Attribute;
import no.uib.inf112.core.map.tile.TileType;
import no.uib.inf112.core.map.tile.api.MovableTile;
import no.uib.inf112.core.map.tile.api.Tile;
import no.uib.inf112.core.map.tile.tiles.ConveyorTile;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * The phase where conveyor move the {@link MovableTile}. If a conveyor has the {@link Attribute#HIGH_PRIORITY} it is considered
 * an express conveyor.
 * <p>
 * In reality it this phase is two phases. First one where only the express conveyor moves, then one where every conveyor moves.
 *
 * @author Elg
 */
public class ConveyorPhase extends AbstractPhase {

    public ConveyorPhase(long totalRunTime) {
        super(totalRunTime);
    }

    //TODO ISSUE #118 test, first all express conveyor moves, then **all** conveyors move
    @Override
    public void startPhase(@NotNull MapHandler map) {
        subPhase(map, false);
        //the next sub-phase must be after a bit of delay for it to properly work
        GameGraphics.scheduleSync(() -> subPhase(map, true), getRunTime() / 2);
    }

    private void subPhase(MapHandler map, boolean allConveyors) {
        for (int x = 0; x < map.getMapWidth(); x++) {
            for (int y = 0; y < map.getMapHeight(); y++) {
                Tile tile = map.getTile(MapHandler.BOARD_LAYER_NAME, x, y);


                //get a tile of the correct type
                if (tile != null && tile.getTileType() == TileType.CONVEYOR && (allConveyors || tile.hasAttribute(Attribute.HIGH_PRIORITY))) {
                    ConveyorTile conveyor = (ConveyorTile) tile;
                    List<Tile> tiles = map.getAllTiles(x, y);
                    for (Tile otherTile : tiles) {
                        if (conveyor.canDoAction(otherTile)) {
                            if (conveyor.action((MovableTile) otherTile)) {
                                conveyor.getActionSound().play();
                            }

                        }
                    }
                }
            }
        }
    }
}
