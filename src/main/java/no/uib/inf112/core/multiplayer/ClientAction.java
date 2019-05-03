package no.uib.inf112.core.multiplayer;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

/**
 * Action the server can send to the client
 */
public enum ClientAction {

    START_GAME("startGame"),
    GIVE_CARDS("giveCards"),
    NAME("name"),
    CONNECTED_PLAYERS("connectedPlayers"),
    THREAD_NAME("threadName"),
    START_ROUND("startRound"),
    COUNT_DOWN("countDown"),
    PARTY_MODE("partyMode");

    private static HashMap<String, ClientAction> ClientActionMap = new HashMap<>();
    private final String command;

    ClientAction(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    @Override
    public String toString() {
        return command + ":";
    }

    static {
        for (ClientAction value : values()) {
            ClientActionMap.put(value.getCommand(), value);
        }
    }

    /**
     * @param command The command of the wanted ClientAction
     * @return The ClientAction with the given command. Will be {@code null} if the given id is not known
     */
    @Nullable
    public static ClientAction fromCommandString(String command) {
        return ClientActionMap.get(command);
    }


}
