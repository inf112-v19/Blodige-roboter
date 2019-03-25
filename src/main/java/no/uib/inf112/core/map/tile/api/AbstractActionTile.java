package no.uib.inf112.core.map.tile.api;

import no.uib.inf112.core.map.tile.Attribute;
import no.uib.inf112.core.map.tile.TileGraphic;
import no.uib.inf112.core.util.Vector2Int;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author Elg
 */
public abstract class AbstractActionTile<T extends Tile> extends AbstractTile implements ActionTile<T> {

    public AbstractActionTile(@NotNull Vector2Int pos, @NotNull TileGraphic tg) {
        super(pos, tg);
    }

    //TODO test (this should look at all the required atts and check that hasAttributes return true on all of them)
    @Override
    public boolean canDoAction(@NotNull Tile tile) {
        if (this.equals(tile)) {
            return false;
        }
        List<Attribute> atts = requiredAttributes();
        return atts == null || atts.stream().allMatch(tile::hasAttribute);
    }
}
