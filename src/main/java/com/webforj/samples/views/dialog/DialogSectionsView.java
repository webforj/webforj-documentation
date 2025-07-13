package com.webforj.samples.views.dialog;

import com.webforj.annotation.InlineStyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.dialog.Dialog;
import com.webforj.component.html.elements.Div;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import com.webforj.samples.config.RouteConfig;

@InlineStyleSheet("context://css/dialog/dialog.css")
@Route(RouteConfig.DIALOG_SECTIONS) 
@FrameTitle("Dialog Sections")
public class DialogSectionsView extends Composite<FlexLayout> {

  private Dialog dialog = new Dialog();

  public DialogSectionsView() {
    getBoundComponent().add(dialog);
    dialog.addToHeader(new Div("Header"));
    dialog.addToContent(new Div("Content"));
    dialog.addToFooter(new Div("Footer"));
    dialog.setCloseable(false);
    dialog.open();
  }
}
