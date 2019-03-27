package no.uib.inf112.core.map.tile;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.map.tile.api.Tile;
import no.uib.inf112.core.util.Vector2Int;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static no.uib.inf112.core.map.tile.Attribute.*;

/**
 * All textures and how to render them
 */
@SuppressWarnings("unused")
public enum TileGraphic {


    /**
     * THE ROBOT
     * (TileType ROBOT)
     */
    ROBOT_TILE_NORTH(137, "player-robot", TileType.ROBOT),
    ROBOT_TILE_EAST(138, "player-robot", TileType.ROBOT),
    ROBOT_TILE_SOUTH(139, "player-robot", TileType.ROBOT),
    ROBOT_TILE_WEST(140, "player-robot", TileType.ROBOT),

    /**
     * THE TILES THAT ROBOTS CAN FALL THROUGH
     * (TileType Void)
     */
    VOID_TILE(92, TileType.VOID),
    VOID_WITH_WARNING_ALL_AROUND(91, TileType.VOID),
    VOID_WITH_WARNING_TOP_AND_LEFT(105, TileType.VOID),
    VOID_WITH_WARNING_TOP(106, TileType.VOID),
    VOID_WITH_WARNING_TOP_AND_RIGHT(107, TileType.VOID),
    VOID_WITH_WARNING_LEFT_AND_BOTTOM(113, TileType.VOID),
    VOID_WITH_WARNING_RIGHT(108, TileType.VOID),
    VOID_WITH_WARNING_LEFT(116, TileType.VOID),
    VOID_WITH_WARNING_BOTTOM_AND_RIGHT(115, TileType.VOID),
    VOID_WITH_WARNING_NOT_LEFT(109, TileType.VOID),
    VOID_WITH_WARNING_NOT_RIGHT(117, TileType.VOID),
    VOID_WITH_WARNING_NOT_UP(110, TileType.VOID),
    VOID_WITH_WARNING_NOT_DOWN(118, TileType.VOID),


    /**
     * CONSIDERED TO BE THE TILE A PLAYER CAN STAND ON WITHOUT FALLING THROUGH
     * (TileType Default)
     */
    DEFAULT_TILE(5, TileType.DEFAULT),

    /**
     * FLAG TILES
     * (TileType Flag)
     */
    FLAG1(55, TileType.FLAG),
    FLAG2(63, TileType.FLAG),
    FLAG3(71, TileType.FLAG),
    FLAG4(79, TileType.FLAG),

    /**
     * SPAWN TILES
     * (Robots starting tiles)
     * (TileType Spawn)
     */
    SPAWN1(121, TileType.SPAWN),
    SPAWN2(122, TileType.SPAWN),
    SPAWN3(123, TileType.SPAWN),
    SPAWN4(124, TileType.SPAWN),
    SPAWN5(129, TileType.SPAWN),
    SPAWN6(130, TileType.SPAWN),
    SPAWN7(131, TileType.SPAWN),
    SPAWN8(132, TileType.SPAWN),

    /**
     * WALL TILES
     * (TileType Wall)
     */
    WALL_TOP(31, TileType.WALL, DIR_NORTH),
    WALL_TOP_WITH_LASER(45, TileType.WALL),
    WALL_TOP_WITH_DOUBLE_LASER(94, TileType.WALL),
    WALL_RIGHT(23, TileType.WALL, DIR_EAST),
    WALL_RIGHT_WITH_LASER(46, TileType.WALL),
    WALL_RIGHT_WITH_DOUBLE_LASER(95, TileType.WALL),
    WALL_LEFT(30, TileType.WALL, DIR_WEST),
    WALL_LEFT_WITH_LASER(38, TileType.WALL),
    WALL_LEFT_WITH_DOUBLE_LASER(93, TileType.WALL),
    WALL_BOTTOM(29, TileType.WALL, DIR_SOUTH),
    WALL_BOTTOM_WITH_LASER(37, TileType.WALL),
    WALL_BOTTOM_WITH_DOUBLE_LASER(87, TileType.WALL),
    WALL_TOP_PUSH_DOWN_2_4(1, TileType.WALL),
    WALL_RIGHT_PUSH_LEFT_2_4(2, TileType.WALL),
    WALL_BOTTOM_PUSH_UP_2_4(3, TileType.WALL),
    WALL_LEFT_PUSH_RIGHT_2_4(4, TileType.WALL),
    WALL_TOP_PUSH_DOWN_1_3_5(9, TileType.WALL),
    WALL_RIGHT_PUSH_LEFT_1_3_5(10, TileType.WALL),
    WALL_BOTTOM_PUSH_UP_1_3_5(11, TileType.WALL),
    WALL_LEFT_PUSH_RIGHT_1_3_5(12, TileType.WALL),
    WALL_BOTTOM_RIGHT_CORNER(8, TileType.WALL),
    WALL_TOP_RIGHT_CORNER(16, TileType.WALL),
    WALL_TOP_LEFT_CORNER(24, TileType.WALL),
    WALL_BOTTOM_LEFT_CORNER(32, TileType.WALL),


