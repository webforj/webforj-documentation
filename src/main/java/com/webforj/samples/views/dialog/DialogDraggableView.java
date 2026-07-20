package com.webforj.samples.views.dialog;

import com.webforj.component.Composite;
import com.webforj.component.dialog.Dialog;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.optioninput.RadioButton;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Dialog Dragging")
public class DialogDraggableView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final Dialog dialog = new Dialog();

  public DialogDraggableView() {
    self.add(dialog);

    RadioButton snapToEdge = RadioButton.Switch("Snap to viewport edges", true);
    snapToEdge.onToggle(e -> dialog.setSnapToEdge(e.isToggled()));
    FlexLayout content =
        FlexLayout.create(
                new Paragraph("Move the dialog by dragging its header, content, or footer."),
                snapToEdge)
            .vertical()
            .build()
            .setSpacing("var(--dwc-space-m)");

    dialog
        .addToHeader(new Div("Drag this dialog"))
        .addToContent(content)
        .setMoveable(true)
        .setSnapToEdge(true)
        .setSnapThreshold(80)
        .setCloseable(false)
        .setMaxWidth("28rem")
        .open();
  }
}
