package com.webforj.samples.views.dialog;

import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.dialog.Dialog;
import com.webforj.component.html.elements.Div;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import com.webforj.utilities.Assets;

@Route
@FrameTitle("Dialog Backdrop Blur")
public class DialogBackdropBlurView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final Dialog dialog = new Dialog();
  private final Button backgroundBlur = new Button("Toggle Background Blur");
  private String modalSource = Assets.resolveWebServerUrl("ws://fun.svg");
  private String cssValue = "center / 50% no-repeat url(" + modalSource + ")";

  public DialogBackdropBlurView() {
    self.add(dialog);
    dialog.setStyle("background", cssValue)
        .addToHeader(new Div("Background Blur"))
        .addToContent(backgroundBlur)
        .setCloseable(false)
        .open();
    backgroundBlur.setStyle("display", "flex")
        .setStyle("justify-content", "center")
        .onClick(e -> dialog.setBlurred(!dialog.isBlurred()));
  }
}
