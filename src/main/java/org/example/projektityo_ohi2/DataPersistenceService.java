package org.example.projektityo_ohi2;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataPersistenceService implements IDataPersistenceService<Sisalto> {

    private static final String FILE_NAME= "matkalaukku.dat";
    @Override
    public void saveData(List<Sisalto> data) {
        try (ObjectOutputStream outputStream= new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            outputStream.writeObject(data);
        }
         catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Sisalto> loadData() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            // File doesn't exist, return empty list
            return new ArrayList<>();
        }

        List<Sisalto> sisaltoList = new ArrayList<>();
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file))) {
            sisaltoList = (List<Sisalto>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // Log or handle the exception, if necessary, but no need to throw it here
            throw new RuntimeException("Error loading data from file", e);
        }

        return sisaltoList;
    }
}
