package it.polimi.ingsw.model.observers;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.player.Player;


public class ShelfObserver {
    private Game game;

    public ShelfObserver(Game game) {
        this.game = game;
        for(Player player: game.getPlayers()) {
            player.getShelf().registerObserver(this);
        }
    }
    public void shelfFull() {
        game.getScoreBoard().setFirstPlayerToFinish(game.getActivePlayer());

        //TODO: call controller to modify the view
    }


}
