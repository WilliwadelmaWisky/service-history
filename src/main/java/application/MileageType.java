package application;

/**
 *
 */
public enum MileageType {
    KILOMETER,
    MILE;


    /**
     * @param mileageInKilometers
     * @return
     */
    public int calculate(int mileageInKilometers) {
        return switch (this) {
            case KILOMETER -> mileageInKilometers;
            case MILE -> (int) (mileageInKilometers * 0.621371192d);
        };
    }
}
