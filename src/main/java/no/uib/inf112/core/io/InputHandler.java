package no.uib.inf112.core.io;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector3;
import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.map.MapHandler;
import no.uib.inf112.core.map.tiled.CustomOrthogonalTiledMapRenderer;
import no.uib.inf112.core.player.Player;
import no.uib.inf112.core.screens.GameScreen;

import java.util.Stack;

public class InputHandler extends InputAdapter {

    private Stack<Integer> logger = new Stack<>();

    public InputHandler() {
        GameScreen.getInputMultiplexer().addProcessor(this);
    }

    @Override
    public boolean scrolled(int direction) {
        GameGraphics.getRoboRally().getCurrentMap().zoomCamera(direction);
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        if (character == '+') {
            GameGraphics.getRoboRally().getCurrentMap().zoomCamera(-1);
        } else if (character == '-') {
            GameGraphics.getRoboRally().getCurrentMap().zoomCamera(1);
        } else {
            return false;
        }
        return true;
    }


    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        GameGraphics.getRoboRally().getCurrentMap().moveCamera(-Gdx.input.getDeltaX(), Gdx.input.getDeltaY());
        return true;
    }


    @Override
    public boolean keyDown(int keycode) {
        if (Input.Keys.ENTER == keycode) {

            Player player = (Player) GameGraphics.getRoboRally().getPlayerHandler().mainPlayer();

            if (GameScreen.getUiHandler().isDrawnCardsVisible()) {
                player.endDrawCards();
            }
            return true;
        }
        logger.push(keycode);
        if (logger.size() > 9 &&
                logger.elementAt(logger.size() - 10) == Input.Keys.UP &&
                logger.elementAt(logger.size() - 9) == Input.Keys.UP &&
                logger.elementAt(logger.size() - 8) == Input.Keys.DOWN &&
                logger.elementAt(logger.size() - 7) == Input.Keys.DOWN &&
                logger.elementAt(logger.size() - 6) == Input.Keys.LEFT &&
                logger.elementAt(logger.size() - 5) == Input.Keys.RIGHT &&
                logger.elementAt(logger.size() - 4) == Input.Keys.LEFT &&
                logger.elementAt(logger.size() - 3) == Input.Keys.RIGHT &&
                logger.elementAt(logger.size() - 2) == Input.Keys.B &&
                logger.elementAt(logger.size() - 1) == Input.Keys.A) {
            logger.clear();
            enableMode();
        }
        return false;
    }

    public static void enableMode() {
        if (!CustomOrthogonalTiledMapRenderer.PARTY) {
            GameGraphics.backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("sound/techno.wav"));
            GameGraphics.backgroundMusic.play();
            GameGraphics.backgroundMusic.setLooping(true);
            CustomOrthogonalTiledMapRenderer.PARTY = true;
            if (GameGraphics.getClient() != null) {
                GameGraphics.getClient().setPartyMode();
            }
        }
    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        MapHandler map = GameGraphics.getRoboRally().getCurrentMap();

        final Vector3 mousePos = map.getCamera().unproject(new Vector3(screenX, screenY, 0));

        final int blockX = (int) (mousePos.x / map.getTileWidth());
        final int blockY = (int) (mousePos.y / map.getTileHeight());

        if (map.isOutsideBoard(blockX, blockY)) {
            return false;
        }
        System.out.printf("Tiles at (%d, %d) = %s%n", blockX, blockY, map.getAllTiles(blockX, blockY));

        return true;
    }
}
