package com.webforj.samples.views.textarea;

import com.webforj.component.Composite;
import com.webforj.component.field.NumberField;
import com.webforj.component.field.TextArea;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexContentAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.layout.flexlayout.FlexWrap;
import com.webforj.router.annotation.Route;

import java.util.List;

@Route
public class TextAreaValidationView extends Composite<FlexLayout> {

  TextArea textArea = new TextArea();
  Paragraph currLength = new Paragraph();
  Paragraph currLineCount = new Paragraph();
  Paragraph currParagraphSizes = new Paragraph();

  int maxLength = 256;
  int maxParagraphLength = 64;
  int maxLines = 10;

  public TextAreaValidationView() {
    FlexLayout layout = getBoundComponent();
    layout.setDirection(FlexDirection.COLUMN)
          .setJustifyContent(FlexJustifyContent.CENTER)
          .setAlignment(FlexAlignment.CENTER)
          .setSpacing("var(--dwc-space-m)")
          .setMargin("var(--dwc-space-m)");

    textArea.setWidth("100%")
            .setMaxWidth("800px")
            .setHeight("200px")
            .setMaxLength(maxLength)
            .setParagraphLengthLimit(maxParagraphLength)
            .setLineCountLimit(maxLines)
            .onValueChange(e -> updateStatus());

    NumberField maxLengthField = new NumberField("Max Length")
        .setMin(1.0)
        .setMax(500.0)
        .setValue((double) maxLength)
        .setWidth("200px");
    maxLengthField.onValueChange(e -> {
      Double val = e.getValue();
      if (val != null && val >= 1 && val <= 500) {
        maxLength = val.intValue();
        textArea.setMaxLength(maxLength);
        updateStatus();
      }
    });

    NumberField maxLinesField = new NumberField("Max Lines")
        .setMin(1.0)
        .setMax(20.0)
        .setValue((double) maxLines)
        .setWidth("200px");
    maxLinesField.onValueChange(e -> {
      Double val = e.getValue();
      if (val != null && val >= 1 && val <= 20) {
        maxLines = val.intValue();
        textArea.setLineCountLimit(maxLines);
        updateStatus();
      }
    });

    NumberField maxParagraphSizeField = new NumberField("Max Paragraph Length")
        .setMin(1.0)
        .setMax(200.0)
        .setValue((double) maxParagraphLength)
        .setWidth("200px");
    maxParagraphSizeField.onValueChange(e -> {
      Double val = e.getValue();
      if (val != null && val >= 1 && val <= 200) {
        maxParagraphLength = val.intValue();
        textArea.setParagraphLengthLimit(maxParagraphLength);
        updateStatus();
      }
    });

    currLength.setText("Current Length: 0");
    currLineCount.setText("Current Line Count: 0");

    FlexLayout statsRow = new FlexLayout(currLength, currLineCount, currParagraphSizes);
    statsRow.setDirection(FlexDirection.ROW)
            .setSpacing("var(--dwc-space-l)")
            .setJustifyContent(FlexJustifyContent.START)
            .setMaxWidth("800px")
            .setWrap(FlexWrap.WRAP)
            .setWidth("100%");

    FlexLayout controlRow = new FlexLayout(maxLengthField, maxLinesField, maxParagraphSizeField);
    controlRow.setDirection(FlexDirection.ROW)
              .setSpacing("var(--dwc-space-s)")
              .setJustifyContent(FlexJustifyContent.BETWEEN)
              .setMaxWidth("800px")
              .setWrap(FlexWrap.WRAP)
              .setWidth("100%");

    layout.add(
        textArea,
        statsRow,
        controlRow
    );

    updateStatus();
  }

  private void updateStatus() {
    String text = textArea.getValue();
    List<String> paragraphs = textArea.getParagraphs();

    currLength.setText("Current Length: " + text.length());
    currLineCount.setText("Current Line Count: " + paragraphs.size());

    StringBuilder sb = new StringBuilder("Paragraph Sizes: ");
    for (int i = 0; i < paragraphs.size(); i++) {
      sb.append("[").append(i).append("=").append(paragraphs.get(i).length()).append("] ");
    }
    currParagraphSizes.setText(sb.toString().trim());
  }
}