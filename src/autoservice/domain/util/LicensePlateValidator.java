package autoservice.domain.util;

import java.util.Set;
import java.util.regex.Pattern;

public class LicensePlateValidator {

    private static final Set<Character> ALLOWED_LETTERS = Set.of('A', 'B', 'E', 'K', 'M', 'H', 'O', 'P', 'C', 'T', 'Y', 'X');

    private static final Pattern RUSSIAN_PLATE_PATTERN =
            Pattern.compile("^[ABEKMHOPCTYX]\\d{3}[ABEKMHOPCTYX]{2}\\d{2,3}$");

    public static boolean isValidRussianLicensePlate(String licensePlate) {
        if (licensePlate == null || licensePlate.trim().isEmpty()) {
            return false;
        }

        String normalizedPlate = licensePlate.toUpperCase().trim();

        if (!RUSSIAN_PLATE_PATTERN.matcher(normalizedPlate).matches()) {
            return false;
        }

        return containsOnlyAllowedLetters(normalizedPlate);
    }

    private static boolean containsOnlyAllowedLetters(String plate) {
        char firstLetter = plate.charAt(0);
        char fourthLetter = plate.charAt(4);
        char fifthLetter = plate.charAt(5);

        return ALLOWED_LETTERS.contains(firstLetter) &&
                ALLOWED_LETTERS.contains(fourthLetter) &&
                ALLOWED_LETTERS.contains(fifthLetter);
    }

    public static String formatLicensePlate(String licensePlate) {
        if (licensePlate == null) return null;
        return licensePlate.toUpperCase().trim();
    }

    public static String getValidationRules() {
        return """
               Формат номерного знака: 
               - Первая буква: A, B, E, K, M, H, O, P, C, T, Y, X
               - Затем 3 цифры
               - Затем 2 буквы из того же набора
               - Затем 2 или 3 цифры региона
               Пример: А123ВC777, В456MН77
               """;
    }
}