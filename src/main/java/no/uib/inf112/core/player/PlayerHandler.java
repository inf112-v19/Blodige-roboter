package no.uib.inf112.core.player;


import java.util.ArrayList;

public class PlayerHandler implements IPlayerHandler {

    private final int players;

    private ArrayList<Robot> robots;

    public PlayerHandler(int players) {
        if(players < 2) {
            throw new IllegalArgumentException("Not enough players");
        } else if(players > 8) {
            throw new IllegalArgumentException("Too many players");
        }
        this.players = players;
        robots = new ArrayList<>(players);


        for (int i = 0; i < players; i++) {
            robots.add(new Robot(5+i, 2, Direction.NORTH));
        }
    }

    @Override
    public int getPlayers() {
        return players;
    }
}
