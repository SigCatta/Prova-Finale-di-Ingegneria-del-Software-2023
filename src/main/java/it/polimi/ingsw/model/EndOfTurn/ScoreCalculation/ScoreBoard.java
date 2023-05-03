package it.polimi.ingsw.model.EndOfTurn.ScoreCalculation;

import it.polimi.ingsw.model.EndOfTurn.EndOfTurnObserver;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.cards.commonGoals.CommonGoalCard;
import it.polimi.ingsw.model.cards.commonGoals.CommonGoalContainer;
import it.polimi.ingsw.model.player.Player;

import java.util.List;

/**
 * The purpose of this class is to calculate the scores and assign points to the players whenever they complete
 * either a personal or common goal, fill their shelf before the others or place tiles of the same colors
 * adjacently in the shelf, it is called at the end of each turn to check if a player can earn any points
 */
public class ScoreBoard implements EndOfTurnObserver {

    private Game game;
    private boolean isFirstPointAssigned;
    private CommonGoalContainer commonGoalContainer;

    /**
     * creates a new scoreboard and assigns it a game
     */
    public ScoreBoard(Game game){
        this.game = game;
        this.commonGoalContainer = game.getCommonGoalContainer();
        this.isFirstPointAssigned = false;
    }

    /**
     * Checks if the player has completed any new common goals and give him points accordingly
     */
    private void scoreCommonGoalCards(Player player) { // at the end of each turn
        for (CommonGoalCard cg : commonGoalContainer) {
            if (commonGoalContainer.isAchieved(cg, player.getNickname())) return;

            commonGoalContainer.signCompleted(cg, player.getNickname());
            int points = cg.calculateScore(player);
            player.updateScore(points);
        }
    }


    /**
     * Called each time a player's turn finishes, it checks if the current player has completed any of the common goals and
     * assigns points accordingly. It also calculates points for personal goals and tile adjacency if the game is finished
     */
    @Override
    public void update() {

        List<Player> players = game.getPlayers();
        Player activePlayer = game.getActivePlayer();

        scoreCommonGoalCards(activePlayer);

        if (activePlayer.getShelf().isFull() && !isFirstPointAssigned) {
            scoreFirstCompletedShelf(activePlayer);
            isFirstPointAssigned = true;
        }

        //it is the last turn of the last player
        if (isFirstPointAssigned && game.getActivePlayer() == players.get(players.size() - 1)) {
            scorePersonalGoals();
            scoreAdjacency();
        }
    }

    public void endGameScoreUpdate() {
        //done for every player in the game
        scorePersonalGoals();
        scoreAdjacency();
    }

    /**
     * Checks if the player who just finished their turn is the first to complete the shelf,
     * if that is the case, the player wins an extra point and the endgame procedure is activated
     */
    private void scoreFirstCompletedShelf(Player player) { // only the first time a player fills the shelf
        if (!isFirstPointAssigned) {
            game.getTurnHandler().startLastTurn();
            isFirstPointAssigned = true;
            player.updateScore(1);
        }
    }

    /**
     * Assigns each player of points based on the completion of their personal goal card
     */
    private void scorePersonalGoals() { // at the end of the game
        for (Player player : game.getPlayers()) {
            int points = player.getPersonalGoal().calculateScore();
            player.updateScore(points);
        }
    }


    /**
     * Assigns each player of points based on the adjacency of tiles in their shelf
     */
    private void scoreAdjacency() { // at the end of the game
        for (Player player : game.getPlayers()) {
            int points = AdjacencyScoreCalculation.calculateScore(player);
            player.updateScore(points);
        }
    }

}
