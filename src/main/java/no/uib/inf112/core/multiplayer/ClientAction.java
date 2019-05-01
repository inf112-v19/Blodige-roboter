package no.uib.inf112.core.multiplayer;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

/**
 * Action the server can send to the client
 */
public enum ClientAction {
    startGame("startGame "),
    giveCards("giveCards"),
    name("name"),
    connectedPlayers("connectedPlayers"),
    threadName("threadName"),
    startRound("startRound"),
    countDown("countDown"),
    partyMode("partyMode");

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

    private static HashMap<String, ClientAction> ClientActionMap = new HashMap<>();

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
