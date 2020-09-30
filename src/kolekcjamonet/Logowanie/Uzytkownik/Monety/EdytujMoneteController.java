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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
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
public class EdytujMoneteController implements Initializable {

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
    private JFXButton zapiszMoneteBtn;
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
        
        try {
            Connection con = Polaczenie.Connect();
            ResultSet rs = con.createStatement().executeQuery("SELECT nazwa,pochodzenie,rok,opis,image FROM Moneta WHERE id_monety='"+MonetyController.idMonety+"'");
            
            
            sciezka = rs.getString(5);
            fNazwa.setText(rs.getString(1));
            fPochodzenie.setText(rs.getString(2));
            fRok.setText(rs.getString(3));
            fOpis.setText(rs.getString(4));
            
            
            
            rs.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(EdytujMoneteController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }    

    @FXML
    private void wybierzZdjecie(ActionEvent event) {
                filechooser = new FileChooser();
        filechooser.setTitle("Open File");
        filechooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        file = filechooser.showOpenDialog(new Stage());
        sciezka = file.getAbsolutePath();
        File f1 = new File(sciezka);
        Image i = new Image(f1.toURI().toString());
        //IV.setImage(i); //imageview
    }

    @FXML
    private void zapiszMonete(ActionEvent event) throws SQLException, IOException {
         
        
        if (!sciezka.isEmpty() && Sprawdzanie.czyLitery(fNazwa.getText()) && Sprawdzanie.czyLitery(fPochodzenie.getText()) && Sprawdzanie.czyLiczby(fRok.getText()) && !fOpis.getText().isEmpty()){
            Connection con = Polaczenie.Connect();
            con.createStatement().executeUpdate("UPDATE Moneta SET nazwa='"+fNazwa.getText()+"',pochodzenie='"+fPochodzenie.getText()+"',rok='"+fRok.getText()+"',opis='"+fOpis.getText()+"',image='"+sciezka+"' WHERE id_monety='"+MonetyController.idKolekcji+"'");
            con.close();
            
                        TrayNotification tray = new TrayNotification();
            tray.setTitle("Sukces!");
            tray.setMessage("Edycja monety powiodła się.");
            tray.setNotificationType(NotificationType.SUCCESS);
            tray.showAndDismiss(Duration.millis(1500));
        
        
        
        
        AnchorPane dodaj = FXMLLoader.load(getClass().getResource("Monety.fxml"));
        AnchorPane home_parent2 = (AnchorPane) zapiszMoneteBtn.getParent().getParent().getParent();
        home_parent2.getChildren().clear();
        home_parent2.getChildren().add(dodaj);
            
        } else {
            TrayNotification tray = new TrayNotification();
            tray.setTitle("Błąd!");
            tray.setMessage("Zapisywanie monety nie powiodło się!");
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
