package it.polimi.ingsw.model.cards.personalGoals;

import exceptions.NullPlayersException;
import exceptions.TooManyPlayersException;
import it.polimi.ingsw.JSONReader.PersonalGoalReader;
import it.polimi.ingsw.model.player.Player;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

/**
 * This class is used to draw a number of {@link PersonalGoal} cards from
 * the deck and assign each card to a different player
 */
public class PersonalCardDealer {
    private static final File personalCardsDirectory = new File("src/data/personal_cards");

    /**
     * Assigns <strong>different</strong> personal goals to a list of players,
     * the method is intended to be executed at the beginning of each game
     *
     * @param players a list of the players whom will be assigned a personal goal card
     * @throws IOException             if the file does not exist or, for any other reason cannot be opened for reading
     * @throws TooManyPlayersException if there are more players than available cards
     * @throws ParseException          if an incorrect JSON is being parsed
     */
    public static void getCards(List<Player> players) throws IOException, ParseException, TooManyPlayersException, NullPlayersException {
        int numOfFiles = Objects.requireNonNull(personalCardsDirectory.list()).length - 1; // not counting points.json
        if (numOfFiles < players.size()) throw new TooManyPlayersException();
        if (players.size() == 0) throw new NullPlayersException();

        HashSet<String> cards = new HashSet<>();
        do {
            cards.add(String.valueOf((int) (Math.random() * numOfFiles) + 1));
        } while (cards.size() < numOfFiles);

        PersonalGoalReader JSONReader = new PersonalGoalReader();
        Stack<Integer> points = JSONReader.getPointStack();
        int i = 0;
        for (String card : cards) {
            Player player = players.get(i);
            PersonalGoal personalGoal = new PersonalGoal(player, JSONReader.getPersonalGoalsData(card + ".json"), points);
            players.get(i).setPersonalGoal(personalGoal);
            i++;
        }
    }
}
