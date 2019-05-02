package no.uib.inf112.core.map.tile;

import no.uib.inf112.core.map.tile.tiles.*;
import no.uib.inf112.core.util.Vector2Int;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertTrue;

public class AttributeTest {

    private Vector2Int vector2Int = new Vector2Int(0,0);
    @Test
    public void conveyorTileHasSpecifiedInterfaces() {
        ConveyorTile conveyorTile = new ConveyorTile(vector2Int, TileGraphic.CONVEYOR_FROM_WEST_GO_NORTH);

        Set<Attribute> set = TileGraphic.CONVEYOR_FROM_WEST_GO_NORTH.getAttributes();

        for (Attribute a: set) {
            assertTrue(a.verifyInterfaces(conveyorTile.getClass()));
        }
    }

    @Test
    public void deathTileHasSpecifiedInterfaces(){
        DeathTile deathTile = new DeathTile(vector2Int, TileGraphic.VOID_TILE);

        Set<Attribute> set = TileGraphic.VOID_TILE.getAttributes();

        for (Attribute a: set) {
            assertTrue(a.verifyInterfaces(deathTile.getClass()));
        }
    }

    @Test
    public void flagTileHasSpecifiedInterfaces(){
        FlagTile flagTile = new FlagTile(vector2Int, TileGraphic.FLAG1);

        Set<Attribute> set = TileGraphic.FLAG1.getAttributes();

        for (Attribute a: set) {
            assertTrue(a.verifyInterfaces(flagTile.getClass()));
        }
    }

    @Test
    public void gearTileHasSpecifiedInterfaces(){
        GearTile gearTile = new GearTile(vector2Int, TileGraphic.ROTATE_CLOCKWISE);

        Set<Attribute> set = TileGraphic.ROTATE_CLOCKWISE.getAttributes();

        for (Attribute a: set) {
            assertTrue(a.verifyInterfaces(gearTile.getClass()));
        }
    }

    @Test
    public void laserTileHasSpecifiedInterfaces(){
        LaserTile laserTile = new LaserTile(vector2Int, TileGraphic.LASER_HORIZONTAL);

        Set<Attribute> set = TileGraphic.LASER_HORIZONTAL.getAttributes();

        for (Attribute a: set) {
            assertTrue(a.verifyInterfaces(laserTile.getClass()));
        }
    }

    @Test
    public void rotationConveyorHasSpecifiedInterfaces(){
        RotationConveyor rotationConveyor =
                new RotationConveyor(vector2Int, TileGraphic.CONVEYOR_FROM_WEST_GO_NORTH);

        Set<Attribute> set = TileGraphic.CONVEYOR_FROM_WEST_GO_NORTH.getAttributes();

        for (Attribute a: set) {
            assertTrue(a.verifyInterfaces(rotationConveyor.getClass()));
        }
    }

    @Test
    public void spawnTileHasSpecifiedInterfaces(){
        SpawnTile spawnTile = new SpawnTile(vector2Int, TileGraphic.SPAWN1);

        Set<Attribute> set = TileGraphic.SPAWN1.getAttributes();

        for (Attribute a: set) {
            assertTrue(a.verifyInterfaces(spawnTile.getClass()));
        }
    }

    @Test
    public void wallTileHasSpecifiedInterfaces(){
        WallTile wallTile = new WallTile(vector2Int, TileGraphic.WALL_EAST);

        Set<Attribute> set = TileGraphic.WALL_EAST.getAttributes();

        for (Attribute a: set) {
            assertTrue(a.verifyInterfaces(wallTile.getClass()));
        }
    }

    @Test
    public void wrenchAndHammerTileHasSpecifiedInterfaces(){
        WrenchAndHammerTile wrenchAndHammerTile =
                new WrenchAndHammerTile(vector2Int, TileGraphic.HAMMER_AND_WRENCH);

        Set<Attribute> set = TileGraphic.HAMMER_AND_WRENCH.getAttributes();

        for (Attribute a: set) {
            assertTrue(a.verifyInterfaces(wrenchAndHammerTile.getClass()));
        }
    }

    @Test
    public void wrenchTileHasSpecifiedInterfaces(){
        WrenchTile wrenchTile = new WrenchTile(vector2Int, TileGraphic.WRENCH);

        Set<Attribute> set = TileGraphic.WRENCH.getAttributes();

        for (Attribute a: set) {
            assertTrue(a.verifyInterfaces(wrenchTile.getClass()));
        }
    }
}
