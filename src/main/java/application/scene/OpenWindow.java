package application.scene;

import api.RequestAPI;
import application.Car;
import application.Config;
import application.ServiceHistory;
import application.scene.controller.OpenWindowController;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import util.fx.FXUtil;
import util.event.ActionII;

import java.util.Objects;

/**
 *
 */
public class OpenWindow implements Window {

    private static final String FXML_PATH = "/fxml/open_window.fxml";

    private final Stage _stage;


    /**
     * @param requestAPI
     * @param onOpen
     */
    public OpenWindow(RequestAPI requestAPI, ActionII<Car, ServiceHistory> onOpen) {
        _stage = new Stage();

        FXUtil.FXMLLoadData loadData = FXUtil.loadFxml(FXML_PATH);
        OpenWindowController controller = (OpenWindowController) loadData.getController();
        controller.setup(requestAPI, (car, serviceHistory) -> {
            onOpen.invoke(car, serviceHistory);
            close();
        });

        _stage.setTitle("Edit a single service record");
        _stage.setResizable(false);
        _stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream(Config.LOGO_PATH))));

        Scene scene = new Scene(loadData.getRoot());
        scene.getStylesheets().add(Config.STYLESHEET_PATH);
        _stage.setScene(scene);
    }


    /**
     *
     */
    @Override
    public void show() { _stage.show(); }

    /**
     *
     */
    @Override
    public void close() { _stage.close(); }
}
