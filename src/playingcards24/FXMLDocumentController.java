/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package playingcards24;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/**
 *
 * @author peterschellhorn
 */
public class FXMLDocumentController implements Initializable {
    
    private Label label;
    @FXML
    private Button findSolution;
    @FXML
    private TextField solutionField;
    @FXML
    private Button refreshText;
    @FXML
    private Button verifyCheck;
    @FXML
    private TextField ExpressionField;
    @FXML
    private ImageView card1;
    @FXML
    private ImageView card2;
    @FXML
    private ImageView card3;
    @FXML
    private ImageView card4;
    
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void loadCardImages(ActionEvent event) {
    }
    
}
