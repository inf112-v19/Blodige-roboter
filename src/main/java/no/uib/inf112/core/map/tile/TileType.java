package no.uib.inf112.core.map.tile;

import no.uib.inf112.core.map.tile.api.Tile;
import no.uib.inf112.core.map.tile.tiles.*;
import no.uib.inf112.core.player.Robot;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static no.uib.inf112.core.map.tile.Attribute.ACTIVE_ONLY_ON_STEP;
import static no.uib.inf112.core.map.tile.Attribute.PUSHABLE;

/**
 * @author Elg
 */
public enum TileType {


    CONVEYOR(ConveyorTile.class),
    PUSHER(PusherTile.class),
    DEFAULT,
    FLAG(FlagTile.class),
    HAMMER_AND_WRENCH(WrenchAndHammerTile.class),
    ROBOT(Robot.class, PUSHABLE),
    GEAR(GearTile.class),
    SPAWN(SpawnTile.class),
    VOID(DeathTile.class, ACTIVE_ONLY_ON_STEP),
    WALL(WallTile.class),
    LASER(LaserTile.class),
    WRENCH(WrenchTile.class);

    private final Class<? extends Tile> implClass;
    private final Set<Attribute> attributes;

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
