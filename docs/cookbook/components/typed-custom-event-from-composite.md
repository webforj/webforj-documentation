---
title: "Fire a typed custom event from a Composite to its parent"
description: "Expose a typed event from a Composite using EventDispatcher so parent views can subscribe to custom domain events."
tags: [components]
components: []
difficulty: intermediate
---

Hold an `EventDispatcher` inside the `Composite`, dispatch your event object from any internal handler, and expose `onRatingChanged` so callers register listeners without touching implementation details.

```java
import java.util.List;

import com.webforj.component.Component;
import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.html.elements.Div;
import com.webforj.component.icons.IconButton;
import com.webforj.component.icons.TablerIcon;
import com.webforj.concern.HasComponents;
import com.webforj.dispatcher.EventDispatcher;
import com.webforj.dispatcher.EventListener;
import com.webforj.dispatcher.ListenerRegistration;
import java.util.EventObject;

public class RatingPicker extends Composite<Div> implements HasComponents {

  private final EventDispatcher dispatcher = new EventDispatcher();
  private int value = 0;
  private final Div self = getBoundComponent();

  public RatingPicker() {
    for (int i = 1; i <= 5; i++) {
      int rating = i;
      IconButton star = new IconButton(TablerIcon.create("star"));
      star.setTheme(Theme.GRAY);
      star.setUserData("rating", rating);
      star.onClick(e -> setValue(rating));
      self.add(star);
    }
  }

  @Override
  public List<Component> getComponents() {
    List<Component> components = self.getComponents();
    return components;
  }

  public void setValue(int newValue) {
    this.value = newValue;
    dispatcher.dispatchEvent(new RatingChangedEvent(this));
  }

  public int getValue() {
    return value;
  }

  public ListenerRegistration<RatingChangedEvent> onRatingChanged(
      EventListener<RatingChangedEvent> listener) {
    return dispatcher.addListener(RatingChangedEvent.class, listener);
  }

  public static class RatingChangedEvent extends EventObject {
    private final int value;

    public RatingChangedEvent(Object source) {
      super(source);
      this.value = ((RatingPicker) source).getValue();
    }

    public int getValue() {
      return value;
    }
  }
}
```

Then, when you use `RatingPicker` in another portion of your app, you can use the custom event as desired, like changing the themes of the star icons:

```java
import java.util.List;

import com.webforj.component.Component;
import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.html.elements.Div;
import com.webforj.component.icons.IconButton;
import com.webforj.router.annotation.Route;

@Route()
public class RatingView extends Composite<Div> {
  
  Div self = getBoundComponent();
  RatingPicker ratingPicker = new RatingPicker();

  public RatingView(){

    ratingPicker.onRatingChanged(e -> {
      List<Component> stars = ratingPicker.getComponents();
      int rating = e.getValue();
      for (int i = 0; i <= 4; i++) {
        IconButton starButton = (IconButton) stars.get(i);
        if ((int) starButton.getUserData("rating") <= rating) {
          starButton.setTheme(Theme.PRIMARY);
        } else {
          starButton.setTheme(Theme.GRAY);
        }
      }
    });

    self.add(ratingPicker);
  }
}
```