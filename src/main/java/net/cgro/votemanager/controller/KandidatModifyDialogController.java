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
import net.cgro.votemanager.model.Wahl;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author hszemi
 */
public class KandidatModifyDialogController implements Initializable {
    @FXML
    private TextField inputName;
    @FXML
    private TextField inputNummer;
    @FXML
    private Button buttonSave;
    @FXML
    private Button buttonCancel;

    private Liste liste;
    private Kandidat kandidat;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inputName.setPromptText("Name des Kandidaten eingeben");
        inputNummer.setPromptText("Nummer des Kandidaten eingeben");
    }

    public void setListe(Liste liste){
        this.liste = liste;
    }

    public void setKandidat(Kandidat kandidat) {
        this.kandidat = kandidat;
        inputNummer.setText(Integer.toString(kandidat.getNummer()));
        inputName.setText(kandidat.getName());
    }

    @FXML
    private void handleButtonSave(ActionEvent event) {
        kandidat.setNummer(Integer.parseInt(inputNummer.getText()));
        kandidat.setName(inputName.getText());

        // Pseudo-Update auf die Kandidatur, damit Änderung in GUI sichtbar wird
        int index = liste.getKandidaten().indexOf(kandidat);
        liste.getKandidaten().set(index, kandidat);

        Stage stage = (Stage) buttonSave.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleButtonCancel(ActionEvent event) {
        Stage stage = (Stage) buttonCancel.getScene().getWindow();
        stage.close();
    }

}
