package com.webforj.samples.views.dialog;

import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.dialog.Dialog;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Fieldset;
import com.webforj.component.layout.columnslayout.ColumnsLayout;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.markdown.MarkdownViewer;
import com.webforj.component.optioninput.RadioButton;
import com.webforj.component.optioninput.RadioButtonGroup;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Dialog Backdrop Blur")
public class DialogBackdropBlurView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final Dialog dialog = new Dialog();
  private final RadioButton blurToggle =
      RadioButton.Switch("Blur", false, e -> dialog.setBlurred(!dialog.isBlurred()));
  private final RadioButton defaultBackdrop = new RadioButton("Default", true);
  private final RadioButton hideBackdrop = new RadioButton("None");
  private final RadioButton customBackdrop = new RadioButton("Custom");
  private final Fieldset backdropFieldset = new Fieldset("Backdrop");
  private final RadioButtonGroup backdropGroup =
      new RadioButtonGroup(defaultBackdrop, hideBackdrop, customBackdrop);
  private final ColumnsLayout backdropOptions = new ColumnsLayout(backdropGroup);
  private final FlexLayout dialogContent = new FlexLayout(backdropFieldset, blurToggle);
  private final Button openBtn =
      new Button(
          "Open dialog",
          ButtonTheme.PRIMARY,
          e -> {
            dialog.open();
          });

  private final String cssValue =
      """
      linear-gradient(
    90deg,
    hsl(200, 70%, 40%),
    hsl(250, 70%, 40%)
  )
      """;
  private static final String SAMPLE_CONTENT =
      """
      # Release overview

      Tracking items intended for the next release

      ## Planning

      - Planned item 1
      - Planned item 2
      - Planned item 3

      ## In review

      - In review item 1
      - In review item 2

      ## Ready

      - Ready item 1
      - Ready item 2
      - Ready item 3
      - Ready item 4
      """;
  private final MarkdownViewer markdown = new MarkdownViewer(SAMPLE_CONTENT);

  public DialogBackdropBlurView() {

    self.setSize("80%", "100vh")
        .setMaxWidth("500px")
        .setDirection(FlexDirection.COLUMN)
        .setJustifyContent(FlexJustifyContent.CENTER)
        .setMargin("auto")
        .add(dialog, markdown, openBtn);

    openBtn.setWidth("fit-content");
    setBackdropOptions();
    setDialog();
  }

  public void setDialog() {
    dialogContent.setAlignment(FlexAlignment.BASELINE);
    dialog
        .addToHeader(new Div("Backdrop and Blur"))
        .addToContent(dialogContent)
        .setAutoWidth(true)
        .open();
  }

  public void setBackdropOptions() {
    backdropFieldset.setWidth("fit-content").add(backdropOptions);

    defaultBackdrop.setUserData("backdrop", "default");
    customBackdrop.setUserData("backdrop", "custom");
    hideBackdrop.setUserData("backdrop", "none");

    backdropGroup.onChange(
        e -> {
          switch ((String) e.getChecked().getUserData("backdrop")) {
            case "default":
              dialog.setStyle("--dwc-dialog-modal-background", "");
              dialog.setBackdrop(true);
              break;
            case "custom":
              dialog.setStyle("--dwc-dialog-modal-background", cssValue);
              dialog.setBackdrop(true);
              break;
            case "none":
              dialog.setBackdrop(false);
              break;
            default:
              dialog.setBackdrop(false);
          }
        });
  }
}
