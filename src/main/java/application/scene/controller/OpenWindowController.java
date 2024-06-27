package application.scene.controller;

import api.Request;
import api.RequestAPI;
import api.RequestType;
import api.StatusCode;
import application.Car;
import application.Config;
import application.ServiceHistory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import util.io.FileUtil;
import util.event.ActionII;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class OpenWindowController {

    @FXML private TextField _searchTextField;
    @FXML private VBox _listVBox;

    private RequestAPI _requestAPI;
    private ActionII<Car, ServiceHistory> _openAction;
    private List<Car> _carList;


    /**
     * @param requestAPI
     * @param onOpen
     */
    public void setup(RequestAPI requestAPI, ActionII<Car, ServiceHistory> onOpen) {
        _requestAPI = requestAPI;
        _openAction = onOpen;
        _carList = new ArrayList<>();

        refresh();
    }


    /**
     *
     */
    private void refresh() {
        _carList.clear();
        _listVBox.getChildren().clear();

        Request request = new Request(RequestType.GET, "/", null);
        _requestAPI.sendRequest(Config.CAR_SERVER_HOST, request, response -> {
            if (!StatusCode.OK.equals(response.getStatus()))
                return;

            String[] fileNames = response.getPayload().split("\n");
            for (String fileName : fileNames) {
                String id = FileUtil.getNameWithoutExtension(fileName);
                Request req = new Request(RequestType.GET, "/" + id + ".txt", null);
                _requestAPI.sendRequest(Config.CAR_SERVER_HOST, req, res -> {
                    if (!StatusCode.OK.equals(res.getStatus()))
                        return;

                    Car car = Car.parse(res.getPayload());
                    if (car == null)
                        return;

                    _carList.add(car);
                    Node entry = createCarEntry(car);
                    _listVBox.getChildren().add(entry);
                });
            }
        });
    }


    /**
     * @param car
     * @return
     */
    private Node createCarEntry(final Car car) {
        VBox root = new VBox();
        root.getStyleClass().add("content-box");
        root.setPadding(new Insets(10));
        root.setOnMouseClicked(e -> open(car.getID()));

        Label carLabel = new Label();
        carLabel.setText(car.getMake() + " " + car.getModel());
        root.getChildren().add(carLabel);

        Label licenseLabel = new Label();
        licenseLabel.setText(car.getLicense());
        root.getChildren().add(licenseLabel);
        return root;
    }


    /**
     * @param id
     */
    private void open(String id) {
        String url = "/" + id + ".txt";
        Request carRequest = new Request(RequestType.GET, url, null);
        _requestAPI.sendRequest(Config.CAR_SERVER_HOST, carRequest, carResponse -> {
            if (!StatusCode.OK.equals(carResponse.getStatus()))
                return;

            final Car car = Car.parse(carResponse.getPayload());
            if (car == null)
                return;

            Request serviceRequest = new Request(RequestType.GET, url, null);
            _requestAPI.sendRequest(Config.SERVICE_SERVER_HOST, serviceRequest, serviceResponse -> {
                if (StatusCode.OK.equals(serviceResponse.getStatus())) {
                    ServiceHistory serviceHistory = ServiceHistory.parse(serviceResponse.getPayload());
                    _openAction.invoke(car, serviceHistory);
                    return;
                }

                _openAction.invoke(car, null);
            });
        });
    }


    /**
     * @param e
     */
    @FXML
    private void onSearch(ActionEvent e) {
        _listVBox.getChildren().clear();

        for (Car car : _carList) {
            if (!car.getLicense().toLowerCase().startsWith(_searchTextField.getText().toLowerCase()))
                continue;

            Node entry = createCarEntry(car);
            _listVBox.getChildren().add(entry);
        }
    }
}
