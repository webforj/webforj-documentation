package com.webforj.samples.blogs.renderers;


public class FormatUtils {
  
  private FormatUtils() {
    // Private constructor to prevent instantiation
  }
  
  public static String formatLargeNumber(double number) {
    if (number >= 1_000_000_000_000L) {
      return String.format("$%.2fT", number / 1_000_000_000_000.0);
    } else if (number >= 1_000_000_000) {
      return String.format("$%.2fB", number / 1_000_000_000.0);
    } else if (number >= 1_000_000) {
      return String.format("$%.2fM", number / 1_000_000.0);
    } else if (number >= 1_000) {
      return String.format("$%.2fK", number / 1_000.0);
    } else {
      return String.format("$%.2f", number);
    }
  }
  
  public static String formatPrice(double price) {
    if (price >= 1000) {
      return String.format("$%,.2f", price);
    } else if (price >= 1) {
      return String.format("$%.2f", price);
    } else {
      return String.format("$%.4f", price);
    }
  }
  
  public static String formatPercentage(double value) {
    return String.format("%.1f%%", value);
  }
}