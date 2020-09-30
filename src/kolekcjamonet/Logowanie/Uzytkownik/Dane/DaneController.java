/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kolekcjamonet.Logowanie.Uzytkownik.Dane;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import kolekcjamonet.Logowanie.LoginViewController;
import kolekcjamonet.Polaczenie.Polaczenie;
import kolekcjamonet.Sprawdzanie.Sprawdzanie;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author BlackHawk
 */
public class DaneController implements Initializable {

    @FXML
    private JFXTextField fLogin;
    @FXML
    private JFXPasswordField fHaslo;
    @FXML
    private JFXPasswordField fHaslo2;
    @FXML
    private JFXTextField fImie;
    @FXML
    private JFXTextField fNazwisko;
    @FXML
    private JFXTextField fEmail;
    @FXML
    private JFXTextField fTelefon;
    @FXML
    private JFXButton rejestrujBtn;
    @FXML
    private Label bladLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //fLogin.setDisable(true);
        fLogin.setEditable(false);
      
         try {
            Connection con = Polaczenie.Connect();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Uzytkownik WHERE id_uzytkownika=" + LoginViewController.r);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                fLogin.setText(rs.getString("login"));
                fHaslo.setText(rs.getString("haslo"));
                fHaslo2.setText(rs.getString("haslo"));
                fImie.setText(rs.getString("imie"));
                fNazwisko.setText(rs.getString("nazwisko"));
                fEmail.setText(rs.getString("email"));
                fTelefon.setText(rs.getString("nr_tel"));
         
                
            }
            
            ps.close();
            rs.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
    }


    private void pokazKomunikat(String text){
        TrayNotification tray = new TrayNotification();
        tray.setTitle("Błąd!");
        tray.setMessage(text);
        tray.setNotificationType(NotificationType.ERROR);
        tray.showAndDismiss(Duration.millis(1500));
    }
    

    @FXML
    private void zapiszDane(ActionEvent event) throws SQLException {
        
        if  (Sprawdzanie.czyLitery(fImie.getText()) && Sprawdzanie.czyLitery(fNazwisko.getText()) && Sprawdzanie.poprawnyEmail(fEmail.getText()) && Sprawdzanie.telefonPoprawny(fTelefon.getText())) {
            if (fHaslo.getText().equals(fHaslo2.getText())) {
                //wykonaj update danych
                
                
                Connection con = Polaczenie.Connect();
                
                con.createStatement().executeUpdate("UPDATE Uzytkownik SET imie='"+fImie.getText()+"', nazwisko='"+fNazwisko.getText()+"',"
                        + "nr_tel='"+fTelefon.getText()+"',haslo='"+fHaslo.getText()+"' WHERE id_uzytkownika='"+LoginViewController.r+"'");
                
                con.close();
                
                
                
                
                
                TrayNotification tray = new TrayNotification();
                tray.setTitle("Sukces!");
                tray.setMessage("Aktualizacja danych przebiegła pomyślnie.");
                tray.setNotificationType(NotificationType.SUCCESS);
                tray.showAndDismiss(Duration.millis(1500));
                
                bladLabel.setVisible(false);
                
            } else {
                bladLabel.setVisible(true);
                pokazKomunikat("Aktualizacja danych zakończyła się fiaskiem!");
            }
        } else {
            
            pokazKomunikat("Dane wejściowe zostały uzupełnione nieprawidłowo!");
            bladLabel.setVisible(false);
        }
        
    }
    
}
