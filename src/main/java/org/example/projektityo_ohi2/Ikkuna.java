package org.example.projektityo_ohi2;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Stack;

public class Ikkuna extends Application {

    Stage stage;

    BorderPane root = new BorderPane();
    //ListView<String>

    private  Matkalaukku matkalaukku;
    @Override
    public void start(Stage stage) throws Exception{
        matkalaukku = new Matkalaukku();
        TextField inputField = new TextField();
        inputField.setPromptText("NIMI");
        TextField quantityField = new TextField();
        quantityField.setPromptText("MÄÄRÄ");
        //ComboBox itemType = new ComboBox();
        //itemType.getItems().addAll("a","b", "c");
        Button LisaaButton = new Button("Lisää tavara");
        Button PaivitaButton = new Button("Päivitä tavara");
        Button PoistaButton = new Button("Poista tavara ");

        ListView<Sisalto> listView = new ListView<>();
        listView.setPrefHeight(200);

        LisaaButton.setOnAction(e -> {
            String sisaltoNimi = inputField.getText();
            String sisaltoMaara= quantityField.getText();
            if (!sisaltoNimi.isEmpty() && !sisaltoMaara.isEmpty()) {
                int maara = Integer.parseInt(sisaltoMaara);

                Sisalto newSisalto = new Sisalto(sisaltoNimi, maara);
                matkalaukku.addSisalto(newSisalto);
                listView.getItems().add(newSisalto);
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
                    Sisalto updatedItem = new Sisalto(updatedNimi, updatedMaara);
                    matkalaukku.updateSisalto(selectedSisalto, updatedItem);
                    listView.getItems().set(listView.getSelectionModel().getSelectedIndex(), updatedItem); // Update
                    // ListView
                    inputField.clear();
                    quantityField.clear(); }
            }
        });

        VBox vbox=new VBox(10, inputField, quantityField, LisaaButton, PaivitaButton, PoistaButton, listView);

        //StackPane stackPane=new StackPane();
        Scene scene = new Scene(vbox, 600, 400);
        stage.setTitle("Muistilista");
        stage.setScene(scene);
        stage.show();
    }




    public static void main(String[] args){
        launch(args);
    }

}
