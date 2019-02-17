package no.uib.inf112.core.map;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import no.uib.inf112.core.RoboRally;

public enum Tiles {

    /**
     *  THE ROBOT
     *  (Group Player)
     */
    PLAYERTILE(106, Group.Player),

    /**
     *  THE TILE THAT ROBOTS CAN FALL THROUGH
     *  (Group Void)
     */
    VOIDTILE(81,Group.Void),

    /**
     * CONSIDERED TO BE THE TILE A PLAYER CAN STAND ON WITHOUT FALLING THROUGH
     * (Group Empty)
     */
    EMPTYTILE(5,Group.Empty),

    /**
     *  FLAGS
     *  (Group Flag)
     */
    FLAG1(85,Group.Flag),
    FLAG2(86,Group.Flag),
    FLAG3(87,Group.Flag),
    FLAG4(88,Group.Flag),

    /**
     *  WALL TILES
     *  (Group Wall)
     */
    WALLTOP(28, Group.Wall),
    WALLTOPWITHLASER(40, Group.Wall),
    WALLTOPWITHDOUBLELASER(83, Group.Wall),
    WALLRIGHT(21, Group.Wall),
    WALLRIGHTWITHLASER(41, Group.Wall),
    WALLRIGHTWITHDOUBLELASER(84, Group.Wall),
    WALLLEFT(27, Group.Wall),
    WALLLEFTWITHLASER(34, Group.Wall),
    WALLLEFTWITHDOUBLELASER(82, Group.Wall),
    WALLBOTTOM(26, Group.Wall),
    WALLBOTTOMWITHLASER(33, Group.Wall),
    WALLBOTTOMWITHDOUBLELASER(77, Group.Wall),

    /* STANDALONE LASERS (Group Laser)*/
    LASERVERTICAL(42, Group.Laser),
    LASERHORIZONTAL(35, Group.Laser),
    DOUBLELASERVERTICAL(90, Group.Laser),
    DOUBLELASERHORIZONTAL(91, Group.Laser),

    /**
     * OPTION TILES
     * (Group Option)
     */
    //Todo find better names
    HAMMERANDWRENCH(7,Group.Option),
    WRENCH(14,Group.Option),


    /**
     * CONVEYOR TILES
     * (Group Conveyor)
     * GO"Direction" is the conveyors that turn into an already straight conveyor
     */
    CONVEYORRIGHT(46,Group.Conveyor),
    CONVEYORLEFT(45,Group.Conveyor),
    CONVEYORUP(43,Group.Conveyor),
    CONVEYORDOWN(44,Group.Conveyor),
    CONVEYORFROMLEFTGOUP(50,Group.Conveyor),
    CONVEYORFROMTOPGORIGHT(36,Group.Conveyor),
    CONVEYORFROMRIGHTGODOWN(29,Group.Conveyor),
    CONVEYORFROMBOTTOMGOLEFT(30,Group.Conveyor),
    CONVEYORFROMTOPANDBOTTOMGORIGHT(54,Group.Conveyor),
    CONVEYORFROMLEFTANDRIGHTGODOWN(55,Group.Conveyor),
    CONVEYORFROMRIGHTGOUP(57,Group.Conveyor),
    CONVEYORFROMBOTTOMGORIGHT(58,Group.Conveyor),
    CONVEYORFROMLEFTGODOWN(59,Group.Conveyor),
    CONVEYORFROMTOPGOLEFT(60,Group.Conveyor),
    CONVEYORFROMRIGHTANDLEFTGOUP(61,Group.Conveyor),
    CONVEYORFROMTOPANDBOTTOMGOLEFT(62,Group.Conveyor),
    CONVEYORRIGHTDOUBLESTEP(13,Group.Conveyor),
    CONVEYORLEFTDOUBLESTEP(20,Group.Conveyor),
    CONVEYORUPDOUBLESTEP(12,Group.Conveyor),
    CONVEYORDOWNDOUBLESTEP(19,Group.Conveyor),
    CONVEYORFROMLEFTGOUPDOUBLESTEP(64,Group.Conveyor),
    CONVEYORFROMTOPGORIGHTDOUBLESTEP(65,Group.Conveyor),
    CONVEYORFROMRIGHTGODOWNDOUBLESTEP(66,Group.Conveyor),
    CONVEYORFROMBOTTOMGOLEFTDOUBLESTEP(67,Group.Conveyor),
    CONVEYORFROMTOPANDBOTTOMGORIGHTDOUBLESTEP(71,Group.Conveyor),
    CONVEYORFROMLEFTANDRIGHTGODOWNDOUBLESTEP(72,Group.Conveyor),
    CONVEYORFROMRIGHTGOUPDOUBLESTEP(68,Group.Conveyor),
    CONVEYORFROMBOTTOMGORIGHTDOUBLESTEP(69,Group.Conveyor),
    CONVEYORFROMLEFTGODOWNDOUBLESTEP(76,Group.Conveyor),
    CONVEYORFROMTOPGOLEFTDOUBLESTEP(75,Group.Conveyor),
    CONVEYORFROMRIGHTANDLEFTGOUPDOUBLESTEP(74,Group.Conveyor),
    CONVEYORFROMTOPANDBOTTOMGOLEFTDOUBLESTEP(73,Group.Conveyor),
    CONVEYORFROMRIGHTDOWN(29,Group.Conveyor),
    CONVEYORFROMTOPRIGHT(36,Group.Conveyor),
    CONVEYORFROMLEFTUP(37,Group.Conveyor),
    CONVEYORFROMBOTTOMLEFT(30,Group.Conveyor),
    CONVEYORFROMBOTTOMRIGTH(31,Group.Conveyor),
    CONVEYORFROMLEFTDOWN(32,Group.Conveyor),
    CONVEYORFROMTOPLEFT(39,Group.Conveyor),
    CONVEYORFROMRIGHTUP(38,Group.Conveyor),
    CONVEYORFROMRIGHTDOWNDOUBLESTEP(15,Group.Conveyor),
    CONVEYORFROMTOPRIGHTDOUBLESTEP(22,Group.Conveyor),
    CONVEYORFROMLEFTUPDOUBLESTEP(23,Group.Conveyor),
    CONVEYORFROMBOTTOMLEFTDOUBLESTEP(16,Group.Conveyor),
    CONVEYORFROMBOTTOMRIGTHDOUBLESTEP(17,Group.Conveyor),
    CONVEYORFROMLEFTDOWNDOUBLESTEP(18,Group.Conveyor),
    CONVEYORFROMTOPLEFTDOUBLESTEP(25,Group.Conveyor),
    CONVEYORFROMRIGHTUPDOUBLESTEP(24,Group.Conveyor);


    private final int id;
    private Group group;

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
        return RoboRally.getCurrentMap().getMapTileSets().getTileSet("player_tileset").getTile(PLAYERTILE.id);
    }

    public enum Group{
        Conveyor,
        Option,
        Wall,
        Flag,
        Empty,
        Void,
        Laser,
        Player;
    }
}

