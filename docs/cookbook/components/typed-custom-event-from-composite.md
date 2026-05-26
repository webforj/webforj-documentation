---
title: "Fire a typed custom event from a Composite to its parent"
description: "Expose a typed event from a Composite using EventDispatcher so parent views can subscribe to custom domain events."
tags: [components]
components: []
difficulty: intermediate
---

Hold an `EventDispatcher` inside the `Composite`, dispatch your event object from any internal handler, and expose `onRatingChanged` so callers register listeners without touching implementation details.

```java
public class RatingPicker extends Composite<Div> {

  private final EventDispatcher dispatcher = new EventDispatcher();
  private int value = 0;

  public RatingPicker() {
    Div self = getBoundComponent();
    for (int i = 1; i <= 5; i++) {
      int rating = i;
      Button star = new Button("★");
      star.onClick(e -> setValue(rating));
      self.add(star);
    }
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
