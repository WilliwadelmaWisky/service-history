package application;

import util.serialization.Serializable;

/**
 *
 */
public class Part implements Serializable {

    private String _id, _name;


    /**
     * @param id
     * @param name
     */
    public Part(String id, String name) {
        this();
        _id = id;
        _name = name;
    }

    /**
     *
     */
    private Part() { }


    /**
     * @return
     */
    public static Part empty() {
        return new Part();
    }


    /**
     * @param s
     * @return
     */
    public static Part parse(String s) {
        Part part = new Part();
        if (part.deserialize(s))
            return part;

        return null;
    }


    public String getID() { return _id; }
    public String getName() { return _name; }


    public void setID(String id) { _id = id; }
    public void setName(String name) { _name = name; }


    /**
     * @return
     */
    @Override
    public String serialize() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(_id);
        stringBuilder.append('¤');
        stringBuilder.append(_name);
        return stringBuilder.toString();
    }

    /**
     * @param s
     * @return
     */
    @Override
    public boolean deserialize(String s) {
        String[] props = s.split("¤");
        if (props.length < 2)
            return false;

        _id = props[0].trim();
        _name = props[1].trim();
        return true;
    }
}
