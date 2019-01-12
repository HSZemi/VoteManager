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
public class ListeRenameDialogController implements Initializable {
    @FXML
    private Button buttonRename;
    @FXML
    private Button buttonCancel;
    @FXML
    private TextField inputName;
    @FXML
    private TextField inputKuerzel;
    @FXML
    private TextField inputNummer;

    private Liste liste;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void setListe(Liste liste) {
        this.liste = liste;
        inputName.setText(this.liste.getName());
        inputKuerzel.setText(this.liste.getKuerzel());
        inputNummer.setText(Integer.toString(this.liste.getNummer()));
    }

    @FXML
    private void handleButtonRename(ActionEvent event) {
        // Ändere Name und Kürzel der Liste
        this.liste.setName(inputName.getText());
        this.liste.setKuerzel(inputKuerzel.getText());
        this.liste.setNummer(Integer.parseInt(inputNummer.getText()));

        // Pseudo-Update auf die Liste, damit Änderung in GUI sichtbar wird
        int index = Wahl.getInstance().getListen().indexOf(this.liste);
        Wahl.getInstance().getListen().set(index, this.liste);

        // Schließe das Fenster
        Stage stage = (Stage) buttonRename.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleButtonCancel(ActionEvent event) {
        // Schließe das Fenster
        Stage stage = (Stage) buttonCancel.getScene().getWindow();
        stage.close();
    }

}
