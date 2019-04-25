package no.uib.inf112.core.screens;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import no.uib.inf112.core.GameGraphics;

public class OptionsScreen extends AbstractMenuScreen {

    public OptionsScreen(GameGraphics game) {
        super(game);
    }


    @Override
    public void show() {
        stage.addActor(createReturnButton());
        stage.addActor(createMusicButton());
    }

    private TextButton createMusicButton() {
        TextButton musicButton;
        if (GameGraphics.backgroundMusic.isPlaying()) {
            musicButton = createButton("Music on", -6);
        } else {
            musicButton = createButton("Music off", -6);
        }
        musicButton.setPosition(3 * stage.getWidth() / 4 - (musicButton.getWidth() / 2), musicButton.getY());
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

}
