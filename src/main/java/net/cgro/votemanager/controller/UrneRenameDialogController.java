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
import net.cgro.votemanager.model.Urne;
import net.cgro.votemanager.model.Wahl;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author fabian
 */
public class UrneRenameDialogController implements Initializable {
    @FXML
    private Button buttonRename;
    @FXML
    private Button buttonCancel;
    @FXML
    private TextField inputName;
    @FXML
    private TextField inputNummer;

    private Urne urne;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void setUrne(Urne urne) {
        this.urne = urne;
        inputName.setText(this.urne.getName());
        inputNummer.setText(Integer.toString(this.urne.getNummer()));
    }

    @FXML
    private void handleButtonRename(ActionEvent event) {
        // Ändere Name der Urne
        this.urne.setName(inputName.getText());
        this.urne.setNummer(Integer.parseInt(inputNummer.getText()));

        // Pseudo-Update auf die Liste, damit Änderung in GUI sichtbar wird
        int index = Wahl.getInstance().getUrnen().indexOf(this.urne);
        Wahl.getInstance().getUrnen().set(index, this.urne);

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
