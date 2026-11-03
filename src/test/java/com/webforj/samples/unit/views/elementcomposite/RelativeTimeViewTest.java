package com.webforj.samples.unit.views.elementcomposite;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mockStatic;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.ComponentUtil;
import com.webforj.component.element.Element;
import com.webforj.samples.views.elementcomposite.RelativeTimeView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

@WebforjTest
class RelativeTimeViewTest {
  private static final Instant OPENED = Instant.parse("2024-01-15T10:30:00Z");

  @Test
  void shouldSetTheLaunchTwoDaysAhead(WebforjTestbed context) {
    try (MockedStatic<Instant> clock = mockStatic(Instant.class, CALLS_REAL_METHODS)) {
      clock.when(Instant::now).thenReturn(OPENED);
      context.add(new RelativeTimeView());

      var time = ComponentLocator.of(RelativeTimeView.RelativeTime.class).locate();
      var element = (Element) ComponentUtil.getBoundComponent(time);

      assertEquals("2024-01-17T10:30:00Z", element.getProperty("date", String.class));
    }
  }
}
