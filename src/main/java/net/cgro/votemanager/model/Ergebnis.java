package net.cgro.votemanager.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlIDREF;
import java.util.ArrayList;
import java.util.List;


public class Ergebnis {
    private int stimmenGesamt = 0;
    private int stimmenGueltig = 0;
    private int stimmenUngueltig = 0;
    private int stimmenEnthaltung = 0;

    private Urne urne;

    private ObservableList<Listenergebnis> listenergebnisse = FXCollections.observableArrayList();

    private Ergebnis() {

    }

    public Ergebnis(Urne urne) {
        this.urne = urne;
        for (Liste liste : Wahl.getInstance().getListen()) {
            Listenergebnis listerg = new Listenergebnis(liste);
            this.addListenergebnis(listerg);
        }

    }

    public Ergebnis getDeepCopy() {
        Ergebnis erg = new Ergebnis();
        erg.stimmenGesamt = stimmenGesamt;
        erg.stimmenGueltig = stimmenGueltig;
        erg.stimmenUngueltig = stimmenUngueltig;
        erg.stimmenEnthaltung = stimmenEnthaltung;
        erg.urne = urne;

        List<Liste> listen = new ArrayList<Liste>(Wahl.getInstance().getListen());

        for (Listenergebnis listenergebnis : listenergebnisse) {
            erg.addListenergebnis(listenergebnis.getDeepCopy());
            listen.remove(listenergebnis.getListe());
        }

        for (Liste liste : listen) {
            Listenergebnis listerg = new Listenergebnis(liste);
            erg.addListenergebnis(listerg);
        }

        return erg;
    }

    public boolean doChecks(List<CheckResult> results) {
        int summeListen = 0;
        boolean state = true;
        List<Liste> listen = new ArrayList<Liste>(Wahl.getInstance().getListen());

        for (Listenergebnis lerg : listenergebnisse) {
            summeListen += lerg.getGesamtstimmen();
            listen.remove(lerg.getListe());

            // Checks für die Listen
            boolean result = lerg.doChecks(results);
            if (result == false)
                state = false;
        }

        // Summe muss übereinstimmen
        if (summeListen + stimmenEnthaltung != stimmenGueltig) {
            results.add(new CheckResult("Urne " + this.urne.getName() + " - Gesamtstimmen der Listen und Enthaltungen entsprechen nicht den gültigen Stimmen", ResultType.RESULT_TYPE_ERROR));
            state = false;
        }

        // Fehlende Kandidatenergebnisse -> Warnung
        for (Liste liste : listen) {
            results.add(new CheckResult("Liste " + liste.getKuerzel() + " - Ergebnisse fehlen", ResultType.RESULT_TYPE_WARNING));
            state = false;
        }

        // Ungültige + Gültige = Abgegebene?
        if (stimmenGueltig + stimmenUngueltig != stimmenGesamt) {
            results.add(new CheckResult("Urne " + this.urne.getName() + " - Gültige und ungültige Stimmen entsprechen nicht den insgesamt abgegebenen Stimmen", ResultType.RESULT_TYPE_ERROR));
            state = false;
        }

        return state;
    }

    @XmlElement(name = "listenergebnis")
    public ObservableList<Listenergebnis> getListenergebnisse() {
        return listenergebnisse;
    }

    public void addListenergebnis(Listenergebnis listenergebnis) {
        this.listenergebnisse.add(listenergebnis);
    }

    @XmlAttribute
    public int getStimmenGesamt() {
        return stimmenGesamt;
    }

    @XmlAttribute
    public int getStimmenGueltig() {
        return stimmenGueltig;
    }

    @XmlAttribute
    public int getStimmenUngueltig() {
        return stimmenUngueltig;
    }

    @XmlAttribute
    public int getStimmenEnthaltung() {
        return stimmenEnthaltung;
    }

    @XmlIDREF
    @XmlAttribute
    public Urne getUrne() {
        return urne;
    }

    public void setUrne(Urne urne) {
        this.urne = urne;
    }

    public void setStimmenGesamt(int stimmen) {
        this.stimmenGesamt = stimmen;
    }

    public void setStimmenGueltig(int stimmen) {
        this.stimmenGueltig = stimmen;
    }

    public void setStimmenUngueltig(int stimmen) {
        this.stimmenUngueltig = stimmen;
    }

    public void setStimmenEnthaltung(int stimmen) {
        this.stimmenEnthaltung = stimmen;
    }
}