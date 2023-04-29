package it.polimi.ingsw.Controller.server.Messages;

import it.polimi.ingsw.View.VirtualView.Messages.ChosenTilesTableMessageToClient;
import it.polimi.ingsw.model.tiles.Color;
import it.polimi.ingsw.model.tiles.ItemTile;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ChosenTilesTableMessageTestToClient {
    @Test
    public void dimension() throws IOException {

        ArrayList<ItemTile> myTiles = new ArrayList<>();
        myTiles.add(new ItemTile(Color.WHITE));

        ChosenTilesTableMessageToClient obj = new ChosenTilesTableMessageToClient(myTiles); // create an instance of your object
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(obj); // serialize the object
        oos.flush();
        oos.close();
        byte[] bytes = bos.toByteArray(); // get the byte array
        int size = bytes.length; // get the length of the byte array
        System.out.println("Serialized chosenTilesTable size: " + size + " bytes");
    }
}
