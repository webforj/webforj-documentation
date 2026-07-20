package com.webforj.samples.views.dialog;

import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.dialog.Dialog;
import com.webforj.component.html.elements.Div;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Dialog Positioning")
public class DialogPositioningView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final Dialog dialog = new Dialog();

  public DialogPositioningView() {
    self.add(dialog);

    Button topLeft = new Button("Top left");
    topLeft.onClick(e -> setPosition("5%", "10%"));
    Button center = new Button("Center", ButtonTheme.PRIMARY);
    center.onClick(e -> setPosition("calc(50% - 10rem)", "calc(50% - 4rem)"));
    Button bottomRight = new Button("Bottom right");
    bottomRight.onClick(e -> setPosition("calc(95% - 20rem)", "calc(90% - 8rem)"));
    FlexLayout positions =
        FlexLayout.create(topLeft, center, bottomRight)
            .horizontal()
            .wrap()
            .build()
            .setSpacing("var(--dwc-space-s)");

    dialog
        .addToHeader(new Div("Position presets"))
        .addToContent(positions)
        .setMoveable(false)
        .setCloseable(false)
        .setMaxWidth("20rem")
        .setPosx("calc(50% - 10rem)")
        .setPosy("calc(50% - 4rem)")
        .open();
  }

  private void setPosition(String x, String y) {
    dialog.setPosx(x).setPosy(y);
  }
}
