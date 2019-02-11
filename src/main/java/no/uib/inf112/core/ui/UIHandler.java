package no.uib.inf112.core.ui;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import no.uib.inf112.core.RoboRally;

import java.io.File;


public class UIHandler implements Disposable {

    private final Skin skin;
    private final Table table;
    private Stage stage;


    public static final String SKIN_NAME = "neutralizer-ui-skin";
    public static final String SKIN_FOLDER = "skins" + File.separator + SKIN_NAME + File.separatorChar;
    public static final String SKIN_JSON_FILE = SKIN_FOLDER + SKIN_NAME + ".json";

    private static final TextureRegion UI_BACKGROUND_TEXTURE;
    private static final TextureRegion CARDS_TEXTURE;

    private static final TextureRegion POWER_DOWN_TEXTURE;
    private static final TextureRegion LIFE_TOKEN_TEXTURE;
    private static final TextureRegion DAMAGE_TOKEN_TEXTURE;

    static {
        UI_BACKGROUND_TEXTURE = createTempTexture(100, Color.BLACK, false);
        CARDS_TEXTURE = createTempTexture(100, Color.BLUE, false);
        POWER_DOWN_TEXTURE = createTempTexture(51, Color.RED, true);
        LIFE_TOKEN_TEXTURE = createTempTexture(25, Color.GREEN, true);
        DAMAGE_TOKEN_TEXTURE = createTempTexture(25, Color.YELLOW, true);

    }

    private static TextureRegion createTempTexture(int size, Color color, boolean circle) {
        final Pixmap pixmap = new Pixmap(size, size, Pixmap.Format.RGB888);
        pixmap.setColor(color);

        if (circle) {
            pixmap.fillCircle(pixmap.getWidth() / 2, pixmap.getWidth() / 2, size / 2);
        }
        else {
            pixmap.fill();
        }
        return new TextureRegion(new Texture(pixmap));
    }

    public UIHandler() {
        stage = new Stage(new ScreenViewport());
        RoboRally.getInputMultiplexer().addProcessor(stage);

        skin = new Skin(Gdx.files.internal(SKIN_JSON_FILE));

        table = new Table();
        stage.addActor(table);

        table.setDebug(true, true);
        table.setBackground(new TextureRegionDrawable(UI_BACKGROUND_TEXTURE));
        table.pad(5);


//        TextButton button = new TextButton("cards", skin);
//        button.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                RoboRally.round(); // user has placed his "cards"
//            }
//        });
//
////        button.setOrigin(Align.left);
//        table.add(button).pad(10);

        HorizontalGroup topRow = new HorizontalGroup();
        table.add(topRow);

        HorizontalGroup lifeTokens = new HorizontalGroup();
        topRow.addActor(lifeTokens);
        lifeTokens.space(25);

        for (int i = 0; i < 3; i++) {
            lifeTokens.addActor(new ImageButton(new TextureRegionDrawable(LIFE_TOKEN_TEXTURE)));
        }
        topRow.addActor(new ImageButton(new TextureRegionDrawable(POWER_DOWN_TEXTURE)));
        topRow.space(350);
        topRow.pad(2);
        table.row();

        HorizontalGroup damageRow = new HorizontalGroup();
        table.add(damageRow);
        table.row();
        damageRow.space(5);
        for (int i = 0; i < 10; i++) {
            damageRow.addActor(new ImageButton(new TextureRegionDrawable(DAMAGE_TOKEN_TEXTURE)));
        }

        HorizontalGroup cardsRow = new HorizontalGroup();
        table.add(cardsRow);
        cardsRow.space(5);
        for (int i = 0; i < 5; i++) {
            cardsRow.addActor(new ImageButton(new TextureRegionDrawable(CARDS_TEXTURE)));
        }

        resize();

    }

    public void update() {
        stage.act(Gdx.graphics.getDeltaTime()); //Perform ui logic
        stage.draw(); //Draw the ui
    }

    public void resize() {
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);

        table.setHeight(Gdx.graphics.getHeight() / 2f);
        table.setWidth(Gdx.graphics.getWidth());//) - (Gdx.graphics.getWidth() / 10f)

    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}

