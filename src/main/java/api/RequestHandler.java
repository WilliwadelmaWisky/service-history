package api;

import server.Server;
import util.event.ActionI;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class RequestHandler {

    private final Map<String, Server> _serverMap;


    /**
     *
     */
    public RequestHandler() {
        _serverMap = new HashMap<>();
    }


    /**
     * @param host
     * @param server
     */
    public void register(String host, Server server) {
        _serverMap.put(host, server);
    }


    /**
     * @param host
     * @param request
     * @param onRespond
     */
    protected void handleRequest(String host, Request request, ActionI<Response> onRespond) {
        Server server = _serverMap.get(host);
        if (server == null)
            return;

        server.onReceiveRequest(request, onRespond);
    }
}
