package no.uib.inf112.core.map.tile;

import no.uib.inf112.core.map.tile.api.Tile;
import no.uib.inf112.core.map.tile.tiles.*;
import no.uib.inf112.core.player.Robot;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static no.uib.inf112.core.map.tile.Attribute.ACTIVE_ONLY_ON_STEP;

/**
 * @author Elg
 */
public enum TileType {


    CONVEYOR(ConveyorTile.class),
    PUSHER,
    DEFAULT,
    FLAG,
    HAMMER_AND_WRENCH(WrenchAndHammerTile.class),
    LASER,
    ROBOT(Robot.class),
    GEAR,
    SPAWN,
    VOID(DeathTile.class, ACTIVE_ONLY_ON_STEP),
    WALL(WallTile.class),
    WRENCH(WrenchTile.class);

    private final Class<? extends Tile> implClass;
    private final Set<Attribute> attributes;

    //FIXME this should be removed when all types have an implantation
    TileType() {
        this(null);
    }

    TileType(Class<? extends Tile> implClass, Attribute... attributes) {

        this.implClass = implClass;

        HashSet<Attribute> tempSet = new HashSet<>();
        Collections.addAll(tempSet, attributes);
        this.attributes = Collections.unmodifiableSet(tempSet);
    }

    @NotNull
    public Set<Attribute> getAttributes() {
        return attributes;
    }

    public Class<? extends Tile> getImplClass() {
        return implClass;
    }
}
