package it.polimi.ingsw.model.cards.personalGoals;

import exceptions.NoPlayersException;
import exceptions.TooManyPlayersException;
import it.polimi.ingsw.ReadFromJSONFile;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.tiles.Color;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;


/**
 * This class tests {@link PersonalGoal}'s methods
 */
public class PersonalGoalsTest {
    @Test
    public void pointStackTest() throws TooManyPlayersException, IOException, ParseException, NoPlayersException {
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player());
        PersonalCardDealer.getCards(players);

        PersonalGoal personalGoal = players.get(0).getPersonalGoal();
        Stack<Integer> pointStack = new ReadFromJSONFile().getPointStack();
        assertEquals(pointStack, personalGoal.getPointStack()); // full Stack
    }

    @Test
    public void scoreCalculationTest1() throws IOException, ParseException {
        Player player = new Player();
        HashMap<Color, Point> achievements = new ReadFromJSONFile().getPersonalGoalsData("1.json");
        Stack<Integer> pointStack = new ReadFromJSONFile().getPointStack();
        PersonalGoal personalGoal = new PersonalGoal(player, achievements, pointStack);

        //TODO write test afeter shelf logic is fixed

        assertEquals(pointStack.pop(), personalGoal.calculateScore()); // 1

        assertEquals(pointStack.pop(), personalGoal.calculateScore()); // 2

        assertEquals(pointStack.pop(), personalGoal.calculateScore()); // 4

        assertEquals(pointStack.pop(), personalGoal.calculateScore()); // 6

        assertEquals(pointStack.pop(), personalGoal.calculateScore()); // 9

        assertEquals(pointStack.pop(), personalGoal.calculateScore()); // 12
    }
}
