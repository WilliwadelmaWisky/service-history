package application;

import util.serialization.Serializable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 *
 */
public class Car implements Serializable {

    private String _id, _vin, _license, _make, _model;
    private LocalDate _manufactureDate, _registrationDate;


    /**
     * @param id
     * @param vin
     * @param license
     * @param make
     * @param model
     * @param manufactureDate
     * @param registrationDate
     */
    public Car(String id, String vin, String license, String make, String model, LocalDate manufactureDate, LocalDate registrationDate) {
        this();
        _id = id;
        _vin = vin;
        _license = license;
        _make = make;
        _model = model;
        _manufactureDate = manufactureDate;
        _registrationDate = registrationDate;
    }

    /**
     *
     */
    private Car() { }


    /**
     * @return
     */
    public static Car empty() {
        return new Car(UUID.randomUUID().toString(), "", "", "", "", LocalDate.now(), LocalDate.now());
    }

    /**
     * @param s
     * @return
     */
    public static Car parse(String s) {
        Car car = new Car();
        if (car.deserialize(s))
            return car;

        return null;
    }


    public String getID() { return _id; }
    public String getVIN() { return _vin; }
    public String getLicense() { return _license; }
    public String getMake() { return _make; }
    public String getModel() { return _model; }
    public LocalDate getManufactureDate() { return _manufactureDate; }
    public LocalDate getRegistrationDate() { return _registrationDate; }


    public void setVIN(String vin) { _vin = vin; }
    public void setLicense(String license) { _license = license; }
    public void setMake(String make) { _make = make; }
    public void setModel(String model) { _model = model; }
    public void setManufactureDate(LocalDate manufactureDate) { _manufactureDate = manufactureDate; }
    public void setRegistrationDate(LocalDate registrationDate) { _registrationDate = registrationDate; }


    /**
     * @return
     */
    @Override
    public String serialize() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(_id);
        stringBuilder.append(';');
        stringBuilder.append(_vin);
        stringBuilder.append(';');
        stringBuilder.append(_license);
        stringBuilder.append(';');
        stringBuilder.append(_make);
        stringBuilder.append(';');
        stringBuilder.append(_model);
        stringBuilder.append(';');

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        stringBuilder.append(_manufactureDate.format(dateFormatter));
        stringBuilder.append(';');
        stringBuilder.append(_registrationDate.format(dateFormatter));
        stringBuilder.append(';');
        return stringBuilder.toString();
    }

    /**
     * @param s
     * @return
     */
    @Override
    public boolean deserialize(String s) {
        String[] props = s.split(";");
        if (props.length < 7)
            return false;

        _id = props[0].trim();
        _vin = props[1].trim();
        _license = props[2].trim();
        _make = props[3].trim();
        _model = props[4].trim();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        _manufactureDate = LocalDate.parse(props[5].trim(), dateFormatter);
        _registrationDate = LocalDate.parse(props[6].trim(), dateFormatter);
        return true;
    }
}
