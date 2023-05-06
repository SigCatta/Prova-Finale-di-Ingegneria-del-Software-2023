package it.polimi.ingsw.network.client.InputStates;

import it.polimi.ingsw.Controller.Client.Messages.CanIPlayMessage;
import it.polimi.ingsw.Controller.Client.Messages.NewGameMessage;
import it.polimi.ingsw.Controller.Client.VirtualModel.EchosRepresentation;
import it.polimi.ingsw.Controller.Client.VirtualModel.GameRepresentation;
import it.polimi.ingsw.View.VirtualView.Messages.EchoToClient;
import it.polimi.ingsw.View.VirtualView.Messages.GameMessageToClient;
import it.polimi.ingsw.network.client.InputReader;

public class StartOrJoinState extends InputState {
    public StartOrJoinState(InputReader reader) {
        super(reader);
    }

    @Override
    public void play() {
        System.out.println("Type 'join' if you want to join a game, 'new' if you want to create a new one: ");
        while (input == null) {
            getInput();
            if (input.equals("join")) {
                joinGame();
                if (input.equals(".")) {
                    input = null;
                    return;
                }
            } else if (input.equals("new")) {
                createNewGame();
                if (input.equals(".")) {
                    input = null;
                    return;
                }
            } else {
                System.out.println("ERROR: Invalid command!\nType 'join' if you want to join a game, 'new' if you want to create a new one: ");
                input = null;
            }
        }
        reader.setState(new WaitingForPlayersState(reader));
    }

    private void joinGame() {
        while (true) {
            while (true) {
                System.out.println("Insert gameID: ");
                getInput();
                if (input.equals(".")) return;
                try {
                    socketClient.sendCommand(new CanIPlayMessage(Integer.parseInt(input)));
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("ERROR: gameID must be a number!");
                }
            }
            while (true) {
                synchronized (EchosRepresentation.getInstance()){
                    waitForVM(EchosRepresentation.getInstance());
                }
                EchoToClient message = EchosRepresentation.getInstance().getMessage();
                if (message.isError()) {
                    System.out.println(message.getOutput());
                    break;
                }
                if (GameRepresentation.getInstance().getGameMessage() != null) {
                    System.out.println(message.getOutput() + GameRepresentation.getInstance().getGameMessage().getGameID());
                    return;
                }
            }
        }
    }

    private void createNewGame() {
        System.out.println("Insert number of players (between 2 and 4): ");
        int numOfPlayers = 0;
        do {
            getInput();
            if (input.equals(".")) return;
            try {
                numOfPlayers = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("ERROR: number of players must be a number!\n Insert number of players: ");
                continue;
            }
            if (numOfPlayers >= 5 || numOfPlayers <= 1) System.out.println("ERROR: the number of players must be between 2 and 4!\nInsert number of players: ");
        } while (numOfPlayers >= 5 || numOfPlayers <= 1);
        socketClient.sendCommand(new NewGameMessage(numOfPlayers));

        GameMessageToClient gameMessage = null;
        while (gameMessage == null) {
            synchronized (GameRepresentation.getInstance()){
                waitForVM(GameRepresentation.getInstance());
            }
            gameMessage = GameRepresentation.getInstance().getGameMessage();
        }
        System.out.println("The game was successfully create! The gameID is: " + gameMessage.getGameID());
    }

}
