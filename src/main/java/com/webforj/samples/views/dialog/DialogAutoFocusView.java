package com.webforj.samples.views.dialog;

import com.webforj.annotation.InlineStyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.dialog.Dialog;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Div;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import com.webforj.samples.config.RouteConfig;

@InlineStyleSheet("context://css/dialog/dialog.css")
@Route(RouteConfig.DIALOG_AUTO_FOCUS)
@FrameTitle("Dialog Auto-Focus")
public class DialogAutoFocusView extends Composite<FlexLayout> {

  private Dialog dialog = new Dialog();

  public DialogAutoFocusView() {
    getBoundComponent().add(dialog);
    dialog.addToHeader(new Div("Auto Focus"));
    dialog.addToContent(new TextField().setLabel("This Box is Auto Focused"));

    dialog.open();
    dialog.setAutoFocus(true);
  }
}
