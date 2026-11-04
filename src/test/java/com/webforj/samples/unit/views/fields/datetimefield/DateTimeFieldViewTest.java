package com.webforj.samples.unit.views.fields.datetimefield;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.field.DateTimeField;
import com.webforj.samples.views.fields.datetimefield.DateTimeFieldView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

@WebforjTest
class DateTimeFieldViewTest {
  @Test
  void shouldHoldTheDepartureTheUserTypes(WebforjTestbed context) {
    context.add(new DateTimeFieldView());

    var departure = ComponentLocator.of(DateTimeField.class).locate();

    UserInteraction.type(departure, "2026-03-14T09:30");

    verifyThat(departure)
        .hasLabel("Departure Date and Time:")
        .hasValue(LocalDateTime.of(2026, 3, 14, 9, 30));
  }
}
