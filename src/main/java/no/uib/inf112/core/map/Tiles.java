package no.uib.inf112.core.map;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import no.uib.inf112.core.RoboRally;

public enum Tiles {

    /**
     *  THE ROBOT
     *  (Group Player)
     */
    PLAYER_TILE(106, Group.Player),

    /**
     *  THE TILE THAT ROBOTS CAN FALL THROUGH
     *  (Group Void)
     */
    VOID_TILE(81,Group.Void),

    /**
     * CONSIDERED TO BE THE TILE A PLAYER CAN STAND ON WITHOUT FALLING THROUGH
     * (Group Empty)
     */
    EMPTY_TILE(5,Group.Empty),

    /**
     *  FLAGS
     *  (Group Flag)
     */
    //TODO fix id
    FLAG1(85,Group.Flag),
    FLAG2(86,Group.Flag),
    FLAG3(87,Group.Flag),
    FLAG4(88,Group.Flag),

    /**
     *  WALL TILES
     *  (Group Wall)
     */
    WALL_TOP(28, Group.Wall),
    WALL_TOP_WITH_LASER(40, Group.Wall),
    WALL_TOP_WITH_DOUBLE_LASER(83, Group.Wall),
    WALL_RIGHT(21, Group.Wall),
    WALL_RIGHT_WITH_LASER(41, Group.Wall),
    WALL_RIGHT_WITH_DOUBLE_LASER(84, Group.Wall),
    WALL_LEFT(27, Group.Wall),
    WALL_LEFT_WITH_LASER(34, Group.Wall),
    WALL_LEFT_WITH_DOUBLE_LASER(82, Group.Wall),
    WALL_BOTTOM(26, Group.Wall),
    WALL_BOTTOM_WITH_LASER(33, Group.Wall),
    WALL_BOTTOM_WITH_DOUBLE_LASER(77, Group.Wall),

    /**
     *  STANDALONE LASERS
     *  (Group Laser)
     */
    LASER_VERTICAL(42, Group.Laser),
    LASER_HORIZONTAL(35, Group.Laser),
    DOUBLE_LASER_VERTICAL(90, Group.Laser),
    DOUBLE_LASER_HORIZONTAL(91, Group.Laser),

    /**
     * OPTION TILES
     * (Group Option)
     */
    //Todo find better names
    HAMMER_AND_WRENCH(7, Group.Option),
    WRENCH(14, Group.Option),

