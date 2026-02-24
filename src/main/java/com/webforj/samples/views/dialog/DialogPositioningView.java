package com.webforj.samples.views.dialog;

import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.dialog.Dialog;
import com.webforj.component.field.NumberField;
import com.webforj.component.html.elements.Div;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Dialog Positioning")
public class DialogPositioningView extends Composite<FlexLayout> {
  private FlexLayout self = getBoundComponent();
  private NumberField xPos = new NumberField("X Pixels:");
  private NumberField yPos = new NumberField("Y Pixels:");
  private Dialog dialog = new Dialog();
  private Button setPosition = new Button("Set Dialog Position", ButtonTheme.PRIMARY);

  public DialogPositioningView() {
    self.add(dialog);

    xPos.setMin(0d);
    yPos.setMin(0d);

    setPosition.setMinHeight("60px");

    setPosition.onClick(e -> {
      Double xValue = xPos.getValue();
      Double yValue = yPos.getValue();

      if (xValue != null && yValue != null) {
        dialog.setPosx(xValue + "px");
        dialog.setPosy(yValue + "px");
      }
    });

    dialog.addToHeader(new Div("Positioning"))
            .addToContent(xPos, yPos)
            .addToFooter(setPosition)
            .setAutoFocus(true)
            .open()
            .setCloseable(false)
            .setMaxWidth("200px");
  }
}
