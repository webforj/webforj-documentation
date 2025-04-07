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

  private TextPredictionService() {
  }

  /**
   * Fetches the best suggestion from the API.
   *
   * @param input The input text
   * @return The best suggestion
   * @throws Exception If the API call fails
   */
  public static String predict(String input) throws Exception {
    String urlString = API_URL + URLEncoder.encode(input, StandardCharsets.UTF_8.toString()) + "*";
    URL url = new URL(urlString);
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("GET");

    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    String inputLine;
    StringBuilder content = new StringBuilder();
    while ((inputLine = in.readLine()) != null) {
      content.append(inputLine);
    }
    in.close();
    conn.disconnect();

    // Parse JSON response
    Gson gson = new Gson();
    List<WordSuggestion> suggestions = gson.fromJson(content.toString(), new TypeToken<List<WordSuggestion>>() {
    }.getType());

    // Find the word with the highest score
    WordSuggestion bestSuggestion = suggestions.stream()
        .max((a, b) -> Integer.compare(a.getScore(), b.getScore())).orElse(new WordSuggestion());

    return bestSuggestion.getWord();
  }

  /**
   * Represents a suggestion with a score.
   */
  public static final class WordSuggestion {
    private String word = "";
    private int score = 0;

    public String getWord() {
      return word;
    }

    public int getScore() {
      return score;
    }
  }
}
