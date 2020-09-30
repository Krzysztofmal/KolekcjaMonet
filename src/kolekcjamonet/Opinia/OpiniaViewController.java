/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kolekcjamonet.Opinia;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import kolekcjamonet.Mail.Mail;

/**
 * FXML Controller class
 *
 * @author BlackHawk
 */
public class OpiniaViewController implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private JFXComboBox depCombo;
    @FXML
    private JFXTextField krotkiOpis;
    @FXML
    private JFXTextArea szczegolowyOpis;
    @FXML
    private JFXButton wyslijBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        depCombo.setStyle("-fx-font: 18px \"System\";");
        depCombo.getItems().addAll(
                "Kontakt",
                "Problem"
        );
    }

    @FXML
    private void wyslijOpinie(ActionEvent event) {
        String sub = String.valueOf(depCombo.getValue()) + " - " + krotkiOpis.getText();
        String content = szczegolowyOpis.getText();

        Task sendingMessage = new Task<Void>() {
            @Override
            protected Void call() throws Exception {

                Mail mail = new Mail();

                mail.sendMail("apitestjava@gmail.com", "apitest123", "apitestjava@gmail.com", sub, content);
                System.out.println("wysłano");

                return null;
            }
        };
        
        new Thread(sendingMessage).start();           
        
        szczegolowyOpis.setText("");
        krotkiOpis.setText("");
        depCombo.setValue("");
    }

}
