package no.uib.inf112.core.map.tile.tiles;

import com.badlogic.gdx.graphics.Color;
import no.uib.inf112.core.map.tile.TileGraphic;
import no.uib.inf112.core.map.tile.api.MoveableTile;
import no.uib.inf112.core.player.Robot;
import no.uib.inf112.core.util.Direction;
import no.uib.inf112.core.util.Vector2Int;
import no.uib.inf112.desktop.TestGraphics;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Elg
 */
public class WallTileTest extends TestGraphics {


    @Test
    public void walkOntoBottomBlocksSouthOnly() {
        Direction blockedDir = Direction.SOUTH;
        WallTile wallTile = new WallTile(new Vector2Int(0, 0), TileGraphic.WALL_BOTTOM);

        for (Direction dir : Direction.values()) {
            MoveableTile<?> moveTile = new RobotImpl(new Vector2Int(dir.inverse().getDx(), dir.inverse().getDy()), dir, Color.BLACK);
            Assert.assertEquals(blockedDir == dir, wallTile.willCollide(moveTile, dir));
        }
    }

    @Test
    public void walkOntoRightBlocksEastOnly() {
        Direction blockedDir = Direction.EAST;
        WallTile wallTile = new WallTile(new Vector2Int(0, 0), TileGraphic.WALL_RIGHT);

        for (Direction dir : Direction.values()) {
            System.out.println("---\ndir=" + dir);
            MoveableTile<?> moveTile = new RobotImpl(new Vector2Int(dir.inverse().getDx(), dir.inverse().getDy()), dir, Color.BLACK);
            Assert.assertEquals(blockedDir == dir, wallTile.willCollide(moveTile, dir));
        }
    }

    @Test
    public void walkOntoTopBlocksNorthOnly() {
        Direction blockedDir = Direction.NORTH;
        WallTile wallTile = new WallTile(new Vector2Int(0, 0), TileGraphic.WALL_TOP);

        for (Direction dir : Direction.values()) {
            MoveableTile<?> moveTile = new RobotImpl(new Vector2Int(dir.inverse().getDx(), dir.inverse().getDy()), dir, Color.BLACK);
            Assert.assertEquals(blockedDir == dir, wallTile.willCollide(moveTile, dir));
        }
    }

    @Test
    public void walkOntoLeftBlocksWestOnly() {
        Direction blockedDir = Direction.WEST;
        WallTile wallTile = new WallTile(new Vector2Int(0, 0), TileGraphic.WALL_LEFT);

        for (Direction dir : Direction.values()) {
            MoveableTile<?> moveTile = new RobotImpl(new Vector2Int(dir.inverse().getDx(), dir.inverse().getDy()), dir, Color.BLACK);
            Assert.assertEquals(blockedDir == dir, wallTile.willCollide(moveTile, dir));
        }
    }

    public class RobotImpl extends Robot {

        public RobotImpl(Vector2Int pos, Direction direction, Color color) {
            super(pos, direction, color);
        }

        @Override
        public void heal(int amount) {

        }

        @Override
        public void kill() {

        }
    }

}
