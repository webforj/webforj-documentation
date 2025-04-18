package com.webforj.samples.views.fields.datefield;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

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

  DateField departureField = new DateField(LocalDate.now());
  DateField returnField = new DateField(LocalDate.now());

  public DateFieldView() {
    getBoundComponent().setDirection(FlexDirection.ROW)
      .setSpacing("var(--dwc-space-l)")
      .setMargin("var(--dwc-space-m)");

    departureField.setLabel("Departure Date:")
      .setWidth("200px")
      .setMin(LocalDate.now())
      .addValueChangeListener(this::setMinReturn);

    returnField.setLabel("Return Date:")
      .setWidth("200px")
      .setMin(LocalDate.now())
      .addValueChangeListener(this::validateReturnField);

    getBoundComponent().add(departureField, returnField);
  }

  private void setMinReturn(ValueChangeEvent e) {
    try {
      LocalDate departureDate = (LocalDate) e.getValue();
      LocalDate returnDate = returnField.getValue();

      if (departureDate != null && returnDate != null && departureDate.isAfter(returnDate)) {
        returnField.setValue(departureDate);
      }

      returnField.setMin(departureDate);
    } catch (DateTimeParseException | NullPointerException ex) {
    }
  }

  private void validateReturnField(ValueChangeEvent e) {
    try {
      LocalDate val = (LocalDate) e.getValue();
    } catch (DateTimeParseException | NullPointerException ex) {
    }
  }
}