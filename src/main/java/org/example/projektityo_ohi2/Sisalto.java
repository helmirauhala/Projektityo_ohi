package org.example.projektityo_ohi2;

import javafx.scene.paint.Color;

public class Sisalto {
    //private int ID;
    private String nimi;
    //private skanneri.Tyyppi vaatetyyppi;
   // private Color vari;
    private int maara;

    public Sisalto(String nimi, int maara) {
        this.nimi = nimi;
        this.maara = maara;
    }

//    public int getID() {
//        return ID;
//    }
//
//    public void setID(int ID) {
//        this.ID = ID;
//    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

//    public skanneri.Tyyppi getVaatetyyppi() {
//        return vaatetyyppi;
//    }
//
//    public void setVaatetyyppi(skanneri.Tyyppi vaatetyyppi) {
//        this.vaatetyyppi = vaatetyyppi;
//    }
//
//    public Color getVari() {
//        return vari;
//    }
//
//    public void setVari(Color vari) {
//        this.vari = vari;
//    }

    public int getMaara() {
        return maara;
    }

    public void setMaara(int maara) {
        this.maara = maara;
    }

}
