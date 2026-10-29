package com.webforj.samples.unit.views.busyindicator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.App;
import com.webforj.component.Theme;
import com.webforj.samples.views.busyindicator.BusyDemoView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class BusyDemoViewTest {
  @Test
  void shouldCoverTheFormWithMessageBackdropAndPrimarySpinner(WebforjTestbed context) {
    var indicator = App.getBusyIndicator();
    assertFalse(indicator.isVisible());

    context.add(new BusyDemoView());

    assertTrue(indicator.isVisible());
    assertEquals("Submitting form... Please wait.", indicator.getText());
    assertTrue(indicator.isBackdropVisible());
    assertEquals(Theme.PRIMARY, indicator.getSpinner().getTheme());
  }
}
