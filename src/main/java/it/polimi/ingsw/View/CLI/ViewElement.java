package it.polimi.ingsw.View.CLI;

import java.util.ArrayList;

public interface ViewElement {
    final String NULL = ".";
    ArrayList<String> getPrint(ArrayList<String> output);
}
