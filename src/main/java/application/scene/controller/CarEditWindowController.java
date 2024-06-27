package application.scene.controller;

import application.Car;
import application.CarMake;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import util.Util;
import util.event.Action;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 */
public class CarEditWindowController {

    @FXML private TextField _vinTextField, _makeTextField, _modelTextField, _licenseTextField;
    @FXML private DatePicker _manufactureDatePicker, _registrationDatePicker;
    @FXML private ComboBox<String> _makeComboBox;

    private final DateTimeFormatter _dateFormatter;
    private Car _car;
    private Action _applyAction, _deleteAction;


    /**
     *
     */
    public CarEditWindowController() {
        _dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    }

    /**
     * @param car
     * @param onApply
     * @param onDelete
     */
    public void setup(Car car, Action onApply, Action onDelete) {
        _car = car;
        _applyAction = onApply;
        _deleteAction = onDelete;

        ObservableList<String> items = FXCollections.observableList(Util.select(CarMake.values(), Enum::name));
        _makeComboBox.setItems(items);
        _makeComboBox.setValue(_car.getMake());
        _makeComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            _makeTextField.setText(newValue);
        });

        StringConverter<LocalDate> converter = new StringConverter<>() {
            @Override
            public String toString(LocalDate date) { return (date != null) ? _dateFormatter.format(date) : ""; }

            @Override
            public LocalDate fromString(String s) { return (s != null && !s.isEmpty()) ? LocalDate.parse(s, _dateFormatter) : null; }
        };

        _manufactureDatePicker.setConverter(converter);
        _registrationDatePicker.setConverter(converter);

        _vinTextField.setText(_car.getVIN());
        _makeTextField.setText(_car.getMake());
        _modelTextField.setText(_car.getModel());
        _licenseTextField.setText(_car.getLicense());
        _manufactureDatePicker.setValue(_car.getManufactureDate());
        _registrationDatePicker.setValue(_car.getRegistrationDate());
    }

    /**
     * @param e
     */
    @FXML
    private void onApply(ActionEvent e) {
        _car.setVIN(_vinTextField.getText());
        _car.setMake(_makeTextField.getText());
        _car.setModel(_modelTextField.getText());
        _car.setLicense(_licenseTextField.getText());
        _car.setManufactureDate(_manufactureDatePicker.getValue());
        _car.setRegistrationDate(_registrationDatePicker.getValue());

        _applyAction.invoke();
    }

    /**
     * @param e
     */
    @FXML
    private void onDelete(ActionEvent e) {
        _deleteAction.invoke();
    }
}
