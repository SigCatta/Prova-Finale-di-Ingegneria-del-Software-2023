package it.polimi.ingsw.View.CLI;

import it.polimi.ingsw.Enum.Color;

import java.util.ArrayList;
import java.util.HashMap;

public class Printer {

    static HashMap<Color, String> colorMap;
    public static final String NULL = ".";

    public static void enableCLIColors(boolean isColored) {
        colorMap = new HashMap<>();
        if (isColored) {
            // COLOR = ANSI_BACKGROUND_COLOR_ID + WHITE spaces + ANSI_COLOR_RESET
            colorMap.put(Color.GREEN, "\033[0;102m" + " " + "\u001B[0m");
            colorMap.put(Color.YELLOW, "\033[0;103m" + " " + "\u001B[0m");
            colorMap.put(Color.BLUE, "\033[0;104m" + " " + "\u001B[0m");
            colorMap.put(Color.PINK, "\033[0;105m" + " " + "\u001B[0m");
            colorMap.put(Color.LIGHTBLUE, "\033[0;106m" + " " + "\u001B[0m");
            colorMap.put(Color.WHITE, "\033[0;107m" + " " + "\u001B[0m");
        } else {
            colorMap.put(Color.GREEN, "G");
            colorMap.put(Color.YELLOW, "Y");
            colorMap.put(Color.BLUE, "B");
            colorMap.put(Color.PINK, "P");
            colorMap.put(Color.LIGHTBLUE, "L");
            colorMap.put(Color.WHITE, "W");
        }
        colorMap.put(Color.EMPTY, " ");
    }

    public static HashMap<Color, String> getColorMap() {
        if (colorMap == null) enableCLIColors(false);
        return colorMap;
    }


    /**
     * Prints the game's home screen, containing:<br>
     *      - board<br>
     *      - personal shelf<br>
     *      - common goals<br>
     *      - personal goal<br>
     */
    public static void printHomeScreen(){
        ArrayList<String> output = new ArrayList<>();

        output = new BoardView().getPrint(output);
        output = new ShelfView().getPrint(output);
        output = new CommonGoalView().getPrint(output);
        output = new PersonalGoalView().getPrint(output);

        output.forEach(System.out::println);
    }
}
