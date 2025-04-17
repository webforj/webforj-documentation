package com.webforj.samples.views.fields.datefield;

import java.time.LocalDate;

import com.webforj.component.Composite;
import com.webforj.component.field.DateField;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.data.event.ValueChangeEvent;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Date Field Demo")
public class DateFieldView extends Composite<FlexLayout> {

  DateField returnField = new DateField(LocalDate.now());
  DateField departure = new DateField(LocalDate.now());

  public DateFieldView() {
    getBoundComponent()
        .setDirection(FlexDirection.ROW)
        .setSpacing("var(--dwc-space-l)")
        .setMargin("var(--dwc-space-m)");

    departure.setLabel("Departure Date:")
        .setWidth("200px")
        .addValueChangeListener(this::setMinReturn);

    returnField.setLabel("Return Date:")
        .setWidth("200px")
        .setMin(LocalDate.now());

    getBoundComponent().add(departure, returnField);
  }

  private void setMinReturn(ValueChangeEvent e) {
    Object val = e.getValue();
    if (!(val instanceof LocalDate departureDate)) {
      return;
    }

    LocalDate arrivalDate = returnField.getValue();

    if (departureDate.isAfter(arrivalDate)) {
      returnField.setValue(departureDate);
    }

    returnField.setMin(departureDate);
  }
}