package net.cgro.votemanager.util;

import javafx.collections.ObservableList;
import net.cgro.votemanager.model.*;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public final class MergeHelper {
    private MergeHelper() {
    }

    public static void validateUrnenAreEqual(Wahl wahl, Wahl other, ErrorCollector errorCollector) {
        ObservableList<Urne> wahlUrnen = wahl.getUrnen();
        ObservableList<Urne> otherUrnen = other.getUrnen();

        if (!otherUrnen.containsAll(wahlUrnen)) {
            for (Urne wahlUrne : wahlUrnen) {
                if (!otherUrnen.contains(wahlUrne)) {
                    errorCollector.addError(String.format("%s fehlt im Import", wahlUrne));
                }
            }
        }

        if (!wahlUrnen.containsAll(otherUrnen)) {
            for (Urne otherUrne : otherUrnen) {
                if (!wahlUrnen.contains(otherUrne)) {
                    errorCollector.addError(String.format("%s aus dem Import ist unbekannt", otherUrne));
                }
            }
        }
    }

    public static void validateListenAreEqual(Wahl wahl, Wahl other, ErrorCollector errorCollector) {
        ObservableList<Liste> wahlListen = wahl.getListen();
        ObservableList<Liste> otherListen = other.getListen();

        if (!otherListen.containsAll(wahlListen)) {
            for (Liste wahlListe : wahlListen) {
                if (!otherListen.contains(wahlListe)) {
                    errorCollector.addError(String.format("%s fehlt im Import", wahlListe));
                }
            }
        }

        if (!wahlListen.containsAll(otherListen)) {
            for (Liste otherListe : otherListen) {
                if (!wahlListen.contains(otherListe)) {
                    errorCollector.addError(String.format("%s aus dem Import ist unbekannt", otherListe));
                }
            }
        }

        for (Liste wahlListe : wahlListen) {
            if (otherListen.contains(wahlListe)) {
                Liste otherListe = otherListen.get(otherListen.indexOf(wahlListe));
                validateListenKandidatenAreEqual(wahlListe, otherListe, errorCollector);
            }
        }

    }

    private static void validateListenKandidatenAreEqual(Liste liste, Liste other, ErrorCollector errorCollector) {
        ObservableList<Kandidat> listeKandidaten = liste.getKandidaten();
        ObservableList<Kandidat> otherKandidaten = other.getKandidaten();

        if (!otherKandidaten.containsAll(listeKandidaten)) {
            for (Kandidat listeKandidat : listeKandidaten) {
                if (!otherKandidaten.contains(listeKandidat)) {
                    errorCollector.addError(String.format("%s: %s fehlt im Import", liste, listeKandidat));
                }
            }
        }
        if (!listeKandidaten.containsAll(otherKandidaten)) {
            for (Kandidat otherKandidat : otherKandidaten) {
                if (!listeKandidaten.contains(otherKandidat)) {
                    errorCollector.addError(String.format("%s: %s aus dem Import ist unbekannt", liste, otherKandidat));
                }
            }
        }
    }

    public static void validateNoResultsAreOverwritten(Wahl wahl, Wahl other, ErrorCollector errorCollector) {
        List<Urne> wahlUrnenWithErgebnis = getUrnenWithErgebnis(wahl);

        List<Urne> otherUrnenWithErgebnis = getUrnenWithErgebnis(other);

        for (Urne otherUrne : otherUrnenWithErgebnis) {
            if (wahlUrnenWithErgebnis.contains(otherUrne)) {
                errorCollector.addError(String.format("Ergebnis von %s würde überschrieben", otherUrne));
            }
        }

    }

    private static List<Urne> getUrnenWithErgebnis(Wahl wahl) {
        return wahl.getUrnen().stream()
                .filter(wahl::existiertErgebnis)
                .collect(Collectors.toList());
    }

    public static void merge(Wahl wahl, Wahl other) {
        for (Urne urne : other.getUrnen()) {
            if (other.existiertErgebnis(urne)) {
                Urne wahlUrne = findMatchingUrne(wahl, urne);
                Ergebnis ergebnis = other.getErgebnis(urne);
                ergebnis.setUrne(wahlUrne);
                wahl.setErgebnis(ergebnis);
            }
        }
    }

    private static Urne findMatchingUrne(Wahl wahl, Urne urne) {
        return wahl.getUrnen().stream()
                .filter(wahlUrne -> wahlUrne.equals(urne))
                .collect(MergeHelper.toSingleElement());
    }

    private static <T> Collector<T, ?, T> toSingleElement() {
        return Collectors.collectingAndThen(
                Collectors.toList(),
                list -> {
                    if (list.size() != 1) {
                        throw new IllegalStateException();
                    }
                    return list.get(0);
                }
        );
    }
}
