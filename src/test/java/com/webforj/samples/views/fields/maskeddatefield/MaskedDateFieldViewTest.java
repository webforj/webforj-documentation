package com.webforj.samples.views.fields.maskeddatefield;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mockStatic;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.field.MaskedDateField;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

@WebforjTest
class MaskedDateFieldViewTest {
  private static final LocalDate TODAY = LocalDate.of(2024, 1, 15);

  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    try (MockedStatic<LocalDate> clock = mockStatic(LocalDate.class, CALLS_REAL_METHODS)) {
      clock.when(LocalDate::now).thenReturn(TODAY);
      var view = new MaskedDateFieldView();
      context.add(view);

      verifyThat(view).matchesSnapshot();
    }
  }

  @Test
  void shouldReadTheTypedDigitsInTheMonthDayYearOrderOfTheMask(WebforjTestbed context) {
    try (MockedStatic<LocalDate> clock = mockStatic(LocalDate.class, CALLS_REAL_METHODS)) {
      clock.when(LocalDate::now).thenReturn(TODAY);
      context.add(new MaskedDateFieldView());

      var field = ComponentLocator.of(MaskedDateField.class).locate();

      UserInteraction.type(field, "070424");

      verifyThat(field).hasValue(LocalDate.of(2024, 7, 4));
    }
  }
}
