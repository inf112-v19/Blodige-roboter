package no.uib.inf112.core.player;

import no.uib.inf112.core.RoboRally;
import no.uib.inf112.core.ui.event.ControlPanelEventHandler;
import no.uib.inf112.core.ui.event.ControlPanelEventListener;
import no.uib.inf112.core.ui.event.events.CardClickedEvent;
import no.uib.inf112.core.ui.event.events.PowerDownEvent;
import no.uib.inf112.core.util.Vector2Int;
import org.jetbrains.annotations.NotNull;

/**
 * @author Elg
 */
public class Player {

    public static final int MAX_LIVES = 3;
    public static final int MAX_HEALTH = 10;
    public static final int MAX_PLAYER_CARDS = 5;
    public static final int MAX_DRAW_CARDS = MAX_HEALTH-1;

    private Robot robot;

    private Vector2Int backup;

    private int dock;

    private int lives;
    private boolean poweredDown;
    private int health;

    private boolean alive;
    private boolean headless;

    private Card[] cards;

    /**
     * @param x         Start x position
     * @param y         Start y position
     * @param direction Start direction
     * @param headless  true if you want player without graphics, false otherwise
     * @throws IllegalArgumentException See {@link Robot#Robot(int, int, Direction)}
     * @throws IllegalStateException    See {@link Robot#Robot(int, int, Direction)}
     */
    public Player(int x, int y, @NotNull Direction direction, boolean headless) {
        backup = new Vector2Int(x, y);

        lives = MAX_LIVES;
        health = MAX_HEALTH;
        poweredDown = false;
        alive = true;
        this.headless = headless;

        if (!headless) {
            //TODO Issue 47 make player choose his cards
            cards = RoboRally.getPlayerHandler().getDeck().draw(MAX_DRAW_CARDS);
            robot = new Robot(x, y, direction, false);

            ControlPanelEventHandler eventHandler = RoboRally.getCPEventHandler();

            eventHandler.registerListener(PowerDownEvent.class, (ControlPanelEventListener<PowerDownEvent>) event -> {
                poweredDown = !poweredDown;
                System.out.println("Powered down? " + isPoweredDown());
            });

            eventHandler.registerListener(CardClickedEvent.class, (ControlPanelEventListener<CardClickedEvent>) event -> {
                System.out.println("Clicked card nr " + event.getId() + " -> " + cards[event.getId()].getAction());
                robot.move(cards[event.getId()].getAction());
            });
        }
    }

    /**
     * damage the player by the given amount and handles death if health is less than or equal to 0
     *
     * @param damageAmount How much to damage the player
     * @throws IllegalArgumentException If the damage amount is not positive
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

    /**
     * Kill the player, decreasing their lives and might permanently remove from map if there are not lives left
     */
    public void kill() {
        lives--;
        if (lives == 0) {
            alive = false;
            if (!headless) {
                RoboRally.getCurrentMap().removeEntity(robot);
            }
            return;
        }
        health = MAX_HEALTH;
        if (!headless) {
            robot.teleport(backup.x, backup.y);
        }
    }

    /**
     * Heal the player by the given amount up to {@link #MAX_HEALTH}
     *
     * @param healAmount How much to heal
     * @throws IllegalArgumentException If the heal amount is not positive
     */
    public void heal(int healAmount) {
        if (healAmount <= 0) {
            throw new IllegalArgumentException("Cannot do non-positive damage");
        }
        health = Math.min(MAX_HEALTH, health + healAmount);
    }

    /**
     * @return If the player is dead. A player is dead if their lives are 0 or less
     */
    public boolean isDead() {
        return lives <= 0;
    }

    @NotNull
    public Card[] getCards() {
        return cards;
    }

    public void drawCards() {
        cards = RoboRally.getPlayerHandler().getDeck().draw(MAX_DRAW_CARDS - getDamageTokens());
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

    public int getDamageTokens() {
        return MAX_HEALTH - health;
    }

    public Vector2Int getBackup() {
        return backup;
    }

    public void setBackup(int x, int y) {
        backup.x = x;
        backup.y = y;
    }

    public Robot getRobot() {
        return robot;
    }

    public int getDock() {
        return dock;
    }

    public void setDock(int dock) {
        this.dock = dock;
    }
}
