package no.uib.inf112.core.map;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import no.uib.inf112.core.RoboRally;

import java.util.HashMap;

public enum TileType {

    /**
     *  THE ROBOT
     *  (Group ROBOT)
     */
    ROBOT_TILE(106, Group.ROBOT, "player_tileset"),

    /**
     *  THE TILES THAT ROBOTS CAN FALL THROUGH
     *  (Group Void)
     */
    VOID_TILE(81, Group.VOID),
    VOID_WITH_WARNING_ALL_AROUND(80, Group.VOID),
    VOID_WITH_WARNING_TOP_AND_LEFT(92, Group.VOID),
    VOID_WITH_WARNING_TOP(93, Group.VOID),
    VOID_WITH_WARNING_TOP_AND_RIGHT(94, Group.VOID),
    VOID_WITH_WARNING_LEFT_AND_BOTTOM(99, Group.VOID),
    VOID_WITH_WARNING_RIGHT(100, Group.VOID),
    VOID_WITH_WARNING_BOTTOM_AND_RIGHT(101, Group.VOID),

    /**
     * CONSIDERED TO BE THE TILE A PLAYER CAN STAND ON WITHOUT FALLING THROUGH
     * (Group Default)
     */
    DEFAULT_TILE(5, Group.DEFAULT),

    /**
     *  FLAG TILES
     *  (Group Flag)
     */
    FLAG1(49, Group.FLAG),
    FLAG2(56, Group.FLAG),
    FLAG3(63, Group.FLAG),
    FLAG4(70, Group.FLAG),

    /**
     * SPAWN TILES
     * (Robots starting tiles)
     * (Group Spawn)
     */
    SPAWN1(85, Group.SPAWN),
    SPAWN2(86, Group.SPAWN),
    SPAWN3(87, Group.SPAWN),
    SPAWN4(88, Group.SPAWN),

    /**
     *  WALL TILES
     *  (Group Wall)
     */
    WALL_TOP(28, Group.WALL),
    WALL_TOP_WITH_LASER(40, Group.WALL),
    WALL_TOP_WITH_DOUBLE_LASER(83, Group.WALL),
    WALL_RIGHT(21, Group.WALL),
    WALL_RIGHT_WITH_LASER(41, Group.WALL),
    WALL_RIGHT_WITH_DOUBLE_LASER(84, Group.WALL),
    WALL_LEFT(27, Group.WALL),
    WALL_LEFT_WITH_LASER(34, Group.WALL),
    WALL_LEFT_WITH_DOUBLE_LASER(82, Group.WALL),
    WALL_BOTTOM(26, Group.WALL),
    WALL_BOTTOM_WITH_LASER(33, Group.WALL),
    WALL_BOTTOM_WITH_DOUBLE_LASER(77, Group.WALL),
    WALL_TOP_PUSH_DOWN_2_4(1, Group.WALL),
    WALL_RIGHT_PUSH_LEFT_2_4(2, Group.WALL),
    WALL_BOTTOM_PUSH_UP_2_4(3, Group.WALL),
    WALL_LEFT_PUSH_RIGHT_2_4(4, Group.WALL),
    WALL_TOP_PUSH_DOWN_1_3_5(8, Group.WALL),
    WALL_RIGHT_PUSH_LEFT_1_3_5(9, Group.WALL),
    WALL_BOTTOM_PUSH_UP_1_3_5(10, Group.WALL),
    WALL_LEFT_PUSH_RIGHT_1_3_5(11, Group.WALL),


    /**
     *  STANDALONE LASERS
     *  (Group Laser)
     */
    LASER_VERTICAL(42, Group.LASER),
    LASER_HORIZONTAL(35, Group.LASER),
    DOUBLE_LASER_VERTICAL(90, Group.LASER),
    DOUBLE_LASER_HORIZONTAL(91, Group.LASER),

    /**
     * OPTION TILES
     * (Group Option)
     */
    HAMMER_AND_WRENCH(7, Group.OPTION),
    WRENCH(14, Group.OPTION),

    /**
     * ROTATION TILES
     * (Group Rotation)
     */
    ROTATE_CLOCKWISE(48, Group.ROTATION),
    ROTATE_COUNTERCLOCKWISE(47, Group.ROTATION),

