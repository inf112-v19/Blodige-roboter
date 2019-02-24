package no.uib.inf112.core.player;

import com.badlogic.gdx.Gdx;
import no.uib.inf112.core.RoboRally;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class PlayerHandler implements IPlayerHandler {

    private Deck deck;
    private int playerCount;
    private ArrayList<Player> players;
    private Player currentPlayer;
    private PriorityQueue<Player> queue = new PriorityQueue<>();

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

        deck = new ProgramDeck(false);
    }

    @Override
    public void generatePlayers(boolean headless) {
        for (int i = 0; i < playerCount; i++) {
            players.add(new Player(5 + i, 2, Direction.NORTH, headless));
        }

        Stack<Integer> docks = new Stack<>();
        for (int i = 1; i <= playerCount; i++) {
            docks.push(i);
        }
        Collections.shuffle(docks);

        for (Player player : players) {
            player.setDock(docks.pop());

            if (player.getDock() == 1) {
                currentPlayer = player;
            }
        }

    }

    public void endTurn() {
//        for (Player p : players) {
//            p.executeMovements();
//        }

        for (int i = 0; i < Player.MAX_PLAYER_CARDS; i++) {
            List<PlayerCard> cards = new ArrayList<>();
            for (Player p : players) {
                cards.add(p.getNextCard(i));
            }
            Collections.sort(cards);
            for (int j = 0; j < cards.size(); j++) {
                PlayerCard card = cards.get(j);

                RoboRally.executorService.schedule(() -> Gdx.app.postRunnable(() -> {
                    card.getPlayer().getRobot().move(card.getCard().getAction());
                }), 500 * (i + 1), TimeUnit.MILLISECONDS);
            }
        }
        //Activate lasers and so on
        startTurn();
    }

    public void startTurn() {
        //TODO Issue #44 check if dead
        //TODO Issue #44 check if player is out side of map
        for (Player p : players) {
            if (p == currentPlayer) continue;
            queue.add(p);
        }
        deck.shuffle();
        Player p = currentPlayer;
        if (p.isPoweredDown()) {
            //TODO Issue #24 check if is powered down (then heal)
            return;
        } else {
            p.beginDrawCards();
        }
    }

    public void nextPlayer() {
        if (queue.isEmpty()) {
            endTurn();
            return;
        }

        Player p = queue.poll();
        currentPlayer = p;
        if (p.isPoweredDown()) {
            //TODO Issue #24 check if is powered down (then heal)
            return;
        } else {
            RoboRally.getUiHandler().displayCards();
            p.beginDrawCards();


        }
    }


    @Override
    public ArrayList<Player> getPlayers() {
        return players;
    }

    @Override
    public int getPlayerCount() {
        return playerCount;
    }

    @Override
    public Deck getDeck() {
        return deck;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    //TODO Remove this

    /**
     * Temporary mainplayer
     *
     * @return player
     */
    public Player mainPlayer() {
        return players.get(0);
    }


}
