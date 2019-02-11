package no.uib.inf112.core.player;

import no.uib.inf112.core.RoboRally;
import no.uib.inf112.core.ui.event.ControlPanelEventHandler;
import no.uib.inf112.core.ui.event.ControlPanelEventListener;
import no.uib.inf112.core.ui.event.events.CardClickedEvent;
import no.uib.inf112.core.ui.event.events.PowerDownEvent;
import no.uib.inf112.core.util.Vector2Int;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Elg
 */
public class Player {

    private static final int MAX_LIVES = 3;
    private static final int MAX_HEALTH = 10;

    private Robot robot;
    private Deck deck;

    private Vector2Int backup;

    private int lives;
    private boolean poweredDown;
    private int health;

    private boolean alive;

    private Card[] cards;

    public Player(int x, int y, Direction direction) {

        robot = new Robot(x, y, direction);
        deck = new Deck();
        //initially have 5 cards
        cards = deck.draw(5);

        backup = new Vector2Int(x, y);

        lives = MAX_LIVES;
        health = MAX_HEALTH;
        poweredDown = false;
        alive = true;

        ControlPanelEventHandler eventHandler = RoboRally.getControlPanelEventHandler();

        eventHandler.registerListener(PowerDownEvent.class, (ControlPanelEventListener<PowerDownEvent>) event -> {
            poweredDown = !poweredDown;
            System.out.println("Powered down? " + poweredDown);
        });

        eventHandler.registerListener(CardClickedEvent.class, (ControlPanelEventListener<CardClickedEvent>) event -> {
            System.out.println("Clicked card " + event.getId());
            System.out.println("movement = " + cards[event.getId()].getAction());
            robot.move(cards[event.getId()].getAction());
        });
    }

    public void doTurn() {
        for (Card card : cards) {
            robot.move(card.getAction());
        }

    }

    public List<Card> getCards() {
        return Collections.unmodifiableList(Arrays.asList(cards));
    }

    /**
     * damage the player by the given amount and handles death if health is less than or equal to 0
     *
     * @param damageAmount
     *     How much to damage the player
     *
     * @throws IllegalArgumentException
     *     If the damage amount is not positive
     */
    public void damage(int damageAmount) {
        if (damageAmount <= 0) {
            throw new IllegalArgumentException("Cannot do non-positive damage");
        }
        health -= damageAmount;
        if (health <= 0) {
            kill();
        }
    }

    public void kill() {
        lives--;
        if (lives == 0) {
            alive = false;
            RoboRally.getCurrentMap().removeEntity(robot);
            return;
        }
        robot.teleport(backup.x, backup.y);
    }

    /**
     * Heal the player by the given amount up to {@link #MAX_HEALTH}
     *
     * @param healAmount
     *     How much to heal
     *
     * @throws IllegalArgumentException
     *     If the heal amount is not positive
     */
    public void heal(int healAmount) {
        if (healAmount <= 0) {
            throw new IllegalArgumentException("Cannot do non-positive damage");
        }
        health = Math.max(MAX_HEALTH, health + healAmount);
    }

    public int getLives() {
        return lives;
    }

    public int getHealth() {
        return health;
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean isPoweredDown() {
        return poweredDown;
    }

    public Vector2Int getBackup() {
        return backup;
    }

    public void setBackup(int x, int y) {
        backup.x = x;
        backup.y = y;
    }
}
