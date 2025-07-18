package com.webforj.samples.views.textarea;

import com.webforj.annotation.StyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.field.NumberField;
import com.webforj.component.field.TextArea;
import com.webforj.component.icons.TablerIcon;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.Route;

import java.util.List;

@Route("textareavalidation")
@StyleSheet("TextAreaValidationView.css")
public class TextAreaValidationView extends Composite<FlexLayout> {
  private FlexLayout self = getBoundComponent();
  private TextArea textArea = new TextArea("Validation Playground", "The quick brown fox jumps over the lazy dog.");
  private TextArea status = new TextArea("Current Status");

  int maxLength = 256;
  int maxParagraphLength = 64;
  int maxLines = 10;

  public TextAreaValidationView() {
    self.setDirection(FlexDirection.COLUMN)
        .setSpacing("var(--dwc-space-m)")
        .setMargin("50px auto")
        .setStyle("padding", "var(--dwc-space-m)")
        .setMaxWidth("500px")
        .setStyle("background-color", "var(--dwc-surface-3)")
        .setStyle("border-radius", "var(--dwc-border-radius)")
        .setStyle("border", "1px solid var(--dwc-color-default)");

    status
        .setHeight("120px")
        .setReadOnly(true);

    textArea.setMaxLength(maxLength)
        .setParagraphLengthLimit(maxParagraphLength)
        .setLineCountLimit(maxLines)
        .setHeight("200px")
        .onValueChange(e -> updateStatus());

    NumberField maxLengthField = new NumberField("Max Length");
    maxLengthField
        .setPrefixComponent(TablerIcon.create("ruler"))
        .setTooltipText("Sets the maximum number of characters allowed across all text.")
        .setMin(1.0)
        .setMax(500.0)
        .setValue((double) maxLength)
        .setStyle("flex", "1 0 auto")
        .onValueChange(e -> {
          Double val = e.getValue();
          if (val != null && val >= 1 && val <= 500) {
            maxLength = val.intValue();
            textArea.setMaxLength(maxLength);
            updateStatus();
          }
        });

    NumberField maxLinesField = new NumberField("Max Lines");
    maxLinesField.setMin(1.0)
        .setPrefixComponent(TablerIcon.create("list-numbers"))
        .setTooltipText("Restricts how many lines the text area can contain.")
        .setMax(20.0)
        .setValue((double) maxLines)
        .setStyle("flex", "1 0 auto")
        .onValueChange(e -> {
          Double val = e.getValue();
          if (val != null && val >= 1 && val <= 20) {
            maxLines = val.intValue();
            textArea.setLineCountLimit(maxLines);
            updateStatus();
          }
        });

    NumberField maxParagraphSizeField = new NumberField("Max Paragraph Length");
    maxParagraphSizeField
        .setPrefixComponent(TablerIcon.create("indent-increase"))
        .setTooltipText("Limits how many characters each paragraph (or line) can have.")
        .setMin(1.0)
        .setMax(200.0)
        .setValue((double) maxParagraphLength)
        .setStyle("flex", "1 0 auto")
        .onValueChange(e -> {
          Double val = e.getValue();
          if (val != null && val >= 1 && val <= 200) {
            maxParagraphLength = val.intValue();
            textArea.setParagraphLengthLimit(maxParagraphLength);
            updateStatus();
          }
        });

    FlexLayout controlRow = FlexLayout.create(maxLengthField, maxLinesField, maxParagraphSizeField)
        .horizontal()
        .justify().between()
        .wrap()
        .build();

    self.add(
        textArea,
        controlRow,
        status);

    whenAttached().thenAccept(e -> updateStatus());
  }

  private void updateStatus() {
    String text = textArea.getValue();
    List<String> paragraphs = textArea.getParagraphs();
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < paragraphs.size(); i++) {
      sb.append("[").append(i).append("=").append(paragraphs.get(i).length()).append("]");
    }

    String paragraphSizes = sb.toString();
    status.setValue("Current Length (including \n): " + text.length() +
        "\nCurrent Line Count: " + paragraphs.size() +
        "\nParagraph Sizes: " + (paragraphSizes.isEmpty() ? "[]" : paragraphSizes));
  }
}
