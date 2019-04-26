package no.uib.inf112.core.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import no.uib.inf112.core.GameGraphics;

import java.io.File;
import java.util.ArrayList;

public abstract class AbstractSetupScreen extends AbstractMenuScreen {


    private ArrayList<String> mapList = new ArrayList<>();
    private final Drawable SELECT_BOX_BACKGROUND = new TextureRegionDrawable(new Texture("drop_down_background.png"));

    private BitmapFont listFont;
    private BitmapFont selectedFont;

    private FrameBuffer fb;
    private Drawable mapImg;
    private float mapImgHeight;
    private float mapImgWidth;
    private Label type;
    private Label length;
    private Label flagCount;

    private boolean startGame = false;

    public AbstractSetupScreen(GameGraphics game) {
        super(game);

        FileHandle[] files = Gdx.files.internal("assets" + File.separatorChar + GameGraphics.MAP_FOLDER).list(".tmx");
        for (FileHandle file : files) {
            mapList.add(nameifyFile(file.nameWithoutExtension()));
        }

        listFont = game.generateFont(GameGraphics.SCREEN_FONT, 16);
        selectedFont = game.generateFont(GameGraphics.SCREEN_FONT_BOLD, 25);

        fb = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
    }

    @Override
    public void show() {
        TextButton returnButton = createReturnButton(50);
        returnButton.setPosition(3 * stage.getWidth() / 4 - returnButton.getWidth() - 10, stage.getHeight() / 20);
        TextButton startButton = createButton("START", 80);
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                startGame = true;
            }
        });
        startButton.setPosition(3 * stage.getWidth() / 4 + 20, stage.getHeight() / 20);

        type = createLabel("Map type: ", camera.viewportWidth / 4f, 4 * camera.viewportHeight / 20, 20);
        type.setColor(Color.WHITE);
        length = createLabel("Length: ", camera.viewportWidth / 4f, 3 * camera.viewportHeight / 20, 20);
        length.setColor(Color.WHITE);
        flagCount = createLabel("# flags: ", camera.viewportWidth / 4f, 2 * camera.viewportHeight / 20, 20);
        flagCount.setColor(Color.WHITE);

        stage.addActor(returnButton);
        stage.addActor(startButton);
        stage.addActor(type);
        stage.addActor(length);
        stage.addActor(flagCount);
        stage.addActor(createMapSelectBox());

        setMapPreview();
        game.batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void render(float v) {
        super.render(v);

        game.batch.begin();

        mapImg.draw(game.batch, (5 * camera.viewportWidth / 12f) - mapImgWidth / 2, (7 * camera.viewportHeight / 12f) - mapImgHeight / 2, mapImgWidth, mapImgHeight);

        game.batch.end();

        if (startGame) { // Using this solution because we can't start game from clicked method
            game.setScreen(new GameScreen(game));
        }
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

        String[] mapListArray = new String[mapList.size()];
        selectBox.setItems(mapList.toArray(mapListArray));
        selectBox.setSelected(nameifyFile(GameGraphics.mapFileName));

        // Add listener for if selected map changes
        selectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GameGraphics.setMap(fileifyName(selectBox.getSelected()));
                setMapPreview();
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
        selectBox.setPosition(5, 14 * stage.getHeight() / 15);

        return selectBox;

    }

    private String fileifyName(String mapName) {
        return mapName.replace(" ", "_").toLowerCase();
    }


    private String nameifyFile(String mapFile) {
        String[] name = mapFile.split("_");
        StringBuilder builder = new StringBuilder();
        for (String s : name) {
            builder.append(s.substring(0, 1).toUpperCase()).append(s.substring(1)).append(" ");
        }
        return builder.toString().substring(0, builder.length() - 1);
    }

    private void setMapPreview() {
        TiledMap map = new TmxMapLoader().load(GameGraphics.MAP_FOLDER + GameGraphics.mapFileName + GameGraphics.MAP_EXTENSION);
        fb.begin();
        TiledMapRenderer renderer = new OrthogonalTiledMapRenderer(map);
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        int mapWidth = map.getProperties().get("width", int.class);
        int mapHeight = map.getProperties().get("height", int.class);
        int tileWidth = map.getProperties().get("tilewidth", int.class);
        int tileHeight = map.getProperties().get("tileheight", int.class);

        int width = tileWidth * mapWidth;
        int height = tileHeight * mapHeight;
        Matrix4 m = new Matrix4();
        m.setToOrtho2D(0, 0, width, height);
        renderer.setView(m, 0, 0, width, height);
        renderer.render();
        fb.end();

        type.setText("Map type: " + map.getProperties().get("Type"));
        length.setText("Length: " + map.getProperties().get("Length"));
        flagCount.setText("# flags: " + map.getProperties().get("FlagCount"));
        map.dispose();

        TextureRegion tr = new TextureRegion(fb.getColorBufferTexture());
        tr.flip(false, true);
        float mapRatio = (float) mapHeight / mapWidth;

        if (mapRatio > 1) {
            mapImgHeight = 2 * camera.viewportHeight / 3;
            mapImgWidth = mapImgHeight / mapRatio;
        } else {
            mapImgWidth = camera.viewportWidth / 3;
            mapImgHeight = mapRatio * mapImgWidth;
        }

        mapImg = new TextureRegionDrawable(tr);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        fb.dispose();
        fb = new FrameBuffer(Pixmap.Format.RGBA8888, width, height, true);
        setMapPreview();
    }
}


