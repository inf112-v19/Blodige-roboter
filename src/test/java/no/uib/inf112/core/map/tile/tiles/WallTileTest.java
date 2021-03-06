package no.uib.inf112.core.map.tile.tiles;

import com.badlogic.gdx.graphics.Color;
import no.uib.inf112.core.map.tile.TileGraphic;
import no.uib.inf112.core.map.tile.api.MovableTile;
import no.uib.inf112.core.map.tile.api.Tile;
import no.uib.inf112.core.player.Robot;
import no.uib.inf112.core.util.ComparableTuple;
import no.uib.inf112.core.util.Direction;
import no.uib.inf112.core.util.Vector2Int;
import no.uib.inf112.desktop.TestGraphics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * @author Elg
 */
public class WallTileTest extends TestGraphics {


    /////////////
    // Walk to //
    /////////////

    @Test
    public void walkOntoBottomWallBlocksSouthOnly() {
        Direction blockedDir = Direction.SOUTH;
        WallTile wallTile = new WallTile(new Vector2Int(0, 0), TileGraphic.WALL_SOUTH);

        for (Direction dir : Direction.values()) {
            MovableTile moveTile = new RobotImpl(new Vector2Int(dir.inverse().getDx(), dir.inverse().getDy()), dir, new ComparableTuple<>("Black", Color.BLACK));
            Assert.assertEquals(blockedDir.equals(dir.inverse()), wallTile.willCollide(moveTile, dir));
        }
    }

    @Test
    public void walkOntoRightWallBlocksEastOnly() {
        Direction blockedDir = Direction.EAST;
        WallTile wallTile = new WallTile(new Vector2Int(0, 0), TileGraphic.WALL_EAST);

        for (Direction dir : Direction.values()) {
            MovableTile moveTile = new RobotImpl(new Vector2Int(dir.inverse().getDx(), dir.inverse().getDy()), dir, new ComparableTuple<>("Black", Color.BLACK));
            Assert.assertEquals(blockedDir.equals(dir.inverse()), wallTile.willCollide(moveTile, dir));
        }
    }

    @Test
    public void walkOntoTopWallBlocksNorthOnly() {
        Direction blockedDir = Direction.NORTH;
        WallTile wallTile = new WallTile(new Vector2Int(0, 0), TileGraphic.WALL_NORTH);

        for (Direction dir : Direction.values()) {
            MovableTile moveTile = new RobotImpl(new Vector2Int(dir.inverse().getDx(), dir.inverse().getDy()), dir, new ComparableTuple<>("Black", Color.BLACK));
            Assert.assertEquals(blockedDir.equals(dir.inverse()), wallTile.willCollide(moveTile, dir));
        }
    }

    @Test
    public void walkOntoLeftWallBlocksWestOnly() {
        Direction blockedDir = Direction.WEST;
        WallTile wallTile = new WallTile(new Vector2Int(0, 0), TileGraphic.WALL_WEST);

        for (Direction dir : Direction.values()) {
            MovableTile moveTile = new RobotImpl(new Vector2Int(dir.inverse().getDx(), dir.inverse().getDy()), dir, new ComparableTuple<>("Black", Color.BLACK));
            Assert.assertEquals(blockedDir.equals(dir.inverse()), wallTile.willCollide(moveTile, dir));
        }
    }

    ///////////////
    // Walk from //
    ///////////////

    @Test
    public void walkFromTopWallBlocksNorthOnly() {
        Direction blockedDir = Direction.NORTH;
        Vector2Int pos = new Vector2Int(0, 0);
        WallTile wallTile = new WallTile(pos, TileGraphic.WALL_NORTH);

        for (Direction dir : Direction.values()) {
            MovableTile moveTile = new RobotImpl(pos, dir, new ComparableTuple<>("Black", Color.BLACK));
            Assert.assertEquals(blockedDir.equals(dir), wallTile.willCollide(moveTile, dir));
        }
    }

    @Test
    public void walkFromRightWallBlocksEastOnly() {
        Direction blockedDir = Direction.EAST;
        Vector2Int pos = new Vector2Int(0, 0);
        WallTile wallTile = new WallTile(pos, TileGraphic.WALL_EAST);

        for (Direction dir : Direction.values()) {
            MovableTile moveTile = new RobotImpl(pos, dir, new ComparableTuple<>("Black", Color.BLACK));
            Assert.assertEquals(blockedDir.equals(dir), wallTile.willCollide(moveTile, dir));
        }
    }

    @Test
    public void walkFromBottomWallBlocksSouthOnly() {
        Direction blockedDir = Direction.SOUTH;
        Vector2Int pos = new Vector2Int(0, 0);
        WallTile wallTile = new WallTile(pos, TileGraphic.WALL_SOUTH);

        for (Direction dir : Direction.values()) {
            MovableTile moveTile = new RobotImpl(pos, dir, new ComparableTuple<>("BLACK", Color.BLACK));
            Assert.assertEquals(blockedDir.equals(dir), wallTile.willCollide(moveTile, dir));
        }
    }


    @Test
    public void walkFromLeftWallBlocksWestOnly() {
        Direction blockedDir = Direction.WEST;
        Vector2Int pos = new Vector2Int(0, 0);
        WallTile wallTile = new WallTile(pos, TileGraphic.WALL_WEST);

        for (Direction dir : Direction.values()) {
            MovableTile moveTile = new RobotImpl(pos, dir, new ComparableTuple<>("Black", Color.BLACK));
            Assert.assertEquals(blockedDir.equals(dir), wallTile.willCollide(moveTile, dir));
        }
    }


    public class RobotImpl extends Robot {

        public RobotImpl(Vector2Int pos, Direction direction, ComparableTuple<String, Color> color) {
            super(pos, direction, color);
        }

        @Override
        public void heal(int amount) {
            //Dummy robot no need for implementation
        }

        @Override
        public void kill() {
            //Dummy robot no need for implementation
        }

        @NotNull
        @Override
        public Vector2Int getBackup() {
            return new Vector2Int(0, 0);
        }

        @Nullable
        @Override
        public List<Class<? extends Tile>> requiredSuperClasses() {
            return null;
        }

        @Override
        public boolean canDoAction(@NotNull Tile tile) {
            return false;
        }

        @Override
        public void clean(@NotNull Tile tile) {
            //This method does not do anything for this test class
        }


        @Override
        public void damage(int damageAmount) {
            //Dummy robot no need for implementation
        }
    }

}
