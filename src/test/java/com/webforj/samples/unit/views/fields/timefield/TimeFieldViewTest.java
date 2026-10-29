package com.webforj.samples.unit.views.fields.timefield;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mockStatic;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.field.TimeField;
import com.webforj.samples.views.fields.timefield.TimeFieldView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

@WebforjTest
class TimeFieldViewTest {
  private static final LocalTime NOW = LocalTime.of(10, 30);

  @Test
  void shouldStartTheReminderAtTheCurrentTime(WebforjTestbed context) {
    try (MockedStatic<LocalTime> clock = mockStatic(LocalTime.class, CALLS_REAL_METHODS)) {
      clock.when(LocalTime::now).thenReturn(NOW);
      context.add(new TimeFieldView());

      var reminder = ComponentLocator.of(TimeField.class).locate();

      verifyThat(reminder).hasLabel("Set Reminder:").hasValue(NOW);
    }
  }

  @Test
  void shouldHoldTheReminderTimeTheUserTypes(WebforjTestbed context) {
    context.add(new TimeFieldView());

    var reminder = ComponentLocator.of(TimeField.class).locate();

    UserInteraction.type(reminder, "07:45");

    verifyThat(reminder).hasValue(LocalTime.of(7, 45));
  }
}
