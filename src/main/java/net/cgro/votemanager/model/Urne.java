package net.cgro.votemanager.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import java.util.ArrayList;
import java.util.Objects;

public class Urne {
    private final SimpleStringProperty name;
    private final SimpleIntegerProperty nummer;
    private final SimpleStringProperty status;

    private final SimpleStringProperty standort;

    public Urne(String name, int nummer) {
        this(name, nummer, "");
    }

    public Urne(String name, int nummer, String standort) {
        this.name = new SimpleStringProperty(name);
        this.nummer = new SimpleIntegerProperty(nummer);
        this.status = new SimpleStringProperty();
        this.standort = new SimpleStringProperty(standort);
        this.updateStatus();
    }

    private Urne() {
        this.name = new SimpleStringProperty();
        this.nummer = new SimpleIntegerProperty();
        this.status = new SimpleStringProperty();
        this.standort = new SimpleStringProperty();
        this.updateStatus();
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

    @XmlAttribute
    public String getStandort() {
        return standort.get();
    }

    public void setStandort(String standort) {
        this.standort.set(standort);
    }

    public SimpleStringProperty standortProperty() {
        return standort;
    }

    public void updateStatus() {
        if (!Wahl.getInstance().existiertErgebnis(this)) {
            status.set("Kein Ergebnis");
            return;
        }

        ArrayList<CheckResult> checks = new ArrayList<>();
        boolean result = Wahl.getInstance().getErgebnis(this).doChecks(checks);

        if (result == true) {
            status.set("Ergebnis existiert - OK");
        } else {
            status.set("Ergebnis existiert - FEHLER GEFUNDEN");
        }
    }

    public SimpleStringProperty statusProperty() {
        return status;
    }

    @Override
    public String toString() {
        return String.format("Urne[name='%s',nummer=%d]", name.getValue(), nummer.getValue());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof Urne) {
            Urne other = (Urne) obj;
            return Objects.equals(other.getName(), name.getValue()) && Objects.equals(other.getNummer(), nummer.getValue());
        } else {
            return false;
        }
    }
}