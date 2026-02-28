package com.webforj.samples.views.button;

import com.webforj.component.Composite;
import com.webforj.component.Expanse;
import com.webforj.component.button.Button;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.list.ChoiceBox;
import com.webforj.component.list.ListItem;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Button Expanses")
public class ButtonExpansesView extends Composite<FlexLayout> {
  // self field enables fluent method chaining from the bound component
  private final FlexLayout self = getBoundComponent();
  private final ChoiceBox expanses = new ChoiceBox();
  private final Button demoButton = new Button("None");

  public ButtonExpansesView() {
    self.setSpacing("var(--dwc-space-l)")
        .setMargin("var(--dwc-space-l)")
        .add(expanses, demoButton);

    // Build list items by iterating in reverse order (excluding NONE)
    for (int i = Expanse.values().length - 1; i >= 0; i--) {
      Expanse expanse = Expanse.values()[i];
      if (expanse != Expanse.NONE) {
        expanses.add(new ListItem(expanse, expanse.name()));
      }
    }

    expanses.selectIndex(0)
        .setWidth("100px")
        .addSelectListener(event -> {
          Expanse selectedValue = (Expanse) expanses.getSelectedKey();
          if (selectedValue != null) {
            demoButton.setExpanse(selectedValue);
            demoButton.setText(selectedValue.name());
          }
        });
  }
}