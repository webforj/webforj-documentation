package com.webforj.samples.unit.views.icon;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.icons.Icon;
import com.webforj.samples.views.icon.IconBasicsView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@WebforjTest
class IconBasicsViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new IconBasicsView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldCreateEachIconFromThePoolAndTheIconName(WebforjTestbed context) {
    context.add(new IconBasicsView());

    var icons = ComponentLocator.of(Icon.class).all();

    assertEquals(
        List.of("tabler:message", "tabler:trash", "tabler:edit"),
        icons.stream().map(icon -> icon.getPool() + ":" + icon.getName()).toList());
  }
}
