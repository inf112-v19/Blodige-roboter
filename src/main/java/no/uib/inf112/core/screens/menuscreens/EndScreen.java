package no.uib.inf112.core.screens.menuscreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.player.IPlayer;

import java.util.ArrayList;
import java.util.List;


public class EndScreen extends AbstractMenuScreen {

    private String[] rankList;
    private final Drawable GAME_OVER = new TextureRegionDrawable(new Texture("game_over.png"));


    public EndScreen(GameGraphics game) {
        super(game);
        List<IPlayer> playerRanks = GameGraphics.getRoboRally().getPlayerHandler().rankPlayers();

        List<String> ranks = new ArrayList<>();
        for (int rank = 0; rank < playerRanks.size(); rank++) {
            IPlayer player = playerRanks.get(rank);
            if (player == null) { continue;}
            ranks.add((rank + 1) + ". " + player.getName() + ": " + player.getFlags() + " flags");
        }

        rankList = ranks.toArray(new String[0]);
    }

    @Override
    public void show() {
        TextButton playAgain = createButton("PLAY AGAIN", 70);
        setPositionCentered(playAgain, 1, 11);
        playAgain.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameGraphics.resetRoborally();
                game.closeResources();
                game.setScreen(new TitleScreen(game));
            }
        });

        TextButton quit = createButton("QUIT", 70);
        setPositionCentered(quit, 1, 15);
        quit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        stage.addActor(createList(rankList));
        stage.addActor(playAgain);
        stage.addActor(quit);
    }

    @Override
    public void render(float v) {
        super.render(v);
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        GAME_OVER.draw(game.batch, camera.viewportWidth / 5, 3 * camera.viewportHeight / 4f, 3 * camera.viewportWidth / 5, camera.viewportHeight / 6f);
        game.batch.end();
    }


}
