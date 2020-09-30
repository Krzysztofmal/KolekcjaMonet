/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kolekcjamonet;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author BlackHawk
 */
public class FXMLDrawerController implements Initializable {

    @FXML
    private JFXButton zaloguj;
    @FXML
    private JFXButton zarejestruj;
    @FXML
    private JFXButton opinia;
    @FXML
    private JFXButton wyjscie;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void zamknijAplikacje(ActionEvent event) {
        System.exit(0);
    }
    
}
