package api;

/**
 *
 */
public class Response {

    private final int _status;
    private final String _payload;


    /**
     * @param status
     * @param payload
     */
    public Response(int status, String payload) {
        _status = status;
        _payload = payload;
    }

    /**
     * @param statusCode
     * @param payload
     */
    public Response(StatusCode statusCode, String payload) {
        this(statusCode.getValue(), payload);
    }


    public int getStatus() { return _status; }
    public String getPayload() { return _payload; }
}
