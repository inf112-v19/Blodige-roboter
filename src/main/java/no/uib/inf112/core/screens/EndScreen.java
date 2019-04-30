package no.uib.inf112.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import no.uib.inf112.core.GameGraphics;


public class EndScreen extends AbstractMenuScreen {

    private final BitmapFont listFont;
    private String[] rankList;
    private final Drawable GAME_OVER = new TextureRegionDrawable(new Texture("game_over.png"));


    public EndScreen(GameGraphics game) {
        super(game);
        listFont = game.generateFont("screen_font.ttf", 30);
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

        stage.addActor(createHighScoreList());
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

    private List<String> createHighScoreList() {
        int listWidth = (int) (stage.getWidth() / 2);
        int listHeigth = (int) stage.getHeight() / 8;

        List.ListStyle style = new List.ListStyle(listFont, Color.WHITE, Color.WHITE, new TextureRegionDrawable(new Texture(listWidth, listHeigth, Pixmap.Format.Intensity)));
        List<String> list = new List<>(style);
        list.setItems(rankList);
        list.setWidth(listWidth);
        list.setHeight(listHeigth);
        list.setPosition((3 * stage.getWidth() / 4) / 2, stage.getHeight() - 300);

        return list;
    }
}
