package no.uib.inf112.core.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;

import java.io.File;

public class SoundPlayer {
    final private String SOUND_FOLDER = "sound" + File.separatorChar;
    final private String WAV = ".WAV";

    private Sound robotMoving;
    private Sound flag;
    private Sound robotShutdown;
    private Sound conveyor;
    private Sound shootLaser;
    private Sound robotFalling;
    private Sound winner;
    private Sound pusher;
    private Sound getOptionCard;
    private Sound robotUpdatesBackup;


    public SoundPlayer() {
        robotMoving = Gdx.audio.newSound(getFile("robotMoving"));
        flag = Gdx.audio.newSound(getFile("flag"));
        robotShutdown = Gdx.audio.newSound(getFile("robotShutdown"));
        conveyor = Gdx.audio.newSound(getFile("conveyor"));
        shootLaser = Gdx.audio.newSound(getFile("shootLaser"));
        robotFalling = Gdx.audio.newSound(getFile("robotFalling"));
        winner = Gdx.audio.newSound(getFile("winner"));
        pusher = Gdx.audio.newSound(getFile("pusher"));
        getOptionCard = Gdx.audio.newSound(getFile("getOptionCard"));
        robotUpdatesBackup = Gdx.audio.newSound(getFile("robotUpdatesBackup"));
    }

    public void playRobotMoving() {
        robotMoving.play();
    }

    public void playFlag() {
        flag.play();
    }

    public void playRobotShutdown() {
        robotShutdown.play();
    }

    public void playConveyor() {
        conveyor.play();
    }

    public void playShootLaser() {
        shootLaser.play();
    }

    public void playRobotFalling() {
        robotFalling.play();
    }

    public void playWinner() {
        winner.play();
    }

    public void playPusher() {
        pusher.play();
    }

    public void playGetOptionCard() {
        getOptionCard.play();
    }

    public void playRobotUpdatesBackup() {
        robotUpdatesBackup.play();
    }

    private FileHandle getFile(String filename) {
        return Gdx.files.internal(SOUND_FOLDER + filename + WAV);
    }
}

/*
TODO wiki
- Hvordan legge inn ny sound
- samme feltnavn som filnavn
- alle lydfiler .wav
- Filers kilde og lisens
 */
