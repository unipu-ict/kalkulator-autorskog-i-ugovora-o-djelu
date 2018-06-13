package hr.unipu.android.bncalculator;

import java.util.ArrayList;

/**
 * Klasa FunkcijeObracun sadrži sve metode koje su potrebne za obračun različitih vrsta ugovora.
 * @author Petra Buršić i Mateo Bošnjak
 * @version 2.0
 * * */

public class FunkcijeObracuna {

    /**
     * Double varijabla predstavlja bruto
     */
    static Double bruto;

    /**
     * Double varijabla predstavlja neto
     */
    static Double neto;

    /**
     * ArrayList varijabla predstavlja stavke u ispisu
     */
    static ArrayList<Stavka> list;

    /**
     * Getter za bruto varijablu
     */
    public static Double getBruto(){
        return bruto;
    }

    /**
     * Setter za bruto varijablu
     */
    public static void setBruto(Double Bruto){
        bruto = Bruto;
    }

    /**
     * Getter za neto varijablu
     */
    public static Double getNeto(){
        return neto;
    }

    /**
     * Getter za listu stavki
     */
    public static ArrayList<Stavka> getList(){
        return list;
    }

    /**
     * Metoda izvodi obračun bruto ugovora o djelu.
     * @param neto neto iznos
     * @param prirezPostotak postotak prireza
     * @return bruto obračun
     */
    public static void PreracunBrutoUgovorDjelu(Double neto, Double prirezPostotak){
        Double prirezIzracun = prirezPostotak * 0.216;

        bruto = neto / (0.684 - prirezIzracun);
    }

    /**
     * Bruto za autorski ugovor.
     * @param neto neto iznos
     * @param prirezPostotak postotak prireza
     * @return bruto iznos
     */
    public static void PreracunBrutoAutorski(Double neto, Double prirezPostotak){
        Double prirezIzracun = prirezPostotak * 0.1512;

        bruto = neto / (0.7788 - prirezIzracun);
    }

    /**
     * Bruto za autorski ugovor umjetnika
     * @param neto neto iznos
     * @param prirezPostotak postotak prireza
     * @return bruto iznos
     */
    public static void PreracunBrutoAutorskiUmjetnika(Double neto, Double prirezPostotak){
        Double prirezIzracun = prirezPostotak * 0.0972;
        bruto = neto / (0.8578 - prirezIzracun);
    }

    /**
     * Metoda vraća ispis ugovora o djelu.
     * @param prirezPostotak postotak prireza
     * @return list lista za ispis
     */
    public static void ispisUgovorDjelu(Double prirezPostotak, Boolean osiguranik, Boolean obveznik){
        Double poreznaOsnovica, porez, prirez;

        //PreracunBrutoUgovorDjelu(iznos, prirezPostotak);

        poreznaOsnovica = bruto * 0.9;
        porez = poreznaOsnovica * 0.24;
        prirez = porez * prirezPostotak;
        neto = poreznaOsnovica - (porez + prirez);

        list = new ArrayList<>();

        list.add(new Stavka("Bruto: ", bruto));
        if(osiguranik){
            list.add(new Stavka("I. stup mirovinkog osiguranja: ", bruto * 0.075));
            list.add(new Stavka("II. stup mirovinkog osiguranja: " ,bruto * 0.025));
        } else {
            list.add(new Stavka("I. stup mirovinkog osiguranja: ", bruto * 0.01));
            list.add(new Stavka("II. stup mirovinkog osiguranja: " ,bruto * 0));
        }
        list.add(new Stavka("Ukupni doprinosi iz bruta: ",bruto * 0.1));
        list.add(new Stavka("Porezna osnovica: ", poreznaOsnovica));
        if(obveznik)
            list.add(new Stavka("PDV na primitak: ",+  bruto * 0.25));
        list.add(new Stavka("Porez:", porez));
        list.add(new Stavka("Prirez:", prirez));
        list.add(new Stavka("Ukupni porez:",porez + prirez));
        list.add(new Stavka("Doprinos za zdravstveno osiguranje: ",bruto * 0.075));
        list.add(new Stavka("Ukupni trošak: ", (bruto + (bruto * 0.075))));
        list.add(new Stavka("Neto: ", neto));

        return;
    }

