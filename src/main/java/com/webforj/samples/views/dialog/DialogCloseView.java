package com.webforj.samples.views.dialog;

import com.webforj.annotation.InlineStyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.dialog.Dialog;
import com.webforj.component.html.elements.Div;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import com.webforj.samples.config.RouteConfig;
  
@InlineStyleSheet("context://css/dialog/dialog.css")
@Route(RouteConfig.DIALOG_CLOSE)
@FrameTitle("Dialog Close")
public class DialogCloseView extends Composite<FlexLayout> {

  private Dialog dialog = new Dialog();
  private Button showDialog = new Button("Show Dialog");
  private Button closeDialog = new Button("Close Dialog");

  public DialogCloseView() {
    getBoundComponent().add(dialog, showDialog);
    showDialog.setStyle("margin-left", "48vw")
        .setStyle("margin-top", "20px")
        .onClick(e -> dialog.open());

    dialog.addToHeader(new Div("Closing the Dialog"));
    dialog.addToContent(closeDialog);
    closeDialog.onClick(e -> dialog.close());
    dialog.setCancelOnEscKey(true);
    dialog.open();
  }
}
