package org.example.projektityo_ohi2;

import java.util.List;
/*
Tämä on pyydetty Interface eli käyttöliittymä/rajapinta
Ja määritelee että metodin pitää pystyä säästämään ja lataamaan dataa.
 */
public interface IDataPersistenceService<T> {
    void saastaaData(List<T> data);
    List<T> lataaData();
}