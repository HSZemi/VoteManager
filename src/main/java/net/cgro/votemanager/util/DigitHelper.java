package net.cgro.votemanager.util;

import javafx.scene.input.KeyCode;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public final class DigitHelper {
    private DigitHelper() {
    }

    public static Map<KeyCode, Integer> DIGIT_CODES = getDigitCodeMap();

    private static Map<KeyCode, Integer> getDigitCodeMap() {
        LinkedHashMap<KeyCode, Integer> digitCodes = new LinkedHashMap<>();
        digitCodes.put(KeyCode.NUMPAD0, 0);
        digitCodes.put(KeyCode.NUMPAD1, 1);
        digitCodes.put(KeyCode.NUMPAD2, 2);
        digitCodes.put(KeyCode.NUMPAD3, 3);
        digitCodes.put(KeyCode.NUMPAD4, 4);
        digitCodes.put(KeyCode.NUMPAD5, 5);
        digitCodes.put(KeyCode.NUMPAD6, 6);
        digitCodes.put(KeyCode.NUMPAD7, 7);
        digitCodes.put(KeyCode.NUMPAD8, 8);
        digitCodes.put(KeyCode.NUMPAD9, 9);

        digitCodes.put(KeyCode.DIGIT0, 0);
        digitCodes.put(KeyCode.DIGIT1, 1);
        digitCodes.put(KeyCode.DIGIT2, 2);
        digitCodes.put(KeyCode.DIGIT3, 3);
        digitCodes.put(KeyCode.DIGIT4, 4);
        digitCodes.put(KeyCode.DIGIT5, 5);
        digitCodes.put(KeyCode.DIGIT6, 6);
        digitCodes.put(KeyCode.DIGIT7, 7);
        digitCodes.put(KeyCode.DIGIT8, 8);
        digitCodes.put(KeyCode.DIGIT9, 9);
        return Collections.unmodifiableMap(digitCodes);
    }



    public static int toDigit(KeyCode code) {
        Integer integer = DIGIT_CODES.get(code);
        if (integer == null) {
            String msg = "KeyCode {} is not a valid digit!";
            throw new NumberFormatException(msg);
        }
        return integer;
    }

    public static boolean isDigit(KeyCode code) {
        return DIGIT_CODES.keySet().contains(code);
    }

}
