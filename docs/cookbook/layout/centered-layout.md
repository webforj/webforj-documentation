---
title: "Center content in a FlexLayout"
description: "Use the FlexLayout component to center components horizontally and vertically"
tags: [layout]
components: [FlexLayout]
difficulty: beginner
---

Using a [FlexLayout](/docs/components/flex-layout), either on its own or as the bound component for a [Composite Component](/docs/building-ui/composite-components), use the `setJustifyContent()` and `setAlignment()` methods to align its content horizontally and vertically. Add at least one child so the centering is visible, and give the layout a height so there's room to center within.

```java
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.H2;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;

public class CenteredLayout extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public CenteredLayout() {
    self.setJustifyContent(FlexJustifyContent.CENTER);
    self.setAlignment(FlexAlignment.CENTER);
    self.setStyle("height", "100vh");

    Div card = new Div(
        new H2("Welcome back"),
        new Button("Sign in", ButtonTheme.PRIMARY));

    self.add(card);
  }
}
```

:::tip
If your content still isn't centered, make sure the `width` and `height` of the `FlexLayout` and any parent components is large enough.
:::