package net.cgro.votemanager.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlIDREF;


public class Kandidatenergebnis {
    private final SimpleIntegerProperty stimmen;
    private Kandidat kandidat;

    public Kandidatenergebnis(Kandidat kandidat) {
        this.kandidat = kandidat;
        stimmen = new SimpleIntegerProperty(0);
    }

    private Kandidatenergebnis() {
        stimmen = new SimpleIntegerProperty(0);
    }

    public Kandidatenergebnis getDeepCopy() {
        Kandidatenergebnis erg = new Kandidatenergebnis(kandidat);
        erg.setStimmen(this.getStimmen());
        return erg;
    }

    @XmlAttribute
    public int getStimmen() {
        return stimmen.get();
    }

    public void setStimmen(int stimmen) {
        this.stimmen.set(stimmen);
    }

    @XmlIDREF
    @XmlAttribute
    public Kandidat getKandidat() {
        return kandidat;
    }

    public void setKandidat(Kandidat kandidat) {
        this.kandidat = kandidat;
    }

    public StringProperty kandidatNameProperty() {
        return kandidat.getNameProperty();
    }

    public IntegerProperty kandidatNummerProperty() {
        return kandidat.getNummerProperty();
    }

    public IntegerProperty stimmenProperty() {
        return stimmen;
    }
}