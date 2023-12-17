package com.example.gasbill_generator;

import javafx.scene.control.TextField;

public class NumericTextField extends TextField {

    private double numericValue = 0.0;

    public double getNumericValue() {
        return numericValue;
    }

    public void setNumericValue(double numericValue) {
        this.numericValue = numericValue;
        setText(String.valueOf(numericValue));
    }

    @Override
    public void replaceText(int start, int end, String text) {
        if (isValidInput(text) || text.isEmpty()) {
            super.replaceText(start, end, text);
            updateNumericValue();
        }
    }

    @Override
    public void replaceSelection(String text) {
        if (isValidInput(text) || text.isEmpty()) {
            super.replaceSelection(text);
            updateNumericValue();
        }
    }

    private void updateNumericValue() {
        try {
            numericValue = Double.parseDouble(getText());
        } catch (NumberFormatException e) {
            // Handle invalid input if needed
            numericValue = 0.0;
        }
    }

    private boolean isValidInput(String text) {
        return text.matches("[0-9.]");
    }
}
