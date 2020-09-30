/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kolekcjamonet.Logowanie.Uzytkownik.Monety;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import javafx.util.Duration;
import javafx.util.StringConverter;
import kolekcjamonet.Details.KolekcjaDetails;
import kolekcjamonet.Details.MonetaDetails;
import kolekcjamonet.Logowanie.LoginViewController;
import kolekcjamonet.Polaczenie.Polaczenie;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author BlackHawk
 */
public class MonetyController implements Initializable {

    @FXML
    private TableView<MonetaDetails> tableMonety;
    @FXML
    private TableColumn<MonetaDetails, String> columnNazwa;
    @FXML
    private TableColumn<MonetaDetails, String> columnPochodzenie;
    @FXML
    private TableColumn<MonetaDetails, String> columnRok;
    @FXML
    private TableColumn<MonetaDetails, String> columnOpis;
    @FXML
    private JFXButton dodajMoneteBtn;
    @FXML
    private JFXButton edytujMoneteBtn;
    @FXML
    private JFXButton usunMoneteBtn;
    @FXML
    private JFXComboBox<KolekcjaDetails> cbKolekcje;

    ObservableList<KolekcjaDetails> dataKolekcje;
    ObservableList<MonetaDetails> dataMonety;
    AnchorPane dodaj,edytuj;
    public static Integer idMonety;
    public static Integer idKolekcji;
    private String sciezka;
    @FXML
    private ImageView IV;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbKolekcje.setStyle("-fx-font: 18px \"System\";");
        dataMonety = FXCollections.observableArrayList();
        dataKolekcje = FXCollections.observableArrayList();
        
                    edytujMoneteBtn.setDisable(true);
            usunMoneteBtn.setDisable(true);
        
        Connection con = Polaczenie.Connect();

        try {
            ResultSet rs = con.createStatement().executeQuery("SELECT id_kolekcji,nazwa,opis FROM Kolekcja WHERE id_uzytkownika='" + LoginViewController.r + "'");
            while (rs.next()) {
                dataKolekcje.add(new KolekcjaDetails(rs.getInt(1), rs.getString(2), rs.getString(3)));
            }

            rs.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(MonetyController.class.getName()).log(Level.SEVERE, null, ex);
        }
        cbKolekcje.setItems(null);
        cbKolekcje.setItems(dataKolekcje);

        /// XD
        cbKolekcje.setCellFactory(new Callback<ListView<KolekcjaDetails>, ListCell<KolekcjaDetails>>() {

            @Override
            public ListCell<KolekcjaDetails> call(ListView<KolekcjaDetails> p) {

                final ListCell<KolekcjaDetails> cell = new ListCell<KolekcjaDetails>() {

                    @Override
                    protected void updateItem(KolekcjaDetails t, boolean bln) {
                        super.updateItem(t, bln);

                        if (t != null) {
                            setText(t.getNazwa());
                        } else {
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        });

        cbKolekcje.setConverter(new StringConverter<KolekcjaDetails>() {
            @Override
            public String toString(KolekcjaDetails object) {
                if (object == null) {
                    return "";
                } else {
                    return object.getNazwa();
                }
            }

            @Override
            public KolekcjaDetails fromString(String string) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });

        cbKolekcje.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if (newValue != null) {
                    try {
                        //newValue = cbKolekcje.getSelectionModel().getSelectedItem().getId_kolekcji();
                        KolekcjaDetails md = (KolekcjaDetails)newValue;
                        idKolekcji = cbKolekcje.getSelectionModel().getSelectedItem().getId_kolekcji();
                        dataMonety.clear();
                        Connection con = Polaczenie.Connect();
                        ResultSet rs = con.createStatement().executeQuery("SELECT id_monety,nazwa,pochodzenie,rok,opis,image FROM Moneta WHERE id_kolekcji='"+cbKolekcje.getSelectionModel().getSelectedItem().getId_kolekcji()+"'");
                        
                        while (rs.next()){
                            dataMonety.add(new MonetaDetails(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6)));
                        }
                        
                        
                        
                        columnNazwa.setCellValueFactory(new PropertyValueFactory<>("nazwa"));
                        columnOpis.setCellValueFactory(new PropertyValueFactory<>("opis"));
                        columnPochodzenie.setCellValueFactory(new PropertyValueFactory<>("pochodzenie"));
                        columnRok.setCellValueFactory(new PropertyValueFactory<>("rok"));
                        
                        
                        tableMonety.setItems(null);
                        tableMonety.setItems(dataMonety);
                        
                        
                        rs.close();
                        con.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(MonetyController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    
                }
            }
        });

        
        tableMonety.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (tableMonety.getSelectionModel().getSelectedItem() != null) {
                sciezka = tableMonety.getSelectionModel().getSelectedItem().getImage();
                System.out.println(sciezka);
                File f1 = new File(sciezka);
                Image i = new Image(f1.toURI().toString());
                IV.setImage(i);
            }
            }
        });
        
        
        
        
        
        
    }

    @FXML
    private void zaznacz(MouseEvent event) {
        if (tableMonety.getSelectionModel().getSelectedItem() != null){
            edytujMoneteBtn.setDisable(false);
            usunMoneteBtn.setDisable(false);
            idMonety = tableMonety.getSelectionModel().getSelectedItem().getId_monety();
        }
    }

    @FXML
    private void dodajMonete(ActionEvent event) {
        try {
            dodaj = FXMLLoader.load(getClass().getResource("DodajMonete.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(MonetyController.class.getName()).log(Level.SEVERE, null, ex);
        }

        AnchorPane home_parent1 = (AnchorPane) dodajMoneteBtn.getParent().getParent();
        home_parent1.getChildren().clear();
        home_parent1.getChildren().add(dodaj);
    }

    @FXML
    private void edytujMonete(ActionEvent event) {
        try {
            edytuj = FXMLLoader.load(getClass().getResource("EdytujMonete.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(MonetyController.class.getName()).log(Level.SEVERE, null, ex);
        }

        AnchorPane home_parent1 = (AnchorPane) edytujMoneteBtn.getParent().getParent();
        home_parent1.getChildren().clear();
        home_parent1.getChildren().add(edytuj);
    }

    @FXML
    private void usunMoneteBtn(ActionEvent event) throws SQLException {
        Connection con = Polaczenie.Connect();
        
        con.createStatement().executeUpdate("DELETE FROM Moneta WHERE id_monety="+tableMonety.getSelectionModel().getSelectedItem().getId_monety());
        
                    TrayNotification tray = new TrayNotification();
            tray.setTitle("Sukces!");
            tray.setMessage("Usuwanie monety powiodło się.");
            tray.setNotificationType(NotificationType.SUCCESS);
            tray.showAndDismiss(Duration.millis(1500));
            
            
    }
    
    
    

}
