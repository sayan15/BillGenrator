package com.example.gasbill_generator;

import javafx.scene.control.Button;
import javafx.scene.control.TableCell;

import java.io.IOException;

public class DeleteTarriffButtonCell extends TableCell<Tariff, Boolean> {

    private final Button deleteButton = new Button("Delete");

    public DeleteTarriffButtonCell(){
        deleteButton.setOnAction(event -> {
            Tariff tariff = getTableView().getItems().get(getIndex());
            try {
                deleteTariff(tariff);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    protected void updateItem(Boolean item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || !item) {
            setGraphic(null);
        } else {
            setGraphic(deleteButton);
        }
    }

    private void deleteTariff(Tariff tariff) throws ClassNotFoundException, IOException {
        System.out.println(tariff.getTarrifName());
        StoreGetTariff.removeTariffFromFile(tariff);
        getTableView().getItems().remove(tariff);
    }
}
