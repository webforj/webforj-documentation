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

  private static final LocalDate MAX_DATE = LocalDate.of(9999, 12, 31);
  private static final LocalDate TODAY = LocalDate.of(2025, 4, 23);

  private final DateField departureField = new DateField(TODAY);
  private final DateField returnField = new DateField(TODAY);

  public DateFieldView() {
    getBoundComponent()
        .setDirection(FlexDirection.ROW)
        .setSpacing("var(--dwc-space-l)")
        .setMargin("var(--dwc-space-m)");

    departureField.setLabel("Departure Date:")
        .setWidth("200px")
        .setMin(TODAY)
        .setMax(MAX_DATE)
        .addValueChangeListener(this::onDepartureChange);

    returnField.setLabel("Return Date:")
        .setWidth("200px")
        .setMin(TODAY)
        .setMax(MAX_DATE)
        .addValueChangeListener(this::onReturnChange);

    getBoundComponent().add(departureField, returnField);
  }

  private void onDepartureChange(ValueChangeEvent<LocalDate> e) {
    try {
      String input = departureField.getText();
      if (input == null || input.length() < 10) return;

      LocalDate departure = e.getValue();
      if (departure == null || String.valueOf(departure.getYear()).length() < 4) return;

      LocalDate returnDate = returnField.getValue();
      if (returnDate != null && returnDate.isBefore(departure)) {
        returnField.setValue(departure);
      }

      returnField.setMin(departure);
    } catch (DateTimeParseException | NullPointerException ignored) {}
  }

  private void onReturnChange(ValueChangeEvent<LocalDate> e) {
    try {
      String input = returnField.getText();
      if (input == null || input.length() < 10) return;

      LocalDate returnDate = e.getValue();
      if (returnDate == null || String.valueOf(returnDate.getYear()).length() < 4) return;

      LocalDate departure = departureField.getValue();
      if (departure != null && returnDate.isBefore(departure)) {
        departureField.setValue(returnDate);
      }

      departureField.setMax(returnDate);
    } catch (DateTimeParseException | NullPointerException ignored) {}
  }
}