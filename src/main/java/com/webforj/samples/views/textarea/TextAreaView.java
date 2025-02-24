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
  int maxLines = 5; 

  public TextAreaView() {
    layout.setDirection(FlexDirection.COLUMN)
      .setAlignContent(FlexContentAlignment.CENTER)
      .setJustifyContent(FlexJustifyContent.CENTER)
      .setMargin("var(--dwc-space-m)");
        
    feedbackArea.setPlaceholder("Enter your feedback here...")
      .setWidth("100%")
      .setMaxLength(maxCharacters)
      .setLineCountLimit(maxLines)
      .setLineWrap(true)
      .setWrapStyle(TextArea.WrapStyle.WORD_BOUNDARIES);

    updateCharCount(feedbackArea.getValue());

    feedbackArea.onValueChange(event -> updateCharCount(event.getValue()));

    Button submitButton = new Button("Submit Feedback").setTheme(ButtonTheme.PRIMARY);
    submitButton.onClick(e -> {
      showToast("Feedback Submitted!", feedbackArea.getValue());
      feedbackArea.setValue(""); 
      updateCharCount(""); 
    });

    layout.add(charCount, feedbackArea, submitButton);
  }

  private void updateCharCount(String text) {
    charCount.setText("Characters: " + text.length() + " / " + maxCharacters);
  }

  private void showToast(String title, String message) {
    Toast toast = new Toast();
    toast.setText(title + "\n" + message)
      .setPlacement(Placement.CENTER)
      .setDuration(3000)
      .open();
    }
}