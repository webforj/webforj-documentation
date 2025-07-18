package utils;

/**
 * Utility class for string operations
 */
public class StringUtils {


    public static double parseNumber(String valueString) {
        if (valueString == null || valueString.isEmpty()) {
            return 0;
        }

        String cleaned = valueString.replaceAll("[^\\d.-]", "");
        try {
            return Double.parseDouble(cleaned);
        } catch (NumberFormatException e) {
            return 0;
        }
    }


    public static boolean isNumeric(String valueString) {
        if (valueString == null || valueString.isEmpty()) {
            return false;
        }
        try {

            Double.parseDouble(valueString.replaceAll("[^\\d.-]", ""));
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    public static int toInt(String valueString, int defaultValue) {
        if (valueString == null || valueString.isEmpty()) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(valueString.replaceAll("[^\\d.-]", ""));
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }


    public static double toDouble(String valueString, double defaultValue) {
        if (valueString == null || valueString.isEmpty()) {
            return defaultValue;
        }
        try {
            return Double.parseDouble(valueString.replaceAll("[^\\d.-]", ""));
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}