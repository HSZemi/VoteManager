package net.cgro.votemanager.util;

import net.cgro.votemanager.model.*;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXB;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

import static net.cgro.votemanager.util.MergeHelper.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class WahlDeserializerTest {

    @Test
    void testDeserializeOldWahl() throws Exception {
        Wahl wahl = JAXB.unmarshal(Paths.get("src/test/resources/oldwahl.xml").toFile(), Wahl.class);
        assertThat(wahl.getUrnen().size()).isEqualTo(2);
        assertThat(wahl.getListen().size()).isEqualTo(2);
        Urne urne1 = wahl.getUrnen().get(0);
        Urne urne2 = wahl.getUrnen().get(1);
        assertThat(urne1.getName()).isEqualTo("Urne 1");
        assertThat(urne1.getNummer()).isEqualTo(1);
        assertThat(urne1.getStandort()).isNull();
        assertThat(urne2.getName()).isEqualTo("Urne 2");
        assertThat(urne2.getNummer()).isEqualTo(2);
        assertThat(urne2.getStandort()).isNull();
    }
}