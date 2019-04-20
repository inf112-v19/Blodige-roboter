package no.uib.inf112.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import no.uib.inf112.core.GameGraphics;

public class EndScreen extends AbstractMenuScreen {

    private final BitmapFont listFont;

    public EndScreen(GameGraphics game) {
        super(game);
        listFont = game.generateFont("screen_font.ttf", 30);
    }

    @Override
    public void show() {
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();

        TextButton play_again = createButton("PLAY AGAIN", 6);
        play_again.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new TitleScreen(game));
            }
        });

        TextButton quit = createButton("QUIT", 8);
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
        //TODO Add game over header
        game.batch.end();
    }

    private List<String> createHighScoreList() {
        int listWidth = (int) (stage.getWidth()/8);
        int listHeigth = (int) stage.getHeight()/8;

        List.ListStyle style = new List.ListStyle(listFont, Color.WHITE, Color.WHITE, new TextureRegionDrawable(new Texture(listWidth, listHeigth, Pixmap.Format.Intensity)));
        List<String> list = new List<>(style);
        list.setItems(new String[]{"Player 1", "Player 2", "Player 3", "Player 4", "Player 5", "Player 6", "Player 7", "Player 8"});
        list.setWidth(listWidth);
        list.setHeight(listHeigth);
        list.setPosition((stage.getWidth() - listWidth) / 2, stage.getHeight()-300);

        return list;
    }
}
