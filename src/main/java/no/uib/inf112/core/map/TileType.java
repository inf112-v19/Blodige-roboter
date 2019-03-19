package no.uib.inf112.core.map;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import no.uib.inf112.core.GameGraphics;

import java.util.HashMap;

public enum TileType {


    /**
     * THE ROBOT
     * (Group ROBOT)
     */
    ROBOT_TILE_NORTH(137, Group.ROBOT, "player-robot"),
    ROBOT_TILE_EAST(138, Group.ROBOT, "player-robot"),
    ROBOT_TILE_SOUTH(139, Group.ROBOT, "player-robot"),
    ROBOT_TILE_WEST(140, Group.ROBOT, "player-robot"),

    /**
     * THE TILES THAT ROBOTS CAN FALL THROUGH
     * (Group Void)
     */
    VOID_TILE(92, Group.VOID),
    VOID_WITH_WARNING_ALL_AROUND(91, Group.VOID),
    VOID_WITH_WARNING_TOP_AND_LEFT(105, Group.VOID),
    VOID_WITH_WARNING_TOP(106, Group.VOID),
    VOID_WITH_WARNING_TOP_AND_RIGHT(107, Group.VOID),
    VOID_WITH_WARNING_LEFT_AND_BOTTOM(113, Group.VOID),
    VOID_WITH_WARNING_RIGHT(108, Group.VOID),
    VOID_WITH_WARNING_LEFT(116, Group.VOID),
    VOID_WITH_WARNING_BOTTOM_AND_RIGHT(115, Group.VOID),
    VOID_WITH_WARING_NOT_LEFT(109, Group.VOID),
    VOID_WITH_WARING_NOT_RIGHT(117, Group.VOID),
    VOID_WITH_WARING_NOT_UP(110, Group.VOID),
    VOID_WITH_WARING_NOT_DOWN(118, Group.VOID),


    /**
     * CONSIDERED TO BE THE TILE A PLAYER CAN STAND ON WITHOUT FALLING THROUGH
     * (Group Default)
     */
    DEFAULT_TILE(5, Group.DEFAULT),

    /**
     * FLAG TILES
     * (Group Flag)
     */
    FLAG1(55, Group.FLAG),
    FLAG2(63, Group.FLAG),
    FLAG3(71, Group.FLAG),
    FLAG4(79, Group.FLAG),

    /**
     * SPAWN TILES
     * (Robots starting tiles)
     * (Group Spawn)
     */
    SPAWN1(121, Group.SPAWN),
    SPAWN2(122, Group.SPAWN),
    SPAWN3(123, Group.SPAWN),
    SPAWN4(124, Group.SPAWN),
    SPAWN5(129, Group.SPAWN),
    SPAWN6(130, Group.SPAWN),
    SPAWN7(131, Group.SPAWN),
    SPAWN8(132, Group.SPAWN),

    /**
     * WALL TILES
     * (Group Wall)
     */
    WALL_TOP(31, Group.WALL),
    WALL_TOP_WITH_LASER(45, Group.WALL),
    WALL_TOP_WITH_DOUBLE_LASER(94, Group.WALL),
    WALL_RIGHT(23, Group.WALL),
    WALL_RIGHT_WITH_LASER(46, Group.WALL),
    WALL_RIGHT_WITH_DOUBLE_LASER(95, Group.WALL),
    WALL_LEFT(30, Group.WALL),
    WALL_LEFT_WITH_LASER(38, Group.WALL),
    WALL_LEFT_WITH_DOUBLE_LASER(93, Group.WALL),
    WALL_BOTTOM(29, Group.WALL),
    WALL_BOTTOM_WITH_LASER(37, Group.WALL),
    WALL_BOTTOM_WITH_DOUBLE_LASER(87, Group.WALL),
    WALL_TOP_PUSH_DOWN_2_4(1, Group.WALL),
    WALL_RIGHT_PUSH_LEFT_2_4(2, Group.WALL),
    WALL_BOTTOM_PUSH_UP_2_4(3, Group.WALL),
    WALL_LEFT_PUSH_RIGHT_2_4(4, Group.WALL),
    WALL_TOP_PUSH_DOWN_1_3_5(9, Group.WALL),
    WALL_RIGHT_PUSH_LEFT_1_3_5(10, Group.WALL),
    WALL_BOTTOM_PUSH_UP_1_3_5(11, Group.WALL),
    WALL_LEFT_PUSH_RIGHT_1_3_5(12, Group.WALL),
    WALL_BOTTOM_RIGHT_CORNER(8, Group.WALL),
    WALL_TOP_RIGHT_CORNER(16, Group.WALL),
    WALL_TOP_LEFT_CORNER(24, Group.WALL),
    WALL_BOTTOM_LEFT_CORNER(32, Group.WALL),


