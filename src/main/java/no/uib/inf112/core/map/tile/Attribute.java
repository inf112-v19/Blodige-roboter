package no.uib.inf112.core.map.tile;

import no.uib.inf112.core.map.tile.api.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Elg
 */
public enum Attribute {
    /**
     * Has the direction north
     *
     * @see no.uib.inf112.core.util.Direction#NORTH
     */
    DIR_NORTH(SingleDirectionalTile.class, MultiDirectionalTile.class),

    /**
     * Has the direction east
     *
     * @see no.uib.inf112.core.util.Direction#EAST
     */
    DIR_EAST(SingleDirectionalTile.class, MultiDirectionalTile.class),

    /**
     * Has the direction south
     *
     * @see no.uib.inf112.core.util.Direction#SOUTH
     */
    DIR_SOUTH(SingleDirectionalTile.class, MultiDirectionalTile.class),

    /**
     * Has the direction west
     *
     * @see no.uib.inf112.core.util.Direction#WEST
     */
    DIR_WEST(SingleDirectionalTile.class, MultiDirectionalTile.class),

    /**
     * Always run the action of this tile when moving over it
     */
    ACTIVE_ONLY_ON_STEP(ActionTile.class),

    /**
     * Mark this tile as a high priority tile (meaning is up to phase implementation)
     */
    HIGH_PRIORITY,

    /**
     * Mark this movable tile to be pushed if something moves onto it
     */
    PUSHABLE(MovableTile.class),

    /**
     * This tile shoots a laser
     */
    SHOOTS_LASER,

    /**
     * This tile rotates to the right
     */
    RIGHT(),

    /**
     * This tile rotates to the left
     */
    LEFT(),

    /**
     * This tile has some special directional condition to be fullfilled
     * e.g multi-directional conveyors
     */
    WEST_NORTH(), NORTH_EAST(), EAST_SOUTH(), SOUTH_WEST(), EAST_NORTH(), SOUTH_EAST(), WEST_SOUTH(), NORTH_WEST(),

    /**
     * This tile lays down a new  laser trail, its an extension for shoot_laser and should not occur together with it
     * meaning it puts down laser on the entitylaser layer
     * e.g robots
     */
    LAYS_DOWN_LASER,
    ;

    private final List<Class<? extends Tile>> requiredInterfaces;

    Attribute() {
        requiredInterfaces = new ArrayList<>();
    }

    /**
     * @param requiredInterfaces All interfaces that is accepted as a super class for a tile class with this attribute
     */
    @SafeVarargs
    Attribute(Class<? extends Tile>... requiredInterfaces) {
        this.requiredInterfaces = Arrays.asList(requiredInterfaces);
    }

    /**
     * TODO ISSUE #118 test if the given tile has the specified interfaces
     *
     * @param tileClass The tile class to check
     * @return {@code true} if the given class is an accepted super class for this attribute
     */
    public boolean verifyInterfaces(Class<? extends Tile> tileClass) {
        if (requiredInterfaces.isEmpty()) {
            return true;
        }
        return requiredInterfaces.stream().anyMatch(superClass -> superClass.isAssignableFrom(tileClass));
    }
}
