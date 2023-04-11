package it.polimi.ingsw.Controller;

import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.network.server.SocketClientHandler;
import it.polimi.ingsw.network.server.SocketServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

class PingControllerTest {

    private PingController pingController;
    private Server server;
    private SocketServer socketServer;
    private SocketClientHandler testClientHandler;

    @BeforeEach
    void setUp() {
        server = new Server(1000);
        socketServer = new SocketServer(server, 5000);
        testClientHandler = new SocketClientHandler(socketServer, new Socket());
        server.addClient("player1", testClientHandler);
        pingController = server.getPingController();
    }

    @Test
    void addToPongMap() {
        pingController.addToClientMap("player1");
        assertTrue(pingController.getClientMap().containsKey("player1"));
    }

    @Test
    void removeFromPongMap() {
        pingController.addToClientMap("player1");
        pingController.removeFromClientMap("player1");
        assertFalse(pingController.getClientMap().containsKey("player1"));
    }

    /*
    @Test
    void receivePong() throws InterruptedException {
        pingController.addToPongMap("player1");
        pingController.receivePong("player1");
        assertTrue(pingController.getReceivedPongMap().get("player1"));

        // wait for more than the pingTimeout
        TimeUnit.MILLISECONDS.sleep(2500);

        assertFalse(pingController.getReceivedPongMap().get("player1"));
    }

     */
}