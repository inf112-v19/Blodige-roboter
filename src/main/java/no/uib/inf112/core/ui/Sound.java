package no.uib.inf112.core.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;

import java.io.File;

public enum Sound {

    ROBOT_MOVING("ROBOT_MOVING.wav"),
    FLAG("FLAG.wav"),
    ROBOT_SHUTDOWN("ROBOT_SHUTDOWN.wav"),
    CONVEYOR("CONVEYOR.wav"),
    SHOOT_LASER("SHOOT_LASER.wav"),
    ROBOT_FALLING("ROBOT_FALLING.wav"),
    WINNER("WINNER.wav"),
    PUSHER("PUSHER.wav"),
    GET_OPTION_CARD("GET_OPTION_CARD.wav"),
    ROBOT_UPDATES_BACKUP("ROBOT_UPDATES_BACKUP.wav");

    private static final String SOUND_FOLDER = "sound";
    public static final Music BACKGROUND_MUSIC = Gdx.audio.newMusic(Gdx.files.internal("sound/backgroundMusic.wav"));
    private final com.badlogic.gdx.audio.Sound sound;

    Sound(String filename) {
        sound = Gdx.audio.newSound(getFile(filename));
    }

    public void play() {
        sound.play();
    }

    private FileHandle getFile(String filename) {
        return Gdx.files.internal(SOUND_FOLDER + File.separatorChar + filename);
    }


}
