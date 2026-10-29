package com.webforj.samples.unit.views.button;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.button.Button;
import com.webforj.component.html.elements.Img;
import com.webforj.component.icons.Icon;
import com.webforj.samples.views.button.ButtonIconView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class ButtonIconViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new ButtonIconView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldPlaceIconsBeforeTheTextAfterTheTextOrAlone(WebforjTestbed context) {
    context.add(new ButtonIconView());

    var before = ComponentLocator.of(Button.class).byText("Notifications").locate();
    var after = ComponentLocator.of(Button.class).byText("Search").locate();
    var alone = ComponentLocator.of(Button.class).byText("").locate();

    assertEquals("bell", ((Icon) before.getPrefixComponent()).getName());
    assertEquals("external-link", ((Icon) after.getSuffixComponent()).getName());
    assertInstanceOf(Img.class, alone.getIcon());
  }
}
