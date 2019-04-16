package no.uib.inf112.core.round.phase;

import com.badlogic.gdx.graphics.Color;
import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.map.MapHandler;
import no.uib.inf112.core.map.tile.Attribute;
import no.uib.inf112.core.map.tile.TileGraphic;
import no.uib.inf112.core.map.tile.api.*;
import no.uib.inf112.core.map.tile.tiles.LaserTile;
import no.uib.inf112.core.player.IPlayer;
import no.uib.inf112.core.screens.GameScreen;
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
                    GameScreen.scheduleSync(() -> shootAlreadyExistingLaser(map, collidablesTile), getRunTime() / 5);
                }
                Tile entitiesTile = map.getTile(ENTITY_LAYER_NAME, x, y);
                if (entitiesTile != null && entitiesTile.hasAttribute(Attribute.LAYS_DOWN_LASER) && !((IPlayer) (entitiesTile)).isPoweredDown()) {
                    GameScreen.scheduleSync(() -> shootLaserFromTile(map, entitiesTile), getRunTime() / 5);
                }
            }

        }
    }

    /**
     * Place a laser trail as long as it should fire witouth being blocked from this tile
     *
     * @param map  map to lay the trace on
     * @param tile tile to shoot from(does not shoot at the position of this tile)
     */
    private void shootLaserFromTile(@NotNull MapHandler map, @NotNull Tile tile) {
        SingleDirectionalTile prevTile = (SingleDirectionalTile) tile;
        Direction direction = prevTile.getDirection();
        Tile onTile = prevTile;
        List<LaserTile> activatedLasers = new ArrayList<>();
        while (canMoveAcrossTile(onTile, direction, map)) {
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
        if (onTile != null && !onTile.equals(tile) && onTile.hasSuperClass(DamageableTile.class)) {
            GameGraphics.getSoundPlayer().playShootLaser();
            DamageableTile damageableTile = (DamageableTile) onTile;
            damageableTile.damage(prevTile.hasAttribute(Attribute.HIGH_PRIORITY) ? 2 : 1);
        }
        final LaserTile[] clone = activatedLasers.toArray(new LaserTile[0]);
        GameScreen.scheduleSync(() -> cleanUpLasers(Arrays.asList(clone), map), getRunTime() * 2);
    }

    /**
     * Removes the trace of the activated lasers on the map (those in the entityLaser layer)
     *
     * @param activatedLasers lasers to clean up after
     * @param map             map to clean up on
     */
    private void cleanUpLasers(List<LaserTile> activatedLasers, MapHandler map) {
        for (LaserTile laserTile : activatedLasers) {
            map.removeEntityLaser(laserTile);
        }
    }

    /**
     * Shoot the already existing lasers
     * Meaning those that are in the lasers layer. They start at wall tiles that have the attribute to shoot lasers
     *
     * @param map  map to shoot on
     * @param tile tile to shoot from, that includes the position of this tile
     */
    private void shootAlreadyExistingLaser(@NotNull MapHandler map, @NotNull Tile tile) {
        MultiDirectionalTile originTile = (MultiDirectionalTile) tile;
        // assuming tiles shooting already existing lasers only have one direction, e.g. walls with laser
        Direction direction = getDirection(originTile);
        Vector2Int originPos = new Vector2Int(tile.getX(), tile.getY());

        //Wall shoot laser in opposite direction
        direction = direction.inverse();
        Tile onPos = map.getTile(ENTITY_LAYER_NAME, originPos.x, originPos.y);
        Vector2Int currentPos = originPos;
        Set<LaserTile> activatedLasers = new HashSet<>();
        while (onPos == null || !onPos.hasSuperClass(CollidableTile.class)) {
            Tile laser = map.getTile(LASERS_LAYER_NAME, currentPos.x, currentPos.y);
            activatedLasers.add(activateLaser(laser));

            if (canMove(laser, direction, map)) {
                currentPos = new Vector2Int(currentPos.x + direction.getDx(), currentPos.y + direction.getDy());
                onPos = map.getTile(ENTITY_LAYER_NAME, currentPos.x, currentPos.y);
            } else {
                break;
            }
        }
        if (onPos != null && onPos.hasSuperClass(DamageableTile.class)) {
            GameGraphics.getSoundPlayer().playShootLaser();
            DamageableTile damageableTile = (DamageableTile) onPos;
            damageableTile.damage(originTile.hasAttribute(Attribute.HIGH_PRIORITY) ? 2 : 1);
        } else if (onPos != null) {
            throw new IllegalStateException("Found something in the entity layer that's not hurtable");
        }
        GameScreen.scheduleSync(() -> deactivateLasers(activatedLasers), getRunTime() * 2);

    }

    /**
     * Resets the already existing lasers on the map back to not being active
     * this means setting their color to dark grey
     *
     * @param activatedLasers lasers to deactivate
     */
    private void deactivateLasers(Set<LaserTile> activatedLasers) {
        for (LaserTile laserTile :
                activatedLasers) {
            laserTile.setColor(Color.DARK_GRAY);
        }
        activatedLasers.clear();
    }

    /**
     * Checks if the tile can move from the current tile
     * This method only checks the current position and does not check the next one to see if it can move there, it only checks the collidables layer
     *
     * @param laser     tile to "move"
     * @param direction direction to move
     * @param map       map to move on
     * @return true if there is nothing on the current tile prohibiting moving in the direction
     */
    private boolean canMove(Tile laser, Direction direction, MapHandler map) {
        Tile standingOnTile = map.getTile(COLLIDABLES_LAYER_NAME, laser.getX(), laser.getY());
        return !willCollide(laser, standingOnTile, direction);
    }

    /**
     * Checks if the given tile can move to the next tile
     * This method checks the current position and the one its moving too to see if there is anything blocking it from doing it
     *
     * @param prevTile  tile to move
     * @param direction direction to move
     * @param map       map to move on
     * @return true if there is nothing on the current or the next tile prohibiting moving in the direction
     */
    private boolean canMoveAcrossTile(Tile prevTile, Direction direction, MapHandler map) {
        Tile standingOnTile = map.getTile(COLLIDABLES_LAYER_NAME, prevTile.getX(), prevTile.getY());
        Tile nextTile = map.getTile(COLLIDABLES_LAYER_NAME, prevTile.getX() + direction.getDx(), prevTile.getY() + direction.getDy());
        return !willCollide(prevTile, standingOnTile, direction) && !willCollide(prevTile, nextTile, direction);
    }

    /**
     * Returns true if the laser will collide with the tile when moving in the given direction
     *
     * @param laser     tile to move
     * @param tile      tile to maybe collide with
     * @param direction directio to move
     * @return true if the tile collides with the other tile
     */
    private boolean willCollide(Tile laser, Tile tile, Direction direction) {
        if (tile != null && tile.hasSuperClass(CollidableTile.class)) {
            CollidableTile collidableTile = (CollidableTile) tile;
            return collidableTile.willCollide(laser, direction);
        }
        return false;
    }

    /**
     * Returns the one direction in an originally multiDirectionalTile
     * Used to get laser direction
     *
     * @param originTile tile to get direction from
     * @return the direction of the tile
     * @Throws IllegalArgumentException if the tile has more than one direction
     */
    private Direction getDirection(MultiDirectionalTile originTile) {
        if (originTile.getDirections().size() == 1) {
            return originTile.getDirections().iterator().next();
        }
        throw new IllegalArgumentException("Wall shooting laser has more than one direction");
    }

    /**
     * Activates an already existing laser
     *
     * @param laserTile laser to activate
     * @return returns the newly activated LaserTile
     */
    private LaserTile activateLaser(Tile laserTile) {
        if (laserTile instanceof LaserTile) {
            LaserTile laser = (LaserTile) laserTile;
            laser.setColor(Color.WHITE);
            return laser;
        } else {
            throw new IllegalStateException("Found something in the laser layer that's not a laser: " + laserTile);
        }
    }
}
