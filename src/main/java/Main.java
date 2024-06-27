import api.RequestAPI;
import api.RequestHandler;
import application.Config;
import server.CarServer;
import application.Application;
import javafx.stage.Stage;
import server.ServiceServer;

/**
 *
 */
public final class Main extends javafx.application.Application {

    /**
     * @param args
     */
    public static void main(String[] args) {
        javafx.application.Application.launch(Main.class);
    }


    /**
     * @param stage
     */
    @Override
    public void start(Stage stage) {
        RequestHandler requestHandler = new RequestHandler();
        CarServer carServer = new CarServer();
        requestHandler.register(Config.CAR_SERVER_HOST, carServer);
        ServiceServer serviceServer = new ServiceServer();
        requestHandler.register(Config.SERVICE_SERVER_HOST, serviceServer);

        RequestAPI requestAPI = new RequestAPI(requestHandler);
        Application application = new Application(stage, requestAPI);
    }
}
