package no.uib.inf112.core.ui;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import no.uib.inf112.core.RoboRally;


public class UIHandler {

    private Stage stage;
    private RoboRally roboRally;

    public UIHandler(RoboRally roboRally) {
        this.roboRally = roboRally;
        stage = new Stage(new ScreenViewport());
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = new BitmapFont();

        TextButton button = new TextButton("cards", style);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                roboRally.round(); // user has placed his "cards"
            }
        });

        stage.addActor(button);
    }

    public void update() {
        stage.act(Gdx.graphics.getDeltaTime()); //Perform ui logic
        stage.draw(); //Draw the ui
    }

    public void resize() {
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
    }

    public Stage getStage() {
        return stage;
    }
}

