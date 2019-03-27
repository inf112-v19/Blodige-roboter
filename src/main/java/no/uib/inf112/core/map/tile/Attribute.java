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

//    //TODO these should be interfaces
//    MOVEABLE(MoveableTile.class),
//    HEALABLE(HealableTile.class),
//    BACKUPABLE(BackupTile.class),
//    COLORABLE(ColorTile.class),
//    COLLIDABLE(CollidableTile.class),
//    CLEAN(Cleanup.class),

    //always run the action of this tile when moving over it
    ACTIVE_ONLY_ON_STEP(ActionTile.class),
    //mark this tile as a high priority tile (meaning is up to phase implementation)
    HIGH_PRIORITY,
    CAN_BE_PUSHED(MovableTile.class),//mark this movable tile to be pushed if something moves onto it
    ;

    private final List<Class<? extends Tile>> requiredInterfaces;

    Attribute() {
        requiredInterfaces = new ArrayList<>();
    }

    @SafeVarargs
    Attribute(Class<? extends Tile>... requiredInterfaces) {
        this.requiredInterfaces = Arrays.asList(requiredInterfaces);
    }

    //TODO test if the given tile has the specified interfaces
    public boolean verifyInterfaces(Class<? extends Tile> tile) {
        if (requiredInterfaces.isEmpty()) {
            return true;
        }
        return requiredInterfaces.stream().anyMatch(superClass -> superClass.isAssignableFrom(tile));
    }
}