    /**
     * Metoda vraća ispis autorskog ugovora.
     * @param prirezPostotak postotak prireza
     * @return list lista za ispis
     */
    public static void ispisAutorski(Double prirezPostotak, Boolean osiguranik, Boolean obveznik){
        Double osnovica, poreznaOsnovica, porez, prirez;

        //PreracunBrutoAutorski(iznos, prirezPostotak);

        osnovica = bruto * 0.7;
        poreznaOsnovica = osnovica - osnovica * 0.1;
        porez = poreznaOsnovica * 0.24;
        prirez = porez * prirezPostotak;
        neto = bruto * 0.7788 - prirez;


        list = new ArrayList<>();

        list.add(new Stavka("Bruto: ", bruto));
        list.add(new Stavka("Izdatak: ", bruto * 0.3));
        list.add(new Stavka("Osnovica za obračun doprinosa: ", bruto * 0.7));
        if(osiguranik){
            list.add(new Stavka("I. stup mirovinkog osiguranja: ", bruto * 0.075));
            list.add(new Stavka("II. stup mirovinkog osiguranja: " ,bruto * 0.025));
        } else {
            list.add(new Stavka("I. stup mirovinkog osiguranja: ", bruto * 0.01));
            list.add(new Stavka("II. stup mirovinkog osiguranja: " ,bruto * 0));
        }
        list.add(new Stavka("Ukupni doprinosi iz bruta: ", osnovica * 0.1));
        list.add(new Stavka("Porezna osnovica: ", poreznaOsnovica));
        if(obveznik)
            list.add(new Stavka("PDV na primitak: ", bruto * 0.25));
        list.add(new Stavka("Porez:", porez));
        list.add(new Stavka("Prirez:", prirez));
        list.add(new Stavka("Ukupni porez:",porez + prirez));
        list.add(new Stavka("Doprinos za zdravstveno osiguranje: ",bruto * 0.075));
        list.add(new Stavka("Ukupni trošak: ", (bruto + (bruto * 0.075))));
        list.add(new Stavka("Neto: ", neto));

        return;
    }

    /**
     * Metoda vraća ispis za autorski igovor umjetnika.
     * @param prirezPostotak postotak prireza
     * @return list lista za ispis
     */
    public static void ispisAutorskiUmjetnika(Double prirezPostotak, Boolean osiguranik, Boolean obveznik){
        Double osnovica, poreznaOsnovica, porez, prirez;

        //PreracunBrutoAutorskiUmjetnika(iznos, prirezPostotak);

        osnovica = bruto * 0.45;
        poreznaOsnovica = osnovica - osnovica * 0.1;
        porez = poreznaOsnovica * 0.24;
        prirez = porez * prirezPostotak;
        neto = bruto * 0.7788;


        list = new ArrayList<>();

        list.add(new Stavka("Bruto: ", bruto));
        list.add(new Stavka("Izdatak: ", bruto * 0.3));
        list.add(new Stavka("Izdatak: ", bruto * 0.25));
        list.add(new Stavka("Osnovica za obračun doprinosa: ", bruto * 0.45));
        if(osiguranik){
            list.add(new Stavka("I. stup mirovinkog osiguranja: ", bruto * 0.075));
            list.add(new Stavka("II. stup mirovinkog osiguranja: " ,bruto * 0.025));
        } else {
            list.add(new Stavka("I. stup mirovinkog osiguranja: ", bruto * 0.01));
            list.add(new Stavka("II. stup mirovinkog osiguranja: " ,bruto * 0));
        }
        list.add(new Stavka("Ukupni doprinosi iz bruta: ", osnovica * 0.1));
        list.add(new Stavka("Porezna osnovica: ", poreznaOsnovica));
        if(obveznik)
            list.add(new Stavka("PDV na primitak: ", bruto * 0.25));
        list.add(new Stavka("Porez:", porez));
        list.add(new Stavka("Prirez:", prirez));
        list.add(new Stavka("Ukupni porez:",porez + prirez));
        list.add(new Stavka("Doprinos za zdravstveno osiguranje: ",bruto * 0.075));
        list.add(new Stavka("Ukupni trošak: ", (bruto + (bruto * 0.075))));
        list.add(new Stavka("Neto: ", neto));

        return;
    }

}
