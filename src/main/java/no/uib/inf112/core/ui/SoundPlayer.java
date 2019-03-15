package no.uib.inf112.core.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;

import java.io.File;

public class SoundPlayer {
    final private String SOUND_FOLDER = "sound/";
    final private String WAV = ".WAV";

    private Sound robotMoving;

    public SoundPlayer() {
        robotMoving = Gdx.audio.newSound(new FileHandle(new File(SOUND_FOLDER + "robotMoving" + WAV)));
    }

    public void playRobotMoving() {
        robotMoving.play();
    }
}

/*
TODO wiki
- Hvordan legge inn ny sound
- samme feltnavn som filnavn
- alle lydfiler .wav
 */