    /**
     * CONVEYOR TILES
     * (Group Conveyor)
     * GO"Direction" is the conveyors that turn into an already straight conveyor
     */
    CONVEYOR_RIGHT(46, Group.Conveyor),
    CONVEYOR_LEFT(45, Group.Conveyor),
    CONVEYOR_UP(43, Group.Conveyor),
    CONVEYOR_DOWN(44, Group.Conveyor),
    CONVEYOR_FROM_LEFT_GO_UP(50, Group.Conveyor),
    CONVEYOR_FROM_TOP_GO_RIGHT(36, Group.Conveyor),
    CONVEYOR_FROM_RIGHT_GO_DOWN(29, Group.Conveyor),
    CONVEYOR_FROM_BOTTOM_GO_LEFT(30, Group.Conveyor),
    CONVEYOR_FROM_TOP_AND_BOTTOM_GO_RIGHT(54, Group.Conveyor),
    CONVEYOR_FROM_LEFT_AND_RIGHT_GO_DOWN(55, Group.Conveyor),
    CONVEYOR_FROM_RIGHT_GO_UP(57, Group.Conveyor),
    CONVEYOR_FROM_BOTTOM_GO_RIGHT(58, Group.Conveyor),
    CONVEYOR_FROM_LEFT_GO_DOWN(59, Group.Conveyor),
    CONVEYOR_FROM_TOP_GO_LEFT(60, Group.Conveyor),
    CONVEYOR_FROM_RIGHT_AND_LEFT_GO_UP(61, Group.Conveyor),
    CONVEYOR_FROM_TOP_AND_BOTTOM_GO_LEFT(62, Group.Conveyor),
    CONVEYOR_RIGHT_DOUBLESTEP(13, Group.Conveyor),
    CONVEYOR_LEFT_DOUBLESTEP(20, Group.Conveyor),
    CONVEYOR_UP_DOUBLESTEP(12, Group.Conveyor),
    CONVEYOR_DOWN_DOUBLESTEP(19, Group.Conveyor),
    CONVEYOR_FROM_LEFT_GO_UP_DOUBLESTEP(64, Group.Conveyor),
    CONVEYOR_FROM_TOP_GO_RIGHT_DOUBLESTEP(65, Group.Conveyor),
    CONVEYOR_FROM_RIGHT_GO_DOWN_DOUBLESTEP(66, Group.Conveyor),
    CONVEYOR_FROM_BOTTOM_GO_LEFT_DOUBLESTEP(67, Group.Conveyor),
    CONVEYOR_FROM_TOP_AND_BOTTOM_GO_RIGHT_DOUBLESTEP(71, Group.Conveyor),
    CONVEYOR_FROM_LEFT_AND_RIGHT_GO_DOWN_DOUBLESTEP(72, Group.Conveyor),
    CONVEYOR_FROM_RIGHT_GO_UP_DOUBLESTEP(68, Group.Conveyor),
    CONVEYOR_FROM_BOTTOM_GO_RIGHT_DOUBLESTEP(69, Group.Conveyor),
    CONVEYOR_FROM_LEFT_GO_DOWN_DOUBLESTEP(76, Group.Conveyor),
    CONVEYOR_FROM_TOP_GO_LEFT_DOUBLESTEP(75, Group.Conveyor),
    CONVEYOR_FROM_RIGHT_AND_LEFT_GO_UP_DOUBLESTEP(74, Group.Conveyor),
    CONVEYOR_FROM_TOP_AND_BOTTOM_GO_LEFT_DOUBLESTEP(73, Group.Conveyor),
    CONVEYOR_FROM_RIGHT_DOWN(29, Group.Conveyor),
    CONVEYOR_FROM_TOP_RIGHT(36, Group.Conveyor),
    CONVEYOR_FROM_LEFT_UP(37, Group.Conveyor),
    CONVEYOR_FROM_BOTTOM_LEFT(30, Group.Conveyor),
    CONVEYOR_FROM_BOTTOM_RIGTH(31, Group.Conveyor),
    CONVEYOR_FROM_LEFT_DOWN(32, Group.Conveyor),
    CONVEYOR_FROM_TOP_LEFT(39, Group.Conveyor),
    CONVEYOR_FROM_RIGHT_UP(38, Group.Conveyor),
    CONVEYOR_FROM_RIGHT_DOWN_DOUBLESTEP(15, Group.Conveyor),
    CONVEYOR_FROM_TOP_RIGHT_DOUBLESTEP(22, Group.Conveyor),
    CONVEYOR_FROM_LEFT_UP_DOUBLESTEP(23, Group.Conveyor),
    CONVEYOR_FROM_BOTTOM_LEFT_DOUBLESTEP(16, Group.Conveyor),
    CONVEYOR_FROM_BOTTOM_RIGTH_DOUBLESTEP(17, Group.Conveyor),
    CONVEYOR_FROM_LEFT_DOWN_DOUBLESTEP(18, Group.Conveyor),
    CONVEYOR_FROM_TOP_LEFT_DOUBLESTEP(25, Group.Conveyor),
    CONVEYOR_FROM_RIGHT_UP_DOUBLESTEP(24, Group.Conveyor);


    private final int id;
    private final Group group;

    Tiles(int id, Group group) {
        this.id = id;
        this.group = group;
    }

    public boolean isInGroup(Group Group){
        return this.group == group;
    }

    public TiledMapTile getTile(Tiles tileName) {
        return RoboRally.getCurrentMap().getMapTileSets().getTileSet("tiles_tileset").getTile(tileName.id);
    }

    public TiledMapTile getPlayerTile() {
        return RoboRally.getCurrentMap().getMapTileSets().getTileSet("player_tileset").getTile(PLAYER_TILE.id);
    }

    public enum Group{
        Conveyor,
        Option,
        Wall,
        Flag,
        Empty,
        Void,
        Laser,
        Player
    }
}

