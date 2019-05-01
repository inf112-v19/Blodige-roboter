package no.uib.inf112.core.map;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.RoboRally;
import no.uib.inf112.core.map.tile.TileType;
import no.uib.inf112.core.map.tile.api.Tile;
import no.uib.inf112.core.player.IPlayer;
import no.uib.inf112.core.player.Player;
import no.uib.inf112.core.util.Direction;
import no.uib.inf112.desktop.TestGraphics;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class GameMapTest extends TestGraphics {

    private RoboRally roboRally;
    private IPlayer player;

    @Before
    public void setUp(){
        roboRally = GameGraphics.createRoboRally
                (TEST_MAP_FOLDER + File.separatorChar + "player_hole_test_map.tmx", 1);
        player = roboRally.getPlayerHandler().testPlayer();
    }

    @Test
    public void removeEntityRemovesEntity(){
        Player player2 = new Player(0, 2, Direction.NORTH, roboRally.getCurrentMap());

        //getAllTiles will only return a robot on this map(player_hole_test_map.tmx).
        List<Tile> listPlayer1 = roboRally.getCurrentMap().getAllTiles(0,0);
        assertEquals(1, listPlayer1.size());

        roboRally.getCurrentMap().addEntity(player2);
        List<Tile> listPlayer2 = roboRally.getCurrentMap().getAllTiles(0, 2);
        assertEquals(1, listPlayer2.size());

        roboRally.getCurrentMap().removeEntity(player);
        listPlayer1 = roboRally.getCurrentMap().getAllTiles(0,0);
        assertEquals(0, listPlayer1.size());

        roboRally.getCurrentMap().removeEntity(player2);
        listPlayer2 = roboRally.getCurrentMap().getAllTiles(0,2);
        assertEquals(0, listPlayer2.size());
    }

    @Test
    public void getTileTest(){
        TiledMapTileLayer layer = roboRally.getCurrentMap().getLayer("board");

        Tile tile = roboRally.getCurrentMap().getTile(layer, 0,1);

        assertEquals(TileType.VOID, tile.getTileType());
    }

    @Test
    public void getAllTilesTest(){
        //Very bad test, but it works.

        List<Tile> list = roboRally.getCurrentMap().getAllTiles(0, 0);
        System.out.println(list.size());

        assertEquals(TileType.ROBOT, list.get(0).getTileType());

        list = roboRally.getCurrentMap().getAllTiles(0, 1);

        assertEquals(TileType.VOID, list.get(0).getTileType());
    }
}
