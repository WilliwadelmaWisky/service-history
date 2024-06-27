package util.serialization;

/**
 * A simple contract to force methods required for serialization
 *
 * @version 18.6.2024
 */
public interface Serializable {

    /**
     * Serialize (Make a string from object data)
     * @return A created string
     */
    String serialize();

    /**
     * Deserialize (Load object data from string)
     * @param s String to load
     * @return True or False depending on the success of the operation
     */
    boolean deserialize(String s);
}
