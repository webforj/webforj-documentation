---
title: "Build a reusable card with title, body, and action slots"
description: "Create a Composite card component with header, body, and footer regions you fill from any view."
tags: [components, layout, styling]
components: []
difficulty: beginner
---

A `Composite<Div>` with three inner `Div` regions gives you "slots" you can fill with `addToHeader`, `addToBody`, and `addToFooter`. The pattern mirrors how webforJ's own `Toolbar` exposes start, title, and end slots.

```java
import com.webforj.annotation.StyleSheet;
import com.webforj.component.Component;
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.H3;

@StyleSheet("ws://cookbook-static/card.css")
public class Card extends Composite<Div> {
  private final Div root = getBoundComponent();
  private final Div header = new Div().addClassName("cookbook-card__header");
  private final Div body = new Div().addClassName("cookbook-card__body");
  private final Div footer = new Div().addClassName("cookbook-card__footer");

  public Card() {
    root.addClassName("cookbook-card").add(header, body, footer);
  }

  public Card setTitle(String title) {
    header.removeAll();
    header.add(new H3(title));
    return this;
  }

  public Card addToHeader(Component... components) {
    header.add(components);
    return this;
  }

  public Card addToBody(Component... components) {
    body.add(components);
    return this;
  }

  public Card addToFooter(Component... components) {
    footer.add(components);
    return this;
  }
}
```

Pair it with a sidecar style sheet at `src/main/resources/static/cookbook-static/card.css` (a path outside `/cookbook/*` because the docs site reserves that namespace):

```css
.cookbook-card {
  background: var(--dwc-surface-2);
  border: var(--dwc-border-width) var(--dwc-border-style) var(--dwc-border-color);
  border-radius: var(--dwc-border-radius);
  box-shadow: var(--dwc-shadow-s);
  overflow: hidden;
  width: 100%;
  max-width: 480px;
  align-self: flex-start;
}

.cookbook-card__header {
  padding: var(--dwc-space-m) var(--dwc-space-l);
  border-bottom: var(--dwc-border-width) var(--dwc-border-style) var(--dwc-border-color);
}

.cookbook-card__header h3 {
  margin: 0;
  font-size: var(--dwc-font-size-l);
  font-weight: var(--dwc-font-weight-semibold);
}

.cookbook-card__body {
  padding: var(--dwc-space-l);
  color: var(--dwc-color-default-text);
}

.cookbook-card__footer {
  padding: var(--dwc-space-m) var(--dwc-space-l);
  border-top: var(--dwc-border-width) var(--dwc-border-style) var(--dwc-border-color);
  display: flex;
  gap: var(--dwc-space-s);
  justify-content: flex-end;
}

.cookbook-card__header:empty,
.cookbook-card__footer:empty {
  display: none;
}
```

Then use it from any view:

```java
Card card = new Card()
    .setTitle("Confirm subscription")
    .addToBody(new Paragraph("You're about to subscribe to the monthly newsletter."))
    .addToFooter(
        new Button("Cancel", ButtonTheme.DEFAULT),
        new Button("Subscribe", ButtonTheme.PRIMARY));
```

Empty header or footer rows collapse via `:empty`, so a card with body content only renders cleanly.
