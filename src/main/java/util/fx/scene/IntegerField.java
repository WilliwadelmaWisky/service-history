package util.fx.scene;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**
 * A text field for whole numbers
 *
 * @version 19.6.2024
 */
public class IntegerField extends TextField {

    private final IntegerProperty _valueProperty;
    private int _minValue, _maxValue;


    /**
     *
     */
    public IntegerField() {
        super();
        _valueProperty = new SimpleIntegerProperty(0);
        _maxValue = Integer.MAX_VALUE;
        _minValue = 0;
        setText(String.valueOf(getValue()));

        addEventFilter(KeyEvent.KEY_TYPED, e -> {
            if (!"0123456789".contains(e.getCharacter()) || (!"-".equals(e.getCharacter()) && _minValue < 0))
                e.consume();
        });

        _valueProperty.addListener((observable, oldValue, newValue) -> {
            if (getText().isEmpty() || getText().equals(newValue.toString()))
                return;

            setText(newValue.toString());
        });

        textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty() || (_minValue < 0 && "-".equals(newValue))) {
                setValue(0);
                return;
            }

            final int intValue = Integer.parseInt(newValue);
            if (_minValue > intValue) {
                setText(String.valueOf(_minValue));
                return;
            }

            if (intValue > _maxValue) {
                setText(String.valueOf(_maxValue));
                return;
            }

            setValue(intValue);
        });
    }


    public int getValue() { return _valueProperty.getValue(); }
    public int getMinValue() { return _minValue; }
    public int getMaxValue() { return _maxValue; }
    public IntegerProperty valueProperty() { return _valueProperty; }


    public void setValue(int newValue) { _valueProperty.setValue(newValue); }
    public void setMinValue(int minValue) { _minValue = minValue; }
    public void setMaxValue(int maxValue) { _maxValue = maxValue; }
}
