package no.uib.inf112.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.player.IPlayer;


public class EndScreen extends AbstractMenuScreen {

    private final BitmapFont listFont;
    private String[] rankList;

    public EndScreen(GameGraphics game) {
        super(game);
        listFont = game.generateFont("screen_font.ttf", 30);
        java.util.List<IPlayer> players = GameGraphics.getRoboRally().getPlayerHandler().getPlayers();
        rankList = new String[players.size()];
        for (int i = 0; i < players.size(); i++) {
            rankList[i] = players.get(i).getDock() + ": " + players.get(i).getFlags() + " flags";
        }
    }

    @Override
    public void show() {
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();

        TextButton play_again = createButton("PLAY AGAIN", 6);
        play_again.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new TitleScreen(game));
            }
        });

        TextButton quit = createButton("QUIT", 8);
        quit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.exit(0);
            }
        });

        stage.addActor(createHighScoreList());
        stage.addActor(play_again);
        stage.addActor(quit);
    }

    @Override
    public void render(float v) {
        super.render(v);
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        //TODO Add game over header
        game.batch.end();
    }

    private List<String> createHighScoreList() {
        int listWidth = (int) (stage.getWidth() / 2);
        int listHeigth = (int) stage.getHeight() / 8;

        List.ListStyle style = new List.ListStyle(listFont, Color.WHITE, Color.WHITE, new TextureRegionDrawable(new Texture(listWidth, listHeigth, Pixmap.Format.Intensity)));
        List<String> list = new List<>(style);
        list.setItems(rankList);
        list.setWidth(listWidth);
        list.setHeight(listHeigth);
        list.setPosition((stage.getWidth() - listWidth) / 2, stage.getHeight() - 300);

        return list;
    }
}
