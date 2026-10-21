package com.webforj.samples.unit.views.textarea;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.field.TextArea;
import com.webforj.samples.views.textarea.TextAreaPredictedTextView;
import com.webforj.samples.views.textarea.TextPredictionService;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@WebforjTest
class TextAreaPredictedTextViewTest {
  @Test
  void shouldClearThePredictionForEmptyInput(WebforjTestbed context) {
    try (var predictions = mockStatic(TextPredictionService.class)) {
      predictions.when(() -> TextPredictionService.predict("Sky")).thenReturn("Sky is blue");
      context.add(new TextAreaPredictedTextView());

      var area = ComponentLocator.of(TextArea.class).locate();

      UserInteraction.type(area, "Sky");
      assertEquals("Sky is blue", area.getPredictedText());

      UserInteraction.type(area, "");

      assertTrue(area.getPredictedText().isEmpty());
      predictions.verify(() -> TextPredictionService.predict(""), never());
    }
  }

  @Test
  void shouldOfferTheServiceSuggestionWithoutReplacingTheInput(WebforjTestbed context) {
    try (var predictions = mockStatic(TextPredictionService.class)) {
      predictions.when(() -> TextPredictionService.predict("Sky is")).thenReturn("Sky is blue");
      context.add(new TextAreaPredictedTextView());

      var area = ComponentLocator.of(TextArea.class).locate();

      UserInteraction.type(area, "Sky is");

      assertEquals("Sky is blue", area.getPredictedText());
      assertEquals("Sky is", area.getValue());
      predictions.verify(() -> TextPredictionService.predict("Sky is"));
    }
  }

  @ParameterizedTest
  @ValueSource(strings = {"", "Sky"})
  void shouldClearThePreviousPredictionForEmptyOrShortSuggestions(
      String suggestion, WebforjTestbed context) {
    try (var predictions = mockStatic(TextPredictionService.class)) {
      predictions.when(() -> TextPredictionService.predict("Sky")).thenReturn("Sky is blue");
      predictions.when(() -> TextPredictionService.predict("Sky is")).thenReturn(suggestion);
      context.add(new TextAreaPredictedTextView());

      var area = ComponentLocator.of(TextArea.class).locate();

      UserInteraction.type(area, "Sky");
      assertEquals("Sky is blue", area.getPredictedText());

      UserInteraction.type(area, "Sky is");

      assertTrue(area.getPredictedText().isEmpty());
      assertEquals("Sky is", area.getValue());
      predictions.verify(() -> TextPredictionService.predict("Sky is"));
    }
  }

  @Test
  void shouldClearThePreviousPredictionWhenTheServiceFails(WebforjTestbed context) {
    try (var predictions = mockStatic(TextPredictionService.class)) {
      predictions.when(() -> TextPredictionService.predict("Sky")).thenReturn("Sky is blue");
      predictions
          .when(() -> TextPredictionService.predict("Sky is"))
          .thenThrow(new IOException("Prediction service unavailable"));
      context.add(new TextAreaPredictedTextView());

      var area = ComponentLocator.of(TextArea.class).locate();

      UserInteraction.type(area, "Sky");
      assertEquals("Sky is blue", area.getPredictedText());

      UserInteraction.type(area, "Sky is");

      assertTrue(area.getPredictedText().isEmpty());
      assertEquals("Sky is", area.getValue());
      predictions.verify(() -> TextPredictionService.predict("Sky is"));
    }
  }
}
