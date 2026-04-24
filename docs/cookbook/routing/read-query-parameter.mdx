---
title: "Read a query parameter from a route"
description: "Access URL query parameters inside a webforJ view using DidEnterObserver and ParametersBag."
tags: [routing, parameters]
components: []
difficulty: beginner
---

Implement `DidEnterObserver` in your view and call `event.getLocation().getQueryParameters()` to get a `ParametersBag`. Use `.get()` for a single value or `.getList()` for multi-value parameters.

```java
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.router.RouteContext;
import com.webforj.router.annotation.Route;
import com.webforj.router.event.DidEnterEvent;
import com.webforj.router.observer.DidEnterObserver;
import com.webforj.router.history.ParametersBag;

@Route(value = "products")
public class ProductView extends Composite<Div> implements DidEnterObserver {

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    ParametersBag query = event.getLocation().getQueryParameters();

    String category = query.get("category").orElse("all");
    String sort     = query.get("sort").orElse("default");
  }
}
```

For multi-value parameters such as `?tag=css&tag=routing`, use `query.getList("tag").orElse(List.of())`.
