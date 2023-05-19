package it.polimi.ingsw.View.GUI.SceneController.VirtualModelObservers;

import it.polimi.ingsw.View.GUI.SceneController.StageController;
import it.polimi.ingsw.VirtualModel.GameRepresentation;
import it.polimi.ingsw.VirtualModel.VirtualModelObserver;

public class GameObserver implements VirtualModelObserver {

    public GameObserver() {
        GameRepresentation.getInstance().registerObserver(this);
    }

    @Override
    public void update() {
        StageController.getController().updateGame();
    }
}
