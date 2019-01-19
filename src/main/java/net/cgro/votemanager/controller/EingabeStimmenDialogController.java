/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.cgro.votemanager.controller;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import net.cgro.votemanager.model.*;
import net.cgro.votemanager.util.CheckArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import static net.cgro.votemanager.util.DigitHelper.isDigit;
import static net.cgro.votemanager.util.DigitHelper.toDigit;

/**
 * FXML Controller class
 *
 * @author fabian
 */
public class EingabeStimmenDialogController implements Initializable {
    private static Logger log = LoggerFactory.getLogger(EingabeStimmenDialogController.class);

    private Ergebnis ergebnis;
    private Listenergebnis current_lerg = null;

    @FXML
    private TextField inputStimmen;

    @FXML
    private Button buttonCancel;

    @FXML
    private TextField inputUngueltig;

    @FXML
    private TextField inputGueltig;

    @FXML
    private Label labelStatus;

    @FXML
    private TextField inputEnthaltungen;

    @FXML
    private Button buttonWeiter;

    @FXML
    private ComboBox<Listenergebnis> comboListen;

    @FXML
    private TextField inputListeGesamt;

    @FXML
    private TextField inputListenstimmen;

    @FXML
    private TableView<Kandidatenergebnis> tableEinzelstimmen;

    @FXML
    private TableColumn<Kandidatenergebnis, Integer> columnNummer;

    @FXML
    private TableColumn<Kandidatenergebnis, String> columnKandidat;

    @FXML
    private TableColumn<Kandidatenergebnis, Integer> columnStimmen;

    private boolean resetCell = true;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        columnNummer.setCellValueFactory(new PropertyValueFactory<>("kandidatNummer"));
        columnKandidat.setCellValueFactory(new PropertyValueFactory<>("kandidatName"));
        columnStimmen.setCellValueFactory(new PropertyValueFactory<>("stimmen"));

        tableEinzelstimmen.getSelectionModel().selectedIndexProperty().addListener(observable -> {
            resetCell = true;
        });

