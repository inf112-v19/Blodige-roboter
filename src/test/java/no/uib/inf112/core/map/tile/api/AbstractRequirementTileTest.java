package no.uib.inf112.core.map.tile.api;

import com.badlogic.gdx.graphics.Color;
import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.RoboRally;
import no.uib.inf112.core.map.tile.TileGraphic;
import no.uib.inf112.core.map.tile.tiles.*;
import no.uib.inf112.core.player.IPlayer;
import no.uib.inf112.core.player.NonPlayer;
import no.uib.inf112.core.util.ComparableTuple;
import no.uib.inf112.core.util.Direction;
import no.uib.inf112.core.util.Vector2Int;
import no.uib.inf112.desktop.TestGraphics;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AbstractRequirementTileTest extends TestGraphics {

    private RoboRally roboRally;
    private  IPlayer player;
    private Vector2Int vector2Int;

    @Before
    public void setUp(){
        roboRally = GameGraphics.createRoboRally(TEST_MAP_FOLDER + File.separatorChar + "conveyor_tile_test_map.tmx", 1);
        player = roboRally.getPlayerHandler().mainPlayer();
        vector2Int = new Vector2Int(0,0);
    }

    @Test
    public void conveyorHasRequiredAttributes(){

        ConveyorTile conveyor = new ConveyorTile(vector2Int, TileGraphic.CONVEYOR_EAST);

        assertTrue(conveyor.canDoAction(player));
    }

    @Test
    public void deathTileHasRequiredAttributes(){

        DeathTile deathTile = new DeathTile(vector2Int, TileGraphic.VOID_TILE);

        assertTrue(deathTile.canDoAction(player));
    }

    @Test
    public void flagTileHasRequiredAttributes(){
        FlagTile flagTile1 = new FlagTile(vector2Int, TileGraphic.FLAG1);
        FlagTile flagTile2 = new FlagTile(vector2Int, TileGraphic.FLAG2);
        FlagTile flagTile3 = new FlagTile(vector2Int, TileGraphic.FLAG3);
        FlagTile flagTile4 = new FlagTile(vector2Int, TileGraphic.FLAG4);

        assertTrue(flagTile1.canDoAction(player));
        assertTrue(flagTile2.canDoAction(player));
        assertTrue(flagTile3.canDoAction(player));
        assertTrue(flagTile4.canDoAction(player));
    }

    @Test
    public void gearTileHasRequiredAttributes(){
        GearTile gearTile1 = new GearTile(vector2Int, TileGraphic.ROTATE_CLOCKWISE);
        GearTile gearTile2 = new GearTile(vector2Int, TileGraphic.ROTATE_COUNTERCLOCKWISE);

        assertTrue(gearTile1.canDoAction(player));
        assertTrue(gearTile2.canDoAction(player));
    }

    @Test
    public void rotationConveyorHasRequiredAttributes(){
        RotationConveyor rotationConveyor1 = new RotationConveyor(vector2Int, TileGraphic.CONVEYOR_FROM_WEST_GO_NORTH);
        RotationConveyor rotationConveyor2 = new RotationConveyor(vector2Int, TileGraphic.CONVEYOR_FROM_EAST_ROTATE_NORTH);

        assertTrue(rotationConveyor1.canDoAction(player));
        assertTrue(rotationConveyor2.canDoAction(player));
    }

    @Test
    public void wrenchAndHammerTileHasRequiredAttributes(){
        WrenchAndHammerTile wrenchAndHammerTile = new WrenchAndHammerTile(vector2Int, TileGraphic.HAMMER_AND_WRENCH);

        assertTrue(wrenchAndHammerTile.canDoAction(player));
    }

    @Test
    public void wrenchHasRequiredAttributes(){
        WrenchTile wrenchTile = new WrenchTile(vector2Int, TileGraphic.WRENCH);

        assertTrue(wrenchTile.canDoAction(player));
    }

    @Test
    public void playerHasRequiredAttributes(){
        assertEquals(true, player.canDoAction(player));
    }

    @Test
    public void nonPlayerHasRequiredAttributes(){
        NonPlayer nonPlayer = new NonPlayer(0, 0, Direction.NORTH, roboRally.getCurrentMap(), new ComparableTuple<>("Black", Color.BLACK));

        assertTrue(nonPlayer.canDoAction(player));
    }
}
