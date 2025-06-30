package utils;

/**
 * Utility class for string operations
 */
public class StringUtils {
    
    /**
     * Parse numeric value from string, handling commas and other formatting
     * 
     * @param valueString The string to parse
     * @return The parsed numeric value, or 0 if the string is null or empty
     */
    public static double parseNumber(String valueString) {
        if (valueString == null || valueString.isEmpty()) {
            return 0;
        }
        // Remove commas and any other non-numeric characters except decimal point and minus
        String cleaned = valueString.replaceAll("[^\\d.-]", "");
        try {
            return Double.parseDouble(cleaned);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    
    /**
     * Check if a string can be parsed as a number
     * 
     * @param valueString The string to check
     * @return true if the string can be parsed as a number
     */
    public static boolean isNumeric(String valueString) {
        if (valueString == null || valueString.isEmpty()) {
            return false;
        }
        try {
            // Remove commas and try to parse
            Double.parseDouble(valueString.replaceAll("[^\\d.-]", ""));
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Convert string to integer, handling parsing errors
     * 
     * @param valueString The string to parse
     * @param defaultValue The default value to return if parsing fails
     * @return The parsed integer value or defaultValue if parsing fails
     */
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
    
    /**
     * Convert string to double, handling parsing errors
     * 
     * @param valueString The string to parse
     * @param defaultValue The default value to return if parsing fails
     * @return The parsed double value or defaultValue if parsing fails
     */
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