        tableEinzelstimmen.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            KeyCode code = event.getCode();
            Kandidatenergebnis kandidatenErgebnis = tableEinzelstimmen.getSelectionModel().getSelectedItem();
            if (kandidatenErgebnis != null) {
                if (code == KeyCode.ESCAPE || code == KeyCode.BACK_SPACE) {
                    kandidatenErgebnis.setStimmen(0);
                    event.consume();
                } else if (isDigit(code)) {
                    if (resetCell) {
                        kandidatenErgebnis.setStimmen(toDigit(code));
                        resetCell = false;
                    } else {
                        int stimmen = kandidatenErgebnis.getStimmen();
                        kandidatenErgebnis.setStimmen(stimmen * 10 + toDigit(code));
                    }
                    event.consume();
                } else if (code == KeyCode.ENTER) {
                    tableEinzelstimmen.getSelectionModel().selectNext();
                    makeSelectedRowVisible();
                }
            }
        });
    }

    private void makeSelectedRowVisible() {
        Skin<?> skin = tableEinzelstimmen.getSelectionModel().getTableView().getSkin();
        if (skin instanceof SkinBase) {
            SkinBase<?> skinBase = (SkinBase<?>) skin;
            Optional<Node> first = skinBase.getChildren().stream()
                    .filter(it -> it instanceof VirtualFlow)
                    .findFirst();
            if (first.isPresent()) {
                VirtualFlow<?> flow = (VirtualFlow<?>) first.get();
                flow.show(tableEinzelstimmen.getSelectionModel().getSelectedIndex());
            }
        }
    }

    public void setErgebnis(Ergebnis ergebnis) {
        this.ergebnis = ergebnis;
        Integer stimmen_gesamt = ergebnis.getStimmenGesamt();
        Integer stimmen_gueltig = ergebnis.getStimmenGueltig();
        Integer stimmen_ungueltig = ergebnis.getStimmenUngueltig();
        Integer stimmen_enthaltung = ergebnis.getStimmenEnthaltung();
        inputStimmen.setText(stimmen_gesamt.toString());
        inputGueltig.setText(stimmen_gueltig.toString());
        inputUngueltig.setText(stimmen_ungueltig.toString());
        inputEnthaltungen.setText(stimmen_enthaltung.toString());

        comboListen.setConverter(new StringConverter<Listenergebnis>() {
            @Override
            public String toString(Listenergebnis lerg) {
                if (lerg == null) {
                    return null;
                } else {
                    return lerg.getListe().getKuerzel();
                }
            }

            @Override
            public Listenergebnis fromString(String userId) {
                return null;
            }
        });

        comboListen.setItems(ergebnis.getListenergebnisse());
        comboListen.getSelectionModel().selectFirst();
        current_lerg = comboListen.getSelectionModel().getSelectedItem();
        loadSelectedListe();
    }

    private void saveInputs() {
        // Neue Werte übernehmen
        ergebnis.setStimmenGesamt(Integer.parseInt(inputStimmen.getText()));
        ergebnis.setStimmenGueltig(Integer.parseInt(inputGueltig.getText()));
        ergebnis.setStimmenUngueltig(Integer.parseInt(inputUngueltig.getText()));
        ergebnis.setStimmenEnthaltung(Integer.parseInt(inputEnthaltungen.getText()));

        if (current_lerg != null && !"".equals(inputListenstimmen.getText()) && !"".equals(inputListeGesamt.getText())) {
            current_lerg.setListenstimmen(Integer.parseInt(inputListenstimmen.getText()));
            current_lerg.setGesamtstimmen(Integer.parseInt(inputListeGesamt.getText()));
        }

    }

    @FXML
    private void handleButtonWeiter(ActionEvent event) {
        // Neue Werte übernehmen
        saveInputs();

        // Prüfung
        ArrayList<CheckResult> results = new CheckArrayList<CheckResult>();
        boolean state = this.ergebnis.doChecks(results);

        if (state == false) {

            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Probleme gefunden");
            alert.setHeaderText("Die Eingabeprüfung hat einen oder mehrere Fehler gefunden.");
            alert.setContentText("Soll das eingegebene Ergebnis trotzdem übernommen werden?");

            Label label = new Label("Folgende Fehler wurden gefunden:");

            String contentText = "";

            for (CheckResult result : results) {
                contentText += result.getInfo() + "\n";
            }

            TextArea textArea = new TextArea(contentText);
            textArea.setEditable(false);
            textArea.setWrapText(true);

            textArea.setMaxWidth(Double.MAX_VALUE);
            textArea.setMaxHeight(Double.MAX_VALUE);
            GridPane.setVgrow(textArea, Priority.ALWAYS);
            GridPane.setHgrow(textArea, Priority.ALWAYS);

            GridPane expContent = new GridPane();
            expContent.setMaxWidth(Double.MAX_VALUE);
            expContent.add(label, 0, 0);
            expContent.add(textArea, 0, 1);

// Set expandable Exception into the dialog pane.
            alert.getDialogPane().setExpandableContent(expContent);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                state = true;
            } else {
                // state remains false
            }
        }

        if (state == true) {
            // Füge neues Ergebnis zur Wahl hinzu
            Wahl wahl = Wahl.getInstance();
            wahl.setErgebnis(ergebnis);

            // Schließe das Fenster
            Stage stage = (Stage) buttonWeiter.getScene().getWindow();
            stage.close();
        }

    }

    @FXML
    private void handleButtonCancel(ActionEvent event) {
        // Schließe das Fenster
        Stage stage = (Stage) buttonCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleComboListen(ActionEvent event) {
        saveInputs();
        loadSelectedListe();
    }

    private void loadSelectedListe() {
        current_lerg = comboListen.getSelectionModel().getSelectedItem();

        inputListenstimmen.setText(Integer.toString(current_lerg.getListenstimmen()));
        inputListeGesamt.setText(Integer.toString(current_lerg.getGesamtstimmen()));

        tableEinzelstimmen.setItems(current_lerg.getKandidatenergebnisse());
    }

}
