package no.uib.inf112.core.player;

import com.badlogic.gdx.graphics.Color;
import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.RoboRally;
import no.uib.inf112.core.map.MapHandler;
import no.uib.inf112.core.map.tile.TileType;
import no.uib.inf112.core.map.tile.api.ActionTile;
import no.uib.inf112.core.map.tile.api.Tile;
import no.uib.inf112.core.round.phase.ActionPhase;
import no.uib.inf112.core.util.Direction;
import no.uib.inf112.core.util.Vector2Int;
import no.uib.inf112.desktop.TestGraphics;
import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class PhaseTest extends TestGraphics {

    private static RoboRally roboRally;
    private static MapHandler map;

    @Before
    public void setUp() {
        roboRally = GameGraphics.createRoboRally(TEST_MAP_FOLDER + File.separatorChar + "player_test_map.tmx", 1);
        map = roboRally.getCurrentMap();
    }

    @Test
    public void movabletest() {
        Roboimpl robot = new Roboimpl(new Vector2Int(0,0), Direction.NORTH, Color.BLACK);
        new ActionPhase(TileType.ROBOT, 0).startPhase(map);
        assertEquals(robot.getX(), 1);
        assertEquals(robot.getY(), 0);

    }

    class Roboimpl extends Robot implements ActionTile<Tile> {



        /**
         * @param pos       The position the player starts at
         * @param direction What direction the player is facing on start
         * @param color
         * @throws IllegalArgumentException If the given position is out of bounds
         * @throws IllegalArgumentException If direction is {@code null}
         * @throws IllegalArgumentException If there is already an entity at the given {@code (x,y)}. See {@link MapHandler#addEntity(Entity)}
         * @throws IllegalStateException    If no {@link TiledMapTile} can be found
         */
        public Roboimpl(Vector2Int pos, Direction direction, Color color) {
            super(pos, direction, color);
        }

        @NotNull
        @Override
        public Vector2Int getBackup() {
            return null;
        }

        @Override
        public void clean(@NotNull Tile tile) {

        }

        @Override
        public void damage(int damageAmount) {

        }

        @Override
        public void heal(int amount) {

        }

        @Override
        public void kill() {

        }

        @Override
        public void action(@NotNull Tile tile) {
            move(1,0,0);
        }

        @Override
        public void playActionSound() {

        }
    }
}
