package no.uib.inf112.core.map.tile.tiles;

import no.uib.inf112.core.map.tile.Attribute;
import no.uib.inf112.core.map.tile.TileGraphic;
import no.uib.inf112.core.map.tile.api.*;
import no.uib.inf112.core.ui.Sound;
import no.uib.inf112.core.util.Direction;
import no.uib.inf112.core.util.Vector2Int;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class PusherTile extends AbstractRequirementTile implements ActionTile<MovableTile>, CollidableTile {

    //The wall tile is used for collision
    private WallTile wall;
    private Direction pushDir;

    public PusherTile(@NotNull Vector2Int pos, @NotNull TileGraphic tg) {
        super(pos, tg);
        wall = new WallTile(pos,tg);
        if(wall.getDirections().size() != 1){
            throw new IllegalStateException("A pusher must have exactly one direction. Found "+wall.getDirections());
        }
        //push in opposite direction of where it blocks
        pushDir = wall.getDirections().iterator().next().inverse();
    }

    @Override
    public boolean action(@NotNull MovableTile tile) {

        return true;
    }

    @Override
    public Sound getActionSound() {
        return Sound.PUSHER;
    }

    @Override
    public boolean willCollide(Tile tile, Direction dir) {
        return wall.willCollide(tile, dir);
    }

    @NotNull
    @Override
    public Set<Direction> getDirections() {
        return wall.getDirections();
    }

    @Nullable
    @Override
    public List<Attribute> requiredAttributes() {
        return Collections.singletonList(Attribute.PUSHABLE); //pushable implies movable
    }
}
