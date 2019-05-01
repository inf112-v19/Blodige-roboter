package no.uib.inf112.core.map.tile.tiles;

import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.RoboRally;
import no.uib.inf112.core.map.tile.TileGraphic;
import no.uib.inf112.core.player.IPlayer;
import no.uib.inf112.core.player.Player;
import no.uib.inf112.core.util.Vector2Int;
import no.uib.inf112.desktop.TestGraphics;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class AbstractRequirementTileTest extends TestGraphics {

    private RoboRally roboRally;
    private  IPlayer player;
    private Vector2Int vector2Int;

    @Before
    public void setup(){
        roboRally = GameGraphics.createRoboRally(TEST_MAP_FOLDER + File.separatorChar + "conveyor_tile_test_map.tmx", 1);
        player = roboRally.getPlayerHandler().testPlayer();
        vector2Int = new Vector2Int(0,0);
    }

    @Test
    public void conveyorHasRequiredAttributes(){

        ConveyorTile conveyor = new ConveyorTile(vector2Int, TileGraphic.CONVEYOR_EAST);

        assertEquals(true, conveyor.canDoAction(player));
    }

    @Test
    public void deathTileHasRequiredAttributes(){

        DeathTile deathTile = new DeathTile(vector2Int, TileGraphic.VOID_TILE);

        assertEquals(true, deathTile.canDoAction(player));
    }

    @Test
    public void flagTileHasRequiredAttributes(){
        FlagTile flagTile1 = new FlagTile(vector2Int, TileGraphic.FLAG1);
        FlagTile flagTile2 = new FlagTile(vector2Int, TileGraphic.FLAG2);
        FlagTile flagTile3 = new FlagTile(vector2Int, TileGraphic.FLAG3);
        FlagTile flagTile4 = new FlagTile(vector2Int, TileGraphic.FLAG4);

        assertEquals(true, flagTile1.canDoAction(player));
        assertEquals(true, flagTile2.canDoAction(player));
        assertEquals(true, flagTile3.canDoAction(player));
        assertEquals(true, flagTile4.canDoAction(player));
    }

    @Test
    public void gearTileHasRequiredAttributes(){
        GearTile gearTile1 = new GearTile(vector2Int, TileGraphic.ROTATE_CLOCKWISE);
        GearTile gearTile2 = new GearTile(vector2Int, TileGraphic.ROTATE_COUNTERCLOCKWISE);

        assertEquals(true, gearTile1.canDoAction(player));
        assertEquals(true, gearTile2.canDoAction(player));
    }

    @Test
    public void rotationConveyorHasRequiredAttributes(){
        RotationConveyor rotationConveyor1 = new RotationConveyor(vector2Int, TileGraphic.CONVEYOR_FROM_WEST_GO_NORTH);
        RotationConveyor rotationConveyor2 = new RotationConveyor(vector2Int, TileGraphic.CONVEYOR_FROM_EAST_ROTATE_NORTH);

        assertEquals(true, rotationConveyor1.canDoAction(player));
        assertEquals(true, rotationConveyor2.canDoAction(player));
    }

    @Test
    public void wrenchAndHammerTileHasRequiredAttributes(){
        WrenchAndHammerTile wrenchAndHammerTile = new WrenchAndHammerTile(vector2Int, TileGraphic.HAMMER_AND_WRENCH);

        assertEquals(true, wrenchAndHammerTile.canDoAction(player));
    }

    @Test
    public void wrenchHasRequiredAttributes(){
        WrenchTile wrenchTile = new WrenchTile(vector2Int, TileGraphic.WRENCH);

        assertEquals(true, wrenchTile.canDoAction(player));
    }

    @Test
    public void playerHasRequiredAttributes(){
        assertEquals(true, player.canDoAction(player));
    }
}
