---
title: "Set the browser tab title dynamically from a route parameter"
description: "Implement HasFrameTitle to generate a per-record page title from URL parameters so every detail view has a descriptive tab label."
tags: [routing]
components: []
difficulty: beginner
---

Implement `HasFrameTitle` and read the route parameter from the `ParametersBag` argument. The router calls `getFrameTitle` just before updating the browser tab, so the value is always in sync with the current URL.

```java
@Route("products/:id")
public class ProductDetailView extends Composite<Div> implements HasFrameTitle {

  public ProductDetailView() {
    // build view
  }

  @Override
  public String getFrameTitle(NavigationContext context, ParametersBag parameters) {
    String id = parameters.get("id").orElse("Unknown");
    return "Product #" + id + " – My Shop";
  }
}
```
