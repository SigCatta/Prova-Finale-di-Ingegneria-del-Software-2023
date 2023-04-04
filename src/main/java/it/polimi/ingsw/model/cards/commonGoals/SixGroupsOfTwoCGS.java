package it.polimi.ingsw.model.cards.commonGoals;

import it.polimi.ingsw.model.player.Shelf;
import it.polimi.ingsw.model.player.ShelfUtils;

/**
 * Rule:
 * Six groups each containing at least 2 tiles of the same type.
 * The tiles of one group can be different from those of another group.
 */
public class SixGroupsOfTwoCGS extends CommonGoalStrategy {
    @Override
    public boolean isGoalAchieved(Shelf shelf) {
        return ShelfUtils.checkMatrixWithDFS(shelf.getShelfGrid(), 6, 2);
    }

    @Override
    public String getDescription() {
        return "Sei gruppi separati formati ciascuno da due tessere adiacenti dello stesso tipo. Le tessere di un gruppo possono essere diverse da quelle di un altro gruppo.";
    }
}
