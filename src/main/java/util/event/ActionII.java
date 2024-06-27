package util.event;

/**
 * A simple function class with two generic input values and no output values
 *
 * @param <T1> Type of the first input value
 * @param <T2> Type of the second input value
 *
 * @version 18.6.2024
 */
@FunctionalInterface
public interface ActionII<T1, T2> {

    /**
     * Calls the function
     * @param arg1 Value of the first input
     * @param arg2 Value of the second input
     */
    void invoke(T1 arg1, T2 arg2);
}
