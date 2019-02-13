package no.uib.inf112.core.ui;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import no.uib.inf112.core.RoboRally;
import no.uib.inf112.core.player.Player;
import no.uib.inf112.core.ui.cards.CardActorSlot;
import no.uib.inf112.core.ui.cards.SlotType;
import no.uib.inf112.core.ui.event.events.PowerDownEvent;

import java.io.File;


public class UIHandler implements Disposable {

    public static final String SKIN_NAME = "neutralizer-ui-skin";
    public static final String SKIN_FOLDER = "skins" + File.separator + SKIN_NAME + File.separator;
    public static final String SKIN_JSON_FILE = SKIN_FOLDER + SKIN_NAME + ".json";

    public static final TextureRegion UI_BACKGROUND_TEXTURE;
    public static final TextureRegion CARDS_TEXTURE;

    private static final TextureRegion POWER_DOWN_TEXTURE;
    private static final TextureRegion LIFE_TOKEN_TEXTURE;
    private static final TextureRegion DAMAGE_TOKEN_TEXTURE;

    private final Skin skin;
    private final Table controlPanelTable;
    private final Stage stage;

    private final DragAndDrop dad;
    private final Table cardDrawTable;

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
        controlPanelTable = new Table(skin);

        dad = new DragAndDrop();
        cardDrawTable = new Table();

        create();
        resize();
    }

    /**
     * Initiate ui
     */
    private void create() {
        stage.addActor(cardDrawTable);
        stage.addActor(controlPanelTable);

        cardDrawTable.setTransform(false);
        cardDrawTable.setBackground(new TextureRegionDrawable(UI_BACKGROUND_TEXTURE));
        cardDrawTable.getColor().a = 0.85f;
        cardDrawTable.setVisible(false);

        //set background to extend a bit out of the table
        controlPanelTable.setBackground(new TextureRegionDrawable(UI_BACKGROUND_TEXTURE));
        controlPanelTable.padLeft(50);
        controlPanelTable.padRight(50);
        controlPanelTable.setTransform(false); //optimization


        //Top row is within a table to make the alignment work
        Table topRow = new Table();
        controlPanelTable.add(topRow).fillX(); //let the top row be as wide as the widest part of the cp
        controlPanelTable.row();

        //display life tokens
        HorizontalGroup lifeTokens = new HorizontalGroup();
        topRow.add(lifeTokens).expandX().align(Align.left); //make sire the life tokens are to the left
        lifeTokens.space(5);
        for (int i = 0; i < Player.MAX_LIVES; i++) {
            lifeTokens.addActor(new ImageButton(new TextureRegionDrawable(LIFE_TOKEN_TEXTURE)));
        }

        //display power button, it will by default be to the right
        topRow.add(createPowerDownButton());

        //display damage tokens
        HorizontalGroup damageRow = new HorizontalGroup();
        damageRow.space(5); //space between tokens
        controlPanelTable.add(damageRow).align(Align.left).padBottom(5);
        controlPanelTable.row();

        for (int i = 0; i < Player.MAX_HEALTH; i++) {
            damageRow.addActor(new ImageButton(new TextureRegionDrawable(DAMAGE_TOKEN_TEXTURE)));
        }

        //display cards
        HorizontalGroup cardsRow = new HorizontalGroup();
        cardsRow.space(5); //space between cards
        controlPanelTable.add(cardsRow);
        CardContainer container = RoboRally.player.getCards();
        for (int i = 0; i < Player.MAX_PLAYER_CARDS; i++) {
            CardActorSlot cardActorSlot = new CardActorSlot(dad, container, SlotType.HAND, i);
            container.handCard[i] = cardActorSlot;
            cardsRow.addActor(cardActorSlot);
        }

        for (int i = 0; i < Player.MAX_HEALTH; i++) {
            CardActorSlot cardActorSlot = new CardActorSlot(dad, container, SlotType.DRAWN, i);
            container.drawnCard[i] = cardActorSlot;
            cardDrawTable.add(cardActorSlot).space(5);
        }
    }

    public void drawNewCards(Player player) {
        player.getCards().draw();
        cardDrawTable.setVisible(true);
    }

    public void finishDrawCards() {
        cardDrawTable.setVisible(false);
    }

    public boolean canMoveCards() {
        return cardDrawTable.isVisible();
    }

    private ImageTextButton createPowerDownButton() {
        ImageTextButton.ImageTextButtonStyle style = new ImageTextButton.ImageTextButtonStyle();
        style.imageUp = new TextureRegionDrawable(UIHandler.POWER_DOWN_TEXTURE);
        style.font = new BitmapFont();

        //display what kind of card it is
        ImageTextButton button = new ImageTextButton("", style);


        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                float state = RoboRally.player.isPoweredDown() ? 0.5f : -0.5f;
                button.getColor().a += state;
                RoboRally.getCPEventHandler().fireEvent(new PowerDownEvent());
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
        controlPanelTable.setHeight(controlPanelTable.getPrefHeight() + 20);

        controlPanelTable.setWidth(controlPanelTable.getPrefWidth()); //make sure the background image is drawn
        controlPanelTable.setX(Gdx.graphics.getWidth() / 2f - controlPanelTable.getWidth() / 2); //center the cp in the x axis
        controlPanelTable.setY(5); //let there be a gap at the bottom of screen

        cardDrawTable.setWidth(((CARDS_TEXTURE.getRegionWidth() + 8) * Player.MAX_HEALTH));
        cardDrawTable.setHeight(CARDS_TEXTURE.getRegionHeight() + 10);

        cardDrawTable.setX(Gdx.graphics.getWidth() / 2f - cardDrawTable.getWidth() / 2f);
        cardDrawTable.setY(controlPanelTable.getY() + controlPanelTable.getHeight() + 5);
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }

    public Stage getStage() {
        return stage;
    }
}

