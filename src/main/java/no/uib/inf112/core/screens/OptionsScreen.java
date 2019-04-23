package no.uib.inf112.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
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
    private Drawable mapImg;
    private final Drawable SELECT_BOX_BACKGROUND = new TextureRegionDrawable(new Texture("drop_down_background.png"));

    private GameGraphics game;
    private Stage stage;
    private OrthographicCamera camera;

    private BitmapFont listFont;
    private BitmapFont selectedFont;

    private boolean returnToMenu;


    public OptionsScreen(GameGraphics game) {
        this.game = game;
        mapImg = new TextureRegionDrawable(new Texture(OPTIONS_FOLDER + fileifyName(GameGraphics.mapName) + ".png"));
        camera = new OrthographicCamera();
        stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera));
        Gdx.input.setInputProcessor(stage);

        listFont = game.generateFont("screen_font.ttf", 20);
        selectedFont = game.generateFont("screen_font_bold.ttf", 25);
    }

    @Override
    public void show() {

        TextButton backButton = game.createButton("BACK", 40);
        backButton.setPosition(stage.getWidth() / 2 - (backButton.getWidth() / 2), 20);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                returnToMenu = true;
            }
        });

        stage.addActor(backButton);
        stage.addActor(createMusicButton());
        stage.addActor(createMapSelectBox());

    }

    @Override
    public void render(float v) {

        Gdx.gl.glClearColor(0.5f, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (returnToMenu) {
            game.setScreen(new TitleScreen(game));
        }

        stage.act(v);
        stage.draw();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        mapImg.draw(game.batch, camera.viewportWidth / 4f, camera.viewportHeight / 6f, camera.viewportWidth / 4 - 10, 2 * (camera.viewportHeight / 3));
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {

        stage.getViewport().update(width, height, true);
        camera.update();

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

    private TextButton createMusicButton() {
        TextButton musicButton;
        if (GameGraphics.backgroundMusic.isPlaying()) {
            musicButton = game.createButton("Music on", 40);
        } else {
            musicButton = game.createButton("Music off", 40);
        }
        musicButton.setPosition(3 * stage.getWidth() / 4, 4 * stage.getHeight() / 5);
        musicButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (musicButton.getText().toString().equals("Music on")) {
                    musicButton.setText("Music off");
                    GameGraphics.backgroundMusic.pause();
                } else {
                    musicButton.setText("Music on");
                    GameGraphics.backgroundMusic.play();
                }
            }
        });
        return musicButton;
    }


    private SelectBox<String> createMapSelectBox() {

        SelectBox.SelectBoxStyle style = new SelectBox.SelectBoxStyle();
        style.font = selectedFont;
        style.fontColor = Color.BLACK;
        style.scrollStyle = new ScrollPane.ScrollPaneStyle();
        style.listStyle = new List.ListStyle(listFont, Color.RED, Color.WHITE, SELECT_BOX_BACKGROUND);

        SelectBox<String> selectBox = new SelectBox<>(style);

        selectBox.setAlignment(Align.center);
        selectBox.getStyle().listStyle.selection.setLeftWidth(20);

        selectBox.setItems(MAP_LIST);
        selectBox.setSelected(GameGraphics.mapName);

        // Add listener for if selected map changes
        selectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GameGraphics.setMap(selectBox.getSelected());
                mapImg = new TextureRegionDrawable(new Texture(OPTIONS_FOLDER + fileifyName(GameGraphics.mapName) + ".png"));
            }
        });
        // Selection box should always show list (it looks nicer)
        selectBox.addAction(new Action() {
            @Override
            public boolean act(float v) {
                selectBox.showList();
                return false;
            }
        });

        selectBox.setSize(stage.getWidth() / 4f - 10, stage.getHeight() / 20f);
        selectBox.setPosition(5, (4 * stage.getHeight() / 5));

        return selectBox;

    }

    private String fileifyName(String mapName) {
        return mapName.replace(" ", "_").toLowerCase();
    }

}
