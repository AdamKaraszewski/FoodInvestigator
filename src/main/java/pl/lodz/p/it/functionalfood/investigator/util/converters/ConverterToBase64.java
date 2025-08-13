package pl.lodz.p.it.functionalfood.investigator.util.converters;

import java.util.Base64;

public class ConverterToBase64 {

    public static String convert(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static byte[] convert(String img) {
        if (img == null) {
            return null; // Zwróć pustą tablicę bajtów
        }
        return Base64.getDecoder().decode(img); // Dekoduj Base64 na tablicę bajtów
    }

}
