package it.polimi.ingsw;

import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.BoardRefresher.BoardRefresher;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.tiles.Bag;

import java.util.ArrayList;

public class Game {

    private final int BOARD_DIMENSION = 9;
    private final int MAX_TILES_FROM_BOARD = 3;

    private int gameID;
    private Board board;

    private BoardRefresher boardRefresher;

    private ArrayList<Player> players;
    private Player activePlayer;
    private Player firstPlayer;

    private Bag bag;

    public Game(){
        //TODO create instances of the classes used here
    }


    public Board getBoard() {
        return board;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    public void setActivePlayer(Player activePlayer) {
        this.activePlayer = activePlayer;
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public Bag getBag() {
        return bag;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public int getMAX_TILES_FROM_BOARD() {
        return MAX_TILES_FROM_BOARD;
    }
}
