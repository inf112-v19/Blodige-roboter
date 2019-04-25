package no.uib.inf112.core.screens;

import com.badlogic.gdx.Gdx;
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
        TextButton fullscreenButton = createButton("Fullscreen", 1);
        fullscreenButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (Gdx.graphics.isFullscreen()) {
                    Gdx.graphics.setWindowedMode(1280, 720);
                } else {
                    Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
                }
            }
        });

        TextButton returnButton = createReturnButton();
        returnButton.setPosition(stage.getWidth() / 2 - (returnButton.getWidth() / 2), 20);
        stage.addActor(returnButton);
        stage.addActor(createMusicButton());
        stage.addActor(fullscreenButton);
        stage.addActor(createSoundButton());
    }

    @Override
    public void render(float v) {
        super.render(v);

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
