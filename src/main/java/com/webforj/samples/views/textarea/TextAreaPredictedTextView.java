package com.webforj.samples.views.textarea;

import com.webforj.annotation.InlineStyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.field.TextArea;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@InlineStyleSheet("dwc-textarea::part(input) { text-transform: capitalize; }")
@Route("textareapredictedtext")
@FrameTitle("Predicted Text Demo")
public class TextAreaPredictedTextView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final TextArea textArea = new TextArea("Predicted Text");

  public TextAreaPredictedTextView() {
    self.setDirection(FlexDirection.COLUMN)
        .setSpacing("var(--dwc-space-m)")
        .setMargin("50px auto")
        .setMaxWidth("400px");

    textArea
        .setHeight("200px")
        .setHelperText("Type something to see suggestions, for instance, type 'Sky is'. "
            + "Then wait for a few seconds to see the suggestions. You can insert the suggestion"
            + "by pressing Tab or ArrowRight key or simply ignore it by typing further.")
        .setPlaceholder("Start typing to see suggestions...")
        .onValueChange(event -> {
          try {
            String input = event.getValue();
            if (input.isEmpty()) {
              textArea.setPredictedText("");
              return;
            }

            String bestSuggestion = TextPredictionService.predict(input);
            String predictedValue = "";
            if (!bestSuggestion.isEmpty() && bestSuggestion.length() >= input.length()) {
              predictedValue = input + bestSuggestion.substring(input.length());
            }

            textArea.setPredictedText(predictedValue);
          } catch (Exception e) {
            textArea.setPredictedText("");
          }
        });

    self.add(textArea);
  }
}
