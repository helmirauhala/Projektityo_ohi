package org.example.projektityo_ohi2;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Stack;

public class Ikkuna extends Application {
    private IDataPersistenceService<Sisalto> dataPersistence;
    Stage stage;
    ListView<Sisalto> listView;

    BorderPane root = new BorderPane();
    //ListView<String>

    private  Matkalaukku matkalaukku;
    @Override
    public void start(Stage stage) throws Exception{
        matkalaukku = new Matkalaukku();
        dataPersistence = new DataPersistenceService();
        TextField inputField = new TextField();
        inputField.setPromptText("NIMI");
        TextField quantityField = new TextField();
        quantityField.setPromptText("MÄÄRÄ");
        ComboBox itemTypeComboBox = new ComboBox();

        itemTypeComboBox.getItems().addAll(matkalaukku.tyyppiOptions);
        itemTypeComboBox.getSelectionModel().selectLast();
        Button LisaaButton = new Button("Lisää tavara");
        Button PaivitaButton = new Button("Päivitä tavara");
        Button PoistaButton = new Button("Poista tavara ");

        listView = new ListView<>();
        listView.setPrefHeight(200);

        // read data from file if available

        LisaaButton.setOnAction(e -> {
            String sisaltoNimi = inputField.getText();
            String sisaltoMaara= quantityField.getText();
            var sisaltoTyyppi= (skanneri.Tyyppi)itemTypeComboBox.getSelectionModel().getSelectedItem();
            if (!sisaltoNimi.isEmpty() && !sisaltoMaara.isEmpty()) {
                int maara = Integer.parseInt(sisaltoMaara);

                Sisalto newSisalto = new Sisalto(sisaltoNimi, maara, sisaltoTyyppi);
                matkalaukku.addSisalto(newSisalto);
                listView.getItems().add(newSisalto);
                // write data to file
                saveSisaltosToFile();
                inputField.clear();
                var itms = listView.getItems().toArray();
                quantityField.clear();

            }
        });

        listView.getSelectionModel().selectedItemProperty().addListener((obs, oldSisalto, newSisaltoo) -> {
            if (newSisaltoo != null){
                inputField.setText(newSisaltoo.getNimi());
                quantityField.setText(String.valueOf(newSisaltoo.getMaara()));
            }
        });

        PoistaButton.setOnAction(e ->{
            Sisalto selectedSisalto = listView.getSelectionModel().getSelectedItem();
            if (selectedSisalto != null){
                matkalaukku.removeSisalto(selectedSisalto);
                listView.getItems().remove(selectedSisalto);
                // write current data to file
                saveSisaltosToFile();
                inputField.clear();
                quantityField.clear();
            }
        });

        PaivitaButton.setOnAction(e -> {
            Sisalto selectedSisalto = listView.getSelectionModel().getSelectedItem();
            if (selectedSisalto != null) {
                String updatedNimi = inputField.getText();
                String updatedMaaraText = quantityField.getText();
                if (!updatedNimi.isEmpty() && !updatedMaaraText.isEmpty()) {
                    int updatedMaara = Integer.parseInt(updatedMaaraText);
                    var updatedSisaltoTyyppi= (skanneri.Tyyppi)itemTypeComboBox.getSelectionModel().getSelectedItem();
                    Sisalto updatedItem = new Sisalto(updatedNimi, updatedMaara,updatedSisaltoTyyppi);
                    matkalaukku.updateSisalto(selectedSisalto, updatedItem);
                    listView.getItems().set(listView.getSelectionModel().getSelectedIndex(), updatedItem); // Update
                    // write current data to file
                    saveSisaltosToFile();
                    inputField.clear();
                    quantityField.clear(); }
            }
        });

        loadSisaltosFromFile();
        VBox vbox=new VBox(10, itemTypeComboBox, inputField, quantityField, LisaaButton, PaivitaButton, PoistaButton, listView);

        //StackPane stackPane=new StackPane();
        Scene scene = new Scene(vbox, 600, 400);
        stage.setTitle("Muistilista");
        stage.setScene(scene);
        stage.show();
    }

private void saveSisaltosToFile(){
    dataPersistence.saveData((List<Sisalto>) matkalaukku.getSisaltos());

    showAlert("Success", "Data saved successfully!", Alert.AlertType.INFORMATION);
}

    private void loadSisaltosFromFile() {
        try {
            List<Sisalto> sisaltos = dataPersistence.loadData();
            if (sisaltos.isEmpty()) {
                showAlert("Information", "No data found in the file.", Alert.AlertType.INFORMATION);
            } else {
                matkalaukku.setSisaltos(sisaltos);
                listView.getItems().addAll(matkalaukku.getSisaltos());
                listView.getSelectionModel().selectFirst();
                showAlert("Success", "Data loaded successfully!", Alert.AlertType.INFORMATION);
            }
        } catch (RuntimeException e) {
            // This will catch any RuntimeException thrown by loadData()
            showAlert("Error", "Error loading data: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args){
        launch(args);
    }

}
