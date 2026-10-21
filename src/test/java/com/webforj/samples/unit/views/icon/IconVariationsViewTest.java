package com.webforj.samples.unit.views.icon;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.icons.Icon;
import com.webforj.samples.views.icon.IconVariationsView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@WebforjTest
class IconVariationsViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new IconVariationsView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldShowTheVariationsOfFontAwesomeAndTablerIcons(WebforjTestbed context) {
    context.add(new IconVariationsView());

    var icons = ComponentLocator.of(Icon.class).all();

    assertEquals(
        List.of(
            "fa:envelope",
            "fa:fas-envelope",
            "fa:fab-instagram",
            "tabler:calendar",
            "tabler:filled-calendar"),
        icons.stream().map(icon -> icon.getPool() + ":" + icon.getName()).toList());
  }
}
