package no.uib.inf112.core.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import no.uib.inf112.core.GameGraphics;

import java.io.File;

public enum Sound {

    ROBOT_MOVING("robotMoving.wav"),
    FLAG("flag.wav"),
    ROBOT_SHUTDOWN("robotShutdown.wav"),
    CONVEYOR("conveyor.wav"),
    SHOOT_LASER("shootLaser.wav"),
    ROBOT_FALLING("robotFalling.wav"),
    WINNER("winner.wav"),
    PUSHER("pusher.wav"),
    GET_OPTION_CARD("getOptionCard.wav"),
    ROBOT_UPDATES_BACKUP("robotUpdatesBackup.wav");

    private static final String SOUND_FOLDER = "sound";
    private static final Music BACKGROUND_MUSIC = Gdx.audio.newMusic(Gdx.files.internal("sound/backgroundMusic.wav"));
    private final com.badlogic.gdx.audio.Sound sound;

    Sound(String filename) {
        sound = Gdx.audio.newSound(getFile(filename));
    }

    public void play() {
        if (GameGraphics.soundMuted) { // Sound was muted in options screen
            return;
        }
        sound.play();
    }

    private FileHandle getFile(String filename) {
        return Gdx.files.internal(SOUND_FOLDER + File.separatorChar + filename);
    }

    public static Music getBackgroundMusic() {
        return BACKGROUND_MUSIC;
    }

}
