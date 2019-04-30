package no.uib.inf112.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import no.uib.inf112.core.GameGraphics;

public abstract class AbstractMenuScreen implements Screen {

    protected final GameGraphics game;
    protected int width;
    protected int height;
    protected Stage stage;
    protected OrthographicCamera camera;
    private final BitmapFont listFont;

    public AbstractMenuScreen(GameGraphics game) {
        this.game = game;
        camera = new OrthographicCamera();
        stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera));
        Gdx.input.setInputProcessor(stage);
        listFont = game.generateFont("screen_font.ttf", 30);
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0.5f, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(v);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        this.width = width;
        this.height = height;
        stage.getViewport().update(width, height, true);
        camera.update();
    }

    @Override
    public void pause() {
        //No standard implementation
    }

    @Override
    public void resume() {
        //No standard implementation
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        stage.clear();
        stage.dispose();
    }

    protected TextButton createButton(String name, int fontSize) {
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        BitmapFont font = GameGraphics.generateFont(GameGraphics.SCREEN_FONT, fontSize);
        BitmapFont boldFont = GameGraphics.generateFont(GameGraphics.SCREEN_FONT_BOLD, fontSize);
        style.font = font;
        style.fontColor = Color.BLACK;
        TextButton button = new TextButton(name, style);
        button.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if (!button.isDisabled()) {
                    style.font = boldFont;
                    button.setStyle(style);
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                style.font = font;
                button.setStyle(style);
            }
        });
        button.setHeight(font.getCapHeight());
        button.padBottom(5);

        return button;
    }

    protected void setPositionCentered(TextButton button, int relativeXPosition, int relativeYPosition) {
        button.setPosition(stage.getWidth() / 2 - (relativeXPosition * button.getWidth() / 2), stage.getHeight() / 2 - (relativeYPosition * button.getHeight() / 2));
    }

    /**
     * This method is to create a return button for submenu screens (like setup screen and options screen)
     *
     * @return A TextButton that returns to title screen. Will be centered horizontally and at the bottom vertically
     */
    protected TextButton createReturnButton(int fontSize) {
        TextButton returnButton = createButton("RETURN", fontSize);
        returnButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new TitleScreen(game));
            }
        });
        return returnButton;
    }

    TextField createInputField(String startText, int maxLength) {
        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font = GameGraphics.generateFont(GameGraphics.SCREEN_FONT, 30);
        textFieldStyle.fontColor = Color.BLACK;

        Pixmap myPixMap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        myPixMap.setColor(1, 1, 1, 0.5f);
        myPixMap.fillRectangle(0, 0, 1, 1);

        textFieldStyle.background = new TextureRegionDrawable(new Texture(myPixMap));
        textFieldStyle.cursor = new TextureRegionDrawable(new Texture(myPixMap));

        TextField textField = new TextField(startText, textFieldStyle);
        textField.setWidth(stage.getWidth() / 5);
        textField.setAlignment(Align.center);
        textField.setMaxLength(maxLength);
        myPixMap.dispose();

        return textField;
    }

    TextField createDigitInputField(String startText, int maxLength) {
        TextField digitField = createInputField(startText, maxLength);

        TextField.TextFieldFilter digitFilter = (textField, c) -> {
            if (Character.isDigit(c)) {
                return true;
            } else {
                return false;
            }
        };

        digitField.setTextFieldFilter(digitFilter);
        return digitField;
    }

    protected List<String> createList(String[] list) {
        int listWidth = (int) (stage.getWidth() / 2);
        int listHeigth = (int) stage.getHeight() / 8;

        List.ListStyle style = new List.ListStyle(listFont, Color.WHITE, Color.WHITE, new TextureRegionDrawable(new Texture(listWidth, listHeigth, Pixmap.Format.Intensity)));
        List<String> uiList = new List<>(style);
        uiList.setItems(list);
        uiList.setWidth(listWidth);
        uiList.setHeight(listHeigth);
        uiList.setPosition((3 * stage.getWidth() / 4) / 2, stage.getHeight() - 300);

        return uiList;
    }

    boolean checkValidPort(String portNumber) {
        int portNb;
        try {
            portNb = Integer.parseInt(portNumber);
        } catch (NumberFormatException e) {
            portNb = -1;
        }
        if (GameGraphics.MIN_PORT <= portNb && GameGraphics.MAX_PORT >= portNb) {
            return true;
        } else {
            return false;
        }
    }
}
