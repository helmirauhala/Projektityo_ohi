package org.example.projektityo_ohi2;

import java.util.List;

public interface IDataPersistenceService<T> {
    void saveData(List<T> data);
    List<T> loadData();
}