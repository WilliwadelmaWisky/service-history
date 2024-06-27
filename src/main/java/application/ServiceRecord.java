package application;

import util.serialization.Serializable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 *
 */
public class ServiceRecord implements Serializable, Iterable<ServicePart> {

    private String _id, _description, _repairer;
    private int _mileage;
    private LocalDate _date;
    private final List<ServicePart> _servicePartList;


    /**
     * @param id
     * @param description
     * @param repairer
     * @param mileage
     * @param date
     */
    public ServiceRecord(String id, String description, String repairer, int mileage, LocalDate date) {
        this();
        _id = id;
        _description = description;
        _repairer = repairer;
        _mileage = mileage;
        _date = date;
    }

    /**
     *
     */
    private ServiceRecord() {
        _servicePartList = new ArrayList<>();
    }


    /**
     * @return
     */
    public static ServiceRecord empty() {
        return new ServiceRecord(UUID.randomUUID().toString(), "", "", 0, LocalDate.now());
    }

    /**
     * @param s
     * @return
     */
    public static ServiceRecord parse(String s) {
        ServiceRecord serviceRecord = new ServiceRecord();
        if (serviceRecord.deserialize(s))
            return serviceRecord;

        return null;
    }


    public String getDescription() { return _description; }
    public String getRepairer() { return _repairer; }
    public int getMileage() { return _mileage; }
    public LocalDate getDate() { return _date; }


    public void setDescription(String description) { _description = description; }
    public void setRepairer(String repairer) { _repairer = repairer; }
    public void setMileage(int mileage) { _mileage = Math.max(mileage, 0); }
    public void setDate(LocalDate date) { _date = date; }


    /**
     * @param serviceParts
     */
    public void setServiceParts(Collection<ServicePart> serviceParts) {
        _servicePartList.clear();
        _servicePartList.addAll(serviceParts);
    }


    /**
     * @return
     */
    @Override
    public String serialize() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(_id);
        stringBuilder.append(';');
        stringBuilder.append(_mileage);
        stringBuilder.append(';');
        stringBuilder.append(_repairer);
        stringBuilder.append(';');
        stringBuilder.append(_description);
        stringBuilder.append(';');

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        stringBuilder.append(_date.format(dateFormatter));
        stringBuilder.append(';');

        for (ServicePart servicePart : _servicePartList) {
            stringBuilder.append(servicePart.serialize());
            stringBuilder.append('|');
        }

        stringBuilder.deleteCharAt(stringBuilder.length() - 1);

        return stringBuilder.toString();
    }

    /**
     * @param s
     * @return
     */
    @Override
    public boolean deserialize(String s) {
        String[] props = s.split(";");
        if (props.length < 5)
            return false;

        _id = props[0].trim();
        _mileage = Integer.parseInt(props[1].trim());
        _repairer = props[2].trim();
        _description = props[3].trim();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        _date = LocalDate.parse(props[4].trim(), dateFormatter);

        _servicePartList.clear();
        if (props.length < 6)
            return true;

        for (String partProp : props[5].split("\\|")) {
            ServicePart servicePart = ServicePart.parse(partProp);
            if (servicePart != null)
                _servicePartList.add(servicePart);
        }

        return true;
    }


    /**
     * @return
     */
    @Override
    public Iterator<ServicePart> iterator() { return _servicePartList.iterator(); }
}
