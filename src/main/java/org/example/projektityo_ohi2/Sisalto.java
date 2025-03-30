package org.example.projektityo_ohi2;
import java.io.Serializable;


public class Sisalto implements Serializable {
    private String nimi;
    private skanneri.Tyyppi vaatetyyppi;
    private int maara;

    public Sisalto(String nimi, int maara) {
        this(nimi, maara, skanneri.Tyyppi.Muu);
    }
    public Sisalto(String nimi, int maara, skanneri.Tyyppi vaatetyyppi) {
        this.nimi = nimi;
        this.maara = maara;
        this.vaatetyyppi = vaatetyyppi;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

   public skanneri.Tyyppi getVaatetyyppi() {
      return vaatetyyppi;
   }

    public void setVaatetyyppi(skanneri.Tyyppi vaatetyyppi) {
        this.vaatetyyppi = vaatetyyppi;
    }


    public int getMaara() {
        return maara;
    }

    public void setMaara(int maara) {
        this.maara = maara;
    }

    @Override
    public String toString() {
        return vaatetyyppi + " " + nimi + " x" + maara;
    }
}
