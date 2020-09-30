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
public class MonetaDetails {
    
    private final IntegerProperty id_monety;
    private final StringProperty nazwa;
    private final StringProperty pochodzenie;
    private final StringProperty rok;
    private final StringProperty opis;
    private final StringProperty image;
    
    public MonetaDetails(int id_monety,String nazwa,String pochodzenie, String rok, String opis, String image){
        this.id_monety = new SimpleIntegerProperty(id_monety);
        this.nazwa = new SimpleStringProperty(nazwa);
        this.pochodzenie = new SimpleStringProperty(pochodzenie);
        this.rok = new SimpleStringProperty(rok);
        this.opis = new SimpleStringProperty(opis);
        this.image = new SimpleStringProperty(image);
    }
    
    public int getId_monety(){
        return id_monety.get();
    }
    public String getNazwa(){
        return nazwa.get();
    }
    public String getPochodzenie(){
        return pochodzenie.get();
    }
    public String getRok(){
        return rok.get();
    }
    public String getOpis(){
        return opis.get();
    }
    public String getImage(){
        return image.get();
    }
    
    public IntegerProperty id_monetyProperty(){
        return id_monety;
    }
    public StringProperty nazwaProperty(){
        return nazwa;
    }
    public StringProperty pochodzenieProperty(){
        return pochodzenie;
    }
    public StringProperty rokProperty(){
        return rok;
    }
    public StringProperty opisProperty(){
        return opis;
    }
    public StringProperty imageProperty(){
        return image;
    }
    
    
    
    
    
    
}
