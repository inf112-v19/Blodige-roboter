package no.uib.inf112.core.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.File;

public enum Sound {

    robotMoving("robotMoving.wav"),
    flag("flag.wav"),
    robotShutdown("robotShutdown.wav"),
    conveyor("conveyor.wav"),
    shootLaser("shootLaser.wav"),
    robotFalling("robotFalling.wav"),
    winner("winner.wav"),
    pusher("pusher.wav"),
    getOptionCard("getOptionCard.wav"),
    robotUpdatesBackup("robotUpdatesBackup.wav");

    private static final String SOUND_FOLDER = "sound";
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
