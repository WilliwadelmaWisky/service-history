package util.event;

/**
 * A simple function class with a single input value and a single output value
 * @param <TIn> Type of the input value
 * @param <TOut> Type of the output value
 *
 * @version 18.6.2024
 */
@FunctionalInterface
public interface FuncI<TIn, TOut> {

    /**
     * Calls the function
     * @param arg Value of the input
     * @return Output value
     */
    TOut invoke(TIn arg);
}
