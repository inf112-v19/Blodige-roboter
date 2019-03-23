package no.uib.inf112.core.map.tile;

import no.uib.inf112.core.map.tile.api.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Elg
 */
public enum Attribute {
    DIR_NORTH(SingleDirectionalTile.class, MultiDirectionalTile.class),
    DIR_EAST(SingleDirectionalTile.class, MultiDirectionalTile.class),
    DIR_SOUTH(SingleDirectionalTile.class, MultiDirectionalTile.class),
    DIR_WEST(SingleDirectionalTile.class, MultiDirectionalTile.class),

    //TODO these should be interfaces
    MOVEABLE(MoveableTile.class),
    HEALABLE(HealableTile.class),
    BACKUPABLE(BackupTile.class),
    COLORABLE(ColorTile.class),


    ACTIVE_DURING_PHASE,
    ACTIVE_AFTER_PHASE;

    private final List<Class<? extends Tile>> requiredInterface;

    Attribute() {
        requiredInterface = new ArrayList<>();
    }

    Attribute(Class<? extends Tile>... requiredInterface) {
        this.requiredInterface = Arrays.asList(requiredInterface);
    }

    //TODO test if the given tile has the specified interfaces
    public boolean verifyInterfaces(Tile<? extends Tile> tile) {
        return requiredInterface.stream().anyMatch(superClass -> superClass.isAssignableFrom(tile.getClass()));
    }
}
