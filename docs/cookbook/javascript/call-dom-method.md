---
title: "Call a DOM method (like scrollIntoView) from Java"
description: "Use executeJs and the component keyword to invoke any DOM method on a component's underlying HTML element."
tags: [javascript]
components: []
difficulty: intermediate
---

`getElement()` returns the underlying `Element` for any HTML component. Call `executeJs` on it and reference `component` inside the JavaScript string; that name is bound to the underlying DOM node, so any method on it (`scrollIntoView`, `focus`, `requestFullscreen`, `select`, `play`, etc.) is available.

```java
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.Route;

@Route("scroll-demo")
public class ScrollDemoView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final Button jumpButton = new Button("Jump to footer", ButtonTheme.PRIMARY);
  private final Div spacer = new Div().setStyle("height", "150vh");
  private final Div footer = new Div(new Paragraph("You scrolled here from Java."));

  public ScrollDemoView() {
    self.setDirection(FlexDirection.COLUMN).setPadding("var(--dwc-space-xl)");

    jumpButton.onClick(e -> footer.getElement().executeJs(
        "component.scrollIntoView({behavior: 'smooth', block: 'center'})"));

    self.add(jumpButton, spacer, footer);
  }
}
```

For a typed call with arguments serialized from Java, use `callJsFunction(name, args...)` instead — webforJ serializes a `Map` as a JS object:

```java
footer.getElement().callJsFunction(
    "scrollIntoView",
    java.util.Map.of("behavior", "smooth", "block", "center"));
```

Prefer `executeJs` when you need to compose multi-step JS or use other browser APIs alongside the method call; prefer `callJsFunction` when you're just invoking a single named method.
