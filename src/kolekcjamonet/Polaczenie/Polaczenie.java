/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kolekcjamonet.Polaczenie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author BlackHawk
 */
public class Polaczenie {

    private static final String DRIVER = "org.sqlite.JDBC";

    
    public static Connection Connect() {
        try {
            String url = "jdbc:sqlite:Monety";
            String user = "";
            String password = "";

            Class.forName(DRIVER);
            Connection conn = DriverManager.getConnection(url); 
            return conn;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Polaczenie.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }
}
