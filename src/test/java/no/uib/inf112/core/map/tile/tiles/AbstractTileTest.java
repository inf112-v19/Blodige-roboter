package no.uib.inf112.core.map.tile.tiles;

import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.RoboRally;
import no.uib.inf112.core.map.tile.Attribute;
import no.uib.inf112.core.map.tile.TileGraphic;
import no.uib.inf112.core.map.tile.api.AbstractTile;
import no.uib.inf112.core.map.tile.api.ActionTile;
import no.uib.inf112.core.map.tile.api.DockableTile;
import no.uib.inf112.core.map.tile.api.MovableTile;
import no.uib.inf112.core.player.IPlayer;
import no.uib.inf112.core.util.Vector2Int;
import no.uib.inf112.desktop.TestGraphics;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AbstractTileTest extends TestGraphics {

    private RoboRally roboRally;
    private Vector2Int vector2Int;
    private IPlayer player;

    @Before
    public void setUp(){
        roboRally = GameGraphics.createRoboRally(TEST_MAP_FOLDER + File.separatorChar + "conveyor_tile_test_map.tmx", 1);
        vector2Int = new Vector2Int(0,0);
        player = roboRally.getPlayerHandler().testPlayer();
    }

    @Test
    public void conveyorEastTileTypeHasAttribute(){
        AbstractTile conveyorTile = new ConveyorTile(vector2Int, TileGraphic.CONVEYOR_EAST);

        assertTrue(conveyorTile.hasAttribute(Attribute.DIR_EAST));
    }

    @Test
    public void conveyorEastTileTypeDoNotHaveAttributeShootsLaser(){
        AbstractTile conveyorTile = new ConveyorTile(vector2Int, TileGraphic.CONVEYOR_EAST);

        assertFalse(conveyorTile.hasAttribute(Attribute.SHOOTS_LASER));
    }

    @Test
    public void wrenchDoesNotHaveAttributes(){
        AbstractTile wrenchTile = new WrenchTile(vector2Int, TileGraphic.WRENCH);

        Attribute[] atts = Attribute.values();

        for (Attribute a: atts) {
            assertFalse(wrenchTile.hasAttribute(a));
        }
    }

    @Test
    public void conveyorHasSuperClassActionTile(){
        ConveyorTile conveyorTile = new ConveyorTile(vector2Int, TileGraphic.CONVEYOR_EAST);

        assertTrue(conveyorTile.hasSuperClass(ActionTile.class));
    }

    @Test
    public void wrenchHasSuperClassActionTile(){
        AbstractTile wrenchTile = new WrenchTile(vector2Int, TileGraphic.CONVEYOR_EAST);

        assertTrue(wrenchTile.hasSuperClass(ActionTile.class));
    }

    @Test
    public void playerHasSuperClassMovableTile(){
        assertTrue(player.hasSuperClass(MovableTile.class));
    }

    @Test
    public void playerDoesNotHaveSuperClassDockableTile(){
        assertFalse(player.hasSuperClass(DockableTile.class));
    }
}
