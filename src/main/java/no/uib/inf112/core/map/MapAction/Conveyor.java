package no.uib.inf112.core.map.MapAction;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import no.uib.inf112.core.player.Direction;
import no.uib.inf112.core.player.Entity;
import no.uib.inf112.core.util.Vector2Int;
import org.jetbrains.annotations.NotNull;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Conveyor implements MapAction {

    private TiledMapTile tile;
    private Entity entity;
    private Direction direction; //Temporary to avoid compile error, shoudl change to tile when this is an enum

    public Conveyor(TiledMapTile tile, Entity entity){
        this.tile = tile;
        this.entity = entity;
    }

    @Override
    public void doAction() {
        switch (direction) {
            //Maybe just set x and y to getResultOfMovement()? Make wall handling and other factors implementet in getResultOfMovement?
            default:
                throw new NotImplementedException();
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
