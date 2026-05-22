---
title: "Open a route in a new browser tab"
description: "Construct a route URL with the router and open it via Page.open so paths aren't hard-coded."
tags: [routing, navigation]
components: []
difficulty: beginner
---

Ask the router for the URI of a route class with `Router.getCurrent().getUri(Class)`, then pass that string to `Page.getCurrent().open(url, "_blank")` to launch it in a new browser tab. Resolving by class means the route's path can change without touching the call site.

```java
import com.webforj.Page;
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.html.elements.Div;
import com.webforj.router.Router;
import com.webforj.router.annotation.Route;

@Route
public class OpenInNewTabView extends Composite<Div> {
  private final Div self = getBoundComponent();
  private final Button openReport = new Button("Open report in new tab", ButtonTheme.PRIMARY);

  public OpenInNewTabView() {
    openReport.onClick(e -> Router.getCurrent()
        .getUri(ReportView.class)
        .ifPresent(url -> Page.getCurrent().open(url, "_blank")));

    self.add(openReport);
  }
}
```

`getUri` returns an `Optional<String>` — if the class isn't registered as a route, the optional is empty and nothing happens. The second argument to `Page.open` accepts any standard browser window-name token (`_blank`, `_self`, `_parent`, `_top`) or a custom name to reuse a named tab on subsequent clicks.
