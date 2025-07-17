package com.webforj.samples.views.fields.timefield;

import com.webforj.component.Composite;
import com.webforj.component.field.TimeField;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

import java.time.LocalTime;

@Route("timefield")
@FrameTitle("Time Field Demo")
public class TimeFieldView extends Composite<FlexLayout> {

  TimeField reminder = new TimeField("Set Reminder:", LocalTime.now());

  public TimeFieldView() {
    getBoundComponent().setMargin("var(--dwc-space-m)");
    getBoundComponent().add(reminder);
  }

}
