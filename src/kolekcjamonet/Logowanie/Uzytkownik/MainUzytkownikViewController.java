/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kolekcjamonet.Logowanie.Uzytkownik;

import com.jfoenix.controls.JFXDrawer;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import kolekcjamonet.KolekcjaMonet;
import kolekcjamonet.Logowanie.LoginViewController;

/**
 * FXML Controller class
 *
 * @author BlackHawk
 */
public class MainUzytkownikViewController implements Initializable {

    @FXML
    private JFXDrawer drawer;
    @FXML
    private AnchorPane dynamicPane;
    @FXML
    private AnchorPane repPane;
    @FXML
    private Label label;
    AnchorPane twojeMonety, twojeDane, twojeKolekcje, wyloguj;
    VBox bocznyPanel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            System.out.println(LoginViewController.r);
            //boczny panel uzytkownikDrawer
            wyloguj = FXMLLoader.load(KolekcjaMonet.class.getResource("FXMLMain.fxml"));
            bocznyPanel = FXMLLoader.load(getClass().getResource("UzytkownikDrawer.fxml"));
            twojeMonety = FXMLLoader.load(getClass().getResource("Monety/Monety.fxml"));
            twojeDane = FXMLLoader.load(getClass().getResource("Dane/Dane.fxml"));
            twojeKolekcje = FXMLLoader.load(getClass().getResource("Kolekcje/Kolekcje.fxml"));
            drawer.setSidePane(bocznyPanel);
            drawer.open();

            for (Node node : bocznyPanel.getChildren()) {
                if (node.getAccessibleText() != null) {

                    node.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent e) {

                            switch (node.getAccessibleText()) {
                               
                                case ("twojeMonety"):
                                    label.setText("TWOJE MONETY");
                                    repPane.getChildren().clear();
                                    repPane.getChildren().add((Node) twojeMonety);
                                    break;
                                case ("twojeDane"):
                                    label.setText("TWOJE DANE");
                                    repPane.getChildren().clear();
                                    repPane.getChildren().add((Node) twojeDane);
                                    break;
                                case ("twojeKolekcje"):
                                    label.setText("TWOJE KOLEKCJE");
                                    repPane.getChildren().clear();
                                    repPane.getChildren().add((Node) twojeKolekcje);
                                    break;
                                case ("wylogujSie"):
                                    Image image = new Image(KolekcjaMonet.class.getResourceAsStream("Obrazki/fawicon.png"));
                                    Stage mainStage = new Stage();
                                    Scene scene = new Scene(wyloguj);
                                    mainStage.setScene(scene);
                                    repPane.getScene().getWindow().hide();
                                    mainStage.getIcons().add(image);
                                    mainStage.show();
                                    break;
                                case ("wyjscieExit"):
                                    System.exit(0);
                                    break;
                            }
                        }

                    });
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void showDrawer(MouseEvent event) {
        if (!drawer.isShown()) {
            drawer.open();
        }
    }

}
