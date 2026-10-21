package com.webforj.samples.views.fields.maskeddatefield;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mockStatic;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.button.Button;
import com.webforj.component.field.MaskedDateField;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

@WebforjTest
class MaskedDateFieldRestoreViewTest {
  private static final LocalDate TODAY = LocalDate.of(2024, 1, 15);

  @Test
  void shouldRestoreTheConfiguredValue(WebforjTestbed context) {
    try (MockedStatic<LocalDate> clock = mockStatic(LocalDate.class, CALLS_REAL_METHODS)) {
      clock.when(LocalDate::now).thenReturn(TODAY);
      context.add(new MaskedDateFieldRestoreView());

      var field = ComponentLocator.of(MaskedDateField.class).locate();

      UserInteraction.type(field, "2024-03-01");
      UserInteraction.click(ComponentLocator.of(Button.class).byText("Reset value").locate());

      assertEquals(LocalDate.of(2024, 1, 14), field.getValue());
    }
  }
}
