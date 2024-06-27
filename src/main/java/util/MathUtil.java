package util;

/**
 * Very generic static helper functions for many different use cases (Math).
 * This class is not meant to be constructed.
 *
 * @version 19.6.2024
 */
public final class MathUtil {

    /**
     * Check if a value is close to a target value by some precision
     * @param v Value to test
     * @param targetValue Target
     * @param precision Precision of how close the values must be the same, smaller value means more close
     * @return True or False depending on if the values are the same by the precision
     */
    public static boolean isClose(double v, double targetValue, double precision) {
        double distance = Math.abs(v - targetValue);
        return distance <= precision;
    }

    /**
     * Check if a value is close to a target value by some precision. Uses 1e-8 precision.
     * @param v Value to test
     * @param targetValue Target
     * @return True or False depending on if the values are the same by the precision
     */
    public static boolean isClose(double v, double targetValue) {
        final double precision = 0.00000001;
        return isClose(v, targetValue, precision);
    }


    /**
     * Check if a decimal value is close to be a whole number by some precision
     * @param v A decimal value to test
     * @param precision Precision of how close the value must be the closest whole number, smaller value means more close
     * @return True or False depending on if the value and the closest whole number are the same by the precision
     */
    public static boolean isInteger(double v, double precision) {
        int roundedValue = (int)Math.round(v);
        return isClose(v, roundedValue, precision);
    }

    /**
     * Check if a decimal value is close to be a whole number by some precision. Uses 1e-8 precision.
     * @param v A decimal value to test
     * @return True or False depending on if the value and the closest whole number are the same by the precision
     */
    public static boolean isInteger(double v) {
        final double precision = 0.00000001;
        return isInteger(v, precision);
    }
}