    /**
     * CONVEYOR TILES
     * (Group Conveyor)
     * GO"Direction" is the conveyors that turn into an already straight conveyor
     */
    CONVEYOR_RIGHT(46, Group.CONVEYOR),
    CONVEYOR_LEFT(45, Group.CONVEYOR),
    CONVEYOR_UP(43, Group.CONVEYOR),
    CONVEYOR_DOWN(44, Group.CONVEYOR),
    CONVEYOR_FROM_LEFT_GO_UP(50, Group.CONVEYOR),
    CONVEYOR_FROM_TOP_GO_RIGHT(36, Group.CONVEYOR),
    CONVEYOR_FROM_RIGHT_GO_DOWN(29, Group.CONVEYOR),
    CONVEYOR_FROM_BOTTOM_GO_LEFT(30, Group.CONVEYOR),
    CONVEYOR_FROM_TOP_AND_BOTTOM_GO_RIGHT(54, Group.CONVEYOR),
    CONVEYOR_FROM_LEFT_AND_RIGHT_GO_DOWN(55, Group.CONVEYOR),
    CONVEYOR_FROM_RIGHT_GO_UP(57, Group.CONVEYOR),
    CONVEYOR_FROM_BOTTOM_GO_RIGHT(58, Group.CONVEYOR),
    CONVEYOR_FROM_LEFT_GO_DOWN(59, Group.CONVEYOR),
    CONVEYOR_FROM_TOP_GO_LEFT(60, Group.CONVEYOR),
    CONVEYOR_FROM_RIGHT_AND_LEFT_GO_UP(61, Group.CONVEYOR),
    CONVEYOR_FROM_TOP_AND_BOTTOM_GO_LEFT(62, Group.CONVEYOR),
    CONVEYOR_RIGHT_DOUBLE_STEP(13, Group.CONVEYOR),
    CONVEYOR_LEFT_DOUBLE_STEP(20, Group.CONVEYOR),
    CONVEYOR_UP_DOUBLE_STEP(12, Group.CONVEYOR),
    CONVEYOR_DOWN_DOUBLE_STEP(19, Group.CONVEYOR),
    CONVEYOR_FROM_LEFT_GO_UP_DOUBLE_STEP(64, Group.CONVEYOR),
    CONVEYOR_FROM_TOP_GO_RIGHT_DOUBLE_STEP(65, Group.CONVEYOR),
    CONVEYOR_FROM_RIGHT_GO_DOWN_DOUBLE_STEP(66, Group.CONVEYOR),
    CONVEYOR_FROM_BOTTOM_GO_LEFT_DOUBLE_STEP(67, Group.CONVEYOR),
    CONVEYOR_FROM_TOP_AND_BOTTOM_GO_RIGHT_DOUBLE_STEP(71, Group.CONVEYOR),
    CONVEYOR_FROM_LEFT_AND_RIGHT_GO_DOWN_DOUBLE_STEP(72, Group.CONVEYOR),
    CONVEYOR_FROM_RIGHT_GO_UP_DOUBLE_STEP(68, Group.CONVEYOR),
    CONVEYOR_FROM_BOTTOM_GO_RIGHT_DOUBLE_STEP(69, Group.CONVEYOR),
    CONVEYOR_FROM_LEFT_GO_DOWN_DOUBLE_STEP(76, Group.CONVEYOR),
    CONVEYOR_FROM_TOP_GO_LEFT_DOUBLE_STEP(75, Group.CONVEYOR),
    CONVEYOR_FROM_RIGHT_AND_LEFT_GO_UP_DOUBLE_STEP(74, Group.CONVEYOR),
    CONVEYOR_FROM_TOP_AND_BOTTOM_GO_LEFT_DOUBLE_STEP(73, Group.CONVEYOR),
    CONVEYOR_FROM_RIGHT_DOWN(29, Group.CONVEYOR),
    CONVEYOR_FROM_TOP_RIGHT(36, Group.CONVEYOR),
    CONVEYOR_FROM_LEFT_UP(37, Group.CONVEYOR),
    CONVEYOR_FROM_BOTTOM_LEFT(30, Group.CONVEYOR),
    CONVEYOR_FROM_BOTTOM_RIGHT(31, Group.CONVEYOR),
    CONVEYOR_FROM_LEFT_DOWN(32, Group.CONVEYOR),
    CONVEYOR_FROM_TOP_LEFT(39, Group.CONVEYOR),
    CONVEYOR_FROM_RIGHT_UP(38, Group.CONVEYOR),
    CONVEYOR_FROM_RIGHT_DOWN_DOUBLE_STEP(15, Group.CONVEYOR),
    CONVEYOR_FROM_TOP_RIGHT_DOUBLE_STEP(22, Group.CONVEYOR),
    CONVEYOR_FROM_LEFT_UP_DOUBLE_STEP(23, Group.CONVEYOR),
    CONVEYOR_FROM_BOTTOM_LEFT_DOUBLE_STEP(16, Group.CONVEYOR),
    CONVEYOR_FROM_BOTTOM_RIGHT_DOUBLE_STEP(17, Group.CONVEYOR),
    CONVEYOR_FROM_LEFT_DOWN_DOUBLE_STEP(18, Group.CONVEYOR),
    CONVEYOR_FROM_TOP_LEFT_DOUBLE_STEP(25, Group.CONVEYOR),
    CONVEYOR_FROM_RIGHT_UP_DOUBLE_STEP(24, Group.CONVEYOR);


    private final int id;
    private final Group group;
    private String tilesetName;

    private static HashMap<Integer, TileType> TileIdMap = new HashMap<>();

    TileType(int id, Group group) {
        this(id, group, "tiles_tileset");
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
        return RoboRally.getCurrentMap().getMapTileSets().getTileSet(tilesetName).getTile(id);
    }

    public Group getGroup(){
        return this.group;
    }

    public int getId(){
        return this.id;
    }

    public enum Group{
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

