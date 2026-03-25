package com.webforj.samples.views.dialog;

import com.webforj.annotation.InlineStyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.dialog.Dialog;
import com.webforj.component.html.elements.Div;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@InlineStyleSheet(id = "login-form", value = /* css */"""
  .loginForm {
    background-color: #263238;
    background-image: url(https://images.pling.com/img/00/00/59/97/06/1588511/1c58fba17fc4c48cd52cf17dd3f36556396e73e34a3d37e5aec6098ccdb01f3d1867.jpg);
    background-size: 100% 100%;
    background-repeat: no-repeat;
  }
""")
@Route
@FrameTitle("Dialog Backdrop Blur")
public class DialogBackdropBlurView extends Composite<FlexLayout> {

  private Dialog dialog = new Dialog();
  private Button backgroundBlur = new Button("Toggle Background Blur");

  public DialogBackdropBlurView() {
    getBoundComponent().add(dialog);
    dialog.addClassName("loginForm");
    dialog.addToHeader(new Div("Background Blur"));
    dialog.addToContent(backgroundBlur);
    dialog.setCloseable(false);
    dialog.open();
    backgroundBlur.setStyle("display", "flex")
        .setStyle("justify-content", "center")
        .onClick(e -> dialog.setBlurred(!dialog.isBlurred()));
  }
}
