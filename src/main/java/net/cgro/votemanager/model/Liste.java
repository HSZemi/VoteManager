package net.cgro.votemanager.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlID;

public class Liste {
    private final SimpleStringProperty name;
    private final SimpleStringProperty kuerzel;
    private final SimpleIntegerProperty nummer;

    private final ObservableList<Kandidat> kandidaten = FXCollections.observableArrayList();

    private int stimmenTemp = 0;
    private int stimmenTemp2 = 0;

    public Liste(String name, String kuerzel, int nummer) {
        this.name = new SimpleStringProperty(name);
        this.kuerzel = new SimpleStringProperty(kuerzel);
        this.nummer = new SimpleIntegerProperty(nummer);
    }

    private Liste() {
        this.name = new SimpleStringProperty();
        this.kuerzel = new SimpleStringProperty();
        this.nummer = new SimpleIntegerProperty();
    }

    @XmlID
    @XmlAttribute
    public String getKuerzel() {
        return kuerzel.get();
    }

    public void setKuerzel(String kuerzel) {
        this.kuerzel.set(kuerzel);
    }

    @XmlAttribute
    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    @XmlAttribute
    public int getNummer() {
        return nummer.get();
    }

    public void setNummer(int nummer) {
        this.nummer.set(nummer);
    }

    @XmlElementWrapper
    @XmlElement(name = "kandidat")
    public ObservableList<Kandidat> getKandidaten() {
        return kandidaten;
    }

    public void addKandidat(Kandidat kandidat) {
        kandidaten.add(kandidat);
    }

    public void resetStimmenTemp() {
        stimmenTemp = 0;
    }

    public void addStimmenTemp(int stimmen) {
        stimmenTemp += stimmen;
    }

    public int getStimmenTemp() {
        return stimmenTemp;
    }

    public void resetStimmenTemp2() {
        stimmenTemp2 = 0;
    }

    public void addStimmenTemp2(int stimmen) {
        stimmenTemp2 += stimmen;
    }

    public int getStimmenTemp2() {
        return stimmenTemp2;
    }

}