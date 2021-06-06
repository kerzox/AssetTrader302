package server.mock;

import server.CommandException;
import util.NetworkUtils;
import util.Request;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import static util.Request.Type.MESSAGE;

public class MockServer {

    private boolean shutdown = false;

    public void doRequests(List<Object> request) {
        Request.Type command = Request.grabValidType(request.get(0).toString());
        if (command == null) throw new CommandException(request.get(0).toString() + " is an invalid command.");

            // do non sql command handling
        if (!Request.Type.isSQLCommand(command)) {
            if (command == MESSAGE) {
                if (request.get(1).toString().contains("quit")) {
                    shutdown = true;
                    System.out.println("Server will quit now...");
                    return;
                }
            }
        }
        Request.Header head  = Request.grabValidHeader(request.get(1).toString());
        if (head == null) throw new CommandException(request.get(1).toString()
                + " is an invalid head.");
    }

    public boolean isShutdown() {
        return shutdown;
    }
}
