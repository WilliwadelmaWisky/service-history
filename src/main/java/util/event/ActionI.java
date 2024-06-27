package util.event;

/**
 * A simple function class with a single generic input value and no output values
 * @param <T> Type of the input value
 *
 * @version 18.6.2024
 */
@FunctionalInterface
public interface ActionI<T> {

    /**
     * Calls the function
     * @param arg Value of the input
     */
    void invoke(T arg);
}
