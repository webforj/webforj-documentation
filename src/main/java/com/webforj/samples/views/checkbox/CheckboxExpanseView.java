package com.webforj.samples.views.checkbox;

import com.webforj.component.Composite;
import com.webforj.component.Expanse;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.layout.flexlayout.FlexWrap;
import com.webforj.component.optioninput.CheckBox;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Checkbox Expanses")
public class CheckboxExpanseView extends Composite<FlexLayout> {
  // self field enables fluent method chaining from the bound component
  private final FlexLayout self = getBoundComponent();

  public CheckboxExpanseView() {
    self.setWrap(FlexWrap.WRAP)
        .setMargin("var(--dwc-space-l)")
        .setSpacing("50px")
        .setJustifyContent(FlexJustifyContent.CENTER)
        .setWidth("100%");

    // Iterate through Expanse values in reverse order
    for (int i = Expanse.values().length - 1; i >= 0; i--) {
      Expanse expanse = Expanse.values()[i];
      self.add(new CheckBox(expanse.name()).setExpanse(expanse));
    }
  }
}
