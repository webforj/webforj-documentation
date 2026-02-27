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

@Route
@StyleSheet("TextAreaValidationView.css")
public class TextAreaValidationView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final TextArea textArea = new TextArea("Validation Playground", "The quick brown fox jumps over the lazy dog.");
  private final TextArea status = new TextArea("Current Status");

  private static final int MAX_LENGTH = 256;
  private static final int MAX_PARAGRAPH_LENGTH = 64;
  private static final int MAX_LINES = 10;

  private int maxLength = MAX_LENGTH;
  private int maxParagraphLength = MAX_PARAGRAPH_LENGTH;
  private int maxLines = MAX_LINES;

  public TextAreaValidationView() {
    self.setDirection(FlexDirection.COLUMN)
        .setSpacing("var(--dwc-space-m)")
        .setMargin("50px auto")
        .setStyle("padding", "var(--dwc-space-m)")
        .setMaxWidth("500px")
        .setStyle("background-color", "var(--dwc-surface-3)")
        .setStyle("border-radius", "var(--dwc-border-radius)")
        .setStyle("border", "1px solid var(--dwc-color-default)");

    status.setHeight("120px")
        .setReadOnly(true);

    textArea.setMaxLength(maxLength)
        .setParagraphLengthLimit(maxParagraphLength)
        .setLineCountLimit(maxLines)
        .setHeight("200px")
        .onValueChange(e -> updateStatus());

    NumberField maxLengthField = new NumberField("Max Length");
    maxLengthField.setPrefixComponent(TablerIcon.create("ruler"))
        .setTooltipText("Sets the maximum number of characters allowed across all text.")
        .setMin(1.0)
        .setMax(500.0)
        .setValue((double) maxLength)
        .setStyle("flex", "1 0 auto")
        .onValueChange(e -> handleNumericChange(e.getValue(), 1, 500, val -> {
          maxLength = val;
          textArea.setMaxLength(maxLength);
        }));

    NumberField maxLinesField = new NumberField("Max Lines");
    maxLinesField.setMin(1.0)
        .setPrefixComponent(TablerIcon.create("list-numbers"))
        .setTooltipText("Restricts how many lines the text area can contain.")
        .setMax(20.0)
        .setValue((double) maxLines)
        .setStyle("flex", "1 0 auto")
        .onValueChange(e -> handleNumericChange(e.getValue(), 1, 20, val -> {
          maxLines = val;
          textArea.setLineCountLimit(maxLines);
        }));

    NumberField maxParagraphSizeField = new NumberField("Max Paragraph Length");
    maxParagraphSizeField.setPrefixComponent(TablerIcon.create("indent-increase"))
        .setTooltipText("Limits how many characters each paragraph (or line) can have.")
        .setMin(1.0)
        .setMax(200.0)
        .setValue((double) maxParagraphLength)
        .setStyle("flex", "1 0 auto")
        .onValueChange(e -> handleNumericChange(e.getValue(), 1, 200, val -> {
          maxParagraphLength = val;
          textArea.setParagraphLengthLimit(maxParagraphLength);
        }));

    FlexLayout controlRow = FlexLayout.create(maxLengthField, maxLinesField, maxParagraphSizeField)
        .horizontal()
        .justify().between()
        .wrap()
        .build();

    self.add(textArea, controlRow, status);

    whenAttached().thenAccept(e -> updateStatus());
  }

  // Use pattern matching and lambda for cleaner numeric validation
  private void handleNumericChange(Double value, int min, int max, IntConsumer updater) {
    if (value != null && value >= min && value <= max) {
      updater.accept(value.intValue());
      updateStatus();
    }
  }

  @FunctionalInterface
  private interface IntConsumer {
    void accept(int value);
  }

  private void updateStatus() {
    String text = textArea.getValue();
    List<String> paragraphs = textArea.getParagraphs();

    // Use text block for cleaner multi-line string formatting
    String paragraphSizes = paragraphs.isEmpty() ? "[]" : paragraphs.stream()
        .map(p -> "[" + paragraphs.indexOf(p) + "=" + p.length() + "]")
        .reduce("", String::concat);

    status.setValue("""
        Current Length (including \\n): %d
        Current Line Count: %d
        Paragraph Sizes: %s
        """.formatted(text.length(), paragraphs.size(), paragraphSizes));
  }
}
