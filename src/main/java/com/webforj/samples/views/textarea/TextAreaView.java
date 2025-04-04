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
import com.webforj.component.toast.Toast.Placement;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Feedback TextArea Demo")
public class TextAreaView extends Composite<FlexLayout> {

  FlexLayout layout = getBoundComponent();
  TextArea feedbackArea = new TextArea(""); 
  Paragraph charCount = new Paragraph();
  int maxCharacters = 200; 

  public TextAreaView() {
    layout.setDirection(FlexDirection.COLUMN)
      .setJustifyContent(FlexJustifyContent.CENTER)
      .setAlignment(FlexAlignment.CENTER)
      .setMaxHeight("400px")
      .setHeight("100vh");

    feedbackArea.setPlaceholder("Enter your feedback here...")
      .setWidth("100%")
      .setMaxWidth("800px")
      .setMaxLength(maxCharacters)
      .setLineWrap(true)
      .setWrapStyle(TextArea.WrapStyle.WORD_BOUNDARIES);

    updateCharCount(feedbackArea.getValue());
    feedbackArea.onValueChange(event -> updateCharCount(event.getValue()));

    FlexLayout textAreaWrapper = new FlexLayout();
    textAreaWrapper.setDirection(FlexDirection.COLUMN)
                   .setJustifyContent(FlexJustifyContent.CENTER)
                   .setAlignContent(FlexContentAlignment.CENTER)
                   .setWidth("100%")
                   .setMargin("var(--dwc-space-m)")
                   .setMaxWidth("400px");

    charCount.setStyle("font-size", "12px")
             .setStyle("color", "var(--dwc-text-secondary)");

    Button submitButton = new Button("Submit Feedback").setTheme(ButtonTheme.PRIMARY);
    submitButton.onClick(e -> {
      String input = feedbackArea.getValue().trim();
      if (!input.isEmpty()) {
        showToast("Feedback Submitted!");
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

    textAreaWrapper.add(feedbackArea, bottomRowLayout);
    layout.add(textAreaWrapper);
  }

  private void updateCharCount(String text) {
    charCount.setText("Characters: " + text.length() + " / " + maxCharacters);
  }

  private void showToast(String title) {
    Toast toast = new Toast();
    toast.setText(title)
      .setPlacement(Placement.CENTER)
      .setTheme(Theme.GRAY)
      .setDuration(3000)
      .open();
  }
}