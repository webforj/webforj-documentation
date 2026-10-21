package com.webforj.samples.unit.views.splitter;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.layout.splitter.Splitter;
import com.webforj.samples.views.splitter.SplitterNestedView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class SplitterNestedViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new SplitterNestedView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldNestTheVerticalSplitterInTheDetailOfTheHorizontalOne(WebforjTestbed context) {
    context.add(new SplitterNestedView());

    var outer = ComponentLocator.of(Splitter.class).first().orElseThrow();
    var inner = assertInstanceOf(Splitter.class, outer.getDetail());

    assertEquals(Splitter.Orientation.HORIZONTAL, outer.getOrientation());
    assertEquals(Splitter.Orientation.VERTICAL, inner.getOrientation());
  }
}
