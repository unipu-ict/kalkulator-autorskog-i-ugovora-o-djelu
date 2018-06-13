package hr.unipu.android.bncalculator;

/**
 * Klasa Stavka predstavlja stavku za ispis.
 * @author Petra Buršić i Mateo Bošnjak
 * @version 2.0
 */

public class Stavka {
    /**
     * String varijabla predstavlja naziv stavke
     */
    String naziv;

    /**
     * Double varijabla predstavlja iznos stavke
     */
    Double iznos;

    Stavka (String s, Double d){
        naziv = s;
        iznos = d;
    }
}