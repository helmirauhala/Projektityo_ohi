package org.example.projektityo_ohi2;

import java.util.ArrayList;
import java.util.List;

public class Matkalaukku {
    private List<Sisalto> sisaltos;

    public Matkalaukku() {
        sisaltos = new ArrayList<Sisalto>();
    }

    public List<Sisalto> getSisaltos() {
        return sisaltos;
    }

    public void addSisalto(Sisalto sisalto) {
        sisaltos.add(sisalto);
    }

    public void removeSisalto(Sisalto sisalto) {
        sisaltos.remove(sisalto);
    }
    public void updateSisalto(Sisalto oldSisalto, Sisalto newSisalto){
        int index= sisaltos.indexOf(oldSisalto);
        if (index !=-1){
            sisaltos.set(index,newSisalto);
        }
    }

}
