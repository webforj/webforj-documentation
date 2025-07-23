package com.webforj.samples.views.fields.datetimefield;

import com.webforj.component.Composite;
import com.webforj.component.field.DateTimeField;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Date Time Field Demo")
public class DateTimeFieldView extends Composite<FlexLayout> {

  DateTimeField departure = new DateTimeField();
  
  public DateTimeFieldView() {
    getBoundComponent().setMargin("var(--dwc-space-m)");

    departure.setLabel("Departure Date and Time:")
        .setWidth("200px");

    getBoundComponent().add(departure);

  }
}