package com.webforj.samples.views.flexlayout.container;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.layout.flexlayout.FlexWrap;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class FlexWrappingViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new FlexWrappingView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldWrapTheButtonsInTheRow(WebforjTestbed context) {
    context.add(new FlexWrappingView());

    var layout = ComponentLocator.of(FlexLayout.class).locate();

    assertEquals(FlexWrap.WRAP, layout.getWrap());
    assertEquals(FlexDirection.ROW, layout.getDirection());
  }
}
