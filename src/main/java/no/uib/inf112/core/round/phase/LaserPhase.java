package no.uib.inf112.core.round.phase;

import com.badlogic.gdx.graphics.Color;
import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.map.MapHandler;
import no.uib.inf112.core.map.tile.Attribute;
import no.uib.inf112.core.map.tile.api.*;
import no.uib.inf112.core.map.tile.tiles.LaserTile;
import no.uib.inf112.core.util.Direction;
import no.uib.inf112.core.util.Vector2Int;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static no.uib.inf112.core.map.MapHandler.*;

public class LaserPhase extends AbstractPhase {

    public LaserPhase(long totalRunTime) {
        super(totalRunTime);
    }

    @Override
    public void startPhase(@NotNull MapHandler map) {
        for (int x = 0; x < map.getMapWidth(); x++) {
            for (int y = 0; y < map.getMapHeight(); y++) {
                Tile tile = map.getTile(COLLIDABLES_LAYER_NAME, x, y);
                if (tile != null && tile.hasAttribute(Attribute.SHOOTS_LASER)) {
                    GameGraphics.scheduleSync(() -> shootLaser(map, tile), getRunTime() / 5);

                }
            }
        }
    }

    private void shootLaser(@NotNull MapHandler map, Tile tile) {
        MultiDirectionalTile originTile = (MultiDirectionalTile) tile;
        Direction direction = getDirection(originTile);
        Vector2Int originPos = new Vector2Int(tile.getX(), tile.getY());
        if (tile.hasSuperClass(MovableTile.class)) {
            //Robot shoot laser in direction of tile
            /*if (canMove(laser, direction, map)) {
                map.addEntityLaser(new LaserTile(currentPos, direction.getDx() == 0 ? LASER_VERTICAL : LASER_HORIZONTAL));*/
        } else {
            //Wall shoot laser in opposite direction
            direction = direction.inverse();
            Tile onPos = map.getTile(ENTITY_LAYER_NAME, originPos.x, originPos.y);
            Vector2Int currentPos = originPos;
            Set<LaserTile> activatedLasers = new HashSet<>();
            while (onPos == null || !onPos.hasSuperClass(CollidableTile.class)) {
                Tile laser = map.getTile(LASERS_LAYER_NAME, currentPos.x, currentPos.y);
                activateLaser(laser, activatedLasers);
                if (canMove(laser, direction, map)) {
                    System.out.println("could move");
                    currentPos = new Vector2Int(currentPos.x + direction.getDx(), currentPos.y + direction.getDy());
                    onPos = map.getTile(ENTITY_LAYER_NAME, currentPos.x, currentPos.y);
                } else {
                    break;
                }
            }
            if (onPos != null && onPos.hasSuperClass(DamagableTile.class)) {
                GameGraphics.getSoundPlayer().playShootLaser();
                DamagableTile damagableTile = (DamagableTile) onPos;
                damagableTile.damage(1);
            } else if (onPos != null) {
                System.out.println(onPos);
                throw new IllegalStateException("Found something in the entity layer that's not hurtable");
            }
            GameGraphics.scheduleSync(() -> resetLasers(activatedLasers, map), getRunTime() * 2);
        }
    }

    private void resetLasers(Set<LaserTile> activatedLasers, MapHandler map) {
        for (LaserTile laserTile :
                activatedLasers) {
            laserTile.setColor(Color.DARK_GRAY);
        }
        activatedLasers.clear();
    }

    private boolean canMove(Tile laser, Direction direction, MapHandler map) {
        Tile standingOnTile = map.getTile(COLLIDABLES_LAYER_NAME, laser.getX(), laser.getY());
        System.out.println(willCollide(laser, standingOnTile, direction));
        Tile movingToTile = map.getTile(COLLIDABLES_LAYER_NAME, laser.getX() + direction.getDx(), laser.getY() + direction.getDy());
        System.out.println(willCollide(laser, movingToTile, direction));
        return !willCollide(laser, standingOnTile, direction) && !willCollide(laser, movingToTile, direction);
    }

    private boolean willCollide(Tile laser, Tile tile, Direction direction) {
        if (tile != null && tile.hasSuperClass(CollidableTile.class)) {
            CollidableTile collidableTile = (CollidableTile) tile;
            return collidableTile.willCollide(laser, direction);
        }
        return false;
    }

    private Direction getDirection(MultiDirectionalTile originTile) {
        if (originTile.getDirections().size() == 1) {
            return originTile.getDirections().iterator().next();
        }
        throw new IllegalArgumentException("Wall shooting laser has more than one direction");
    }

    /**
     * TODO javadoc
     *
     * @param activatedLasers
     * @param laserTile
     */
    private void activateLaser(Tile laserTile, Collection<LaserTile> activatedLasers) {
        if (laserTile instanceof LaserTile) {
            LaserTile tile1 = (LaserTile) laserTile;
            tile1.setColor(Color.WHITE);
            activatedLasers.add(tile1);
        } else {
            throw new IllegalStateException("Found something in the laser layer that's not a laser");
        }
    }
}
