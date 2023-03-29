package it.polimi.ingsw.model.cards.commonGoals;

import it.polimi.ingsw.model.player.Shelf;
import it.polimi.ingsw.model.tiles.Color;
import it.polimi.ingsw.model.tiles.ItemTile;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SixDiffColorsCGSTest {
    @Test
    public void testHasTwoColumnsOfSixDifferentColors() {
        SixDiffColorsCGS cg = new SixDiffColorsCGS();
        ItemTile[][] matrix1 = {
                {new ItemTile(Color.PINK), new ItemTile(Color.BLUE), new ItemTile(Color.LIGHTBLUE), new ItemTile(Color.WHITE), new ItemTile(Color.YELLOW), new ItemTile(Color.GREEN)},
                {new ItemTile(Color.PINK), new ItemTile(Color.BLUE), new ItemTile(Color.LIGHTBLUE), new ItemTile(Color.WHITE), new ItemTile(Color.YELLOW), new ItemTile(Color.GREEN)},
                {new ItemTile(Color.GREEN), new ItemTile(Color.GREEN), new ItemTile(Color.BLUE), new ItemTile(Color.WHITE), new ItemTile(Color.WHITE), new ItemTile(Color.GREEN)},
                {new ItemTile(Color.PINK), new ItemTile(Color.PINK), new ItemTile(Color.BLUE), new ItemTile(Color.WHITE), new ItemTile(Color.WHITE), new ItemTile(Color.GREEN)},
                {new ItemTile(Color.GREEN), new ItemTile(Color.GREEN), new ItemTile(Color.BLUE), new ItemTile(Color.WHITE), new ItemTile(Color.WHITE), new ItemTile(Color.GREEN)},
        };
        Shelf shelf = new Shelf(matrix1);
        assertTrue(cg.isGoalAchieved(shelf));

        ItemTile[][] matrix2 = {
                {new ItemTile(Color.GREEN), new ItemTile(Color.GREEN), new ItemTile(Color.BLUE), new ItemTile(Color.WHITE), new ItemTile(Color.WHITE), new ItemTile(Color.GREEN)},
                {new ItemTile(Color.GREEN), new ItemTile(Color.GREEN), new ItemTile(Color.BLUE), new ItemTile(Color.WHITE), new ItemTile(Color.WHITE), new ItemTile(Color.GREEN)},
                {new ItemTile(Color.GREEN), new ItemTile(Color.GREEN), new ItemTile(Color.BLUE), new ItemTile(Color.WHITE), new ItemTile(Color.WHITE), new ItemTile(Color.GREEN)},
                {new ItemTile(Color.PINK), new ItemTile(Color.PINK), new ItemTile(Color.BLUE), new ItemTile(Color.WHITE), new ItemTile(Color.WHITE), new ItemTile(Color.GREEN)},
                {new ItemTile(Color.GREEN), new ItemTile(Color.GREEN), new ItemTile(Color.BLUE), new ItemTile(Color.WHITE), new ItemTile(Color.WHITE), new ItemTile(Color.GREEN)},
        };
        shelf = new Shelf(matrix2);
        assertFalse(cg.isGoalAchieved(shelf));
    }
}
