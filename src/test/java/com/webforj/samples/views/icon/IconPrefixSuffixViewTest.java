package com.webforj.samples.views.icon;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.button.Button;
import com.webforj.component.icons.Icon;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class IconPrefixSuffixViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new IconPrefixSuffixView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldPlaceTheArrowAfterNextAndTheFilterIconBeforeFilter(WebforjTestbed context) {
    context.add(new IconPrefixSuffixView());

    var next = ComponentLocator.of(Button.class).byText("Next").locate();
    var filter = ComponentLocator.of(Button.class).byText("Filter").locate();

    assertEquals("arrow-narrow-right", ((Icon) next.getSuffixComponent()).getName());
    assertEquals("filter", ((Icon) filter.getPrefixComponent()).getName());
  }
}
