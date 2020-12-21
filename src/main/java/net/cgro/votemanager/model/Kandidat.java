package net.cgro.votemanager.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import java.util.Objects;

public class Kandidat {
    private final SimpleStringProperty name;
    private final SimpleIntegerProperty nummer;

    private int stimmenTemp = 0;

    public Kandidat(String name, int nummer) {
        this.name = new SimpleStringProperty(name);
        this.nummer = new SimpleIntegerProperty(nummer);
    }

    private Kandidat() {
        this.name = new SimpleStringProperty();
        this.nummer = new SimpleIntegerProperty();
    }

    @XmlID
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

    public SimpleStringProperty getNameProperty() {
        return name;
    }

    public SimpleIntegerProperty getNummerProperty() {
        return nummer;
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

    @Override
    public String toString() {
        return String.format("Kandidatur[name='%s',nummer=%d]", name.getValue(), nummer.getValue());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof Kandidat) {
            Kandidat other = (Kandidat) obj;
            return Objects.equals(other.getName(), name.getValue())
                    && Objects.equals(other.getNummer(), nummer.getValue());
        } else {
            return false;
        }
    }
}