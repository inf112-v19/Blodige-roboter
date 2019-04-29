package no.uib.inf112.core.ui;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.player.IPlayer;
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

    // The different card textures
    public static final TextureRegion UI_BACKGROUND_TEXTURE;
    public static final TextureRegion CARDS_SLOT_TEXTURE;
    public static final TextureRegion MOVE1_TEXTURE;
    public static final TextureRegion MOVE2_TEXTURE;
    public static final TextureRegion MOVE3_TEXTURE;
    public static final TextureRegion BACK_UP_TEXTURE;
    public static final TextureRegion TURN_LEFT_TEXTURE;
    public static final TextureRegion TURN_RIGHT_TEXTURE;
    public static final TextureRegion U_TURN_TEXTURE;

    public static final TextureRegion POWER_DOWN_TEXTURE;
    public static final TextureRegion NOT_POWER_DOWN_TEXTURE;

    private static final TextureRegion LIFE_TOKEN_TEXTURE;
    private static final TextureRegion NOT_LIFE_TOKEN_TEXTURE;

    private static final TextureRegion DAMAGE_TOKEN_TEXTURE;
    private static final TextureRegion NOT_DAMAGE_TOKEN_TEXTURE;

    private static final TextureRegion FLAG_TAKEN_TEXTURE;
    private static final TextureRegion NOT_FLAG_TAKEN_TEXTURE;

    //How much space there should be between each element in the ui
    private static final int DEFAULT_SPACING = 5;

    private final Table controlPanelTable;
    private final Stage stage;
    private PowerButton powerButton;

    private final DragAndDrop dad;
    private final Table cardDrawTable;

    private static final String UI_FOLDER = "ui" + File.separatorChar;
    private static final String CARD_SKIN_FOLDER = UI_FOLDER + "cardSkins" + File.separatorChar;
    private static final String BUTTON_FOLDER = UI_FOLDER + "buttons" + File.separatorChar;

    public static final FreeTypeFontGenerator card_font_generator;
    public static final FreeTypeFontGenerator.FreeTypeFontParameter card_font_parameter;

    static {
        UI_BACKGROUND_TEXTURE = new TextureRegion(new Texture(UI_FOLDER + "background2.png"), 602, 198);

        CARDS_SLOT_TEXTURE = new TextureRegion(new Texture(CARD_SKIN_FOLDER + "emptySlot.png"));
        MOVE1_TEXTURE = new TextureRegion(new Texture(CARD_SKIN_FOLDER + "move1.png"));
        MOVE2_TEXTURE = new TextureRegion(new Texture(CARD_SKIN_FOLDER + "move2.png"));
        MOVE3_TEXTURE = new TextureRegion(new Texture(CARD_SKIN_FOLDER + "move3.png"));
        BACK_UP_TEXTURE = new TextureRegion(new Texture(CARD_SKIN_FOLDER + "backUp.png"));
        TURN_LEFT_TEXTURE = new TextureRegion(new Texture(CARD_SKIN_FOLDER + "turnLeft.png"));
        TURN_RIGHT_TEXTURE = new TextureRegion(new Texture(CARD_SKIN_FOLDER + "turnRight.png"));
        U_TURN_TEXTURE = new TextureRegion(new Texture(CARD_SKIN_FOLDER + "uTurn.png"));

        POWER_DOWN_TEXTURE = new TextureRegion(new Texture(BUTTON_FOLDER + "power_down.png"));
        NOT_POWER_DOWN_TEXTURE = new TextureRegion(new Texture(BUTTON_FOLDER + "not_power_down.png"));

        LIFE_TOKEN_TEXTURE = new TextureRegion(new Texture(BUTTON_FOLDER + "life.png"));
        NOT_LIFE_TOKEN_TEXTURE = new TextureRegion(new Texture(BUTTON_FOLDER + "not_life.png"));

        DAMAGE_TOKEN_TEXTURE = new TextureRegion(new Texture(BUTTON_FOLDER + "damage.png"));
        NOT_DAMAGE_TOKEN_TEXTURE = new TextureRegion(new Texture(BUTTON_FOLDER + "not_damage.png"));

        FLAG_TAKEN_TEXTURE = new TextureRegion(new Texture(BUTTON_FOLDER + "flag.png"));
        NOT_FLAG_TAKEN_TEXTURE = new TextureRegion(new Texture(BUTTON_FOLDER + "not_flag.png"));

        card_font_generator = new FreeTypeFontGenerator(Gdx.files.internal("card_font.ttf"));
        card_font_parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    }


    public UIHandler() {
        stage = new Stage(new FitViewport(1920, 1080));
        GameGraphics.getInputMultiplexer().addProcessor(stage);

        // Setting color and border for font since this should be the same even though the size and padding can vary
        card_font_parameter.borderWidth = 1;
        card_font_parameter.borderColor = Color.BLACK;
        card_font_parameter.color = Color.valueOf("#f1c232");

//        stage.setDebugAll(true);

        dad = new DragAndDrop();
        dad.setDragTime(50);
        dad.setDragActorPosition(CARDS_SLOT_TEXTURE.getRegionWidth() / 2f, -CARDS_SLOT_TEXTURE.getRegionHeight() / 2f);


        Table backgroundTable = new Table();
        stage.addActor(backgroundTable);
        backgroundTable.setFillParent(true);

        controlPanelTable = new Table();
        cardDrawTable = new Table();

        backgroundTable.add(cardDrawTable).row();
        backgroundTable.add(controlPanelTable).space(DEFAULT_SPACING);
        backgroundTable.align(Align.bottom).padBottom(DEFAULT_SPACING);

        create();
    }

    /**
     * Initiate ui
     */
    private void create() {

        cardDrawTable.setTransform(false);
        cardDrawTable.pad(DEFAULT_SPACING);
        cardDrawTable.setVisible(false);

        //set background to extend a bit out of the table
        controlPanelTable.setBackground(new TextureRegionDrawable(UI_BACKGROUND_TEXTURE));
        controlPanelTable.pad(DEFAULT_SPACING * 2, DEFAULT_SPACING * 4, DEFAULT_SPACING * 4, DEFAULT_SPACING * 4);
        controlPanelTable.setTransform(false); //optimization


        //Top row is within a table to make the alignment work
        Table topRow = new Table();
        controlPanelTable.add(topRow).fillX(); //let the top row be as wide as the widest part of the cp
        controlPanelTable.row();

        //display life tokens
        HorizontalGroup lifeTokens = new HorizontalGroup().space(0);
        topRow.add(lifeTokens).expandX().align(Align.left); //make sure the life tokens are to the left
        for (int i = 0; i < IPlayer.MAX_LIVES; i++) {
            int id = i;
            lifeTokens.addActor(new ControlPanelElement(LIFE_TOKEN_TEXTURE) {
                @Override
                public boolean isDisabled() {
                    return GameGraphics.getRoboRally().getPlayerHandler().mainPlayer().getLives() <= id;
                }

                @Override
                public void act(float delta) {
                    if (isDisabled()) {
                        getStyle().imageUp = new TextureRegionDrawable(UIHandler.NOT_LIFE_TOKEN_TEXTURE);
                        updateImage();
                    } else {
                        getStyle().imageUp = new TextureRegionDrawable(UIHandler.LIFE_TOKEN_TEXTURE);
                        updateImage();
                    }
                }
            });
        }

        int flags = GameGraphics.getRoboRally().getPlayerHandler().getFlagCount();

        HorizontalGroup flagsTaken = new HorizontalGroup().space(2 * DEFAULT_SPACING).padRight(3 * DEFAULT_SPACING);
        topRow.add(flagsTaken);
        for (int i = 0; i < flags; i++) {
            int id = i;
            flagsTaken.addActor(new ControlPanelElement(FLAG_TAKEN_TEXTURE) {
                @Override
                public boolean isDisabled() {
                    return GameGraphics.getRoboRally().getPlayerHandler().mainPlayer().getFlags() <= id;
                }

                @Override
                public void act(float delta) {
                    if (isDisabled()) {
                        getStyle().imageUp = new TextureRegionDrawable(UIHandler.NOT_FLAG_TAKEN_TEXTURE);
                        updateImage();
                    } else {
                        getStyle().imageUp = new TextureRegionDrawable(UIHandler.FLAG_TAKEN_TEXTURE);
                        updateImage();
                    }
                }
            });
        }

        //display power button, it will by default be to the right
        powerButton = new PowerButton();
        topRow.add(powerButton);

        //display damage tokens
        HorizontalGroup damageRow = new HorizontalGroup();
        damageRow.space(-2 * DEFAULT_SPACING); //space between tokens
        controlPanelTable.add(damageRow).align(Align.left).padBottom(DEFAULT_SPACING);
        controlPanelTable.row();

        for (int i = 0; i < IPlayer.MAX_HEALTH; i++) {
            int id = i;
            damageRow.addActor(new ControlPanelElement(DAMAGE_TOKEN_TEXTURE) {
                @Override
                public boolean isDisabled() {
                    return GameGraphics.getRoboRally().getPlayerHandler().mainPlayer().getHealth() <= id;
                }

                @Override
                public void act(float delta) {
                    if (isDisabled()) {
                        getStyle().imageUp = new TextureRegionDrawable(UIHandler.NOT_DAMAGE_TOKEN_TEXTURE);
                        updateImage();
                    } else {
                        getStyle().imageUp = new TextureRegionDrawable(UIHandler.DAMAGE_TOKEN_TEXTURE);
                        updateImage();
                    }
                }
            });
        }

        //display cards
        HorizontalGroup cardsRow = new HorizontalGroup();
        cardsRow.space(DEFAULT_SPACING); //space between cards
        controlPanelTable.add(cardsRow);
        CardContainer container = ((Player) GameGraphics.getRoboRally().getPlayerHandler().mainPlayer()).getCards();

        for (int i = 0; i < IPlayer.MAX_PLAYER_CARDS; i++) {
            CardSlot cardSlot = new CardSlot(i, SlotType.HAND, container, dad);
            container.handCard[i] = cardSlot;
            cardsRow.addActor(cardSlot);
        }

        for (int i = 0; i < IPlayer.MAX_DRAW_CARDS; i++) {
            CardSlot cardSlot = new CardSlot(i, SlotType.DRAWN, container, dad);
            container.drawnCard[i] = cardSlot;
            cardDrawTable.add(cardSlot).space(DEFAULT_SPACING);
        }
    }


    /**
     * Show the drawn cards table of the main player.
     * Do not use this at the start of a new round, use {@link Player#beginDrawCards()}
     *
     * @throws IllegalStateException If no drawn card slots have a card in them
     */
    public void showDrawnCards() {
        Stream<CardSlot> drawnCard = Arrays.stream(((Player) GameGraphics.getRoboRally().getPlayerHandler().mainPlayer()).getCards().drawnCard);
        if (drawnCard.allMatch(Objects::isNull)) {
            throw new IllegalStateException("At least one card must be present on the drawn cards to show them");
        }
        cardDrawTable.setVisible(true);
    }

    /**
     * Hide the drawn cards table of the main player. After this the player is ready for the round
     * Do not use this to end the player draw turn, use {@link Player#endDrawCards()}
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
    }

    public PowerButton getPowerButton() {
        return powerButton;
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public Stage getStage() {
        return stage;
    }

    public DragAndDrop getDad() {
        return dad;
    }
}

