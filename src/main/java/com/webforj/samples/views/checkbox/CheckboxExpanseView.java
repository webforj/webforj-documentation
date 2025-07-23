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

  public CheckboxExpanseView() {
    getBoundComponent().setWrap(FlexWrap.WRAP).setMargin("var(--dwc-space-l)").setSpacing("50px")
    .setJustifyContent(FlexJustifyContent.CENTER).setWidth("100%");

    for (int i = Expanse.values().length - 1; i >= 0; i--) {
      CheckBox expanseCheckBox = new CheckBox(Expanse.values()[i].name());
      getBoundComponent().add(expanseCheckBox.setExpanse(Expanse.values()[i]));
    }
  }
}
