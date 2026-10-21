package com.webforj.samples.views.splitter;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.layout.splitter.Splitter;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class SplitterBasicViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new SplitterBasicView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldPutTheFirstComponentInTheMasterAndTheSecondInTheDetail(WebforjTestbed context) {
    context.add(new SplitterBasicView());

    var splitter = ComponentLocator.of(Splitter.class).locate();

    assertTrue(
        ComponentLocator.of(FlexLayout.class)
            .within(splitter.getMaster())
            .byText("Master")
            .isPresent());
    assertTrue(
        ComponentLocator.of(FlexLayout.class)
            .within(splitter.getDetail())
            .byText("Detail")
            .isPresent());
  }
}
