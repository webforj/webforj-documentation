package com.webforj.samples.views.dialog;

import com.webforj.component.Composite;
import com.webforj.component.dialog.Dialog;
import com.webforj.component.html.elements.Div;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Dialog Dragging")
public class DialogDraggableView extends Composite<FlexLayout> {

  private FlexLayout self = getBoundComponent();
  private Dialog dialog = new Dialog();

  public DialogDraggableView() {
    self.add(dialog);

    dialog.addToHeader(new Div("Snapping"))
            .addToContent(new Div("This dialog will snap when dragged within 100px of the edge of the display."))
            .open()
            .setSnapToEdge(true)
            .setSnapThreshold(100);
  }
}
