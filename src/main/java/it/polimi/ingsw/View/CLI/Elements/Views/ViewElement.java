package it.polimi.ingsw.View.CLI.Elements.Views;

import java.util.ArrayList;

public abstract class ViewElement {
    final String NULL = ".";

    public abstract ArrayList<String> getPrint(ArrayList<String> output);
}
