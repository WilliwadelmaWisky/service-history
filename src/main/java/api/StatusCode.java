package api;

/**
 *
 */
public enum StatusCode {
    OK(200),
    NOT_FOUND(404);

    private final int _value;
    StatusCode(final int value) { _value = value; }
    int getValue() { return _value; }

    /**
     * @param value
     * @return
     */
    public boolean equals(int value) { return getValue() == value; }
}
