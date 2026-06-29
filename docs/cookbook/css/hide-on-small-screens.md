---
title: "Hide a component on small screens with a media query"
description: "Use a sidecar CSS file with a media query to hide a region of the UI below a breakpoint."
tags: [css, layout]
components: []
difficulty: beginner
---

Add a CSS class to any region you want to hide responsively, then let the sidecar style sheet handle the breakpoint with `@media (max-width: ...)`. Java stays declarative with no resize listeners or JavaScript glue.

```java
import com.webforj.annotation.StyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Anchor;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.H2;
import com.webforj.component.html.elements.H3;
import com.webforj.component.html.elements.ListEntry;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.html.elements.UnorderedList;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.Route;

@Route("responsive-hide")
@StyleSheet("ws://cookbook-static/responsive-hide.css")
public class ResponsiveHideView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public ResponsiveHideView() {
    self.setDirection(FlexDirection.ROW)
        .setSpacing("var(--dwc-space-l)")
        .setPadding("var(--dwc-space-xl)")
        .setAlignment(FlexAlignment.START);

    Div main = new Div().addClassName("cookbook-responsive-hide__main");
    main.add(
        new H2("Main content"),
        new Paragraph("Resize the window; the sidebar disappears below 768px."));

    Div sidebar = new Div().addClassName("cookbook-responsive-hide__sidebar");
    sidebar.add(
        new H3("Quick links"),
        new UnorderedList(
            new ListEntry(new Anchor("#", "Documentation")),
            new ListEntry(new Anchor("#", "Components")),
            new ListEntry(new Anchor("#", "Cookbook"))));

    self.add(main, sidebar);

    // Main fills the row; sidebar stays a fixed 240px.
    self.setItemGrow(1, main);
    self.setItemShrink(0, sidebar).setItemBasis("240px", sidebar);
  }
}
```

The sidecar lives at `src/main/resources/static/cookbook-static/responsive-hide.css` (kept outside `/cookbook/*`, which the docs site reserves):

```css
.cookbook-responsive-hide__main,
.cookbook-responsive-hide__sidebar {
  background: var(--dwc-surface-2);
  border: var(--dwc-border-width) var(--dwc-border-style) var(--dwc-border-color);
  border-radius: var(--dwc-border-radius);
  padding: var(--dwc-space-l);
}

.cookbook-responsive-hide__main {
  min-width: 0;
}

@media (max-width: 768px) {
  .cookbook-responsive-hide__sidebar {
    display: none;
  }
}

```

Pick the breakpoint to match your layout's natural collapse point. `768px` is a common device breakpoint, but the technique works for any width. Use `min-width` instead if you'd rather progressively reveal the region as the viewport grows.
