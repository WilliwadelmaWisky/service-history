package application;

import util.serialization.Serializable;

/**
 *
 */
public class ServicePart implements Serializable {

    public static final int MIN_QUANTITY = 1;
    public static final int MAX_QUANTITY = 1000;

    private Part _part;
    private double _quantity;


    /**
     * @param part
     * @param quantity
     */
    public ServicePart(Part part, double quantity) {
        this();
        _part = part;
        _quantity = quantity;
    }

    /**
     *
     */
    private ServicePart() { }


    /**
     * @param s
     * @return
     */
    public static ServicePart parse(String s) {
        ServicePart servicePart = new ServicePart();
        if (servicePart.deserialize(s))
            return servicePart;

        return null;
    }


    public Part getPart() { return _part; }
    public double getQuantity() { return _quantity; }


    public void setQuantity(double quantity) { _quantity = quantity; }


    /**
     * @return
     */
    @Override
    public String serialize() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(_part.serialize());
        stringBuilder.append('=');
        stringBuilder.append(_quantity);

        return stringBuilder.toString();
    }

    /**
     * @param s
     * @return
     */
    @Override
    public boolean deserialize(String s) {
        String[] props = s.split("=");
        if (props.length < 2)
            return false;

        _part = Part.empty();
        if (!_part.deserialize(props[0].trim()))
            return false;

        _quantity = Double.parseDouble(props[1].trim());
        return true;
    }
}
