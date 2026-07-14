package com.webforj.samples.views.dialog;

import com.webforj.component.Composite;
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
      # The Octopus: Nature's Escape Artist

      Octopuses are **incredibly intelligent** creatures with some remarkable abilities.

      ## Fun Facts

      - They have **three hearts** and blue blood
      - Each arm contains its own "mini-brain"
      - They can change color in just 200 milliseconds
      - Some species can edit their own RNA

      ## Escape Artists

      Octopuses are famous for escaping aquariums:

      ```
      1. Squeeze through tiny gaps
      2. Unscrew jar lids from inside
      3. Short out lights by splashing water
      4. Make a run for the ocean
      ```

      > "The octopus is the closest we will come to meeting an intelligent alien." - Peter Godfrey-Smith
      """;

  private final MarkdownViewer markdown = new MarkdownViewer(SAMPLE_CONTENT);

  public DialogBackdropBlurView() {

    self.setSize("80%", "100vh")
        .setMaxWidth("500px")
        .setDirection(FlexDirection.COLUMN)
        .setJustifyContent(FlexJustifyContent.CENTER)
        .setMargin("auto")
        .add(dialog, markdown);

    setBackdropOptions();
    setDialog();
  }

  public void setDialog() {
    dialogContent.setAlignment(FlexAlignment.BASELINE);
    dialog
        .addToHeader(new Div("Backdrop and Blur"))
        .addToContent(dialogContent)
        .setCloseable(false)
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
