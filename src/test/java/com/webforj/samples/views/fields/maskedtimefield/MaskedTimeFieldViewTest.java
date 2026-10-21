package com.webforj.samples.views.fields.maskedtimefield;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mockStatic;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.field.MaskedTimeField;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

@WebforjTest
class MaskedTimeFieldViewTest {
  private static final LocalTime NOW = LocalTime.of(10, 30);

  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    try (MockedStatic<LocalTime> clock = mockStatic(LocalTime.class, CALLS_REAL_METHODS)) {
      clock.when(LocalTime::now).thenReturn(NOW);
      var view = new MaskedTimeFieldView();
      context.add(view);

      verifyThat(view).matchesSnapshot();
    }
  }

  @Test
  void shouldReadTheTypedTimeThroughTheTwelveHourMask(WebforjTestbed context) {
    try (MockedStatic<LocalTime> clock = mockStatic(LocalTime.class, CALLS_REAL_METHODS)) {
      clock.when(LocalTime::now).thenReturn(NOW);
      context.add(new MaskedTimeFieldView());

      var field = ComponentLocator.of(MaskedTimeField.class).locate();

      UserInteraction.type(field, "2:45 pm");

      verifyThat(field).hasValue(LocalTime.of(14, 45));
    }
  }
}
