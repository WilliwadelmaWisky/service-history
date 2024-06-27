package util.event;

/**
 * A simple function class with no input or output values
 *
 * @version 18.6.2024
 */
@FunctionalInterface
public interface Action {

    /**
     * Calls the function
     */
    void invoke();
}
