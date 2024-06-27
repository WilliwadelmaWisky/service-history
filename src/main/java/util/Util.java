package util;

import util.event.FuncI;

import java.util.ArrayList;
import java.util.List;

/**
 * Very generic static helper functions for many different use cases.
 * This class is not meant to be constructed.
 *
 * @version 18.6.2024
 */
public final class Util {

    /**
     * Add specified extra characters to the start of the string until a certain length is achieved.
     * @param s String to modify
     * @param length Target length of the string
     * @param padChar A character to add
     * @return A new modified string
     */
    public static String padLeft(String s, int length, char padChar) {
        if (s.length() >= length)
            return s;

        StringBuilder stringBuilder = new StringBuilder(s);
        for (int i = 0; i < length - s.length(); i++) {
            stringBuilder.insert(0, padChar);
        }

        return stringBuilder.toString();
    }

    /**
     * Add specified extra characters to the end of the string until a certain length is achieved.
     * @param s String to modify
     * @param length Target length of the string
     * @param padChar A character to add
     * @return A new modified string
     */
    public static String padRight(String s, int length, char padChar) {
        if (s.length() >= length)
            return s;

        StringBuilder stringBuilder = new StringBuilder(s);
        for (int i = 0; i < length - s.length(); i++) {
            stringBuilder.append(padChar);
        }

        return stringBuilder.toString();
    }


    /**
     * Convert an array of some type to some other type
     * @param array Array to modify
     * @param selector Modifier function
     * @param <TIn> Input type
     * @param <TOut> Output type
     * @return A list of modified array elements
     */
    public static <TIn, TOut> List<TOut> select(TIn[] array, FuncI<TIn, TOut> selector) {
        List<TOut> selectedList = new ArrayList<>();
        for (TIn value : array) {
            selectedList.add(selector.invoke(value));
        }

        return selectedList;
    }
}
