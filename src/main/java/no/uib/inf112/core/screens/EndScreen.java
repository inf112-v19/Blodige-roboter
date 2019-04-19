package no.uib.inf112.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import no.uib.inf112.core.GameGraphics;

public class EndScreen extends AbstractMenuScreen {

    public EndScreen(GameGraphics game) {
        super(game);
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
        game.batch.end();
    }

    private Table createHighScoreList() {
//        List.ListStyle style = new List.ListStyle(screenFont, Color.BLACK, Color.BLACK, new TextureRegionDrawable(new Texture(10, 10, Pixmap.Format.Alpha)));
//        style.
//        List<String> list = new List<>(style);
//        list.setItems(new String[]{"Riskyfasf", "Player 1", "fgasdhfjkhasdklfhakj"});
//
//        list.setPosition(stage.getWidth()/2, stage.getHeight());
//        return list;
//        Table table = new Table();
//        table.add(new Label("Hey", new Skin()));
//        return table;
        return null;
    }
}
