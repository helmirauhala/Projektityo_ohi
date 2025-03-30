package org.example.projektityo_ohi2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.List;

/**
 * Seuraavaksi luon sen ikkunan java fx avulla mikä tulee esille.
 * Ensin teen pääikkunan ja sitten
 * Tämä on aloutus ikkuna ohjelmalle!!
 */
public class Ikkuna extends Application {
    private IDataPersistenceService<Sisalto> dataPersistence;
    Stage stage;
    ListView<Sisalto> listView; //tämä on soitä varten että sisälöt asiat näytetään käyttäjälle
    BorderPane root = new BorderPane();
//Lutiin uusi Border joka tarjoaa käyttöliittymä asettelun
    private  Matkalaukku matkalaukku; //ku
    @Override
    /*
    Seuraaksi luodaan teksti kentät johon tiedot syötetään nimi ja määrä tiedot
     */
    public void start(Stage stage) throws Exception{
        matkalaukku = new Matkalaukku();
        dataPersistence = new DataPersistenceService(); //Huolehtii datan säilyttämisestä
        TextField syottokentta = new TextField();
        syottokentta.setPromptText("NIMI");
        TextField maarakentta = new TextField();
        maarakentta.setPromptText("MÄÄRÄ");
        ComboBox itemTypeComboBox = new ComboBox();

        /*
        Seuraavaksi teen buttonit eli napit jotka ovat koodattu lisäämää,
        päivitämään ja poistamaan asioilta listalta
         */

        itemTypeComboBox.getItems().addAll(matkalaukku.tyyppiOptions);
        itemTypeComboBox.getSelectionModel().selectLast();
        Button LisaaButton = new Button("Lisää tavara");
        Button PaivitaButton = new Button("Päivitä tavara");
        Button PoistaButton = new Button("Poista tavara ");

        //Nyt määritellään buttonien koko

        listView = new ListView<>();
        listView.setPrefHeight(200); //määritetllään kentänkoko


        //Nyt annetaan tehtävä miten lisaabutton toimii.

        LisaaButton.setOnAction(e -> {
            String sisaltoNimi = syottokentta.getText(); //Ottaa syötetyn tekstin kohdasta nimi
            String sisaltoMaara= maarakentta.getText(); // Otta annetun arvon kohdasta Määrä
            /**
             * https://docs.oracle.com/en/java/javase/17/language/local-variable-type-inference.html
             * Selitää miksi päätin käyttää var tässä kohtaa eli se pystyy itse päätellä minkä tyypiset ne on
             * esim int vai string.
             */
            var sisaltoTyyppi= (skanneri.Tyyppi)itemTypeComboBox.getSelectionModel().getSelectedItem();

            //Tarkistetaan että kumpikaan knetistä ei ole tyhjä ja arvo on yli 0 koska negatiivinne arvo ei ole järkevä tässä ohjelmassa
            if (!sisaltoNimi.isEmpty() && !sisaltoMaara.isEmpty() && Integer.parseInt(sisaltoMaara) > 0) {
                int maara = Integer.parseInt(sisaltoMaara); //nyt pass sen int tyypin eli ei ota vastaan aakosia

                /*
                Luodaan uusi sisältobjekti käyttäen syötettyjä tietoja.
                 */
                Sisalto uusiSisalto = new Sisalto(sisaltoNimi, maara, sisaltoTyyppi);
                //Laiteaan uusi sisaltoobjekti matkalaukkuun.
                matkalaukku.addSisalto(uusiSisalto);
                listView.getItems().add(uusiSisalto);
                // kirjoita data tiedostoon/file ja säästää ne sinne
                saveSisaltosToFile();
                //Tyhjentää sen jälkeen nimi ja määrä kentän
                syottokentta.clear();
                maarakentta.clear();

            }
        });

        /*
        Päivtetään syöte/input ja määrä kentätä ja combobox valinta mätsäämään valittuja kohteita listVIewssä

        * */
        listView.getSelectionModel().selectedItemProperty().addListener((obs, oldSisalto, uusiSisaltoo) -> {
            if (uusiSisaltoo != null){
                syottokentta.setText(uusiSisaltoo.getNimi());
                maarakentta.setText(String.valueOf(uusiSisaltoo.getMaara()));
                itemTypeComboBox.setValue(uusiSisaltoo.getVaatetyyppi());
            }
        });

        /*
        POISTA button poistaa sisällön valitun sisältö listView to ja
        selitety aikaiemmin taekmmin miten ylikitjoittaa koko tiedoston aina
         */
        PoistaButton.setOnAction(e ->{
            Sisalto selectedSisalto = listView.getSelectionModel().getSelectedItem();
            if (selectedSisalto != null){
                matkalaukku.removeSisalto(selectedSisalto);
                listView.getItems().remove(selectedSisalto);
                // kirjoittaa tämän hetkisen datantiedostoon
                saveSisaltosToFile();
                syottokentta.clear();
                maarakentta.clear();
            }
        });

        /*
        Seuraavaksi päivitä nappi joka päivitää jo olemassa olevia tietoja.
         */

        PaivitaButton.setOnAction(e -> {
            Sisalto selectedSisalto = listView.getSelectionModel().getSelectedItem();
            if (selectedSisalto != null) {
                String paivitaNimi = syottokentta.getText(); //Ottaa päivitetyn nimen
                String paivitaMaara = maarakentta.getText(); //Ottaa päivitetyn määrn
                /*
                Ensin
                 if (!sisaltoNimi.isEmpty() && !sisaltoMaara.isEmpty()
                 */
                if (!paivitaNimi.isEmpty() && !paivitaMaara.isEmpty()&& Integer.parseInt(paivitaMaara) > 0) {
                    int updatedMaara = Integer.parseInt(paivitaMaara);
                    var updatedSisaltoTyyppi= (skanneri.Tyyppi)itemTypeComboBox.getSelectionModel().getSelectedItem();
                    Sisalto updatedItem = new Sisalto(paivitaNimi, updatedMaara,updatedSisaltoTyyppi);
                    matkalaukku.updateSisalto(selectedSisalto, updatedItem);

                    listView.getItems().set(listView.getSelectionModel().getSelectedIndex(), updatedItem); // Update
                    // kirjoitaa ne nyt data tiedostoon
                    saveSisaltosToFile();
                    syottokentta.clear();
                    maarakentta.clear(); }
            }
        });

        //ladataan sisältö tiedostosta ja  seuraavassa koodirivissä määrätään missä järjestyksessä ne esiintyy
        loadSisaltosFromFile();
        VBox vbox=new VBox(10, itemTypeComboBox, syottokentta, maarakentta, LisaaButton, PaivitaButton, PoistaButton, listView);

        Scene scene = new Scene(vbox, 600, 400);
        stage.setTitle("Muistilista");
        stage.setScene(scene);
        stage.show();
        //Tässä tein sen uloimmaisen osuuden java fx se ikkuna.
    }


private void saveSisaltosToFile(){
    dataPersistence.saastaaData((List<Sisalto>) matkalaukku.getSisaltos());

    showAlert("Huomio", "Data säätetty onnistuneesti!", Alert.AlertType.INFORMATION);
}

    /**
     *
     */
    private void loadSisaltosFromFile() {
        try {
            List<Sisalto> sisaltos = dataPersistence.lataaData();
            if (sisaltos.isEmpty()) {
                showAlert("Huomio", "No data found in the file.", Alert.AlertType.INFORMATION);
            } else {
                matkalaukku.setSisaltos(sisaltos);
                listView.getItems().addAll(matkalaukku.getSisaltos());
                listView.getSelectionModel().selectFirst();
                showAlert("Huomio", "Data ladattu onnistuneesti!", Alert.AlertType.INFORMATION);
            }
        } catch (RuntimeException e) {
            // Huomaa kaikki  RuntimeException jotka loadData() on aiheuttanut
            showAlert("Virhe", "Virhe dataa ladatessa: " + e.getMessage(), Alert.AlertType.ERROR);
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
