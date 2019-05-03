package no.uib.inf112.core.multiplayer;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

/**
 * Actions a client can send to the server
 */
public enum ServerAction {

    GET_NAME("getName"),
    SET_DISPLAY_NAME("setDisplayName"),
    GET_CONNECTED_PLAYERS("getConnectedPlayers"),
    START_GAME("START_GAME"),
    SEND_SELECTED_CARDS("sendSelectedCards"),
    SET_HOST_ID("setHostId"),
    FINISHED_SETUP("finishedSetup"),
    PARTY_MODE("partyMode");

    public final String command;
    private static HashMap<String, ServerAction> ServerActionMap = new HashMap<>();

    ServerAction(String command) {
        this.command = command;
    }


    @Override
    public String toString() {
        return command + ":";
    }


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
