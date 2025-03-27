package com.webforj.samples.views.textarea;

import com.webforj.annotation.InlineStyleSheet;
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

@InlineStyleSheet("dwc-textarea::part(input) { text-transform: capitalize; }")
@Route
@FrameTitle("Predicted Text Demo")
public class TextAreaPredictedTextView extends Composite<FlexLayout> {

  private final FlexLayout layout = getBoundComponent();
  private final TextArea textArea = new TextArea("Enter a US state:");
  private final List<String> states = Arrays.asList(
      "Alabama", "Alaska", "Arizona", "Arkansas", "California",
      "Colorado", "Connecticut", "Delaware", "Florida", "Georgia",
      "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa",
      "Kansas", "Kentucky", "Louisiana", "Maine", "Maryland",
      "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri",
      "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey",
      "New Mexico", "New York", "North Carolina", "North Dakota", "Ohio",
      "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina",
      "South Dakota", "Tennessee", "Texas", "Utah", "Vermont",
      "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming"
  );

  public TextAreaPredictedTextView() {
    layout.setDirection(FlexDirection.COLUMN)
          .setJustifyContent(FlexJustifyContent.CENTER)
          .setAlignment(FlexAlignment.CENTER)
          .setSpacing("var(--dwc-space-m)")
          .setMargin("var(--dwc-space-m)");

    textArea.setPlaceholder("Start typing a state name...")
            .setWidth("100%")
            .onValueChange(event -> predictText(event.getValue()));

    layout.add(textArea);
  }

  private void predictText(String input) {
    if (input == null || input.isEmpty()) {
      textArea.setPredictedText("");
      return;
    }

    String prediction = states.stream()
        .filter(state -> state.toLowerCase().startsWith(input.toLowerCase()))
        .findFirst()
        .orElse("");

    textArea.setPredictedText(prediction);
  }
}