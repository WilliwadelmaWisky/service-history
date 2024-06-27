package server;

import java.nio.file.Paths;

/**
 *
 */
public class ServiceServer extends AbstractLocalServer {

    private static final String DIRECTORY_PATH = Paths.get(System.getProperty("user.home"), ".wwisky", "sh", "services").toString();


    /**
     *
     */
    public ServiceServer() {
        super(DIRECTORY_PATH);
    }
}
