/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kolekcjamonet.Logowanie.Uzytkownik.Kolekcje;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import kolekcjamonet.Details.KolekcjaDetails;
import kolekcjamonet.Polaczenie.Polaczenie;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author BlackHawk
 */
public class KolekcjeController implements Initializable {

    @FXML
    private TableView<KolekcjaDetails> tableKolekcja;
    @FXML
    private TableColumn<KolekcjaDetails, String> columnNazwa;
    @FXML
    private TableColumn<KolekcjaDetails, String> columnOpis;
    @FXML
    private JFXButton dodajKolekcjeBtn;
    @FXML
    private JFXButton edytujKolekcjeBtn;

    private ObservableList<KolekcjaDetails> dataKolekcje;
    AnchorPane dodaj, edytuj;
    @FXML
    private JFXButton usunKolekcjeBtn;
    public static Integer idKolekcji;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        edytujKolekcjeBtn.setDisable(true);
        usunKolekcjeBtn.setDisable(true);
        odswiez();
    }

    @FXML
    private void dodajKolekcje(ActionEvent event) {
        try {
            dodaj = FXMLLoader.load(getClass().getResource("DodajKolekcje.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(KolekcjeController.class.getName()).log(Level.SEVERE, null, ex);
        }

        AnchorPane home_parent1 = (AnchorPane) dodajKolekcjeBtn.getParent().getParent();
        home_parent1.getChildren().clear();
        home_parent1.getChildren().add(dodaj);
    }

    @FXML
    private void edytujKolekcje(ActionEvent event) {
        try {
            edytuj = FXMLLoader.load(getClass().getResource("EdytujKolekcje.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(KolekcjeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //idKolekcji = tableKolekcja.getSelectionModel().getSelectedItem().getId_kolekcji();
        AnchorPane home_parent1 = (AnchorPane) edytujKolekcjeBtn.getParent().getParent();
        home_parent1.getChildren().clear();
        home_parent1.getChildren().add(edytuj);
    }

    @FXML
    private void usunKolekcje(ActionEvent event) throws SQLException {
        Connection con = Polaczenie.Connect();
        con.createStatement().executeUpdate("DELETE FROM Kolekcja WHERE id_kolekcji="+tableKolekcja.getSelectionModel().getSelectedItem().getId_kolekcji());
        con.createStatement().executeUpdate("DELETE FROM Moneta WHERE id_kolekcji="+tableKolekcja.getSelectionModel().getSelectedItem().getId_kolekcji());
        
                    TrayNotification tray = new TrayNotification();
            tray.setTitle("Sukces!");
            tray.setMessage("Usuwanie Kolekcji powiodło się.");
            tray.setNotificationType(NotificationType.SUCCESS);
            tray.showAndDismiss(Duration.millis(1500));
            odswiez();
    }

    private void odswiez() {
        // dataKolekcje.clear();
        dataKolekcje = FXCollections.observableArrayList();
        dataKolekcje.clear();
        try {
            Connection con = Polaczenie.Connect();
            PreparedStatement ps = con.prepareStatement("SELECT id_kolekcji,nazwa,opis FROM Kolekcja");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                dataKolekcje.add(new KolekcjaDetails(rs.getInt(1), rs.getString(2), rs.getString(3)));                
            }

            ps.close();
            rs.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(KolekcjeController.class.getName()).log(Level.SEVERE, null, ex);
        }

        columnNazwa.setCellValueFactory(new PropertyValueFactory<>("nazwa"));
        columnOpis.setCellValueFactory(new PropertyValueFactory<>("opis"));
        tableKolekcja.setItems(null);
        tableKolekcja.setItems(dataKolekcje);

    }

    @FXML
    private void zaznacz(MouseEvent event) {
        if (tableKolekcja.getSelectionModel().getSelectedItem() != null){
            edytujKolekcjeBtn.setDisable(false);
            usunKolekcjeBtn.setDisable(false);
            idKolekcji = tableKolekcja.getSelectionModel().getSelectedItem().getId_kolekcji();
        }
    }

}
