package no.uib.inf112.core.multiplayer;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

/**
 * Actions a client can send to the server
 */
public enum ServerAction {

    getName("getName "),
    setDisplayName("setDisplayName"),
    getConnectedPlayers("getConnectedPlayers"),
    startGame("startGame"),
    sendSelectedCards("sendSelectedCards"),
    setHostId("setHostId"),
    finishedSetup("finishedSetup"),
    partyMode("partyMode");

    public final String command;

    ServerAction(String command) {
        this.command = command;
    }


    @Override
    public String toString() {
        return command + ":";
    }

    private static HashMap<String, ServerAction> ServerActionMap = new HashMap<>();

    static {
        for (ServerAction value : values()) {
            ServerActionMap.put(value.command, value);
        }
    }

    /**
     * @param command The command of the wanted ServerAction
     * @return The ServerAction with the given command. Will be {@code null} if the given id is not known
     */
    @Nullable
    public static ServerAction fromCommandString(String command) {
        return ServerActionMap.get(command);
    }
}
