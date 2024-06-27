package application;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 *
 */
public class TestCar {

    /**
     *
     */
    @Test
    public void testSerialize() {
        Car car1 = new Car("id1", "vin1", "license1", "make1", "model1", LocalDate.of(1000, 1, 1), LocalDate.of(1000, 1, 1));
        String serializeResult = car1.serialize();
        Car car2 = new Car("id2", "vin2", "license2", "make2", "model2", LocalDate.of(2000, 2, 2), LocalDate.of(2000, 2, 2));
        boolean deserializeResult = car2.deserialize(serializeResult);
        assertTrue(deserializeResult);

        assertEquals(car1.getID(), car2.getID());
        assertEquals(car1.getVIN(), car2.getVIN());
        assertEquals(car1.getLicense(), car2.getLicense());
        assertEquals(car1.getMake(), car2.getMake());
        assertEquals(car1.getModel(), car2.getModel());
        assertEquals(car1.getManufactureDate(), car2.getManufactureDate());
        assertEquals(car1.getRegistrationDate(), car2.getRegistrationDate());
    }
}
