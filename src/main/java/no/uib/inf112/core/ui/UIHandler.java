package no.uib.inf112.core.ui;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.player.Player;
import no.uib.inf112.core.ui.actors.ControlPanelElement;
import no.uib.inf112.core.ui.actors.PowerButton;
import no.uib.inf112.core.ui.actors.cards.CardSlot;
import no.uib.inf112.core.ui.actors.cards.SlotType;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;


public class UIHandler implements Disposable {

    public static final String SKIN_NAME = "neutralizer-ui-skin";
    public static final String SKIN_FOLDER = "skins" + File.separator + SKIN_NAME + File.separator;
    public static final String SKIN_JSON_FILE = SKIN_FOLDER + SKIN_NAME + ".json";

    public static final TextureRegion UI_BACKGROUND_TEXTURE;
    public static final TextureRegion CARDS_TEXTURE;

    public static final TextureRegion POWER_DOWN_TEXTURE;
    private static final TextureRegion LIFE_TOKEN_TEXTURE;
    private static final TextureRegion DAMAGE_TOKEN_TEXTURE;
    private static final TextureRegion FLAG_TAKEN_TEXTURE;

    //How much space there should be between each element in the ui
    public static final int DEFAULT_SPACING = 5;

    private final Skin skin;
    private final Table controlPanelTable;
    private final Stage stage;

    private final DragAndDrop dad;
    private final Table cardDrawTable;

