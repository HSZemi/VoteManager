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
import net.cgro.votemanager.model.Kandidat;
import net.cgro.votemanager.model.Liste;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author fabian
 */
public class KandidatAddDialogController implements Initializable {
    @FXML
    private TextField inputName;
    @FXML
    private TextField inputNummer;
    @FXML
    private Button buttonAdd;
    @FXML
    private Button buttonCancel;

    private Liste liste;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inputName.setPromptText("Name der Kandidaturen eingeben");
        inputNummer.setPromptText("Nummer der Kandidatur eingeben");
    }

    public void setListe(Liste liste) {
        this.liste = liste;
    }

    @FXML
    private void handleButtonAdd(ActionEvent event) {
        // Füge neuen Kandidaten hinzu
        Kandidat kandidat = new Kandidat(inputName.getText(), Integer.parseInt(inputNummer.getText()));
        liste.addKandidat(kandidat);

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
