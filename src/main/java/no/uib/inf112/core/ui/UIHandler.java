package no.uib.inf112.core.ui;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import no.uib.inf112.core.RoboRally;
import no.uib.inf112.core.player.Card;
import no.uib.inf112.core.player.Player;
import no.uib.inf112.core.ui.event.ControlPanelEvent;
import no.uib.inf112.core.ui.event.events.CardClickedEvent;
import no.uib.inf112.core.ui.event.events.PowerDownEvent;

import java.io.File;


public class UIHandler implements Disposable {

    private final Skin skin;
    private final Table table;
    private final Stage stage;


    public static final String SKIN_NAME = "neutralizer-ui-skin";
    public static final String SKIN_FOLDER = "skins" + File.separator + SKIN_NAME + File.separator;
    public static final String SKIN_JSON_FILE = SKIN_FOLDER + SKIN_NAME + ".json";

    private static final TextureRegion UI_BACKGROUND_TEXTURE;
    private static final TextureRegion CARDS_TEXTURE;

    private static final TextureRegion POWER_DOWN_TEXTURE;
    private static final TextureRegion LIFE_TOKEN_TEXTURE;
    private static final TextureRegion DAMAGE_TOKEN_TEXTURE;

    static {
        //temp textures, to be replaced with real textures
        //TODO find/create real textures for control panel
        UI_BACKGROUND_TEXTURE = createTempRectTexture(1, 1, new Color(0.145f, 0.145f, 0.145f, 0.9f));
        CARDS_TEXTURE = createTempRectTexture(100, 161, Color.BLUE); //make sure the card are golden ratios (ish)
        POWER_DOWN_TEXTURE = createTempCircleTexture(41, Color.RED);
        LIFE_TOKEN_TEXTURE = createTempCircleTexture(25, Color.GREEN);
        DAMAGE_TOKEN_TEXTURE = createTempCircleTexture(19, Color.YELLOW);
    }

    /*
     * Size CANNOT be dividable by two, as it will make the returning texture look cut off
     */
    private static TextureRegion createTempCircleTexture(int size, Color color) {
        final Pixmap pixmap = new Pixmap(size, size, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fillCircle(pixmap.getWidth() / 2, pixmap.getWidth() / 2, size / 2);
        return new TextureRegion(new Texture(pixmap));
    }

    private static TextureRegion createTempRectTexture(int width, int height, Color color) {
        final Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fill();
        return new TextureRegion(new Texture(pixmap));
    }


    public UIHandler() {
        stage = new Stage(new ScreenViewport());
        RoboRally.getInputMultiplexer().addProcessor(stage);

//        stage.setDebugAll(true);

        skin = new Skin(Gdx.files.internal(SKIN_JSON_FILE));
        table = new Table(skin);

        create();
        resize();
    }

    /**
     * Initiate ui
     */
    private void create() {
        stage.addActor(table);

        //set background to extend a bit out of the table
        table.setBackground(new TextureRegionDrawable(UI_BACKGROUND_TEXTURE));
        table.padLeft(50);
        table.padRight(50);
        table.setTransform(false); //optimization


        //Top row is within a table to make the alignment work
        Table topRow = new Table();
        table.add(topRow).fillX(); //let the top row be as wide as the widest part of the cp
        table.row();

        //display life tokens
        HorizontalGroup lifeTokens = new HorizontalGroup();
        topRow.add(lifeTokens).expandX().align(Align.left); //make sire the life tokens are to the left
        lifeTokens.space(5);
        for (int i = 0; i < Player.MAX_LIVES; i++) {
            lifeTokens.addActor(new ImageButton(new TextureRegionDrawable(LIFE_TOKEN_TEXTURE)));
        }

        //display power button, it will by default be to the right
        topRow.add(createInteractButton(PowerDownEvent.class, 0, POWER_DOWN_TEXTURE));

        //display damage tokens
        HorizontalGroup damageRow = new HorizontalGroup();
        damageRow.space(5); //space between tokens
        table.add(damageRow).align(Align.left).padBottom(5);
        table.row();

        for (int i = 0; i < Player.MAX_HEALTH; i++) {
            damageRow.addActor(new ImageButton(new TextureRegionDrawable(DAMAGE_TOKEN_TEXTURE)));
        }

        //display cards
        HorizontalGroup cardsRow = new HorizontalGroup();
        cardsRow.space(5); //space between cards
        table.add(cardsRow);
        for (int i = 0; i < Player.MAX_PLAYER_CARDS; i++) {
            cardsRow.addActor(createInteractButton(CardClickedEvent.class, i, CARDS_TEXTURE));
        }
    }

    private ImageTextButton createInteractButton(Class<? extends ControlPanelEvent> eventType, int id,
                                                 TextureRegion textureRegion) {
        ImageTextButton.ImageTextButtonStyle style = new ImageTextButton.ImageTextButtonStyle();
        style.imageUp = new TextureRegionDrawable(textureRegion);
        style.font = new BitmapFont();

        //display what kind of card it is
        ImageTextButton button = new ImageTextButton("", style);
        button.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                if (eventType != CardClickedEvent.class) {
                    return true;
                }
                Card card = RoboRally.getPlayerHandler().mainPlayer().getCards()[id];
                button.setText("pri " + card.getPriority() + "\n" + card.getAction().name());
                button.getLabelCell().padLeft(-textureRegion.getRegionWidth()); //make sure the tex is with in the card
                return false;
            }
        });

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //this can be written better
                ControlPanelEvent cpEvent;
                if (eventType == CardClickedEvent.class) {
                    cpEvent = new CardClickedEvent(id);
                } else {
                    cpEvent = new PowerDownEvent();
                    float state = RoboRally.getPlayerHandler().mainPlayer().isPoweredDown() ? 0.5f : -0.5f;
                    button.getColor().a += state;
                }
                RoboRally.getCPEventHandler().fireEvent(cpEvent);
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                //darken when hovering over to signal this object can be clicked
                button.getColor().a -= 0.25f;
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                //reset color when leaving
                button.getColor().a += 0.25;
            }
        });
        return button;
    }

    public void update() {
        stage.act(Gdx.graphics.getDeltaTime()); //Perform ui logic
        stage.draw(); //Draw the ui
    }

    public void resize() {
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);

        //make sure we have some background around the down down button
        table.setHeight(table.getPrefHeight() + 20);

        table.setWidth(table.getPrefWidth()); //make sure the background image is drawn
        table.setX(Gdx.graphics.getWidth() / 2f - table.getPrefWidth() / 2); //center the cp in the x axis
        table.setY(5); //let there be a gap at the bottom of screen
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}

