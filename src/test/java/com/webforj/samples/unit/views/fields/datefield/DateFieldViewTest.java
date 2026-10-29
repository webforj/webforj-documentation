package com.webforj.samples.unit.views.fields.datefield;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.field.DateField;
import com.webforj.samples.views.fields.datefield.DateFieldView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class DateFieldViewTest {
  @Test
  void shouldLimitBothDatesToTheComingYear(WebforjTestbed context) {
    context.add(new DateFieldView());

    var fields = ComponentLocator.of(DateField.class).all();
    var departure = fields.get(0);
    var returning = fields.get(1);
    var today = departure.getValue();

    assertEquals(today, departure.getMin());
    assertEquals(today.plusYears(1), departure.getMax());
    assertEquals(today, returning.getMin());
    assertEquals(today.plusYears(1), returning.getMax());
  }

  @Test
  void shouldMoveTheReturnWhenTheDepartureIsPickedAfterIt(WebforjTestbed context) {
    context.add(new DateFieldView());

    var fields = ComponentLocator.of(DateField.class).all();
    var departure = fields.get(0);
    var returning = fields.get(1);
    var today = departure.getMin();

    UserInteraction.type(departure, today.plusDays(6).toString());

    assertEquals(today.plusDays(6), returning.getValue());
  }

  @Test
  void shouldMoveTheDepartureWhenTheReturnIsPickedBeforeIt(WebforjTestbed context) {
    context.add(new DateFieldView());

    var fields = ComponentLocator.of(DateField.class).all();
    var departure = fields.get(0);
    var returning = fields.get(1);
    var today = departure.getMin();

    UserInteraction.type(departure, today.plusDays(6).toString());
    UserInteraction.type(returning, today.plusDays(3).toString());

    assertEquals(today.plusDays(3), departure.getValue());
  }

  @Test
  void shouldKeepTheDepartureWhenTheReturnIsPickedAfterIt(WebforjTestbed context) {
    context.add(new DateFieldView());

    var fields = ComponentLocator.of(DateField.class).all();
    var departure = fields.get(0);
    var returning = fields.get(1);
    var today = departure.getMin();

    UserInteraction.type(departure, today.plusDays(3).toString());
    UserInteraction.type(returning, today.plusDays(9).toString());

    assertEquals(today.plusDays(3), departure.getValue());
    assertEquals(today.plusDays(9), returning.getValue());
  }
}
