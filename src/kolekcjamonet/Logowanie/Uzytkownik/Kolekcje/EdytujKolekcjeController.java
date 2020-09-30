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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import kolekcjamonet.Polaczenie.Polaczenie;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author BlackHawk
 */
public class EdytujKolekcjeController implements Initializable {

    @FXML
    private JFXTextField fNazwa;
    @FXML
    private JFXTextArea fOpis;
    @FXML
    private JFXButton zapiszKolekcjeBtn;
    @FXML
    private JFXButton anulujBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        
        Connection con = Polaczenie.Connect();
        try {
            ResultSet rs = con.createStatement().executeQuery("SELECT nazwa,opis FROM Kolekcja WHERE id_kolekcji=" + KolekcjeController.idKolekcji + "");
            //System.out.println(rs.getString("nazwa"));
            while (rs.next()) {
                fNazwa.setText(rs.getString("nazwa"));
                fOpis.setText(rs.getString("opis"));
            }

            rs.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(EdytujKolekcjeController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void zapiszKolekcje(ActionEvent event) throws SQLException, IOException {
        Connection con = Polaczenie.Connect();
        if (!fOpis.getText().isEmpty() && !fNazwa.getText().isEmpty()) {
            con.createStatement().executeUpdate("UPDATE Kolekcja SET nazwa='" + fNazwa.getText() + "',opis='" + fOpis.getText() + "' WHERE id_kolekcji='" + KolekcjeController.idKolekcji + "'");
            TrayNotification tray = new TrayNotification();
            tray.setTitle("Sukces!");
            tray.setMessage("Aktualizacja danych przebiegła pomyślnie.");
            tray.setNotificationType(NotificationType.SUCCESS);
            tray.showAndDismiss(Duration.millis(1500));
            
        AnchorPane anuluj = FXMLLoader.load(getClass().getResource("Kolekcje.fxml"));
        AnchorPane home_parent2 = (AnchorPane) anulujBtn.getParent().getParent().getParent();
        home_parent2.getChildren().clear();
        home_parent2.getChildren().add(anuluj);
            
        }
        con.close();
    }

    @FXML
    private void anuluj(ActionEvent event) throws IOException {
        AnchorPane anuluj = FXMLLoader.load(getClass().getResource("Kolekcje.fxml"));
        AnchorPane home_parent2 = (AnchorPane) anulujBtn.getParent().getParent().getParent();
        home_parent2.getChildren().clear();
        home_parent2.getChildren().add(anuluj);
    }

}
