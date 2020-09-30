/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kolekcjamonet.Logowanie.Uzytkownik.Kolekcje;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import kolekcjamonet.Logowanie.LoginViewController;
import kolekcjamonet.Polaczenie.Polaczenie;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author BlackHawk
 */
public class DodajKolekcjeController implements Initializable {

    @FXML
    private JFXTextField fNazwa;
    @FXML
    private JFXTextArea fOpis;
    @FXML
    private JFXButton dodajKolekcjeBtn;
    @FXML
    private JFXButton anulujBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
    }    

    @FXML
    private void dodajKolekcje(ActionEvent event) throws SQLException, IOException {
        Connection con = Polaczenie.Connect();
        if (!fNazwa.getText().isEmpty() && !fOpis.getText().isEmpty()){
            con.createStatement().executeUpdate("INSERT INTO Kolekcja(id_kolekcji,nazwa,opis,id_uzytkownika) VALUES (NULL,'"+fNazwa.getText()+"','"+fOpis.getText()+"','"+LoginViewController.r+"')");
        }
        con.close();
        
        
            TrayNotification tray = new TrayNotification();
            tray.setTitle("Sukces!");
            tray.setMessage("Dodawanie kolekcji powiodło się.");
            tray.setNotificationType(NotificationType.SUCCESS);
            tray.showAndDismiss(Duration.millis(1500));
        
        
        
        
        AnchorPane anuluj = FXMLLoader.load(getClass().getResource("Kolekcje.fxml"));
        AnchorPane home_parent2 = (AnchorPane) anulujBtn.getParent().getParent().getParent();
        home_parent2.getChildren().clear();
        home_parent2.getChildren().add(anuluj);
        
    }

    @FXML
    private void anuluj(ActionEvent event) throws IOException {
        AnchorPane anuluj = FXMLLoader.load(getClass().getResource("Kolekcje.fxml"));
        AnchorPane home_parent2 = (AnchorPane) anulujBtn.getParent().getParent().getParent();
        home_parent2.getChildren().clear();
        home_parent2.getChildren().add(anuluj);
    }
    
}
