package com.webforj.samples.views.navigator;

import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.navigator.Navigator;
import com.webforj.component.navigator.event.NavigatorChangeEvent;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Navigator Basics")
public class NavigatorBasicView extends Composite<Div> {
  private final Div self = getBoundComponent();

  // Counter state for the navigator
  private int count = 0;

  public NavigatorBasicView() {
    // Create navigator with initial value
    Navigator nav = new Navigator("Value: " + count);

    // Handle navigation direction changes
    nav.onChange(e -> handleNavigation(e, nav));

    // Configure container and add navigator
    self.setStyle("padding", "20px")
        .add(nav);
  }

  /**
   * Handles navigation events by updating the counter based on direction.
   */
  private void handleNavigation(NavigatorChangeEvent e, Navigator nav) {
    NavigatorChangeEvent.Direction direction = e.getDirection();

    // Update count based on navigation direction using switch expression
    count = switch (direction) {
      case NEXT -> count + 1;
      case PREVIOUS -> count - 1;
      case FIRST -> 0;
      case LAST -> 10;
    };

    // Clamp count to valid range [0, 10]
    count = Math.max(0, Math.min(10, count));

    nav.setText("Value: " + count);
  }
}

