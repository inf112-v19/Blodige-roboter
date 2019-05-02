package no.uib.inf112.core.map.tile;

import no.uib.inf112.core.map.MapHandler;
import no.uib.inf112.core.map.tile.api.Tile;
import no.uib.inf112.core.map.tile.tiles.*;
import no.uib.inf112.core.player.Robot;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static no.uib.inf112.core.map.tile.Attribute.*;

/**
 * @author Elg
 */
public enum TileType {


    CONVEYOR(MapHandler.BOARD_LAYER_NAME, ConveyorTile.class),
    ROTATION_CONVEYOR(MapHandler.BOARD_LAYER_NAME, RotationConveyor.class),
    PUSHER(MapHandler.BOARD_LAYER_NAME, PusherTile.class),
    DEFAULT(MapHandler.BOARD_LAYER_NAME),
    FLAG(MapHandler.FLAG_LAYER_NAME, FlagTile.class),
    HAMMER_AND_WRENCH(MapHandler.BOARD_LAYER_NAME, WrenchAndHammerTile.class),
    ROBOT(MapHandler.ENTITY_LAYER_NAME, Robot.class, PUSHABLE, LAYS_DOWN_LASER),
    GEAR(MapHandler.BOARD_LAYER_NAME, GearTile.class),
    SPAWN(MapHandler.BOARD_LAYER_NAME, SpawnTile.class),
    VOID(MapHandler.BOARD_LAYER_NAME, DeathTile.class, ACTIVE_ONLY_ON_STEP),
    WALL(MapHandler.COLLIDABLES_LAYER_NAME, WallTile.class),
    LASER(MapHandler.LASERS_LAYER_NAME, LaserTile.class),
    WRENCH(MapHandler.BOARD_LAYER_NAME, WrenchTile.class);

    private final String layerName;
    private final Class<? extends Tile> implClass;
    private final Set<Attribute> attributes;

    TileType(@NotNull String layerName) {
        this(layerName, null);
    }

    TileType(@NotNull String layerName, @Nullable Class<? extends Tile> implClass, Attribute... attributes) {
        this.layerName = layerName;
        this.implClass = implClass;

        HashSet<Attribute> tempSet = new HashSet<>();
        Collections.addAll(tempSet, attributes);
        this.attributes = Collections.unmodifiableSet(tempSet);
    }

    /**
     * These will be added to the concrete {@link TileGraphic}
     *
     * @return A  set of attributes this TileType has
     */
    @NotNull
    public Set<Attribute> getAttributes() {
        return attributes;
    }

    /**
     * @return The class that this TileType must implement
     */
    @Nullable
    public Class<? extends Tile> getImplClass() {
        return implClass;
    }

    /**
     * Note that this is not a hard rule, there might be TileTypes on other layers (see f.eks {@link MapHandler#LASERS_LAYER_NAME} and {@link MapHandler#ENITTY_LASER_LAYER_NAME}).
     *
     * @return The expected layer NAME of this tile type
     */
    @NotNull
    public String getLayerName() {
        return layerName;
    }
}
