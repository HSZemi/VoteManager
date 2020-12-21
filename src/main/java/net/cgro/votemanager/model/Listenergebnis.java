package net.cgro.votemanager.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlIDREF;
import java.util.ArrayList;
import java.util.List;

public class Listenergebnis {
    private ObservableList<Kandidatenergebnis> kandidatenergebnisse = FXCollections.observableArrayList();
    private Liste liste;
    private int listenstimmen = 0;
    private int gesamtstimmen = 0;

    private Listenergebnis() {

    }

    public Listenergebnis getDeepCopy() {
        Listenergebnis erg = new Listenergebnis();
        erg.listenstimmen = listenstimmen;
        erg.gesamtstimmen = gesamtstimmen;
        erg.liste = liste;

        List<Kandidat> kandidaten = new ArrayList<Kandidat>(liste.getKandidaten());

        for (Kandidatenergebnis kandidatenergebnis : kandidatenergebnisse) {
            erg.addKandidatenergebnis(kandidatenergebnis.getDeepCopy());
            kandidaten.remove(kandidatenergebnis.getKandidat());
        }

        for (Kandidat kandidat : kandidaten) {
            Kandidatenergebnis kanderg = new Kandidatenergebnis(kandidat);
            erg.addKandidatenergebnis(kanderg);
        }

        return erg;
    }

    public Listenergebnis(Liste liste) {
        this.liste = liste;
        for (Kandidat kandidat : liste.getKandidaten()) {
            Kandidatenergebnis erg = new Kandidatenergebnis(kandidat);
            this.addKandidatenergebnis(erg);
        }
    }

    public boolean doChecks(List<CheckResult> results) {
        int summeEinzel = 0;
        boolean state = true;
        List<Kandidat> kandidaten = new ArrayList<Kandidat>(liste.getKandidaten());

        for (Kandidatenergebnis kerg : kandidatenergebnisse) {
            summeEinzel += kerg.getStimmen();
            kandidaten.remove(kerg.getKandidat());
        }

        // Summe muss übereinstimmen
        if (summeEinzel + this.listenstimmen != this.gesamtstimmen) {
            results.add(new CheckResult("Liste " + this.liste.getKuerzel() + " - Summe der Listenstimmen und Einzelstimmen entspricht nicht den Gesamtstimmen", ResultType.RESULT_TYPE_ERROR));
            state = false;
        }

        // Fehlende Kandidatenergebnisse -> Warnung
        for (Kandidat kandidat : kandidaten) {
            results.add(new CheckResult("Kandidatur " + kandidat.getName() + " - Kandidaturenstimmen fehlen", ResultType.RESULT_TYPE_WARNING));
            state = false;
        }

        return state;
    }

    @XmlAttribute
    public int getListenstimmen() {
        return listenstimmen;
    }

    public void setListenstimmen(int stimmen) {
        this.listenstimmen = stimmen;
    }

    @XmlAttribute
    public int getGesamtstimmen() {
        return gesamtstimmen;
    }

    public void setGesamtstimmen(int stimmen) {
        this.gesamtstimmen = stimmen;
    }

    @XmlElement(name = "kandidatenergebnis")
    public ObservableList<Kandidatenergebnis> getKandidatenergebnisse() {
        return kandidatenergebnisse;
    }

    public void addKandidatenergebnis(Kandidatenergebnis kandidatenergebnis) {
        this.kandidatenergebnisse.add(kandidatenergebnis);
    }

    @XmlIDREF
    @XmlAttribute
    public Liste getListe() {
        return liste;
    }

    public void setListe(Liste liste) {
        this.liste = liste;
    }

}