    static {
        //temp textures, to be replaced with real textures
        //TODO Issue #52 find/create real textures for control panel
        UI_BACKGROUND_TEXTURE = createTempRectTexture(1, 1, new Color(0.145f, 0.145f, 0.145f, 0.9f));
        CARDS_TEXTURE = createTempRectTexture(100, 161, Color.BLUE); //make sure the card are golden ratios (ish)
        POWER_DOWN_TEXTURE = createTempCircleTexture(41, Color.RED);
        LIFE_TOKEN_TEXTURE = createTempCircleTexture(25, Color.GREEN);
        DAMAGE_TOKEN_TEXTURE = createTempCircleTexture(19, Color.YELLOW);
        FLAG_TAKEN_TEXTURE = createTempFlagTexture(20, 25, Color.ORANGE);
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

    private static TextureRegion createTempFlagTexture(int width, int height, Color color) {
        final Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fillTriangle(width, height / 4, 0, height / 2, 0, 0);
        pixmap.fillRectangle(0, 0, width / 6, height);
        return new TextureRegion(new Texture(pixmap));
    }

    public UIHandler() {
        stage = new Stage(new ScreenViewport());
        GameGraphics.getInputMultiplexer().addProcessor(stage);

//        stage.setDebugAll(true);
        skin = new Skin(Gdx.files.internal(SKIN_JSON_FILE));
        controlPanelTable = new Table(skin);

        dad = new DragAndDrop();
        dad.setDragTime(50);
        dad.setDragActorPosition(CARDS_TEXTURE.getRegionWidth() / 2f, -CARDS_TEXTURE.getRegionHeight() / 2f);
        cardDrawTable = new Table();

        create();
    }

    /**
     * Initiate ui
     */
    private void create() {
        stage.addActor(cardDrawTable);
        stage.addActor(controlPanelTable);

        cardDrawTable.setTransform(false);
        cardDrawTable.setBackground(new TextureRegionDrawable(UI_BACKGROUND_TEXTURE));
        cardDrawTable.getColor().a = 0.9f;
        cardDrawTable.pad(DEFAULT_SPACING);
        cardDrawTable.setVisible(false);

        //set background to extend a bit out of the table
        controlPanelTable.setBackground(new TextureRegionDrawable(UI_BACKGROUND_TEXTURE));
        controlPanelTable.pad(DEFAULT_SPACING * 2, DEFAULT_SPACING * 4, DEFAULT_SPACING * 2, DEFAULT_SPACING * 4);
        controlPanelTable.setTransform(false); //optimization


        //Top row is within a table to make the alignment work
        Table topRow = new Table();
        controlPanelTable.add(topRow).fillX(); //let the top row be as wide as the widest part of the cp
        controlPanelTable.row();

        //display life tokens
        HorizontalGroup lifeTokens = new HorizontalGroup().space(DEFAULT_SPACING);
        topRow.add(lifeTokens).expandX().align(Align.left); //make sure the life tokens are to the left
        for (int i = 0; i < Player.MAX_LIVES; i++) {
            int id = i;
            lifeTokens.addActor(new ControlPanelElement(LIFE_TOKEN_TEXTURE) {
                @Override
                public boolean isDisabled() {
                    return GameGraphics.getRoboRally().getPlayerHandler().mainPlayer().getLives() <= id;
                }
            });
        }

        int flags = 8;

        HorizontalGroup flagsTaken = new HorizontalGroup().space(DEFAULT_SPACING).padRight(DEFAULT_SPACING);
        topRow.add(flagsTaken);
        for (int i = 0; i < flags; i++) {
            int id = i;
            flagsTaken.addActor(new ControlPanelElement(FLAG_TAKEN_TEXTURE) {
                @Override
                public boolean isDisabled() {
                    return GameGraphics.getRoboRally().getPlayerHandler().mainPlayer().getFlags() <= id;
                }
            });
        }

        //display power button, it will by default be to the right
        topRow.add(new PowerButton());

        //display damage tokens
        HorizontalGroup damageRow = new HorizontalGroup();
        damageRow.space(DEFAULT_SPACING); //space between tokens
        controlPanelTable.add(damageRow).align(Align.left).padBottom(DEFAULT_SPACING);
        controlPanelTable.row();

        for (int i = 0; i < Player.MAX_HEALTH; i++) {
            int id = i;
            damageRow.addActor(new ControlPanelElement(DAMAGE_TOKEN_TEXTURE) {
                @Override
                public boolean isDisabled() {
                    return GameGraphics.getRoboRally().getPlayerHandler().mainPlayer().getHealth() <= id;
                }
            });
        }

        //display cards
        HorizontalGroup cardsRow = new HorizontalGroup();
        cardsRow.space(DEFAULT_SPACING); //space between cards
        controlPanelTable.add(cardsRow);
        CardContainer container = GameGraphics.getRoboRally().getPlayerHandler().mainPlayer().getCards();

        for (int i = 0; i < Player.MAX_PLAYER_CARDS; i++) {
            CardSlot cardSlot = new CardSlot(i, SlotType.HAND, container, dad);
            container.handCard[i] = cardSlot;
            cardsRow.addActor(cardSlot);
        }

        for (int i = 0; i < Player.MAX_DRAW_CARDS; i++) {
            CardSlot cardSlot = new CardSlot(i, SlotType.DRAWN, container, dad);
            container.drawnCard[i] = cardSlot;
            cardDrawTable.add(cardSlot).space(DEFAULT_SPACING);
        }
    }


    /**
     * Show the drawn cards table of the main player.
     * Do not use this at the start of a new round, use {@link no.uib.inf112.core.player.UserPlayer#beginDrawCards()}
     *
     * @throws IllegalStateException If no drawn card slots have a card in them
     */
    public void showDrawnCards() {
        Stream<CardSlot> drawnCard = Arrays.stream(GameGraphics.getRoboRally().getPlayerHandler().mainPlayer().getCards().drawnCard);
        if (drawnCard.allMatch(Objects::isNull)) {
            throw new IllegalStateException("At least one card must be present on the drawn cards to show them");
        }
        cardDrawTable.setVisible(true);
    }

    /**
     * Hide the drawn cards table of the main player. After this the player is ready for the round
     * Do not use this to end the player draw turn, use {@link no.uib.inf112.core.player.UserPlayer#endDrawCards()}
     */
    public void hideDrawnCards() {
        cardDrawTable.setVisible(false);
    }

    /**
     * @return If the player has finished choosing their cards
     */
    public boolean isDrawnCardsVisible() {
        return cardDrawTable.isVisible();
    }

    public void update() {
        stage.act(Gdx.graphics.getDeltaTime()); //Perform ui logic
        stage.draw(); //Draw the ui
    }

    public void resize() {
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);

        controlPanelTable.pack();
        cardDrawTable.pack();

        controlPanelTable.setX(Gdx.graphics.getWidth() / 2f - controlPanelTable.getWidth() / 2f); //center the cp in the x axis
        controlPanelTable.setY(DEFAULT_SPACING); //let there be a gap at the bottom of screen

        cardDrawTable.setX(Gdx.graphics.getWidth() / 2f - cardDrawTable.getWidth() / 2f);
        //place the draw ui just above the control panel
        cardDrawTable.setY(controlPanelTable.getY() + controlPanelTable.getHeight() + DEFAULT_SPACING);
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }

    public Stage getStage() {
        return stage;
    }

    public DragAndDrop getDad() {
        return dad;
    }
}

