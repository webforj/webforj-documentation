package com.webforj.samples.unit.views.elementcomposite;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mockStatic;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.ComponentUtil;
import com.webforj.component.element.Element;
import com.webforj.samples.views.elementcomposite.RelativeTimePropertiesView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

@WebforjTest
class RelativeTimePropertiesViewTest {
  private static final Instant OPENED = Instant.parse("2024-01-15T10:30:00Z");

  @Test
  void shouldConfigureEachActivityTimeWithItsOwnFormat(WebforjTestbed context) {
    try (MockedStatic<Instant> clock = mockStatic(Instant.class, CALLS_REAL_METHODS)) {
      clock.when(Instant::now).thenReturn(OPENED);
      context.add(new RelativeTimePropertiesView());

      List<List<String>> configured = new ArrayList<>();
      for (var time : ComponentLocator.of(RelativeTimePropertiesView.RelativeTime.class).all()) {
        var element = (Element) ComponentUtil.getBoundComponent(time);
        configured.add(
            List.of(
                element.getProperty("date", String.class),
                element.getProperty("format", String.class),
                element.getProperty("numeric", String.class)));
      }

      assertEquals(
          List.of(
              List.of("2024-01-15T10:28:00Z", "long", "auto"),
              List.of("2024-01-15T07:30:00Z", "short", "auto"),
              List.of("2024-01-14T10:30:00Z", "long", "always"),
              List.of("2024-01-08T10:30:00Z", "narrow", "always")),
          configured);
    }
  }
}
