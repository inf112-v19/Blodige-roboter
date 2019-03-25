package no.uib.inf112.core.player;

import com.badlogic.gdx.Gdx;
import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.map.MapHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

import static no.uib.inf112.core.GameGraphics.HEADLESS;

public class PlayerHandler implements IPlayerHandler {

    private int playerCount;
    private ArrayList<IPlayer> players;
    private IPlayer user;
    private MapHandler map;

    /**
     * @param playerCount
     * @throws IllegalArgumentException if playercount is invalid
     */
    public PlayerHandler(int playerCount, MapHandler map) {
        if (playerCount < 2) {
            if (!HEADLESS) {
                throw new IllegalArgumentException("Not enough players");
            }
        } else if (playerCount > 8) {
            throw new IllegalArgumentException("Too many players");
        }
        this.map = map;
        this.playerCount = playerCount;
        players = new ArrayList<>(playerCount);
        user = new UserPlayer(0, 0, Direction.NORTH, map);
        players.add(user);
        for (int i = 1; i < playerCount; i++) {
            players.add(new NonPlayer(i, 0, Direction.NORTH, map));

        }

        Stack<Integer> docks = new Stack<>();
        for (int i = 1; i <= playerCount; i++) {
            docks.push(i);
        }
        Collections.shuffle(docks);

        for (IPlayer player : players) {
            player.setDock(docks.pop());
        }

    }

    @Override
    public void generatePlayers() {
        //Keeping this in case we want to generate new players, currently only used for testing
        if (HEADLESS) {
            players = new ArrayList<>(playerCount);
            for (int i = 0; i < playerCount; i++) {
                players.add(new NonPlayer(5 + i, 2, Direction.NORTH, map));
            }

            Stack<Integer> docks = new Stack<>();
            for (int i = 1; i <= playerCount; i++) {
                docks.push(i);
            }
            Collections.shuffle(docks);

            for (IPlayer player : players) {
                player.setDock(docks.pop());
            }
        }

    }

    @Override
    public void generateOnePlayer() {
        players = new ArrayList<>(playerCount);
        players.add(new NonPlayer(0, 0, Direction.NORTH, map));
        players.get(0).setDock(1);
    }

    @Override
    public void endTurn() {
        for (int i = 0; i < Player.MAX_PLAYER_CARDS; i++) {
            List<PlayerCard> cards = new ArrayList<>();
            for (IPlayer p : players) {
                cards.add(p.getNextCard(i));
            }
            Collections.sort(cards);
            for (int j = 0; j < cards.size(); j++) {
                PlayerCard card = cards.get(j);

                GameGraphics.executorService.schedule(() ->
                        Gdx.app.postRunnable(() ->
                                card.getPlayer().moveRobot(card.getCard().getAction())), 500 * (i + 1), TimeUnit.MILLISECONDS);
            }
        }

        GameGraphics.executorService.schedule(() ->
                Gdx.app.postRunnable(() -> GameGraphics.getRoboRally().round()), 500 * (Player.MAX_PLAYER_CARDS + 2), TimeUnit.MILLISECONDS);
    }

    @Override
    public void startTurn() {
        //TODO Issue #44 check if dead
        //TODO Issue #44 check if player is out side of map

        GameGraphics.getRoboRally().getDeck().shuffle();
        UserPlayer p = mainPlayer();
        if (p.isPoweredDown()) {
            p.fullHeal();

        } else {
            p.beginDrawCards();
        }
    }

    @Override
    public ArrayList<IPlayer> getPlayers() {
        return players;
    }

    @Override
    public int getPlayerCount() {
        return playerCount;
    }

    public NonPlayer testPlayer() {
        if (!HEADLESS) {
            throw new IllegalStateException("Game is not headless");
        }
        return (NonPlayer) players.get(0);
    }


    public UserPlayer mainPlayer() {
        if (HEADLESS) {
            throw new IllegalStateException("Game is headless");
        }
        return (UserPlayer) players.get(0);
    }

    @Override
    public String toString() {
        return "PlayerHandler{" +
                "playerCount= " + playerCount +
                ", players= " + players +
                ", user= " + user +
                "}";
    }
}
