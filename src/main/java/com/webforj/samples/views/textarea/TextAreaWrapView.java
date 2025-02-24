package com.webforj.samples.views.textarea;

import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.field.TextArea;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Word Wrap and Line Wrapping Demo")
public class TextAreaWrapView extends Composite<FlexLayout> {

  FlexLayout layout = getBoundComponent();
  TextArea textArea = new TextArea("");
  boolean isWordWrap = true;
  Button toggleWrapButton = new Button("Switch to CHARACTER_BOUNDARIES");

  public TextAreaWrapView() {
    layout.setDirection(FlexDirection.COLUMN)
          .setJustifyContent(FlexJustifyContent.CENTER)
          .setAlignment(FlexAlignment.CENTER)
          .setSpacing("var(--dwc-space-m)")
          .setMargin("var(--dwc-space-l)");

    textArea.setPlaceholder("Enter text to see wrapping behavior...")
            .setWidth("100%")
            .setRows(2)
            .setLineWrap(true)
            .setWrapStyle(TextArea.WrapStyle.WORD_BOUNDARIES);

    toggleWrapButton.setTheme(ButtonTheme.PRIMARY);
    toggleWrapButton.onClick(event -> toggleWrapStyle());

    layout.add(textArea, toggleWrapButton);
  }

  private void toggleWrapStyle() {
    isWordWrap = !isWordWrap;
    if (isWordWrap) {
      textArea.setWrapStyle(TextArea.WrapStyle.WORD_BOUNDARIES);
      toggleWrapButton.setText("Switch to CHARACTER_BOUNDARIES");
    } else {
      textArea.setWrapStyle(TextArea.WrapStyle.CHARACTER_BOUNDARIES);
      toggleWrapButton.setText("Switch to WORD_BOUNDARIES");
    }
  }
}