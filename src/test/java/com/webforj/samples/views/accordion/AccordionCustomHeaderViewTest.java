package com.webforj.samples.views.accordion;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.accordion.AccordionPanel;
import com.webforj.component.html.elements.Span;
import com.webforj.component.icons.Icon;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@WebforjTest
class AccordionCustomHeaderViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new AccordionCustomHeaderView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @ParameterizedTest
  @MethodSource("getHeaders")
  void shouldShowTheIconAndTextInTheHeaderInsteadOfTheLabel(
      String icon, String title, WebforjTestbed context) {
    context.add(new AccordionCustomHeaderView());

    var panel =
        ComponentLocator.of(AccordionPanel.class)
            .has(ComponentLocator.of(Span.class).byText(title))
            .locate();

    assertEquals("", panel.getLabel());
    assertTrue(
        ComponentLocator.of(FlexLayout.class)
            .within(panel)
            .has(ComponentLocator.of(Icon.class).byName(icon))
            .has(ComponentLocator.of(Span.class).byText(title))
            .isPresent());
  }

  private static Stream<Arguments> getHeaders() {
    return Stream.of(
        Arguments.of("settings", "Custom Header with Icon"), Arguments.of("user", "User Settings"));
  }
}
