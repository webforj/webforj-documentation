package com.webforj.samples.views.fields.datefield;

import java.time.LocalDate;

import com.webforj.component.Composite;
import com.webforj.component.field.DateField;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.data.event.ValueChangeEvent;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import com.webforj.samples.config.RouteConfig;

@Route(RouteConfig.DATE_FIELD)
@FrameTitle("Date Field Demo")
public class DateFieldView extends Composite<FlexLayout> {

  private static final LocalDate TODAY = LocalDate.now();
  private static final LocalDate MAX_DATE = TODAY.plusYears(1);

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
        .addValueChangeListener(this::syncDates);

    returnField.setLabel("Return Date:")
        .setWidth("200px")
        .setMin(TODAY)
        .setMax(MAX_DATE)
        .addValueChangeListener(this::syncDates);

    getBoundComponent().add(departureField, returnField);
  }

  private void syncDates(ValueChangeEvent<LocalDate> e) {
    LocalDate rawDep = departureField.getValue();
    LocalDate rawRet = returnField.getValue();

    LocalDate dep = clamp(rawDep);
    LocalDate ret = clamp(rawRet);

    if (dep == null || ret == null) {
      return;
    }

    if (ret.isBefore(dep)) {
      if (e.getSource() == departureField)
        returnField.setValue(dep);
      else
        departureField.setValue(ret);
    }
  }

  private LocalDate clamp(LocalDate date) {
    if (date == null)
      return null;
    return date.isBefore(TODAY) ? TODAY
        : date.isAfter(MAX_DATE) ? MAX_DATE
            : date;
  }
}