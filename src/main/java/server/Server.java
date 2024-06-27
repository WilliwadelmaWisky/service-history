package server;

import api.Request;
import api.Response;
import util.event.ActionI;

/**
 *
 */
public interface Server {

    /**
     * @param request
     * @param onRespond
     */
    void onReceiveRequest(Request request, ActionI<Response> onRespond);
}
