package com.webforj.samples.views.checkbox;

import com.webforj.component.Composite;
import com.webforj.component.Expanse;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.layout.flexlayout.FlexWrap;
import com.webforj.component.optioninput.CheckBox;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

import java.util.Arrays;

@Route
@FrameTitle("Checkbox Expanses")
public class CheckboxExpanseView extends Composite<FlexLayout> {
  private FlexLayout self = getBoundComponent();

  public CheckboxExpanseView() {
    self.setWrap(FlexWrap.WRAP)
            .setMargin("var(--dwc-space-l)")
            .setSpacing("50px")
            .setJustifyContent(FlexJustifyContent.CENTER)
            .setWidth("100%");

    Arrays.asList(Expanse.values())
            .reversed()
            .stream()
            .map(expanse -> new CheckBox(expanse.name()).setExpanse(expanse))
            .forEach(self::add);
  }
}
