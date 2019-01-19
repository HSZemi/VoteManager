package net.cgro.votemanager.util;

import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.Test;

import static net.cgro.votemanager.util.DigitHelper.DIGIT_CODES;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class DigitHelperTest {

    @Test
    void testDigitCodeMapping() {
        assertThat(DIGIT_CODES.keySet()).hasSize(2 * 10);

        assertThat(DIGIT_CODES.get(KeyCode.NUMPAD0)).isEqualTo(0);
        assertThat(DIGIT_CODES.get(KeyCode.NUMPAD1)).isEqualTo(1);
        assertThat(DIGIT_CODES.get(KeyCode.NUMPAD2)).isEqualTo(2);
        assertThat(DIGIT_CODES.get(KeyCode.NUMPAD3)).isEqualTo(3);
        assertThat(DIGIT_CODES.get(KeyCode.NUMPAD4)).isEqualTo(4);
        assertThat(DIGIT_CODES.get(KeyCode.NUMPAD5)).isEqualTo(5);
        assertThat(DIGIT_CODES.get(KeyCode.NUMPAD6)).isEqualTo(6);
        assertThat(DIGIT_CODES.get(KeyCode.NUMPAD7)).isEqualTo(7);
        assertThat(DIGIT_CODES.get(KeyCode.NUMPAD8)).isEqualTo(8);
        assertThat(DIGIT_CODES.get(KeyCode.NUMPAD9)).isEqualTo(9);
        assertThat(DIGIT_CODES.get(KeyCode.DIGIT0)).isEqualTo(0);
        assertThat(DIGIT_CODES.get(KeyCode.DIGIT1)).isEqualTo(1);
        assertThat(DIGIT_CODES.get(KeyCode.DIGIT2)).isEqualTo(2);
        assertThat(DIGIT_CODES.get(KeyCode.DIGIT3)).isEqualTo(3);
        assertThat(DIGIT_CODES.get(KeyCode.DIGIT4)).isEqualTo(4);
        assertThat(DIGIT_CODES.get(KeyCode.DIGIT5)).isEqualTo(5);
        assertThat(DIGIT_CODES.get(KeyCode.DIGIT6)).isEqualTo(6);
        assertThat(DIGIT_CODES.get(KeyCode.DIGIT7)).isEqualTo(7);
        assertThat(DIGIT_CODES.get(KeyCode.DIGIT8)).isEqualTo(8);
        assertThat(DIGIT_CODES.get(KeyCode.DIGIT9)).isEqualTo(9);
    }

    @Test
    void testDigitCodesAreUnmodifiable() {
        assertThatExceptionOfType(UnsupportedOperationException.class)
                .isThrownBy(() -> DIGIT_CODES.put(KeyCode.ENTER, 9999));

        assertThat(DIGIT_CODES).containsKey(KeyCode.NUMPAD0);
        assertThatExceptionOfType(UnsupportedOperationException.class)
                .isThrownBy(() -> DIGIT_CODES.remove(KeyCode.NUMPAD0));
    }

}