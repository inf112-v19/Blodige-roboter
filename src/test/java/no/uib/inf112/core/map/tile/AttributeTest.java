package no.uib.inf112.core.map.tile;

import no.uib.inf112.core.map.tile.api.Tile;
import no.uib.inf112.core.map.tile.tiles.ConveyorTile;
import no.uib.inf112.core.map.tile.tiles.DeathTile;
import no.uib.inf112.core.map.tile.tiles.FlagTile;
import no.uib.inf112.core.map.tile.tiles.GearTile;
import no.uib.inf112.core.player.Robot;
import no.uib.inf112.core.util.Vector2Int;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertEquals;

public class AttributeTest {

    private Vector2Int vector2Int = new Vector2Int(0,0);
    @Test
    public void conveyorTileHasSpecifiedInterfaces() {
        ConveyorTile conveyorTile = new ConveyorTile(vector2Int, TileGraphic.CONVEYOR_FROM_WEST_GO_NORTH);

        Set<Attribute> set = TileGraphic.CONVEYOR_FROM_WEST_GO_NORTH.getAttributes();

        for (Attribute a: set) {
            assertEquals(true , a.verifyInterfaces(conveyorTile.getClass()));
        }
    }

    @Test
    public void deathTileHasSpecifiedInterfaces(){
        DeathTile deathTile = new DeathTile(vector2Int, TileGraphic.VOID_TILE);

        Set<Attribute> set = TileGraphic.VOID_TILE.getAttributes();

        for (Attribute a: set) {
            assertEquals(true , a.verifyInterfaces(deathTile.getClass()));
        }
    }

    @Test
    public void flagTileHasSpecifiedInterfaces(){
        FlagTile flagTile = new FlagTile(vector2Int, TileGraphic.FLAG1);

        Set<Attribute> set = TileGraphic.VOID_TILE.getAttributes();

        for (Attribute a: set) {
            assertEquals(true , a.verifyInterfaces(flagTile.getClass()));
        }
    }

    @Test
    public void gearTileHasSpecifiedInterfaces(){
        GearTile gearTile = new GearTile(vector2Int, TileGraphic.ROTATE_CLOCKWISE);

        Set<Attribute> set = TileGraphic.VOID_TILE.getAttributes();

        for (Attribute a: set) {
            assertEquals(true , a.verifyInterfaces(gearTile.getClass()));
        }
    }
    
}
