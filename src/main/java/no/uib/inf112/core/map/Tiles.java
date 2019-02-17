package no.uib.inf112.core.map;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import no.uib.inf112.core.RoboRally;

public enum Tiles {

    /* THE ROBOT (Group Player)*/
    PLAYERTILE(106, "Player"),

    /* THE TILE THAT ROBOTS CAN FALL THROUGH (Group Void)*/
    VOIDTILE(81,"Void"),

    /* CONSIDERED TO BE THE TILE A PLAYER CAN STAND ON WITHOUT FALLING THROUGH (Group Empty)*/
    EMPTYTILE(5,"Empty"),

    /* FLAGS (Group Flag)*/
    FLAG1(85,"Flag"),
    FLAG2(86,"Flag"),
    FLAG3(87,"Flag"),
    FLAG4(88,"Flag"),

    /* WALL TILES (Group Wall)*/
    WALLTOP(28, "Wall"),
    WALLTOPWITHLASER(40, "Wall"),
    WALLTOPWITHDOUBLELASER(83, "Wall"),
    WALLRIGHT(21, "Wall"),
    WALLRIGHTWITHLASER(41, "Wall"),
    WALLRIGHTWITHDOUBLELASER(84, "Wall"),
    WALLLEFT(27, "Wall"),
    WALLLEFTWITHLASER(34, "Wall"),
    WALLLEFTWITHDOUBLELASER(82, "Wall"),
    WALLBOTTOM(26, "Wall"),
    WALLBOTTOMWITHLASER(33, "Wall"),
    WALLBOTTOMWITHDOUBLELASER(77, "Wall"),

    /* STANDALONE LASERS (Group Laser)*/
    LASERVERTICAL(42, "Laser"),
    LASERHORIZONTAL(35, "Laser"),
    DOUBLELASERVERTICAL(90, "Laser"),
    DOUBLELASERHORIZONTAL(91, "Laser"),

    /**
     * OPTION TILES
     * (Group Option)
     */
    //Todo find better names
    HAMMERANDWRENCH(7,"Option"),
    WRENCH(14,"Option"),


    /**
     * CONVEYOR TILES
     * (Group Conveyor)
     * GO"Direction" is the conveyors that turn into an already straight conveyor
     */
    CONVEYORRIGHT(46,"Conveyor"),
    CONVEYORLEFT(45,"Conveyor"),
    CONVEYORUP(43,"Conveyor"),
    CONVEYORDOWN(44,"Conveyor"),
    CONVEYORFROMLEFTGOUP(50,"Conveyor"),
    CONVEYORFROMTOPGORIGHT(36,"Conveyor"),
    CONVEYORFROMRIGHTGODOWN(29,"Conveyor"),
    CONVEYORFROMBOTTOMGOLEFT(30,"Conveyor"),
    CONVEYORFROMTOPANDBOTTOMGORIGHT(54,"Conveyor"),
    CONVEYORFROMLEFTANDRIGHTGODOWN(55,"Conveyor"),
    CONVEYORFROMRIGHTGOUP(57,"Conveyor"),
    CONVEYORFROMBOTTOMGORIGHT(58,"Conveyor"),
    CONVEYORFROMLEFTGODOWN(59,"Conveyor"),
    CONVEYORFROMTOPGOLEFT(60,"Conveyor"),
    CONVEYORFROMRIGHTANDLEFTGOUP(61,"Conveyor"),
    CONVEYORFROMTOPANDBOTTOMGOLEFT(62,"Conveyor"),
    CONVEYORRIGHTDOUBLESTEP(13,"Conveyor"),
    CONVEYORLEFTDOUBLESTEP(20,"Conveyor"),
    CONVEYORUPDOUBLESTEP(12,"Conveyor"),
    CONVEYORDOWNDOUBLESTEP(19,"Conveyor"),
    CONVEYORFROMLEFTGOUPDOUBLESTEP(64,"Conveyor"),
    CONVEYORFROMTOPGORIGHTDOUBLESTEP(65,"Conveyor"),
    CONVEYORFROMRIGHTGODOWNDOUBLESTEP(66,"Conveyor"),
    CONVEYORFROMBOTTOMGOLEFTDOUBLESTEP(67,"Conveyor"),
    CONVEYORFROMTOPANDBOTTOMGORIGHTDOUBLESTEP(71,"Conveyor"),
    CONVEYORFROMLEFTANDRIGHTGODOWNDOUBLESTEP(72,"Conveyor"),
    CONVEYORFROMRIGHTGOUPDOUBLESTEP(68,"Conveyor"),
    CONVEYORFROMBOTTOMGORIGHTDOUBLESTEP(69,"Conveyor"),
    CONVEYORFROMLEFTGODOWNDOUBLESTEP(76,"Conveyor"),
    CONVEYORFROMTOPGOLEFTDOUBLESTEP(75,"Conveyor"),
    CONVEYORFROMRIGHTANDLEFTGOUPDOUBLESTEP(74,"Conveyor"),
    CONVEYORFROMTOPANDBOTTOMGOLEFTDOUBLESTEP(73,"Conveyor"),
    CONVEYORFROMRIGHTDOWN(29,"Conveyor"),
    CONVEYORFROMTOPRIGHT(36,"Conveyor"),
    CONVEYORFROMLEFTUP(37,"Conveyor"),
    CONVEYORFROMBOTTOMLEFT(30,"Conveyor"),
    CONVEYORFROMBOTTOMRIGTH(31,"Conveyor"),
    CONVEYORFROMLEFTDOWN(32,"Conveyor"),
    CONVEYORFROMTOPLEFT(39,"Conveyor"),
    CONVEYORFROMRIGHTUP(38,"Conveyor"),
    CONVEYORFROMRIGHTDOWNDOUBLESTEP(15,"Conveyor"),
    CONVEYORFROMTOPRIGHTDOUBLESTEP(22,"Conveyor"),
    CONVEYORFROMLEFTUPDOUBLESTEP(23,"Conveyor"),
    CONVEYORFROMBOTTOMLEFTDOUBLESTEP(16,"Conveyor"),
    CONVEYORFROMBOTTOMRIGTHDOUBLESTEP(17,"Conveyor"),
    CONVEYORFROMLEFTDOWNDOUBLESTEP(18,"Conveyor"),
    CONVEYORFROMTOPLEFTDOUBLESTEP(25,"Conveyor"),
    CONVEYORFROMRIGHTUPDOUBLESTEP(24,"Conveyor");


    private final int id;
    private String group;

    Tiles(int id, String group) {
        this.id = id;
        this.group = group;
    }

    public TiledMapTile getTile(Tiles tileName) {
        return RoboRally.getCurrentMap().getMapTileSets().getTileSet("tiles_tileset").getTile(tileName.id);
    }

    public TiledMapTile getPlayerTile() {
        return RoboRally.getCurrentMap().getMapTileSets().getTileSet("player_tileset").getTile(106);
    }
}

