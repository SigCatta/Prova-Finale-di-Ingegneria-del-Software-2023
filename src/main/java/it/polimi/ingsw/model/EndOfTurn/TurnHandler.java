package it.polimi.ingsw.model.EndOfTurn;

import exceptions.NullItemTileException;
import exceptions.TooManyCardsRequestedException;
import it.polimi.ingsw.model.EndOfTurn.ScoreCalculation.ScoreBoard;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class TurnHandler implements EndOfTurnSubject{


    public Game game;
    private List<EndOfTurnObserver> observers;
    private List<Player> players;


    public TurnHandler(Game game) throws TooManyCardsRequestedException {
        this.game = game;
        this.observers = new ArrayList<>();
        game.setScoreBoard(new ScoreBoard(game));
        this.observers.add(game.getScoreBoard());
        this.players = game.getPlayers();
        //TODO set the observer
    }

    public void changeTurn() throws NullItemTileException {
        notifyObservers();

        int nextIndex = players.indexOf(game.getActivePlayer()) + 1;
        nextIndex = nextIndex >= players.size() ? 0 : nextIndex;
        game.setActivePlayer(players.get(nextIndex));

    }

    @Override
    public void attachEndOfTurn(EndOfTurnObserver observer) {
        observers.add(observer);
    }

    @Override
    public void detachEndOfTurn(EndOfTurnObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for(EndOfTurnObserver observer : observers){
            observer.update();
        }
    }
}
