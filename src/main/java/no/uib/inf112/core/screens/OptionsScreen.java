package no.uib.inf112.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
import com.badlogic.gdx.utils.viewport.StretchViewport;
import no.uib.inf112.core.GameGraphics;

import java.io.File;

public class OptionsScreen implements Screen {

    private final String OPTIONS_FOLDER = "optionsscreen" + File.separatorChar;
    private final String[] MAP_LIST = new String[]{"Risky Exchange", "Checkmate", "Dizzy Dash", "Island Hop", "Chop Shop Challenge"};
    private Drawable mapImg = new TextureRegionDrawable(new Texture(OPTIONS_FOLDER + GameGraphics.mapName + ".png"));

    private GameGraphics game;
    Stage stage;

    private BitmapFont listFont;
    private BitmapFont selectedFont;

    private float width;
    private float height;

    private SelectBox<String> selectBox;

    public OptionsScreen(GameGraphics game) {
        this.game = game;
        stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);

        listFont = game.generateFont("screen_font.ttf", 20);
        selectedFont = game.generateFont("screen_font_bold.ttf", 25);
    }

    @Override
    public void show() {
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();

        SelectBox.SelectBoxStyle style = new SelectBox.SelectBoxStyle();
        style.font = selectedFont;
        style.fontColor = Color.BLACK;
        style.scrollStyle = new ScrollPane.ScrollPaneStyle();
        Drawable img = new TextureRegionDrawable(new Texture("drop_down_background.png"));
        style.listStyle = new List.ListStyle(listFont, Color.RED, Color.WHITE, img);

        selectBox = new SelectBox<>(style);
        selectBox.setAlignment(Align.center);
        selectBox.getList().setAlignment(Align.left);
        selectBox.getStyle().listStyle.selection.setRightWidth(5);
        selectBox.getStyle().listStyle.selection.setLeftWidth(15);
        selectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setMap(selectBox.getSelected());
                mapImg = new TextureRegionDrawable(new Texture(OPTIONS_FOLDER + GameGraphics.mapName + ".png"));
            }
        });
        selectBox.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                selectBox.showList();
            }
        });

        selectBox.setItems(MAP_LIST);
        selectBox.setSize(stage.getWidth() / 4f - 10, stage.getHeight() / 20f);
        selectBox.setPosition(10, (3 * stage.getHeight() / 4));
        stage.addActor(selectBox);

    }

    @Override
    public void render(float v) {

        Gdx.gl.glClearColor(0.5f, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            game.setScreen(new TitleScreen(game));
        }

        stage.act(v);
        stage.draw();

        game.batch.begin();
        mapImg.draw(game.batch, stage.getWidth() / 4f, stage.getHeight() / 6f, width / 4 - 10, 2 * (stage.getHeight() / 3));
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {

        this.width = width;
        this.height = height;

        stage.getViewport().update(width, height, true);

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
