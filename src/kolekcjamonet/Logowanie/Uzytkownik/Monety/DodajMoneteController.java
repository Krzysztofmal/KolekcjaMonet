/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kolekcjamonet.Logowanie.Uzytkownik.Monety;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.Duration;
import kolekcjamonet.Polaczenie.Polaczenie;
import kolekcjamonet.Sprawdzanie.Sprawdzanie;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author BlackHawk
 */
public class DodajMoneteController implements Initializable {

    @FXML
    private JFXTextField fNazwa;
    @FXML
    private JFXTextField fPochodzenie;
    @FXML
    private JFXTextField fRok;
    @FXML
    private JFXTextArea fOpis;
    @FXML
    private JFXButton wybierzZdjecieBtn;
    @FXML
    private JFXButton dodajMoneteBtn;
    @FXML
    private JFXButton anulujBtn;
    FileChooser filechooser;
    File file;
    String sciezka;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void wybierzZdjecie(ActionEvent event) {        
        filechooser = new FileChooser();
        filechooser.setTitle("Open File");
        filechooser.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        file = filechooser.showOpenDialog(new Stage());
        sciezka = file.getAbsolutePath();
        File f1 = new File(sciezka);
        Image i = new Image(f1.toURI().toString());
        //IV.setImage(i); //imageview
    }

    @FXML
    private void dodajMonete(ActionEvent event) throws SQLException, IOException {
        
        if (!sciezka.isEmpty() && !fNazwa.getText().isEmpty() && Sprawdzanie.czyLitery(fPochodzenie.getText()) && Sprawdzanie.czyLiczby(fRok.getText()) && !fOpis.getText().isEmpty()){
            Connection con = Polaczenie.Connect();
            con.createStatement().executeUpdate("INSERT INTO Moneta(id_monety,nazwa,pochodzenie,rok,opis,id_kolekcji,image) VALUES(NULL,'"+fNazwa.getText()+"','"+fPochodzenie.getText()+"','"+fRok.getText()+"','"+fOpis.getText()+"','"+MonetyController.idKolekcji+"','"+sciezka+"')");
            con.close();
            
                        TrayNotification tray = new TrayNotification();
            tray.setTitle("Sukces!");
            tray.setMessage("Dodawanie monety powiodło się.");
            tray.setNotificationType(NotificationType.SUCCESS);
            tray.showAndDismiss(Duration.millis(1500));
        
        
        
        
        AnchorPane dodaj = FXMLLoader.load(getClass().getResource("Monety.fxml"));
        AnchorPane home_parent2 = (AnchorPane) dodajMoneteBtn.getParent().getParent().getParent();
        home_parent2.getChildren().clear();
        home_parent2.getChildren().add(dodaj);
            
        } else {
            TrayNotification tray = new TrayNotification();
            tray.setTitle("Błąd!");
            tray.setMessage("Dodawanie monety nie powiodło się!");
            tray.setNotificationType(NotificationType.ERROR);
            tray.showAndDismiss(Duration.millis(1500));
        }
        
        
    }

    @FXML
    private void anuluj(ActionEvent event) throws IOException {
        AnchorPane anuluj = FXMLLoader.load(getClass().getResource("Monety.fxml"));
        AnchorPane home_parent2 = (AnchorPane) anulujBtn.getParent().getParent().getParent();
        home_parent2.getChildren().clear();
        home_parent2.getChildren().add(anuluj);
    }
    
}
