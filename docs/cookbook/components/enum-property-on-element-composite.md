---
title: "Expose an enum property on an ElementComposite"
description: "Declare a typed enum property on an ElementComposite so it serialises to the correct web-component attribute value."
tags: [components]
components: []
difficulty: intermediate
---

Use `@SerializedName` on each enum constant to control the attribute string written to the DOM, then wire the enum through a `PropertyDescriptor` so `get`/`set` handle serialisation automatically.

```java
@NodeName("my-card")
public class MyCard extends ElementComposite {

  public enum Variant {
    @SerializedName("default")
    DEFAULT,

    @SerializedName("primary")
    PRIMARY,

    @SerializedName("danger")
    DANGER;
  }

  private final PropertyDescriptor<Variant> variantProp =
      PropertyDescriptor.property("variant", Variant.DEFAULT);

  public Variant getVariant() {
    return get(variantProp);
  }

  public MyCard setVariant(Variant variant) {
    set(variantProp, variant);
    return this;
  }
}
```
