package com.webforj.samples.unit.views.fields.maskeddatefield;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mockStatic;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.field.MaskedDateFieldSpinner;
import com.webforj.samples.views.fields.maskeddatefield.MaskedDateFieldSpinnerView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

@WebforjTest
class MaskedDateFieldSpinnerViewTest {
  private static final LocalDate TODAY = LocalDate.of(2024, 1, 15);

  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    try (MockedStatic<LocalDate> clock = mockStatic(LocalDate.class, CALLS_REAL_METHODS)) {
      clock.when(LocalDate::now).thenReturn(TODAY);
      var view = new MaskedDateFieldSpinnerView();
      context.add(view);

      verifyThat(view).matchesSnapshot();
    }
  }

  @Test
  void shouldStepTheAppointmentByOneDay(WebforjTestbed context) {
    try (MockedStatic<LocalDate> clock = mockStatic(LocalDate.class, CALLS_REAL_METHODS)) {
      clock.when(LocalDate::now).thenReturn(TODAY);
      context.add(new MaskedDateFieldSpinnerView());

      var field = ComponentLocator.of(MaskedDateFieldSpinner.class).locate();

      field.spinUp();
      assertEquals(LocalDate.of(2024, 1, 16), field.getValue());

      field.spinDown();
      assertEquals(TODAY, field.getValue());
    }
  }
}
