package com.webforj.samples.unit.views.splitter;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.layout.splitter.Splitter;
import com.webforj.samples.views.splitter.SplitterMinMaxView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class SplitterMinMaxViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new SplitterMinMaxView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldBoundTheMasterPanelBetween200PixelsAnd75Percent(WebforjTestbed context) {
    context.add(new SplitterMinMaxView());

    var splitter = ComponentLocator.of(Splitter.class).locate();

    assertEquals("200px", splitter.getMasterMinSize());
    assertEquals("75%", splitter.getMasterMaxSize());
  }
}
