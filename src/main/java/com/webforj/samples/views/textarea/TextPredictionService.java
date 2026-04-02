package com.webforj.samples.views.textarea;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

public final class TextPredictionService {
  private static final String API_URL = "https://api.datamuse.com/sug?s=";

  private TextPredictionService() {}

  /**
   * Fetches the best suggestion from the API.
   *
   * @param input The input text
   * @return The best suggestion
   * @throws Exception If the API call fails
   */
  public static String predict(String input) throws Exception {
    String urlString = API_URL + URLEncoder.encode(input, StandardCharsets.UTF_8) + "*";
    URL url = new URL(urlString);

    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("GET");

    try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
      String content = in.lines().reduce("", String::concat);

      // Parse JSON response using Gson
      Gson gson = new Gson();
      List<WordSuggestion> suggestions = gson.fromJson(content,
          new TypeToken<List<WordSuggestion>>() {}.getType());

      // Find the word with the highest score using Stream API
      return suggestions.stream()
          .map(WordSuggestion::word)
          .max((a, b) -> Integer.compare(
              suggestions.stream().filter(s -> s.word().equals(a)).findFirst().orElse(WordSuggestion.empty()).score(),
              suggestions.stream().filter(s -> s.word().equals(b)).findFirst().orElse(WordSuggestion.empty()).score()))
          .orElse("");
    } finally {
      conn.disconnect();
    }
  }

  /**
   * Represents a suggestion with a word and score.
   * Using Java record for immutable data carrier.
   */
  public record WordSuggestion(String word, int score) {
    // Compact record constructor with validation
    public WordSuggestion {
      if (word == null) {
        word = "";
      }
    }

    // Static factory method for default empty suggestion
    public static WordSuggestion empty() {
      return new WordSuggestion("", 0);
    }
  }
}
