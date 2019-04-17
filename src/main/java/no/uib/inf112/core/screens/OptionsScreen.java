package no.uib.inf112.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import no.uib.inf112.core.GameGraphics;

import java.io.File;

public class OptionsScreen implements Screen {

    private final String OPTIONS_FOLDER = "optionsscreen" + File.separatorChar;
    private final String[] MAP_LIST = new String[]{"Risky Exchange", "Checkmate", "Dizzy Dash", "Island Hop", "Chop Shop Challenge"};
    private GameGraphics game;
    Stage stage;

    public OptionsScreen(GameGraphics game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("screen_font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 14;
        BitmapFont font = generator.generateFont(parameter); // font size 12 pixels
        generator.dispose();


        SelectBox.SelectBoxStyle style = new SelectBox.SelectBoxStyle();
        style.font = font;
        style.fontColor = Color.BLUE;
        style.scrollStyle = new ScrollPane.ScrollPaneStyle();
        Drawable img = new TextureRegionDrawable(new Texture("titlescreen/header.png"));
        style.listStyle = new List.ListStyle(style.font, Color.RED, Color.BLACK, img);

        final SelectBox<String> selectBox = new SelectBox<>(style);
        selectBox.setAlignment(Align.left);
        selectBox.getList().setAlignment(Align.left);
        selectBox.getStyle().listStyle.selection.setRightWidth(10);
        selectBox.getStyle().listStyle.selection.setLeftWidth(10);
        selectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setMap(selectBox.getSelected());

            }
        });
        selectBox.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                selectBox.showList();
            }
        });

        selectBox.setItems(MAP_LIST);
        selectBox.setSize(Gdx.graphics.getWidth() / 8f, Gdx.graphics.getHeight() / 20f);
        selectBox.setPosition(10, (Gdx.graphics.getHeight() - selectBox.getHeight()) / 2);
        stage.addActor(selectBox);

    }

    @Override
    public void render(float v) {

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            game.setScreen(new TitleScreen(game));
        }

        stage.act(v);
        stage.draw();


        game.batch.begin();
        Texture mapImg = new Texture(OPTIONS_FOLDER + GameGraphics.mapName + ".png");
        game.batch.draw(mapImg, 200, Gdx.graphics.getHeight() / 2f - mapImg.getHeight() / 2f);

        game.batch.end();
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
