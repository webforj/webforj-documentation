package com.webforj.samples.views.textarea;

import com.webforj.component.Composite;
import com.webforj.component.field.TextArea;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import java.util.Arrays;
import java.util.List;

@Route
@FrameTitle("Predicted Text Demo")
public class TextAreaPredictedTextView extends Composite<FlexLayout> {

  FlexLayout layout = getBoundComponent();
  TextArea textArea = new TextArea("Enter a US city:");
  List<String> cities = Arrays.asList(
      "New York", "Los Angeles", "Chicago", "Houston", "Phoenix",
      "Philadelphia", "San Antonio", "San Diego", "Dallas", "San Jose",
      "Austin", "Jacksonville", "San Francisco", "Columbus", "Fort Worth", "Orlando"
  );

  public TextAreaPredictedTextView() {
    layout.setDirection(FlexDirection.COLUMN)
          .setJustifyContent(FlexJustifyContent.CENTER)
          .setAlignment(FlexAlignment.CENTER)
          .setSpacing("var(--dwc-space-m)")
          .setMargin("var(--dwc-space-m)");

    textArea.setPlaceholder("Start typing a city name...")
            .setWidth("100%")
            .onValueChange(event -> predictText(event.getValue()));

    layout.add(textArea);
  }

  private void predictText(String input) {
    if (input == null || input.isEmpty()) {
      textArea.setPredictedText("");
      return;
    }

    String prediction = cities.stream()
        .filter(city -> city.toLowerCase().startsWith(input.toLowerCase()))
        .findFirst()
        .orElse("");

    if (!prediction.isEmpty()) {
      textArea.setPredictedText(prediction);
    } else {
      textArea.setPredictedText("");
    }
  }
}