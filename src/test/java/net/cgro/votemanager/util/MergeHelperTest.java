package net.cgro.votemanager.util;

import net.cgro.votemanager.model.*;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXB;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import static net.cgro.votemanager.util.MergeHelper.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MergeHelperTest {

    @Test
    void testValidateUrnenAreEqual_success() throws Exception {
        Wahl wahl = createWahl();
        wahl.addUrne(new Urne("Urne 1", 1));
        Wahl other = createWahl();
        other.addUrne(new Urne("Urne 1", 1));
        ErrorCollector errorCollector = new ErrorCollector();

        validateUrnenAreEqual(wahl, other, errorCollector);

        errorCollector.throwIfHasErrors();
    }

    @Test
    void testValidateUrnenAreEqual_fails() throws Exception {
        Wahl wahl = createWahl();
        wahl.addUrne(new Urne("Urne 1", 1));
        Wahl other = createWahl();
        other.addUrne(new Urne("Urne 2", 2));
        ErrorCollector errorCollector = new ErrorCollector();

        validateUrnenAreEqual(wahl, other, errorCollector);

        assertThatThrownBy(errorCollector::throwIfHasErrors)
                .isInstanceOf(WahlMergeException.class)
                .hasMessage("Urne[name='Urne 1',nummer=1] fehlt im Import\nUrne[name='Urne 2',nummer=2] aus dem Import ist unbekannt");
    }

    @Test
    void testValidateListenAreEqual_success() throws Exception {
        Wahl wahl = createWahl();
        Liste liste1 = new Liste("Liste 1", "liste1", 1);
        liste1.addKandidat(new Kandidat("kandidat1", 1));
        wahl.addListe(liste1);
        Liste liste2 = new Liste("Liste 1", "liste1", 1);
        liste2.addKandidat(new Kandidat("kandidat1", 1));
        Wahl other = createWahl();
        other.addListe(liste2);
        ErrorCollector errorCollector = new ErrorCollector();

        validateListenAreEqual(wahl, other, errorCollector);

        errorCollector.throwIfHasErrors();
    }

    @Test
    void testValidateListenAreEqual_failsWithListenNotEqual() throws Exception {
        Wahl wahl = createWahl();
        Liste liste1 = new Liste("Liste 1", "liste1", 1);
        liste1.addKandidat(new Kandidat("kandidat1", 1));
        wahl.addListe(liste1);
        Liste liste2 = new Liste("Liste 2", "liste2", 2);
        liste2.addKandidat(new Kandidat("kandidat1", 1));
        Wahl other = createWahl();
        other.addListe(liste2);
        ErrorCollector errorCollector = new ErrorCollector();

        validateListenAreEqual(wahl, other, errorCollector);

        assertThatThrownBy(errorCollector::throwIfHasErrors)
                .isInstanceOf(WahlMergeException.class)
                .hasMessage("Liste[name='Liste 1',kuerzel='liste1',nummer=1] fehlt im Import\n" +
                        "Liste[name='Liste 2',kuerzel='liste2',nummer=2] aus dem Import ist unbekannt");
    }

    @Test
    void testValidateListenAreEqual_failsWithKandidatenNotEqual() throws Exception {
        Wahl wahl = createWahl();
        Liste liste1 = new Liste("Liste 1", "liste1", 1);
        liste1.addKandidat(new Kandidat("kandidat1", 1));
        wahl.addListe(liste1);
        Liste liste2 = new Liste("Liste 1", "liste1", 1);
        liste2.addKandidat(new Kandidat("kandidat2", 2));
        Wahl other = createWahl();
        other.addListe(liste2);
        ErrorCollector errorCollector = new ErrorCollector();

        validateListenAreEqual(wahl, other, errorCollector);

        assertThatThrownBy(errorCollector::throwIfHasErrors)
                .isInstanceOf(WahlMergeException.class)
                .hasMessage("Liste[name='Liste 1',kuerzel='liste1',nummer=1]: Kandidat[name='kandidat1',nummer=1] fehlt im Import\n" +
                        "Liste[name='Liste 1',kuerzel='liste1',nummer=1]: Kandidat[name='kandidat2',nummer=2] aus dem Import ist unbekannt");
    }


    @Test
    void testValidateNoResultsAreOverwritten_success() throws Exception {
        Wahl wahl = createWahl();
        wahl.addUrne(new Urne("Urne 1", 1));
        Wahl other = createWahl();
        other.addUrne(new Urne("Urne 1", 1));
        ErrorCollector errorCollector = new ErrorCollector();

        validateUrnenAreEqual(wahl, other, errorCollector);

        errorCollector.throwIfHasErrors();
    }

    @Test
    void testValidateNoResultsAreOverwritten_fails() throws Exception {
        Wahl wahl = createWahl();
        Urne urne1 = new Urne("Urne 1", 1);
        wahl.addUrne(urne1);
        Liste liste1 = new Liste("Liste 1", "liste1", 1);
        liste1.addKandidat(new Kandidat("Kandidat 1", 1));
        wahl.addListe(liste1);
        Ergebnis ergebnis1 = new Ergebnis(urne1);
        wahl.setErgebnis(ergebnis1);

        Wahl other = createWahl();
        Urne urne2 = new Urne("Urne 1", 1);
        other.addUrne(urne2);
        Liste liste2 = new Liste("Liste 1", "liste1", 1);
        liste2.addKandidat(new Kandidat("Kandidat 1", 1));
        other.addListe(liste2);
        Ergebnis ergebnis2 = new Ergebnis(urne2);
        other.setErgebnis(ergebnis2);

        ErrorCollector errorCollector = new ErrorCollector();

        validateNoResultsAreOverwritten(wahl, other, errorCollector);

        assertThatThrownBy(errorCollector::throwIfHasErrors)
                .isInstanceOf(WahlMergeException.class)
                .hasMessage("Ergebnis von Urne[name='Urne 1',nummer=1] würde überschrieben");
    }

    private Wahl createWahl() {
        return JAXB.unmarshal(new ByteArrayInputStream("<wahl/>".getBytes(StandardCharsets.UTF_8)), Wahl.class);
    }
}