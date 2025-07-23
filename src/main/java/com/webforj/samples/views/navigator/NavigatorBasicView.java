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
  private int count = 0;

  public NavigatorBasicView() {
    Navigator nav = new Navigator("Value: " + String.valueOf(count));
    nav.onChange(e -> {
      NavigatorChangeEvent.Direction direction = e.getDirection();
      switch (direction) {
        case NEXT:
          count++;
          break;
        case PREVIOUS:
          count--;
          break;
        case FIRST:
          count = 0;
          break;
        case LAST:
          count = 10;
          break;
        default:
          break;
      }

      if (count < 0) {
        count = 0;
      } else if (count > 10) {
        count = 10;
      }

      nav.setText("Value: " + String.valueOf(count));
    });

    getBoundComponent().setStyle("padding", "20px");
    getBoundComponent().add(nav);
  }
}

