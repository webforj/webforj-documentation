package com.webforj.samples.views.textarea;

import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.field.TextArea;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.layout.flexlayout.FlexContentAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.toast.Toast;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Text Area Line Limit Demo")
public class TextAreaLineLimitView extends Composite<FlexLayout> {

  FlexLayout layout = getBoundComponent();
  TextArea feedbackArea = new TextArea("Feedback");
  Paragraph instructionText = new Paragraph();
  int MAX_LINES = 5;
  int MAX_PARAGRAPH_LENGTH = 80;

  public TextAreaLineLimitView() {
    layout = getBoundComponent()
        .setDirection(FlexDirection.COLUMN)
        .setJustifyContent(FlexJustifyContent.CENTER)
        .setAlignContent(FlexContentAlignment.CENTER)
        .setSpacing("var(--dwc-space-m)")
        .setStyle("margin", "auto")
        .setStyle("max-width", "600px")
        .setStyle("padding", "var(--dwc-space-l)");

    instructionText.setText("Please provide your feedback below. "
        + "Limit your response to a maximum of 5 lines, and no more than "
        + "80 characters per paragraph.")
        .setStyle("font-size", "1rem")
        .setStyle("margin-bottom", "var(--dwc-space-m)");

    feedbackArea.setPlaceholder("Enter your feedback here...")
        .setLineCountLimit(MAX_LINES)
        .setParagraphLengthLimit(MAX_PARAGRAPH_LENGTH)
        .setLineWrap(true)
        .setWidth("100%");

    Button submitButton = new Button("Submit Feedback").setTheme(ButtonTheme.PRIMARY);
    submitButton.onClick(event -> {
      showToast("Feedback Submitted", "Thank you for your feedback!");
      feedbackArea.setValue("");
    });

    layout.add(instructionText, feedbackArea, submitButton);
  }

  private void showToast(String title, String message) {
    new Toast()
        .setText(title + ": " + message)
        .setDuration(3000)
        .setPlacement(Toast.Placement.CENTER)
        .open();
  }
}