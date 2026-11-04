package com.webforj.samples.unit.views.fields.maskeddatefield;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mockStatic;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.field.MaskedDateField;
import com.webforj.samples.views.fields.maskeddatefield.MaskedDateFieldPickerView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

@WebforjTest
class MaskedDateFieldPickerViewTest {
  private static final LocalDate TODAY = LocalDate.of(2024, 1, 15);

  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    try (MockedStatic<LocalDate> clock = mockStatic(LocalDate.class, CALLS_REAL_METHODS)) {
      clock.when(LocalDate::now).thenReturn(TODAY);
      var view = new MaskedDateFieldPickerView();
      context.add(view);

      verifyThat(view).matchesSnapshot();
    }
  }

  @Test
  void shouldTakeTheDateOnlyThroughThePickerWithWeekNumbers(WebforjTestbed context) {
    context.add(new MaskedDateFieldPickerView());

    var field = ComponentLocator.of(MaskedDateField.class).locate();
    var picker = field.getPicker();

    assertFalse(field.isAllowCustomValue());
    assertTrue(picker.isIconVisible());
    assertTrue(picker.isAutoOpen());
    assertTrue(picker.isShowWeeks());
  }
}
