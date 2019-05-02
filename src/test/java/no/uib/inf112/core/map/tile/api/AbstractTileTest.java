package no.uib.inf112.core.map.tile.api;

import no.uib.inf112.core.map.tile.Attribute;
import no.uib.inf112.core.map.tile.TileGraphic;
import no.uib.inf112.core.map.tile.tiles.ConveyorTile;
import no.uib.inf112.core.map.tile.tiles.WrenchTile;
import no.uib.inf112.core.util.Vector2Int;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AbstractTileTest {

    Vector2Int vector2Int = new Vector2Int(0,0);

    @Test
    public void conveyorEastTileTypeHasAttribute(){
        AbstractTile conveyorTile = new ConveyorTile(vector2Int, TileGraphic.CONVEYOR_EAST);

        assertTrue(conveyorTile.hasAttribute(Attribute.DIR_EAST));
    }

    @Test
    public void conveyorEastTileTypeDoNotHaveAttributeSHOOTS_LASER(){
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
    public void conveyorHasSuperClass(){
        AbstractTile conveyorTile = new ConveyorTile(vector2Int, TileGraphic.CONVEYOR_EAST);

        assertTrue(conveyorTile.hasSuperClass(ConveyorTile.class));
    }

    @Test
    public void wrenchHasSuperClass(){
        AbstractTile wrenchTile = new WrenchTile(vector2Int, TileGraphic.CONVEYOR_EAST);

        assertTrue(wrenchTile.hasSuperClass(WrenchTile.class));
    }
}
