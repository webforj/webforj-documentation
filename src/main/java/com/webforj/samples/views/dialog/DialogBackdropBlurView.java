package com.webforj.samples.views.dialog;

import com.webforj.component.Composite;
import com.webforj.component.Expanse;
import com.webforj.component.dialog.Dialog;
import com.webforj.component.html.elements.Div;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.optioninput.RadioButton;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import com.webforj.utilities.Assets;

@Route
@FrameTitle("Dialog Backdrop Blur")
public class DialogBackdropBlurView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final Dialog dialog = new Dialog();
  private final RadioButton backdropToggle = RadioButton.Switch("Backdrop", true);
  private String modalSource = Assets.resolveWebServerUrl("ws://fun.svg");
  private String cssValue = "center / 50% no-repeat url(" + modalSource + ")";
  private RadioButton blurToggle = RadioButton.Switch("Blur");

  public DialogBackdropBlurView() {
    self.add(dialog);
    dialog
        .setStyle("--dwc-dialog-modal-background", cssValue)
        .addToHeader(new Div("Backdrop and Blur"))
        .addToContent(blurToggle, backdropToggle)
        .setCloseable(false)
        .open();

    blurToggle
        .setExpanse(Expanse.XLARGE)
        .onToggle(
            e -> {
              dialog.setBlurred(e.isToggled());
            });

    backdropToggle
        .setExpanse(Expanse.XLARGE)
        .onToggle(
            e -> {
              dialog.setBackdrop(e.isToggled());
            });
  }
}
