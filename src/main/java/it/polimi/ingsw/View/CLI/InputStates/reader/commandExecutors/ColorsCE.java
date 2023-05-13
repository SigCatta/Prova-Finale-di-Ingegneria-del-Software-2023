package it.polimi.ingsw.View.CLI.InputStates.reader.commandExecutors;

import it.polimi.ingsw.View.CLI.Elements.Printer;
import it.polimi.ingsw.View.CLI.InputStates.reader.Reader;

public class ColorsCE implements CommandExecutor {

    /**
     * Asks the user to choose between using colors or letters for item tiles
     */
    @Override
    public void execute() {
        String input;
        while (true) {
            System.out.println("Do you want to use colors? (y/n)");
            input = Reader.getInput();
            if (input.equals(".")) return;
            if (!(input.equalsIgnoreCase("y") || input.equalsIgnoreCase("n"))) {
                System.out.println("ERROR: Invalid input!");
            } else break;
        }

        Printer.enableCLIColors(input.equalsIgnoreCase("y"));
        Printer.clearConsole();
        Printer.printHomeScreen();
    }
}
