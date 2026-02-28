package com.webforj.samples.views.fields.timefield;

import com.webforj.component.Composite;
import com.webforj.component.field.TimeField;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

import java.time.LocalTime;

@Route
@FrameTitle("Time Field Demo")
public class TimeFieldView extends Composite<FlexLayout> {
  // self field enables fluent method chaining from the bound component
  private final FlexLayout self = getBoundComponent();
  private final TimeField reminder = new TimeField("Set Reminder:", LocalTime.now());

  public TimeFieldView() {
    self.setMargin("var(--dwc-space-m)")
        .add(reminder);
  }
}
