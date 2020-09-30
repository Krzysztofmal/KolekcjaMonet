/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kolekcjamonet.Logowanie;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.sun.javaws.Main;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import javafx.util.Duration;
import kolekcjamonet.FXMLMainController;
import kolekcjamonet.KolekcjaMonet;
import kolekcjamonet.Polaczenie.Polaczenie;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author BlackHawk
 */
public class LoginViewController implements Initializable {

    //public static Statement stmt;
    //public static PreparedStatement prep;
    private FXMLMainController mc;

    Stage stage;

    @FXML
    private JFXTextField loginPole;
    @FXML
    private JFXPasswordField hasloPole;
    @FXML
    private JFXButton loginBtn;
    @FXML
    private Label label;
    public static String r;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void zaloguj(ActionEvent event) {

        try {

            Connection con = Polaczenie.Connect();

            //ps = con.prepareStatement("SELECT login,haslo FROM " + rola );
            
            PreparedStatement ps = con.prepareStatement("SELECT id_uzytkownika,login,haslo FROM uzytkownik");
            ResultSet rs = ps.executeQuery();
            boolean sprawdzenie = false;
            
            
            while (rs.next()) {
                        if (rs.getString("login").equals(loginPole.getText())) {
                            if (rs.getString("haslo").equals(hasloPole.getText())) {
                                sprawdzenie = true;
                                r = rs.getString("id_uzytkownika");
                                break;
                            }
                        }
                    }
            

           

            if (sprawdzenie) {
                System.out.println("Logowanie przebiegło pomyślnie!");
                label.setVisible(false);

                AudioClip powiadomMnie = new AudioClip((KolekcjaMonet.class.getResource("Audio/Notify.wav")).toString());
                powiadomMnie.play();

                TrayNotification tray = new TrayNotification();
                tray.setTitle("Sukces!");
                tray.setMessage("Logowanie przebiegło pomyślnie");
                tray.setNotificationType(NotificationType.SUCCESS);
                tray.showAndDismiss(Duration.millis(1500));
                Parent root = null;
                root = FXMLLoader.load(LoginViewController.class.getResource("Uzytkownik/MainUzytkownikView.fxml"));
                

                stage = new Stage();
                Image icon = new Image(KolekcjaMonet.class.getResourceAsStream("Obrazki/fawicon.png"));
                stage.getIcons().add(icon);
                
                stage.setTitle("Panel użytkownika");
                Scene scene = new Scene(root);
                stage.setScene(scene);
                loginBtn.getScene().getWindow().hide();
                stage.show();
                loginPole.setText(null);
                hasloPole.setText(null);
                //}
            } else {
                loginPole.setText(null);
                hasloPole.setText(null);
                label.setVisible(true);

                AudioClip powiadomMnie = new AudioClip((KolekcjaMonet.class.getResource("Audio/Notify.wav")).toString());
                powiadomMnie.play();

                TrayNotification tray = new TrayNotification();
                tray.setTitle("Błąd!");
                tray.setMessage("Logowanie nie powiodło się.");
                tray.setNotificationType(NotificationType.ERROR);
                tray.showAndDismiss(Duration.millis(1500));
                //}
            }


            ps.close();
            rs.close();
            con.close();
        } catch (SQLException ex) {
            System.out.println("Błąd SQL!");
        } catch (IOException ex) {
            Logger.getLogger(LoginViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

       
    }

}
