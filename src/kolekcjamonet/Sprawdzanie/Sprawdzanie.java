/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kolekcjamonet.Sprawdzanie;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author BlackHawk
 */
public class Sprawdzanie {
    
    
    public static Boolean telefonPoprawny(String text) {
        if (text.length() == 9 && text.matches("\\d+"))
            return true;
        else return false;
    }
    
    public static Boolean peselPoprawny(String text) {
        if (text.length() == 11 && text.matches("\\d+"))
            return true;
        else return false;
    }
 
    public static Boolean poprawnyEmail(String email){
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$"; 
        Pattern pattern = Pattern.compile(regex); 
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    
    public static Boolean czyLiczby(String text) {
        return text.matches("\\d+");
    }
    
    public static Boolean czyLitery(String text){
        return text.matches("[A-Za-zżźćńółęąśŻŹĆĄŚĘŁÓŃ]+.*[A-Za-zżźćńółęąśŻŹĆĄŚĘŁÓŃ]");
    }
    
    
}
