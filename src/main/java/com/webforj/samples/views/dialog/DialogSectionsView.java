package com.webforj.samples.views.dialog;

import com.webforj.component.Composite;
import com.webforj.component.dialog.Dialog;
import com.webforj.component.html.elements.Div;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Dialog Sections")
public class DialogSectionsView extends Composite<FlexLayout> {

  private FlexLayout self = getBoundComponent();
  private Dialog dialog = new Dialog();

  public DialogSectionsView() {
    self.add(dialog);
    dialog.addToHeader(new Div("Header"))
          .addToContent(new Div("Content"))
          .addToFooter(new Div("Footer"))
          .setCloseable(false)
          .open();
  }
}
