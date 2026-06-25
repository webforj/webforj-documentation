package com.webforj.samples.views.flexlayout.container;

import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.html.elements.Div;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Flex Wrapping")
public class FlexWrappingView extends Composite<Div> {
  private final Div self = getBoundComponent();

  public FlexWrappingView() {
    Div container = createContainer();
    Button[] buttons = createButtons();
    FlexLayout buttonsLayout = createButtonsLayout(buttons);

    container.add(buttonsLayout);
    self.add(container);
  }

  private Div createContainer() {
    return new Div().setWidth("200px").setStyle("border", "1px black dotted");
  }

  private Button[] createButtons() {
    Button[] buttons = new Button[3];
    for (int i = 0; i < buttons.length; i++) {
      buttons[i] = new Button("Button " + (i + 1));
      if (i == 0) {
        buttons[i].setTheme(ButtonTheme.PRIMARY);
      }
    }
    return buttons;
  }

  private FlexLayout createButtonsLayout(Button[] buttons) {
    return FlexLayout.create(buttons).horizontal().wrap().build();
  }
}