    /**
     * STANDALONE LASERS
     * (Group Laser)
     */
    LASER_VERTICAL(47, Group.LASER),
    LASER_HORIZONTAL(39, Group.LASER),
    DOUBLE_LASER_VERTICAL(102, Group.LASER),
    DOUBLE_LASER_HORIZONTAL(103, Group.LASER),

    /**
     * OPTION TILES
     * (Group Option)
     */
    HAMMER_AND_WRENCH(7, Group.OPTION),
    WRENCH(15, Group.OPTION),

    /**
     * ROTATION TILES
     * (Group Rotation)
     */
    ROTATE_CLOCKWISE(54, Group.ROTATION),
    ROTATE_COUNTERCLOCKWISE(53, Group.ROTATION),

    /**
     * CONVEYOR TILES
     * (Group Conveyor)
     * GO"Direction" is the conveyors that turn into an already straight conveyor
     */
    CONVEYOR_RIGHT(52, Group.CONVEYOR),
    CONVEYOR_LEFT(51, Group.CONVEYOR),
    CONVEYOR_UP(49, Group.CONVEYOR),
    CONVEYOR_DOWN(50, Group.CONVEYOR),
    CONVEYOR_FROM_LEFT_GO_UP(57, Group.CONVEYOR),
    CONVEYOR_FROM_TOP_GO_RIGHT(58, Group.CONVEYOR),
    CONVEYOR_FROM_RIGHT_GO_DOWN(59, Group.CONVEYOR),
    CONVEYOR_FROM_BOTTOM_GO_LEFT(60, Group.CONVEYOR),
    CONVEYOR_FROM_TOP_AND_BOTTOM_GO_RIGHT(61, Group.CONVEYOR),
    CONVEYOR_FROM_LEFT_AND_RIGHT_GO_DOWN(62, Group.CONVEYOR),
    CONVEYOR_FROM_RIGHT_GO_UP(65, Group.CONVEYOR),
    CONVEYOR_FROM_BOTTOM_GO_RIGHT(66, Group.CONVEYOR),
    CONVEYOR_FROM_LEFT_GO_DOWN(67, Group.CONVEYOR),
    CONVEYOR_FROM_TOP_GO_LEFT(68, Group.CONVEYOR),
    CONVEYOR_FROM_RIGHT_AND_LEFT_GO_UP(69, Group.CONVEYOR),
    CONVEYOR_FROM_TOP_AND_BOTTOM_GO_LEFT(70, Group.CONVEYOR),
    CONVEYOR_RIGHT_DOUBLE_STEP(14, Group.CONVEYOR),
    CONVEYOR_LEFT_DOUBLE_STEP(22, Group.CONVEYOR),
    CONVEYOR_UP_DOUBLE_STEP(13, Group.CONVEYOR),
    CONVEYOR_DOWN_DOUBLE_STEP(21, Group.CONVEYOR),
    CONVEYOR_FROM_LEFT_GO_UP_DOUBLE_STEP(73, Group.CONVEYOR),
    CONVEYOR_FROM_TOP_GO_RIGHT_DOUBLE_STEP(74, Group.CONVEYOR),
    CONVEYOR_FROM_RIGHT_GO_DOWN_DOUBLE_STEP(75, Group.CONVEYOR),
    CONVEYOR_FROM_BOTTOM_GO_LEFT_DOUBLE_STEP(76, Group.CONVEYOR),
    CONVEYOR_FROM_TOP_AND_BOTTOM_GO_RIGHT_DOUBLE_STEP(81, Group.CONVEYOR),
    CONVEYOR_FROM_LEFT_AND_RIGHT_GO_DOWN_DOUBLE_STEP(82, Group.CONVEYOR),
    CONVEYOR_FROM_RIGHT_GO_UP_DOUBLE_STEP(77, Group.CONVEYOR),
    CONVEYOR_FROM_BOTTOM_GO_RIGHT_DOUBLE_STEP(78, Group.CONVEYOR),
    CONVEYOR_FROM_LEFT_GO_DOWN_DOUBLE_STEP(86, Group.CONVEYOR),
    CONVEYOR_FROM_TOP_GO_LEFT_DOUBLE_STEP(83, Group.CONVEYOR),
    CONVEYOR_FROM_RIGHT_AND_LEFT_GO_UP_DOUBLE_STEP(82, Group.CONVEYOR),
    CONVEYOR_FROM_TOP_AND_BOTTOM_GO_LEFT_DOUBLE_STEP(83, Group.CONVEYOR),
    CONVEYOR_FROM_RIGHT_TURN_DOWN(33, Group.CONVEYOR),
    CONVEYOR_FROM_TOP_TURN_RIGHT(41, Group.CONVEYOR),
    CONVEYOR_FROM_LEFT_TURN_UP(42, Group.CONVEYOR),
    CONVEYOR_FROM_BOTTOM_TURN_LEFT(34, Group.CONVEYOR),
    CONVEYOR_FROM_BOTTOM_TURN_RIGHT(35, Group.CONVEYOR),
    CONVEYOR_FROM_LEFT_TURN_DOWN(36, Group.CONVEYOR),
    CONVEYOR_FROM_TOP_TURN_LEFT(44, Group.CONVEYOR),
    CONVEYOR_FROM_RIGHT_TURN_UP(43, Group.CONVEYOR),
    CONVEYOR_FROM_RIGHT_TURN_DOWN_DOUBLE_STEP(19, Group.CONVEYOR),
    CONVEYOR_FROM_TOP_TURN_RIGHT_DOUBLE_STEP(25, Group.CONVEYOR),
    CONVEYOR_FROM_LEFT_TURN_UP_DOUBLE_STEP(26, Group.CONVEYOR),
    CONVEYOR_FROM_BOTTOM_TURN_LEFT_DOUBLE_STEP(18, Group.CONVEYOR),
    CONVEYOR_FROM_BOTTOM_TURN_RIGHT_DOUBLE_STEP(19, Group.CONVEYOR),
    CONVEYOR_FROM_LEFT_TURN_DOWN_DOUBLE_STEP(20, Group.CONVEYOR),
    CONVEYOR_FROM_TOP_TURN_LEFT_DOUBLE_STEP(28, Group.CONVEYOR),
    CONVEYOR_FROM_RIGHT_TURN_UP_DOUBLE_STEP(26, Group.CONVEYOR);


    private final int id;
    private final Group group;
    private String tilesetName;

    private static HashMap<Integer, TileType> TileIdMap = new HashMap<>();

    TileType(int id, Group group) {
        this(id, group, "tiles");
    }

    TileType(int id, Group group, String tilesetName) {
        this.id = id;
        this.group = group;
        this.tilesetName = tilesetName;
    }

    static {
        for (TileType value : values()) {
            TileIdMap.put(value.id, value);
        }
    }

    public static TileType fromTiledId(int id) {
        return TileIdMap.get(id);
    }

    public boolean isInGroup(Group group) {
        return this.group == group;
    }

    public TiledMapTile getTile() {
        return GameGraphics.getRoboRally().getCurrentMap().getMapTileSets().getTileSet(tilesetName).getTile(id);
    }

    public Group getGroup() {
        return group;
    }

    public int getId() {
        return id;
    }

    public enum Group {
        CONVEYOR,
        OPTION,
        WALL,
        FLAG,
        DEFAULT,
        VOID,
        LASER,
        SPAWN,
        ROBOT,
        ROTATION
    }
}