    /**
     * STANDALONE LASERS
     * (TileType Laser)
     */
    LASER_VERTICAL(47, TileType.LASER),
    LASER_HORIZONTAL(39, TileType.LASER),
    DOUBLE_LASER_VERTICAL(102, TileType.LASER),
    DOUBLE_LASER_HORIZONTAL(103, TileType.LASER),

    /**
     * OPTION TILES
     * (TileType Option)
     */
    HAMMER_AND_WRENCH(7, TileType.HAMMER_AND_WRENCH),
    WRENCH(15, TileType.WRENCH),

    /**
     * GEAR TILES
     * (TileType Rotation)
     */
    ROTATE_CLOCKWISE(54, TileType.GEAR, DIR_EAST),
    ROTATE_COUNTERCLOCKWISE(53, TileType.GEAR, DIR_WEST),

    /**
     * CONVEYOR TILES
     * (TileType Conveyor)
     * GO"Direction" is the conveyors that turn into an already straight conveyor
     */
    CONVEYOR_RIGHT(52, TileType.CONVEYOR, DIR_EAST),
    CONVEYOR_LEFT(51, TileType.CONVEYOR, DIR_WEST),
    CONVEYOR_UP(49, TileType.CONVEYOR, DIR_NORTH),
    CONVEYOR_DOWN(50, TileType.CONVEYOR, DIR_SOUTH),
    CONVEYOR_FROM_LEFT_GO_UP(57, TileType.CONVEYOR),
    CONVEYOR_FROM_TOP_GO_RIGHT(58, TileType.CONVEYOR),
    CONVEYOR_FROM_RIGHT_GO_DOWN(59, TileType.CONVEYOR),
    CONVEYOR_FROM_BOTTOM_GO_LEFT(60, TileType.CONVEYOR),
    CONVEYOR_FROM_TOP_AND_BOTTOM_GO_RIGHT(61, TileType.CONVEYOR),
    CONVEYOR_FROM_LEFT_AND_RIGHT_GO_DOWN(62, TileType.CONVEYOR),
    CONVEYOR_FROM_RIGHT_GO_UP(65, TileType.CONVEYOR),
    CONVEYOR_FROM_BOTTOM_GO_RIGHT(66, TileType.CONVEYOR),
    CONVEYOR_FROM_LEFT_GO_DOWN(67, TileType.CONVEYOR),
    CONVEYOR_FROM_TOP_GO_LEFT(68, TileType.CONVEYOR),
    CONVEYOR_FROM_RIGHT_AND_LEFT_GO_UP(69, TileType.CONVEYOR),
    CONVEYOR_FROM_TOP_AND_BOTTOM_GO_LEFT(70, TileType.CONVEYOR),
    CONVEYOR_FROM_RIGHT_TURN_DOWN(33, TileType.CONVEYOR),
    CONVEYOR_FROM_TOP_TURN_RIGHT(41, TileType.CONVEYOR),
    CONVEYOR_FROM_LEFT_TURN_UP(42, TileType.CONVEYOR),
    CONVEYOR_FROM_BOTTOM_TURN_LEFT(34, TileType.CONVEYOR),
    CONVEYOR_FROM_BOTTOM_TURN_RIGHT(35, TileType.CONVEYOR),
    CONVEYOR_FROM_LEFT_TURN_DOWN(36, TileType.CONVEYOR),
    CONVEYOR_FROM_TOP_TURN_LEFT(44, TileType.CONVEYOR),
    CONVEYOR_FROM_RIGHT_TURN_UP(43, TileType.CONVEYOR),

