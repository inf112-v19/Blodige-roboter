package no.uib.inf112.core.screens;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import no.uib.inf112.core.GameGraphics;

public class SinglePlayerSetup extends AbstractSetupScreen {

    private GameGraphics game;

    public SinglePlayerSetup(GameGraphics game) {
        super(game);
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        Label playerLabel = createLabel("Number of players", 7 * stage.getWidth() / 10, stage.getHeight() / 2 + 75, 30);
        Label numberLabel = createLabel(GameGraphics.players + "", 4 * stage.getWidth() / 5, (stage.getHeight() / 2) - 75, 50);
        Slider slider = createSlider();
        slider.setValue((float) GameGraphics.players);
        slider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                numberLabel.setText((int) slider.getValue());
                GameGraphics.players = (int) slider.getValue();
            }
        });
        stage.addActor(slider);
        stage.addActor(numberLabel);
        stage.addActor(playerLabel);
    }


    private Slider createSlider() {
        Slider.SliderStyle sliderStyle = new Slider.SliderStyle();
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(0f, 0f, 0f, 1f);
        pixmap.fillRectangle(0, 0, 10, 10);
        sliderStyle.background = new TextureRegionDrawable(new Texture(pixmap));
        sliderStyle.knobDown = new TextureRegionDrawable(new Texture(pixmap));
        sliderStyle.knobOver = new TextureRegionDrawable(new Texture(pixmap));
        sliderStyle.knobOver.setMinWidth(23);
        sliderStyle.knobOver.setMinHeight(23);
        sliderStyle.knobDown.setMinHeight(26);
        sliderStyle.knobDown.setMinWidth(26);
        sliderStyle.knob = new TextureRegionDrawable(new Texture(pixmap));
        sliderStyle.knob.setMinHeight(20);
        sliderStyle.knob.setMinWidth(20);
        sliderStyle.background.setMinHeight(5);

        Slider slider = new Slider(2f, 8f, 1f, false, sliderStyle);
        slider.setWidth(300f);
        slider.setHeight(20f);
        slider.setPosition(7 * stage.getWidth() / 10, stage.getHeight() / 2);

        return slider;
    }

}
