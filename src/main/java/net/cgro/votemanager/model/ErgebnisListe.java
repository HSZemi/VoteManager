/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.cgro.votemanager.model;

import javafx.scene.control.TreeItem;

/**
 * @author fabian
 */
public class ErgebnisListe {

    private int stimmenGesamt = 0;
    private int stimmenUngueltig = 0;
    private int stimmenGueltig = 0;
    private int stimmenEnthaltung = 0;

    private TreeItem<ErgebnisEintrag> root = new TreeItem<>(new ErgebnisEintrag("", 0));

    public ErgebnisListe(Urne urne) {
        // Reset der Listenstimmen
        for (Liste l : Wahl.getInstance().getListen()) {
            l.resetStimmenTemp();
            l.resetStimmenTemp2();

            for (Kandidat k : l.getKandidaten()) {
                k.resetStimmenTemp();
            }
        }

        for (Ergebnis ergebnis : Wahl.getInstance().getErgebnisse()) {
            // Wenn spezielle Urne ausgewählt, andere auslassen
            if (urne != null && ergebnis.getUrne() != urne)
                continue;

            stimmenGesamt += ergebnis.getStimmenGesamt();
            stimmenUngueltig += ergebnis.getStimmenUngueltig();
            stimmenGueltig += ergebnis.getStimmenGueltig();
            stimmenEnthaltung += ergebnis.getStimmenEnthaltung();

            for (Listenergebnis lerg : ergebnis.getListenergebnisse()) {
                lerg.getListe().addStimmenTemp(lerg.getGesamtstimmen());
                lerg.getListe().addStimmenTemp2(lerg.getListenstimmen());

                for (Kandidatenergebnis kerg : lerg.getKandidatenergebnisse()) {
                    kerg.getKandidat().addStimmenTemp(kerg.getStimmen());
                }
            }
        }

        TreeItem<ErgebnisEintrag> basis = new TreeItem<>(new ErgebnisEintrag("BASISDATEN: Anzahl der abgegebenen Stimmen", stimmenGesamt));
        basis.setExpanded(true);
        root.getChildren().add(basis);

        basis.getChildren().add(new TreeItem<>(new ErgebnisEintrag("Gültige Stimmen", stimmenGueltig)));
        basis.getChildren().add(new TreeItem<>(new ErgebnisEintrag("Ungültige Stimmen", stimmenUngueltig)));
        basis.getChildren().add(new TreeItem<>(new ErgebnisEintrag("Enthaltungen", stimmenEnthaltung)));

        for (Liste l : Wahl.getInstance().getListen()) {
            TreeItem<ErgebnisEintrag> litem = new TreeItem<>(new ErgebnisEintrag("LISTE " + l.getNummer() + ": " + l.getName(), l.getStimmenTemp()));
            litem.setExpanded(true);

            TreeItem<ErgebnisEintrag> litem2 = new TreeItem<>(new ErgebnisEintrag("LISTENSTIMMEN", l.getStimmenTemp2()));
            litem.getChildren().add(litem2);

            for (Kandidat k : l.getKandidaten()) {
                TreeItem<ErgebnisEintrag> kitem = new TreeItem<>(new ErgebnisEintrag(k.getName(), k.getStimmenTemp()));
                litem.getChildren().add(kitem);
            }

            root.getChildren().add(litem);
        }

    }

    public TreeItem<ErgebnisEintrag> getEintraege() {
        return root;
    }

}
