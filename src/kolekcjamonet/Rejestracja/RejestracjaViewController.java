/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kolekcjamonet.Rejestracja;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import kolekcjamonet.KolekcjaMonet;
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
public class RejestracjaViewController implements Initializable {

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

        //statusCb.getSelectionModel().getSelectedItem().toString().toLowerCase();
    }

    @FXML
    private void rejestrujUzytkownika(ActionEvent event) throws SQLException, IOException {

        //sprawdzic unikalnosc loginu
        Connection con = Polaczenie.Connect();
        ResultSet rs = con.createStatement().executeQuery("SELECT login FROM uzytkownik");
        boolean rejestruj = true;
        while (rs.next()) {
            if (fLogin.getText().equals(rs.getString("login"))) {
                rejestruj = false;
            }
            // wyrzucić błąd o nieunikalnosci loginu
        }
        if (!fHaslo.getText().equals(fHaslo2.getText())) {
            //rejestruj = false;
        }
        rs.close();
        //prawidlowe dane wejsciowe sprawdzic
        
        if (Sprawdzanie.czyLiczby(fTelefon.getText()) && Sprawdzanie.poprawnyEmail(fEmail.getText()) && Sprawdzanie.czyLitery(fImie.getText()) && Sprawdzanie.czyLitery(fNazwisko.getText()) && Sprawdzanie.telefonPoprawny(fTelefon.getText())){
            rejestruj = true;
        } else {
            rejestruj = false;
        }
        
        

        if (rejestruj) {
            
             con.createStatement().executeUpdate("INSERT INTO Uzytkownik(id_uzytkownika,login,haslo,email,imie,nazwisko,nr_tel) VALUES "
                    + "(NULL,'"+fLogin.getText()+"','"+fHaslo.getText()+"','"+fEmail.getText()+"','"+fImie.getText()+"','"+fNazwisko.getText()+"','"+fTelefon.getText()
                    +"')");
            
            
                        
            con.close();

            AudioClip powiadomMnie = new AudioClip((KolekcjaMonet.class.getResource("Audio/Notify.wav")).toString());
            powiadomMnie.play();

            TrayNotification tray = new TrayNotification();
            tray.setTitle("Sukces!");
            tray.setMessage("Rejestracja przebiegła pomyślnie. \n Możesz się teraz zalogować.");
            tray.setNotificationType(NotificationType.SUCCESS);
            tray.showAndDismiss(Duration.millis(1500));

            //zmienić
            /*
            AnchorPane logowanie = FXMLLoader.load(SzkolaJezykowa.class.getResource("logowanie/LoginView.fxml"));
            AnchorPane home_parent2 = (AnchorPane) rejestrujBtn.getParent().getParent().getParent();
            
            home_parent2.getChildren().clear();
            home_parent2.getChildren().add(logowanie);*/
        }

        if (rejestruj == false) {
            //komunikat o błędzie
            AudioClip powiadomMnie = new AudioClip((KolekcjaMonet.class.getResource("Audio/Notify.wav")).toString());
            powiadomMnie.play();

            TrayNotification tray = new TrayNotification();
            tray.setTitle("Błąd!");
            tray.setMessage("Podczas rejestracji wystąpił błąd.");
            tray.setNotificationType(NotificationType.ERROR);
            tray.showAndDismiss(Duration.millis(1500));
            con.close();
        }

    }

}
