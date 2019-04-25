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
        stage.addActor(createSoundButton());
    }

    private TextButton createMusicButton() {
        TextButton musicButton;
        if (GameGraphics.backgroundMusic.isPlaying()) {
            musicButton = createButton("Disable Music", -3);
        } else {
            musicButton = createButton("Enable Music", -3);
        }
        musicButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (musicButton.getText().toString().equals("Disable Music")) {
                    musicButton.setText("Enable Music");
                    GameGraphics.backgroundMusic.pause();
                } else {
                    musicButton.setText("Disable Music");
                    GameGraphics.backgroundMusic.play();
                }
            }
        });
        return musicButton;
    }

    private TextButton createSoundButton() {
        TextButton soundButton;
        if (GameGraphics.soundMuted) {
            soundButton = createButton("Enable Sound", -1);
        } else {
            soundButton = createButton("Disable Sound", -1);
        }
        soundButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (soundButton.getText().toString().equals("Disable Sound")) {
                    soundButton.setText("Enable Sound");
                    GameGraphics.soundMuted = true;
                } else {
                    soundButton.setText("Disable Music");
                    GameGraphics.soundMuted = false;
                }
            }
        });
        return soundButton;
    }

}
