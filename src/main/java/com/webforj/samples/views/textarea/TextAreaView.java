package com.webforj.samples.views.textarea;

import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.field.TextArea;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexContentAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.toast.Toast;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Feedback TextArea Demo")
public class TextAreaView extends Composite<FlexLayout> {

  FlexLayout self = getBoundComponent();
  TextArea feedbackArea = new TextArea("What do you think about this demo?");
  Paragraph charCount = new Paragraph();
  Button submitButton = new Button("Submit Feedback", ButtonTheme.GRAY);
  int maxCharacters = 200;

  public TextAreaView() {
    self.setDirection(FlexDirection.COLUMN)
        .setJustifyContent(FlexJustifyContent.CENTER)
        .setAlignment(FlexAlignment.CENTER)
        .setWidth("100%")
        .setMargin("50px auto")
        .setMaxWidth("400px");

    feedbackArea.setPlaceholder("Enter your feedback here...")
        .setWidth("100%")
        .setMaxWidth("800px")
        .setMaxLength(maxCharacters)
        .setLineWrap(true)
        .setWrapStyle(TextArea.WrapStyle.WORD_BOUNDARIES)
        .onValueChange(event -> updateCharCount(event.getValue()));

    updateCharCount(feedbackArea.getValue());

    charCount
        .setStyle("font-size", "12px")
        .setStyle("color", "var(--dwc-color-gray-text)");

    submitButton.onClick(e -> {
      String input = feedbackArea.getValue().trim();
      if (!input.isEmpty()) {
        Toast.show("Thank you for your feedback!", Theme.SUCCESS);
        feedbackArea.setValue("");
        updateCharCount("");
      }
    });

    FlexLayout bottomRowLayout = new FlexLayout();
    bottomRowLayout.setDirection(FlexDirection.ROW)
        .setJustifyContent(FlexJustifyContent.BETWEEN)
        .setAlignContent(FlexContentAlignment.CENTER)
        .setWidth("100%");

    bottomRowLayout.add(charCount, submitButton);

    self.add(feedbackArea, bottomRowLayout);
  }

  private void updateCharCount(String text) {
    charCount.setText("Characters: " + text.length() + " / " + maxCharacters);
  }
}
