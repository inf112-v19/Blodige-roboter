package no.uib.inf112.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import no.uib.inf112.core.GameGraphics;


public class EndScreen extends AbstractMenuScreen {

    private String[] rankList;
    private final Drawable GAME_OVER = new TextureRegionDrawable(new Texture("game_over.png"));


    public EndScreen(GameGraphics game) {
        super(game);
        rankList = GameGraphics.getRoboRally().getPlayerHandler().rankPlayers();
        for (int i = 0; i < rankList.length; i++) {
            if (rankList[i] == null) {
                rankList[i] = "";
            }
        }
    }

    @Override
    public void show() {
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();

        TextButton play_again = createButton("PLAY AGAIN", 70);
        setPositionCentered(play_again, 1, 8);
        play_again.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameGraphics.resetRoborally();
                game.closeResources();
                game.setScreen(new TitleScreen(game));

            }
        });

        TextButton quit = createButton("QUIT", 70);
        setPositionCentered(quit, 1, 11);
        quit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.exit(0);
            }
        });

        stage.addActor(createList(rankList));
        stage.addActor(play_again);
        stage.addActor(quit);
    }

    @Override
    public void render(float v) {
        super.render(v);
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        GAME_OVER.draw(game.batch, camera.viewportWidth / 5, 3 * camera.viewportHeight / 4f, 3 * camera.viewportWidth / 5, camera.viewportHeight / 6f);
        game.batch.end();
    }


}
