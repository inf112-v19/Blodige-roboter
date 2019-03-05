package no.uib.inf112.core.player;

import com.badlogic.gdx.Gdx;
import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.map.OutSideBoardException;
import no.uib.inf112.desktop.Main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

public class PlayerHandler implements IPlayerHandler {

    private int playerCount;
    private ArrayList<Player> players;
    private Player currentPlayer;
//    private PriorityQueue<Player> queue = new PriorityQueue<>();

    /**
     * @param playerCount
     * @throws IllegalArgumentException if playercount is invalid
     */
    public PlayerHandler(int playerCount) {
        if (playerCount < 2) {
            throw new IllegalArgumentException("Not enough players");
        } else if (playerCount > 8) {
            throw new IllegalArgumentException("Too many players");
        }
        this.playerCount = playerCount;
        players = new ArrayList<>(playerCount);

        for (int i = 0; i < playerCount; i++) {
            players.add(new Player(5 + i, 2, Direction.NORTH));
        }

        Stack<Integer> docks = new Stack<>();
        for (int i = 1; i <= playerCount; i++) {
            docks.push(i);
        }
        Collections.shuffle(docks);

        for (Player player : players) {
            player.setDock(docks.pop());
        }

    }

    @Override
    public void generatePlayers() {
        //Keeping this in case we want to generate new players, currently only used for testing
        if (Main.HEADLESS) {
            players = new ArrayList<>(playerCount);

            for (int i = 0; i < playerCount; i++) {
                players.add(new Player(5 + i, 2, Direction.NORTH));
            }

            Stack<Integer> docks = new Stack<>();
            for (int i = 1; i <= playerCount; i++) {
                docks.push(i);
            }
            Collections.shuffle(docks);

            for (Player player : players) {
                player.setDock(docks.pop());
            }
        }

    }

    @Override
    public void endTurn() {
        for (int i = 0; i < Player.MAX_PLAYER_CARDS; i++) {
            List<PlayerCard> cards = new ArrayList<>();
            for (Player p : players) {
                cards.add(p.getNextCard(i));
            }
            Collections.sort(cards);
            for (int j = 0; j < cards.size(); j++) {
                PlayerCard card = cards.get(j);

                GameGraphics.executorService.schedule(() -> Gdx.app.postRunnable(() -> {
                    try {
                        card.getPlayer().getRobot().move(card.getCard().getAction());
                    } catch (OutSideBoardException e) {
                        System.out.println("Outside board");
                    }
                }), 500 * (i + 1), TimeUnit.MILLISECONDS);
            }
        }
        //Activate lasers and so on
        startTurn();
    }

    @Override
    public void startTurn() {
        //TODO Issue #44 check if dead
        //TODO Issue #44 check if player is out side of map

//        GameGraphics.getRoboRally().getDeck().shuffle();
        Player p = mainPlayer();
        if (p.isPoweredDown()) {
            //TODO Issue #24 check if is powered down (then heal)
            return;
        } else {
            p.beginDrawCards();
        }
    }

    @Override
    public void nextPlayer() {
//        if (queue.isEmpty()) {
        endTurn();
//            return;
//        }

//        for (int i = 1; i < players.size(); i++) {

//        }
//        Player p = queue.poll();
//        currentPlayer = p;
//        if (p.isPoweredDown()) {
//            TODO Issue #24 check if is powered down (then heal)
//            return;
//        } else {
//            GameGraphics.getUiHandler().displayCards();
//            p.beginDrawCards();
//        }
    }


    @Override
    public ArrayList<Player> getPlayers() {
        return players;
    }

    @Override
    public int getPlayerCount() {
        return playerCount;
    }

//    public Player getCurrentPlayer() {
//        return currentPlayer;
//    }


    /**
     * Temporary mainplayer
     *
     * @return player
     */
    public Player mainPlayer() {
        return players.get(0);
    }
}
