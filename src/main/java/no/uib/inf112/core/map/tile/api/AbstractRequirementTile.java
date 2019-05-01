package no.uib.inf112.core.map.tile.api;

import no.uib.inf112.core.map.tile.Attribute;
import no.uib.inf112.core.map.tile.TileGraphic;
import no.uib.inf112.core.util.Vector2Int;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author Elg
 */
public abstract class AbstractRequirementTile extends AbstractTile implements RequirementTile {

    public AbstractRequirementTile(@NotNull Vector2Int pos, @NotNull TileGraphic tg) {
        super(pos, tg);
    }

    @Override
    public boolean canDoAction(@NotNull Tile tile) {
        if (equals(tile) && !canRunOnSelf()) {
            return false;
        }
        List<Attribute> atts = requiredAttributes();
        if (atts != null && !atts.stream().allMatch(tile::hasAttribute)) {
            return false;
        }
        List<Class<? extends Tile>> interfaces = requiredSuperClasses();
        if (interfaces == null) {
            return true;
        }
        return interfaces.stream().allMatch(tile::hasSuperClass);
    }

    @Nullable
    @Override
    public List<Class<? extends Tile>> requiredSuperClasses() {
        return null;
    }

    @Nullable
    @Override
    public List<Attribute> requiredAttributes() {
        return null;
    }

    @Override
    public boolean canRunOnSelf() {
        return false;
    }
}
