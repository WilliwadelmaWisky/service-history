package application.scene;

import application.Car;
import application.Config;
import application.scene.controller.CarEditWindowController;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import util.fx.FXUtil;
import util.event.Action;

import java.util.Objects;

/**
 *
 */
public class CarEditWindow implements Window {

    private static final String FXML_PATH = "/fxml/car_edit_window.fxml";

    private final Stage _stage;


    /**
     * @param car
     * @param onApply
     * @param onDelete
     */
    public CarEditWindow(Car car, Action onApply, Action onDelete) {
        _stage = new Stage();

        FXUtil.FXMLLoadData loadData = FXUtil.loadFxml(FXML_PATH);
        CarEditWindowController controller = (CarEditWindowController) loadData.getController();
        controller.setup(car, () -> {
            onApply.invoke();
            close();
        }, () -> {
            onDelete.invoke();
            close();
        });

        _stage.setTitle("Edit a car");
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
