package com.webforj.samples.views.button;

import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.html.elements.Div;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.layout.flexlayout.FlexLayoutBuilder;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Button Event")
public class ButtonEventView extends Composite<FlexLayout> {
  private FlexLayout self = getBoundComponent();
  private int counter = 0;
  private Div text = new Div("Current Counter: 0");
  private Div payload = new Div("Event Payload: null");
  private Button button = new Button("Click Me!");

  public ButtonEventView() {
    button.setWidth("150px")
            .onClick(e -> {
              text.setText("Current Counter: " + (++counter));
              payload.setText("Event payload: " + e.getData());
            });

    FlexLayout textDisplay = FlexLayout.create(text, payload)
            .vertical()
            .build()
            .setSpacing("0px");

    self.setDirection(FlexDirection.ROW)
            .setSpacing("var(--dwc-space-l)")
            .setPadding("var(--dwc-space-l)")
            .add(button, textDisplay);
  }
}
