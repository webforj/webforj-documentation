package com.webforj.samples.views.textarea;

import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.field.TextArea;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Div;
import com.webforj.component.layout.flexlayout.FlexContentAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.toast.Toast;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import java.util.List;

@Route
@FrameTitle("Paragraph Manager Demo")
public class TextAreaParagraphManagerView extends Composite<FlexLayout> {

  FlexLayout layout = getBoundComponent();
  TextArea textArea = new TextArea();
  TextField paragraphInput = new TextField("Paragraph Text");
  TextField indexInput = new TextField("Index");

  public TextAreaParagraphManagerView() {
    layout.setDirection(FlexDirection.COLUMN)
          .setJustifyContent(FlexJustifyContent.CENTER)
          .setAlignContent(FlexContentAlignment.CENTER)
          .setSpacing("var(--dwc-space-m)")
          .setStyle("margin", "auto")
          .setStyle("padding", "var(--dwc-space-m)")
          .setStyle("max-width", "600px");

    textArea.setPlaceholder("Manage your paragraphs here...")
            .setWidth("100%")
            .setRows(8)
            .setLineWrap(true);

    paragraphInput.setWidth("50%");
    indexInput.setWidth("50%");

    Button addParagraphButton = new Button("Add Paragraph").setTheme(ButtonTheme.PRIMARY);
    addParagraphButton.onClick(event -> {
      String text = paragraphInput.getValue();
      int index = parseIndex(indexInput.getValue());
      if (!text.isEmpty()) {
        textArea.addParagraph(index, text);
        paragraphInput.setValue("");
        indexInput.setValue("");
      }
    });

    Button removeButton = new Button("Remove Paragraph").setTheme(ButtonTheme.DANGER);
    removeButton.onClick(event -> {
      try {
        int index = Integer.parseInt(indexInput.getValue().trim());

        List<String> paragraphs = textArea.getParagraphs();
        if (index >= 0 && index < paragraphs.size()) {
          textArea.removeParagraph(index);
        } else {
          showToast("Invalid Index", "Index out of range. Cannot remove paragraph.");
        }
      } catch (NumberFormatException e) {
        showToast("Error", "Please enter a valid numeric index.");
      }
    });

    Div inputContainer = new Div();
    inputContainer.setStyle("display", "flex")
                  .setStyle("gap", "var(--dwc-space-s)")
                  .setWidth("100%");
    inputContainer.add(paragraphInput, indexInput);

    Div buttonContainer = new Div();
    buttonContainer.setStyle("display", "flex")
                   .setStyle("gap", "var(--dwc-space-s)");
    buttonContainer.add(addParagraphButton, removeButton);

    layout.add(textArea, inputContainer, buttonContainer);
  }

  private int parseIndex(String input) {
    try {
      return Integer.parseInt(input);
    } catch (NumberFormatException e) {
      return -1;
    }
  }

  private void showToast(String title, String message) {
    Toast toast = new Toast();
    toast.setText(title + ": " + message)
         .setPlacement(Toast.Placement.CENTER)
         .setDuration(3000)
         .open();
  }
}