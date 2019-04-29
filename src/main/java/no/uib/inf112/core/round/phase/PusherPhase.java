package no.uib.inf112.core.round.phase;

import no.uib.inf112.core.map.MapHandler;
import no.uib.inf112.core.map.tile.Attribute;
import no.uib.inf112.core.map.tile.TileType;
import no.uib.inf112.core.map.tile.api.MovableTile;
import no.uib.inf112.core.map.tile.api.Tile;
import no.uib.inf112.core.map.tile.tiles.PusherTile;
import no.uib.inf112.core.util.UVector2Int;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class PusherPhase extends AbstractStaticTilePhase {

    public PusherPhase(long totalRunTime) {
        super(totalRunTime, TileType.PUSHER);
    }

    @Override
    public void startPhase(@NotNull MapHandler map, int phaseNr) {
        for (Map.Entry<UVector2Int, Tile> entry : getTiles(map).entrySet()) {
            PusherTile tile = (PusherTile) entry.getValue();
            UVector2Int pos = entry.getKey();

            //TODO test correct pusher does push (and no incorrect pushers push)
            if ((phaseNr % 2 == 0 && tile.hasAttribute(Attribute.PUSH_ODD)) ||
                    (phaseNr % 2 != 0 && tile.hasAttribute(Attribute.PUSH_EVEN))) {
                continue;
            }

            Tile entTile = map.getTile(MapHandler.ENTITY_LAYER_NAME, pos.x, pos.y);


            if (tile.canDoAction(entTile)) {
                //noinspection ConstantConditions
                boolean pushed = tile.action((MovableTile) entTile);
                if (pushed) {
                    tile.getActionSound().play();
                }
            }

        }

    }
}
