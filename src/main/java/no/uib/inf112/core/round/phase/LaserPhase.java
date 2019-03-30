package no.uib.inf112.core.round.phase;

import com.badlogic.gdx.graphics.Color;
import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.map.MapHandler;
import no.uib.inf112.core.map.tile.Attribute;
import no.uib.inf112.core.map.tile.TileGraphic;
import no.uib.inf112.core.map.tile.api.*;
import no.uib.inf112.core.map.tile.tiles.LaserTile;
import no.uib.inf112.core.util.Direction;
import no.uib.inf112.core.util.Vector2Int;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static no.uib.inf112.core.map.MapHandler.*;

public class LaserPhase extends AbstractPhase {

    public LaserPhase(long totalRunTime) {
        super(totalRunTime);
    }

    @Override
    public void startPhase(@NotNull MapHandler map) {

        for (int y = 0; y < map.getMapHeight(); y++) {
            for (int x = 0; x < map.getMapWidth(); x++) {
                Tile collidablesTile = map.getTile(COLLIDABLES_LAYER_NAME, x, y);
                if (collidablesTile != null && collidablesTile.hasAttribute(Attribute.SHOOTS_LASER)) {
                    GameGraphics.scheduleSync(() -> shootAlreadyExistingLaser(map, collidablesTile), getRunTime() / 5);
                }
                Tile entitiesTile = map.getTile(ENTITY_LAYER_NAME, x, y);
                if (entitiesTile != null && entitiesTile.hasAttribute(Attribute.LAYS_DOWN_LASER)) {
                    GameGraphics.scheduleSync(() -> shootLaserFromTile(map, entitiesTile), getRunTime() / 5);
                }
            }

        }
    }

    private void shootLaserFromTile(MapHandler map, Tile tile) {
        SingleDirectionalTile prevTile = (SingleDirectionalTile) tile;
        Direction direction = prevTile.getDirection();
        Tile onTile = prevTile;
        List<LaserTile> activatedLasers = new ArrayList<>();
        while (canMove2(onTile, direction, map)) {
            Vector2Int newPos = new Vector2Int(onTile.getX() + direction.getDx(), onTile.getY() + direction.getDy());
            onTile = map.getTile(ENTITY_LAYER_NAME, newPos.x, newPos.y);
            if (!map.isOutsideBoard(newPos.x, newPos.y) && (onTile == null || !onTile.hasSuperClass(CollidableTile.class))) {
                LaserTile laserTile = new LaserTile(newPos, direction.getDx() == 0 ? TileGraphic.LASER_VERTICAL : TileGraphic.LASER_HORIZONTAL, Color.WHITE);
                map.addEntityLaser(laserTile);
                activatedLasers.add(laserTile);
                onTile = laserTile;
            } else {
                break;
            }
        }
        if (onTile != null && !onTile.equals(tile) && onTile.hasSuperClass(DamagableTile.class)) {
            GameGraphics.getSoundPlayer().playShootLaser();
            DamagableTile damagableTile = (DamagableTile) onTile;
            damagableTile.damage(prevTile.hasAttribute(Attribute.DOUBLE_LASER) ? 2 : 1);
        }
        final LaserTile[] clone = activatedLasers.toArray(new LaserTile[activatedLasers.size()]);
        GameGraphics.scheduleSync(() -> cleanUpLasers(Arrays.asList(clone), map), getRunTime() * 2);
    }

    private void cleanUpLasers(List<LaserTile> activatedLasers, MapHandler map) {
        for (LaserTile laserTile : activatedLasers) {
            map.removeEntityLaser(laserTile);
        }
    }

    private boolean canMove2(Tile prevTile, Direction direction, MapHandler map) {
        Tile standingOnTile = map.getTile(COLLIDABLES_LAYER_NAME, prevTile.getX(), prevTile.getY());
        Tile nextTile = map.getTile(COLLIDABLES_LAYER_NAME, prevTile.getX() + direction.getDx(), prevTile.getY() + direction.getDy());
        return !willCollide(prevTile, standingOnTile, direction) || !willCollide(prevTile, nextTile, direction);
    }

    private void shootAlreadyExistingLaser(@NotNull MapHandler map, Tile tile) {
        MultiDirectionalTile originTile = (MultiDirectionalTile) tile;
        Direction direction = getDirection(originTile);
        Vector2Int originPos = new Vector2Int(tile.getX(), tile.getY());

        //Wall shoot laser in opposite direction
        direction = direction.inverse();
        Tile onPos = map.getTile(ENTITY_LAYER_NAME, originPos.x, originPos.y);
        Vector2Int currentPos = originPos;
        Set<LaserTile> activatedLasers = new HashSet<>();
        while (onPos == null || !onPos.hasSuperClass(CollidableTile.class)) {
            Tile laser = map.getTile(LASERS_LAYER_NAME, currentPos.x, currentPos.y);
            activateLaser(laser, activatedLasers);

            if (canMove(laser, direction, map)) {
                currentPos = new Vector2Int(currentPos.x + direction.getDx(), currentPos.y + direction.getDy());
                onPos = map.getTile(ENTITY_LAYER_NAME, currentPos.x, currentPos.y);
            } else {
                break;
            }
        }
        if (onPos != null && onPos.hasSuperClass(DamagableTile.class)) {
            GameGraphics.getSoundPlayer().playShootLaser();
            DamagableTile damagableTile = (DamagableTile) onPos;
            damagableTile.damage(originTile.hasAttribute(Attribute.DOUBLE_LASER) ? 2 : 1);
        } else if (onPos != null) {
            throw new IllegalStateException("Found something in the entity layer that's not hurtable");
        }
        GameGraphics.scheduleSync(() -> resetLasers(activatedLasers, map), getRunTime() * 2);

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
        return !willCollide(laser, standingOnTile, direction);
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
