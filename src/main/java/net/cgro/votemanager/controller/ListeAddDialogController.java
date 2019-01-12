/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.cgro.votemanager.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import net.cgro.votemanager.model.Liste;
import net.cgro.votemanager.model.Wahl;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author fabian
 */
public class ListeAddDialogController implements Initializable {
    @FXML
    private TextField inputName;
    @FXML
    private TextField inputKuerzel;
    @FXML
    private TextField inputNummer;
    @FXML
    private Button buttonAdd;
    @FXML
    private Button buttonCancel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inputName.setPromptText("Name der Liste eingeben");
        inputKuerzel.setPromptText("Kürzel der Liste eingeben");
        inputNummer.setPromptText("Nummer der Liste eingeben");
    }

    @FXML
    private void handleButtonAdd(ActionEvent event) {
        // Füge neue Liste zur Wahl hinzu
        Wahl wahl = Wahl.getInstance();
        Liste liste = new Liste(inputName.getText(), inputKuerzel.getText(), Integer.parseInt(inputNummer.getText()));
        wahl.addListe(liste);

        // Schließe das Fenster
        Stage stage = (Stage) buttonAdd.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleButtonCancel(ActionEvent event) {
        // Schließe das Fenster
        Stage stage = (Stage) buttonCancel.getScene().getWindow();
        stage.close();
    }

}
