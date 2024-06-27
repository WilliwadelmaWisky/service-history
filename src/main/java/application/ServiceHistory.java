package application;

import util.serialization.Serializable;

import java.util.*;

/**
 *
 */
public class ServiceHistory implements Serializable, Iterable<ServiceRecord> {

    private String _id;
    private final List<ServiceRecord> _serviceRecordList;


    /**
     * @param car
     */
    public ServiceHistory(Car car) {
        this(car.getID());
    }

    /**
     * @param id
     */
    public ServiceHistory(String id) {
        this();
        _id = id;
    }

    /**
     *
     */
    private ServiceHistory() {
        _serviceRecordList = new ArrayList<>();
    }


    /**
     * @param s
     * @return
     */
    public static ServiceHistory parse(String s) {
        ServiceHistory serviceHistory = new ServiceHistory();
        if (serviceHistory.deserialize(s))
            return serviceHistory;

        return null;
    }


    public String getID() { return _id; }


    /**
     * @param serviceRecord
     */
    public void add(ServiceRecord serviceRecord) {
        _serviceRecordList.add(serviceRecord);
    }

    /**
     * @param serviceRecord
     */
    public void delete(ServiceRecord serviceRecord) {
        _serviceRecordList.remove(serviceRecord);
    }

    /**
     *
     */
    public void clear() { _serviceRecordList.clear(); }


    /**
     *
     */
    public void sort() {
        _serviceRecordList.sort(Comparator.comparingInt(ServiceRecord::getMileage).thenComparing(ServiceRecord::getDate));
    }


    /**
     * @return
     */
    @Override
    public String serialize() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(_id);
        stringBuilder.append('\n');

        for (ServiceRecord serviceRecord : _serviceRecordList) {
            stringBuilder.append(serviceRecord.serialize());
            stringBuilder.append('\n');
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
        String[] props = s.split("\n");
        _id = props[0].trim();

        _serviceRecordList.clear();
        for (int i = 1; i < props.length; i++) {
            ServiceRecord serviceRecord = ServiceRecord.parse(props[i]);
            if (serviceRecord != null)
                _serviceRecordList.add(serviceRecord);
        }

        return true;
    }


    /**
     * @return
     */
    @Override
    public Iterator<ServiceRecord> iterator() { return _serviceRecordList.iterator(); }
}
