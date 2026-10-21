package com.webforj.samples.views.splitter;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.button.Button;
import com.webforj.component.layout.splitter.Splitter;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import com.webforj.webstorage.LocalStorage;
import java.util.List;
import org.junit.jupiter.api.Test;

@WebforjTest
class SplitterAutoSaveViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new SplitterAutoSaveView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldTurnOnAutosaveForTheThreeSplitters(WebforjTestbed context) {
    context.add(new SplitterAutoSaveView());

    assertEquals(
        List.of(true, true, true),
        ComponentLocator.of(Splitter.class).all().stream().map(Splitter::isAutosave).toList());
  }

  @Test
  void shouldForgetTheSavedPositionsWhenTheUserClearsTheState(WebforjTestbed context) {
    context.add(new SplitterAutoSaveView());

    var storage = LocalStorage.getCurrent();
    storage.add("dwc-splitter-topSplitter", "40");
    storage.add("dwc-splitter-bottomSplitter", "60");
    storage.add("dwc-splitter-masterSplitter", "50");

    UserInteraction.click(ComponentLocator.of(Button.class).byText("Clear State").locate());

    assertNull(storage.get("dwc-splitter-topSplitter"));
    assertNull(storage.get("dwc-splitter-bottomSplitter"));
    assertNull(storage.get("dwc-splitter-masterSplitter"));
  }
}
