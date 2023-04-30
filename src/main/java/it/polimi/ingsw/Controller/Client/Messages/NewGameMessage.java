package it.polimi.ingsw.Controller.Client.Messages;

import it.polimi.ingsw.Controller.Server.ServerController.ServerController;

import java.io.Serializable;

public class NewGameMessage extends MessageToServer implements Serializable {
    private final int NUMBER_OF_PLAYERS;

    private String newNickname;

    private int newGameId;

    public NewGameMessage(String newNickname, int numberOfPlayers){
        this.NUMBER_OF_PLAYERS = numberOfPlayers;
        this.newNickname = newNickname;
    }

    @Override
    public void update() {
        ServerController.getInstance().newGame(this);
    }

    public int getNumberOfPlayers() {
        return NUMBER_OF_PLAYERS;
    }

    public String getNewNickname() {
        return newNickname;
    }

    public int getNewGameId() {
        return newGameId;
    }

}
