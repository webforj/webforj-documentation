---
title: "Center content in a FlexLayout"
description: "Use the FlexLayout component to center components horizontally and vertically"
tags: [layout]
components: [FlexLayout]
difficulty: beginner
---

Using a [FlexLayout](/docs/components/flex-layout), either on its own or as the bound component for a [Composite Component](/docs/building-ui/composite-components), use the `setJustifyContent()` and `setAlignment()` methods to align its content horizontally and vertically.

```java
import com.webforj.component.Composite;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;

public class CenteredLayout extends Composite<FlexLayout>{
  private FlexLayout self = getBoundComponent();

  public CenteredLayout(){
    self.setJustifyContent(FlexJustifyContent.CENTER); // aligns the content along main axis 
    self.setAlignment(FlexAlignment.CENTER); // aligns the content along cross axis 
  }
}
```

:::tip
If your content still isn't centered, make sure the `width` and `height` of the `FlexLayout` and any parent components is large enough.
:::