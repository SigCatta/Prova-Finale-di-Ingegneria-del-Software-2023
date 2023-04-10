package it.polimi.ingsw.network.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

/**
 * Socket implementation of the ClientHandler interface.
 */
public class SocketClientHandler implements ClientHandler, Runnable {
    private final Socket client;
    private final SocketServer socketServer;

    private boolean connected;

    private final Object inputLock;
    private final Object outputLock;

    private ObjectOutputStream output;
    private ObjectInputStream input;

    /**
     * @param socketServer the socket of the server.
     * @param client the client asking to connect
     */
    public SocketClientHandler(SocketServer socketServer, Socket client) {
        this.socketServer = socketServer;
        this.client = client;
        this.connected = true;

        this.inputLock = new Object();
        this.outputLock = new Object();

        try {
            this.output = new ObjectOutputStream(client.getOutputStream());
            this.input = new ObjectInputStream(client.getInputStream());
        } catch (IOException e) {
            Server.LOGGER.severe(e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            handleClientConnection();
        } catch (IOException e) {
            Server.LOGGER.severe("Client " + client.getInetAddress() + " connection dropped.");
            disconnect();
        }
    }

    /**
     * Handles the connection of a new client and keep listening to the socket for new messages.
     *
     * @throws IOException for Input/Output related exceptions.
     */
    private void handleClientConnection() throws IOException {
        Server.LOGGER.info("Client connected from " + client.getInetAddress());

        try {
            while (!Thread.currentThread().isInterrupted()) {
                synchronized (inputLock) {
                    HashMap<String, String> commandMap = (HashMap<String, String>) input.readObject();

                    if (commandMap != null ) {
                        String nickname = commandMap.get("NICKNAME");
                        if (commandMap.get("COMMAND_TYPE").equals("CAN_I_PLAY")) {
                            socketServer.addClient(nickname, this);
                        } else {
                            Server.LOGGER.info(() -> "Received: " + commandMap.get("COMMAND_TYPE"));
                            socketServer.onCommandReceived(commandMap);
                        }
                    }
                }
            }
        } catch (ClassCastException | ClassNotFoundException e) {
            Server.LOGGER.severe("Invalid stream from client");
        }
        client.close();
    }

    /**
     * Returns the current status of the connection.
     *
     * @return true if the connection is still active, @code otherwise.
     */
    @Override
    public boolean isConnected() {
        return connected;
    }

    @Override
    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    /**
     * Disconnect the socket.
     */
    @Override
    public void disconnect() {
        if (connected) {
            try {
                if (!client.isClosed()) {
                    client.close();
                }
            } catch (IOException e) {
                Server.LOGGER.severe(e.getMessage());
            }
            connected = false;
            Thread.currentThread().interrupt();

            socketServer.onDisconnect(this);
        }
    }

    /**
     * Sends a command to the client via socket.
     *
     * @param commandMap the command and overhead data to be sent.
     */
    @Override
    public void sendCommand(HashMap<String, String> commandMap) {
        try {
            synchronized (outputLock) {
                output.writeObject(commandMap);
                output.reset();
                Server.LOGGER.info(() -> "Sent: " + commandMap);
            }
        } catch (IOException e) {
            Server.LOGGER.severe(e.getMessage());
            disconnect();
        }
    }

    /**
     * Sends a PING to the client via socket.
     */
    @Override
    public void sendPing() {
        HashMap<String, String> commandMap = new HashMap<>();
        commandMap.put("NICKNAME", "SERVER");
        commandMap.put("COMMAND_TYPE", "PING");

        sendCommand(commandMap);
    }

    /**
     * sends the message regarding the disconnection or reconnection of a client.
     *
     * @param nickname the client disconnecting or reconnection
     * @param reconnection true if the client has reconnected to the game
     * @param connectionLost true if the client hasn't responded to a PING message
     *                       false if the client has been disconnected from the game
     */
    public void sendConnectionMessage(String nickname, boolean reconnection, boolean connectionLost) {
        HashMap<String, String> commandMap = new HashMap<>();
        commandMap.put("NICKNAME", nickname);

        if(reconnection) {
            commandMap.put("COMMAND_TYPE", "PLAYER_RECONNECTED");
            commandMap.put("COMMAND_DATA", nickname + " is back online :)");
        } else {
            if(connectionLost) {
                commandMap.put("COMMAND_TYPE", "PLAYER_DOWN");
                commandMap.put("COMMAND_DATA", "We've lost conection from " + nickname + " :(");
            } else {
                commandMap.put("COMMAND_TYPE", "BYE");
                commandMap.put("COMMAND_DATA", nickname + " has disconnected from the game.");
            }
        }

        sendCommand(commandMap);
    }
}