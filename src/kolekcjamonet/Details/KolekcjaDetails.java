/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kolekcjamonet.Details;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author BlackHawk
 */
public class KolekcjaDetails {

    private final IntegerProperty id_kolekcji;
    private final StringProperty nazwa;
    private final StringProperty opis;

    public KolekcjaDetails(int id_kolekcji, String nazwa, String opis) {
        this.id_kolekcji = new SimpleIntegerProperty(id_kolekcji);
        this.nazwa = new SimpleStringProperty(nazwa);
        this.opis = new SimpleStringProperty(opis);
    }
    
    public int getId_kolekcji(){
        return id_kolekcji.get();
    }
    
    public String getNazwa(){
        return nazwa.get();
    }
    
    public String getOpis(){
        return opis.get();
    }

    public void setNazwa(String value){
        nazwa.set(value);
    }
    
    public void setOpis(String value){
        opis.set(value);
    }
    
    public IntegerProperty id_kolekcjiProperty(){
        return id_kolekcji;
    }
    
    public StringProperty opisProperty(){
        return opis;
    }
    
    public StringProperty nazwaProperty(){
        return nazwa;
    }
    
}
