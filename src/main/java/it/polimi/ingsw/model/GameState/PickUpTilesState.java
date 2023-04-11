package it.polimi.ingsw.model.GameState;

import it.polimi.ingsw.JSONReader.GameStateDataReader;

import java.util.Set;

public class PickUpTilesState implements GameState{
    private static Set<String> possibleCommands;

    public PickUpTilesState(){
        if(possibleCommands == null){
            possibleCommands = GameStateDataReader.getSetFromJSON("PickUpTilesCommands.json");
        }
    }

    @Override
    public boolean isCommandPossible(String command) {
        return possibleCommands.contains(command);
    }
}
