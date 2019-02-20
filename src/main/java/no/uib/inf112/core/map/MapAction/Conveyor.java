package no.uib.inf112.core.map.MapAction;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import no.uib.inf112.core.player.Direction;
import no.uib.inf112.core.player.Entity;
import no.uib.inf112.core.player.Robot;
import no.uib.inf112.core.util.Vector2Int;
import org.jetbrains.annotations.NotNull;

public class Conveyor implements MapAction {

    private TiledMapTile tile;
    private Robot entity;
    private Direction direction; //Temporary to avoid compile error, should change to tile when this is an enum

    /**
     * Create new Conveyor object
     * @param tile the tile that caused the creation of this conveyor object
     * @param entity the entity standing on the tile
     */
    public Conveyor(TiledMapTile tile, Robot entity){
        this.tile = tile;
        this.entity = entity;
    }

    @Override
    public void doAction() {
        switch (direction) {
            case NORTH:
                entity.teleport(1, 0); //Should be move
            //Maybe just set x and y to getResultOfMovement()? Make wall handling and other factors implementet in getResultOfMovement?
            default:
                throw new UnsupportedOperationException();
        }
    }

    @Override
    public Vector2Int getResultOfMovement() {
        //Calculate
        return null;
    }

    @Override
    public Entity getParent() {
        return entity;
    }
}
