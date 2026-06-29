---
title: "Fire a typed custom event from a Composite to its parent"
description: "Expose a typed event from a Composite using EventDispatcher so parent views can subscribe to custom domain events."
tags: [components]
components: []
difficulty: intermediate
---

Hold an `EventDispatcher` inside the `Composite`, dispatch your event object from any internal handler, and expose `onRatingChanged` so callers register listeners without reaching into the component's internals. The `RatingPicker` owns its star buttons and recolors them itself when the value changes; the event simply carries the new rating.

```java
import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.html.elements.Div;
import com.webforj.component.icons.IconButton;
import com.webforj.component.icons.TablerIcon;
import com.webforj.dispatcher.EventDispatcher;
import com.webforj.dispatcher.EventListener;
import com.webforj.dispatcher.ListenerRegistration;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

public class RatingPicker extends Composite<Div> {
  private final Div self = getBoundComponent();
  private final EventDispatcher dispatcher = new EventDispatcher();
  private final List<IconButton> stars = new ArrayList<>();
  private int value = 0;

  public RatingPicker() {
    for (int i = 1; i <= 5; i++) {
      int rating = i;
      IconButton star = new IconButton(TablerIcon.create("star"));
      star.setTheme(Theme.GRAY);
      star.onClick(e -> setValue(rating));
      stars.add(star);
      self.add(star);
    }
  }

  public void setValue(int newValue) {
    this.value = newValue;
    updateStars();
    dispatcher.dispatchEvent(new RatingChangedEvent(this, newValue));
  }

  public int getValue() {
    return value;
  }

  private void updateStars() {
    for (int i = 0; i < stars.size(); i++) {
      stars.get(i).setTheme(i < value ? Theme.PRIMARY : Theme.GRAY);
    }
  }

  public ListenerRegistration<RatingChangedEvent> onRatingChanged(
      EventListener<RatingChangedEvent> listener) {
    return dispatcher.addListener(RatingChangedEvent.class, listener);
  }

  public static class RatingChangedEvent extends EventObject {
    private final int value;

    public RatingChangedEvent(Object source, int value) {
      super(source);
      this.value = value;
    }

    public int getValue() {
      return value;
    }
  }
}
```

Any view that uses `RatingPicker` subscribes through `onRatingChanged` and reacts to the typed event. The parent reads the value from the event without touching the picker's stars:

```java
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.router.annotation.Route;

@Route
public class RatingView extends Composite<Div> {
  private final Div self = getBoundComponent();
  private final RatingPicker ratingPicker = new RatingPicker();
  private final Paragraph summary = new Paragraph("No rating yet");

  public RatingView() {
    ratingPicker.onRatingChanged(e -> summary.setText("You rated: " + e.getValue() + " / 5"));
    self.add(ratingPicker, summary);
  }
}
```