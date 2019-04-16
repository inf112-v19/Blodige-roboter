package no.uib.inf112.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import no.uib.inf112.core.GameGraphics;

public class OptionsScreen implements Screen {

    private GameGraphics game;
    Stage stage;

    public OptionsScreen(GameGraphics game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {

        SelectBox.SelectBoxStyle style = new SelectBox.SelectBoxStyle();
        style.font = new BitmapFont();
        style.fontColor = Color.BLUE;
        style.scrollStyle = new ScrollPane.ScrollPaneStyle();
        Drawable img = new TextureRegionDrawable(new Texture("titlescreen/header.png"));
        style.listStyle = new List.ListStyle(style.font, Color.RED, Color.BLACK, img);

        final SelectBox<String> selectBox = new SelectBox<>(style);
        selectBox.setAlignment(Align.right);
        selectBox.getList().setAlignment(Align.right);
        selectBox.getStyle().listStyle.selection.setRightWidth(10);
        selectBox.getStyle().listStyle.selection.setLeftWidth(20);
        selectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println(selectBox.getSelected());
            }
        });
        selectBox.setItems("XYZ", "ABC", "PQR", "LMN");
        selectBox.setSize(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);
        selectBox.setPosition(0, Gdx.graphics.getHeight() - selectBox.getHeight());

        stage.addActor(selectBox);
    }

    @Override
    public void render(float v) {

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(v);
        stage.draw();

        //selectBox.draw(game.batch, v);
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
