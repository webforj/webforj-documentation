package com.webforj.samples.views.flexlayout.container;

import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.html.elements.Div;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.window.Panel;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Flex Wrapping")
public class FlexWrappingView extends Composite<Div> {
  public FlexWrappingView() {
    Panel container = new Panel();
    container.setWidth("200px")
        .setStyle("border", "1px black dotted");
    getBoundComponent().add(container);

    Button[] buttons = new Button[3];
    for (int i = 0; i < buttons.length; i++) {
      buttons[i] = new Button("Button " + (i + 1));
      if (i == 0) {
        buttons[i].setTheme(ButtonTheme.PRIMARY);
      }
    }

    FlexLayout buttonsLayout = FlexLayout.create(buttons)
        .horizontal()
        .wrap()
        .build();

    container.add(buttonsLayout);
  }
}
