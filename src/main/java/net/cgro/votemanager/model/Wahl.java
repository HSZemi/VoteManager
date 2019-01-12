package net.cgro.votemanager.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Wahl {
    private static Wahl instance = null;

    private ObservableList<Urne> urnen = FXCollections.observableArrayList();
    private ObservableList<Liste> listen = FXCollections.observableArrayList();
    private ObservableList<Ergebnis> ergebnisse = FXCollections.observableArrayList();

    private Wahl() {
        // Constructor private!
    }

    public static void setInstance(Wahl wahl) {
        instance = wahl;
    }

    // Singleton pattern
    public static Wahl getInstance() {
        if (instance == null) {
            instance = new Wahl();
        }
        return instance;
    }

    public static void reset() {
        instance = new Wahl();
    }

    @XmlElementWrapper
    @XmlElement(name = "urne")
    public ObservableList<Urne> getUrnen() {
        return urnen;
    }

    public void addUrne(Urne urne) {
        urnen.add(urne);
    }

    public void removeUrne(Urne urne) {
        ergebnisse.removeIf(ergebnis -> ergebnis.getUrne() == urne);
        urnen.remove(urne);
    }

    @XmlElementWrapper
    @XmlElement(name = "liste")
    public ObservableList<Liste> getListen() {
        return listen;
    }

    public void addListe(Liste liste) {
        listen.add(liste);
    }

    public void removeListe(Liste liste) {
        for (Ergebnis ergebnis : ergebnisse) {
            ergebnis.getListenergebnisse().removeIf(listenergebnis -> listenergebnis.getListe() == liste);
        }
        listen.remove(liste);
    }

    @XmlElementWrapper
    @XmlElement(name = "ergebnis")
    ObservableList<Ergebnis> getErgebnisse() {
        return ergebnisse;
    }

    public void setErgebnis(Ergebnis ergebnis) {
        // Altes Ergebnis der Urne löschen, falls es existiert
        ergebnisse.removeIf(ergebnis1 -> ergebnis1.getUrne() == ergebnis.getUrne());

        // Neues Ergebnis eintragen
        ergebnisse.add(ergebnis);
        ergebnis.getUrne().updateStatus();
    }

    boolean existiertErgebnis(Urne urne) {
        // Suche passendes Ergebnis
        for (Ergebnis e : ergebnisse) {
            if (e.getUrne() == urne) {
                return true;
            }
        }

        return false;
    }

    public Ergebnis getErgebnis(Urne urne) {
        // Suche passendes Ergebnis
        for (Ergebnis e : ergebnisse) {
            if (e.getUrne() == urne) {
                return e;
            }
        }

        // Wenn noch kein Ergebnis existiert, neues anlegen.
        return new Ergebnis(urne);
    }

    public void removeKandidat(Kandidat kandidat) {
        ergebnisse.forEach(
                ergebnis -> ergebnis.getListenergebnisse().forEach(
                        listenergebnis -> listenergebnis.getKandidatenergebnisse().removeIf(
                                kandidatenergebnis -> kandidatenergebnis.getKandidat() == kandidat)));
        for (Liste liste : listen) {
            liste.getKandidaten().remove(kandidat);
        }
    }
}