    //express
    CONVEYOR_RIGHT_EXPRESS(14, TileType.CONVEYOR, HIGH_PRIORITY, DIR_EAST),
    CONVEYOR_LEFT_EXPRESS(22, TileType.CONVEYOR, HIGH_PRIORITY, DIR_WEST),
    CONVEYOR_UP_EXPRESS(13, TileType.CONVEYOR, HIGH_PRIORITY, DIR_NORTH),
    CONVEYOR_DOWN_EXPRESS(21, TileType.CONVEYOR, HIGH_PRIORITY, DIR_SOUTH),
    CONVEYOR_FROM_LEFT_GO_UP_EXPRESS(73, TileType.CONVEYOR, HIGH_PRIORITY),
    CONVEYOR_FROM_TOP_GO_RIGHT_EXPRESS(74, TileType.CONVEYOR, HIGH_PRIORITY),
    CONVEYOR_FROM_RIGHT_GO_DOWN_EXPRESS(75, TileType.CONVEYOR, HIGH_PRIORITY),
    CONVEYOR_FROM_BOTTOM_GO_LEFT_EXPRESS(76, TileType.CONVEYOR, HIGH_PRIORITY),
    CONVEYOR_FROM_TOP_AND_BOTTOM_GO_RIGHT_EXPRESS(81, TileType.CONVEYOR, HIGH_PRIORITY),
    CONVEYOR_FROM_LEFT_AND_RIGHT_GO_DOWN_EXPRESS(82, TileType.CONVEYOR, HIGH_PRIORITY),
    CONVEYOR_FROM_RIGHT_GO_UP_EXPRESS(77, TileType.CONVEYOR, HIGH_PRIORITY),
    CONVEYOR_FROM_BOTTOM_GO_RIGHT_EXPRESS(78, TileType.CONVEYOR, HIGH_PRIORITY),
    CONVEYOR_FROM_LEFT_GO_DOWN_EXPRESS(86, TileType.CONVEYOR, HIGH_PRIORITY),
    CONVEYOR_FROM_TOP_GO_LEFT_EXPRESS(83, TileType.CONVEYOR, HIGH_PRIORITY),
    CONVEYOR_FROM_RIGHT_AND_LEFT_GO_UP_EXPRESS(82, TileType.CONVEYOR, HIGH_PRIORITY),
    CONVEYOR_FROM_TOP_AND_BOTTOM_GO_LEFT_EXPRESS(83, TileType.CONVEYOR, HIGH_PRIORITY),
    CONVEYOR_FROM_RIGHT_TURN_DOWN_EXPRESS(19, TileType.CONVEYOR, HIGH_PRIORITY),
    CONVEYOR_FROM_TOP_TURN_RIGHT_EXPRESS(25, TileType.CONVEYOR, HIGH_PRIORITY),
    CONVEYOR_FROM_LEFT_TURN_UP_EXPRESS(26, TileType.CONVEYOR, HIGH_PRIORITY),
    CONVEYOR_FROM_BOTTOM_TURN_LEFT_EXPRESS(18, TileType.CONVEYOR, HIGH_PRIORITY),
    CONVEYOR_FROM_BOTTOM_TURN_RIGHT_EXPRESS(19, TileType.CONVEYOR, HIGH_PRIORITY),
    CONVEYOR_FROM_LEFT_TURN_DOWN_EXPRESS(20, TileType.CONVEYOR, HIGH_PRIORITY),
    CONVEYOR_FROM_TOP_TURN_LEFT_EXPRESS(28, TileType.CONVEYOR, HIGH_PRIORITY),
    CONVEYOR_FROM_RIGHT_TURN_UP_EXPRESS(26, TileType.CONVEYOR, HIGH_PRIORITY),
    ;


    private final int id;
    private String tilesetName;
    @NotNull
    private final TileType tileType;
    private Set<Attribute> attributes;

    private static HashMap<Integer, TileGraphic> TileIdMap = new HashMap<>();

    static {
        for (TileGraphic value : values()) {
            TileIdMap.put(value.id, value);
        }
    }

    public static TileGraphic fromTiledId(int id) {
        return TileIdMap.get(id);
    }

    TileGraphic(int id, TileType tileType, Attribute... attributes) {
        this(id, "tiles", tileType, attributes);
    }

    TileGraphic(int id, String tilesetName, TileType tileType, Attribute... attributes) {
        this.id = id;
        this.tilesetName = tilesetName;
        this.tileType = tileType;

        HashSet<Attribute> tempSet = new HashSet<>();
        Collections.addAll(tempSet, attributes);
        tempSet.addAll(tileType.getAttributes());
        this.attributes = Collections.unmodifiableSet(tempSet);
    }

    public TiledMapTile getTile() {
        return GameGraphics.getRoboRally().getCurrentMap().getMapTileSets().getTileSet(tilesetName).getTile(id);
    }

    /**
     * @return List of all attributes from both the type and graphic
     */
    @NotNull
    public Set<Attribute> getAttributes() {
        return attributes;
    }

    public int getId() {
        return id;
    }

    @NotNull
    public TileType getTileType() {
        return tileType;
    }

    public Tile createInstance(int x, int y) {
        if (tileType.getImplClass() == null) {
            return null;
        }

        if (!tileType.getAttributes().stream().allMatch(att -> att.verifyInterfaces(tileType.getImplClass()))) {
            throw new IllegalStateException("TileType class (" + tileType.getImplClass() + ") does not have the required interface " + tileType.getAttributes());
        }

        try {
            Constructor<? extends Tile> constructor = tileType.getImplClass().getDeclaredConstructor(Vector2Int.class, TileGraphic.class);
            return constructor.newInstance(new Vector2Int(x, y), this);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}

