package api;

import util.event.ActionI;

/**
 *
 */
public class RequestAPI {

    private final RequestHandler _handler;


    /**
     * @param handler
     */
    public RequestAPI(RequestHandler handler) {
        _handler = handler;
    }


    /**
     * @param host
     * @param request
     * @param onRespond
     */
    public void sendRequest(String host, Request request, ActionI<Response> onRespond) {
        _handler.handleRequest(host, request, onRespond);
    }
}
