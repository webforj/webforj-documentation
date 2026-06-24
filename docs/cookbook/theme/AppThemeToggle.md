---
title: "Add a toggle for the app's theme"
description: "Have a toggle component that switches the app's theme between a light and a dark mode."
tags: [styling, components, theme]
components: [IconButton]
difficulty: beginner
---

Add the following composite component to your project:

```java
import com.webforj.App;
import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.html.elements.Div;
import com.webforj.component.icons.IconButton;

public class AppThemeToggle extends Composite<Div> {
  private final Div self = getBoundComponent();
  private final IconButton iconButton = new IconButton("sun", "tabler");

  public AppThemeToggle() {

    iconButton.setTheme(Theme.GRAY)
        .onClick(e -> switchAppTheme());

    switch (String.valueOf(App.getTheme())) {
      case "light":
        updateButton("sun", "Switch to dark mode");
        break;
      case "dark":
        updateButton("moon", "Switch to light mode");
        break;
      default:
        updateButton("device-desktop", "Switch to dark mode");
        break;
    }

    self.setStyle("padding", "var(--dwc-space-xs)");
    self.add(iconButton);
  }

  private void switchAppTheme() {
    if ("dark".equals(App.getTheme())) {
      setLightAppTheme();
    } else {
      setDarkAppTheme();
    }
  }

  private void setDarkAppTheme() {
    App.setTheme("dark");
    updateButton("moon", "Switch to light mode");
  }

  private void setLightAppTheme() {
    App.setTheme("light");
    updateButton("sun", "Switch to dark mode");
  }

  private void updateButton(String icon, String tooltip) {
    iconButton.setName(icon).setTooltipText(tooltip);
  }
}
```

Then, add it when you need it in your project, like inside a `Toolbar`:

```java
private final Toolbar toolbar = new Toolbar();

toolbar.addToEnd(new AppThemeToggle());
```
