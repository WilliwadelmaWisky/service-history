package server;

import java.nio.file.Paths;

/**
 *
 */
public class CarServer extends AbstractLocalServer {

    private static final String DIRECTORY_PATH = Paths.get(System.getProperty("user.home"), ".wwisky", "sh", "cars").toString();


    /**
     *
     */
    public CarServer() {
        super(DIRECTORY_PATH);
    }
}
