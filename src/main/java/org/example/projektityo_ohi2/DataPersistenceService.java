package org.example.projektityo_ohi2;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataPersistenceService implements IDataPersistenceService<Sisalto> {

    private static final String FILE_NAME= "matkalaukku.dat";
    /*
    Tämä ei ole toimivin tapa tehdä tässä sillä ylikirjoitamme kaiken ja sen jälkeen näyttä,
    että olsin muutanut vain haluttua asiaa tideostossa.
    Ei ole toivoin soissa tiedostoissa mutta tässä pinessä koossa se toimii vielä.
     */
    @Override
    public void saastaaData(List<Sisalto> data) {
        try (ObjectOutputStream outputStream= new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            outputStream.writeObject(data);
        }
         catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Sisalto> lataaData() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            // Jos Tiedostoa ei olemassa tai ei löydy niin palauta tyhjä lista.
            return new ArrayList<>();
        }

        List<Sisalto> sisaltoList = new ArrayList<>();
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file))) {
            sisaltoList = (List<Sisalto>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // Tänne vaan poikeuksessa tilanteeesa ei normaalisti ei tule tämä ei tule vastaan poikkeusia varten siis
            throw new RuntimeException("Virhe ladataessa dataa tiedostosta", e);
        }

        return sisaltoList;
    }
}
