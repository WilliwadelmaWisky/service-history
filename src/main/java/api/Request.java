package api;

/**
 *
 */
public class Request {

    private final RequestType _requestType;
    private final String _url, _payload;


    /**
     * @param requestType
     * @param url
     * @param payload
     */
    public Request(RequestType requestType, String url, String payload) {
        _requestType = requestType;
        _url = url;
        _payload = payload;
    }


    public RequestType getType() { return _requestType; }
    public String getURL() { return _url; }
    public String getPayload() { return _payload; }
}
