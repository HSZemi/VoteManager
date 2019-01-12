/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.cgro.votemanager.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;

/**
 * @author fabian
 */
public class ErgebnisEintrag {

    private final SimpleStringProperty text;
    private final SimpleIntegerProperty stimmen;

    public ErgebnisEintrag(String text, Integer stimmen) {
        this.text = new SimpleStringProperty(text);
        this.stimmen = new SimpleIntegerProperty(stimmen);
    }

    public String getText() {
        return text.get();
    }

    public int getStimmen() {
        return stimmen.get();
    }

    public ObservableValue<String> textProperty() {
        return this.text;
    }

    public ObservableValue<Integer> stimmenProperty() {
        return this.stimmen.asObject();
    }
}
