# Styling and CSS Integration

webforJ components integrate with CSS through multiple mechanisms.

## CSS Custom Properties

Many web components use CSS custom properties for styling:

```java
public class StylableComponent extends ElementComposite {
    public StylableComponent() {
        // Set CSS custom properties
        getElement().setStyle("--component-background", "#f0f0f0");
        getElement().setStyle("--component-border-radius", "8px");
        getElement().setStyle("--component-padding", "16px");
    }

    public StylableComponent setCustomBackground(String color) {
        getElement().setStyle("--component-background", color);
        return this;
    }
}
```

## CSS Classes

Use CSS classes for complex styling:

```java
public class ThemedComponent extends ElementComposite {
    public enum Theme {
        LIGHT("theme-light"),
        DARK("theme-dark"),
        HIGH_CONTRAST("theme-high-contrast");

        private final String className;

        Theme(String className) {
            this.className = className;
        }

        public String getClassName() {
            return className;
        }
    }

    private Theme currentTheme = Theme.LIGHT;

    public ThemedComponent setTheme(Theme theme) {
        // Remove old theme
        removeClassName(currentTheme.getClassName());

        // Add new theme
        addClassName(theme.getClassName());

        this.currentTheme = theme;
        return this;
    }
}
```

## Shadow Parts

Style internal component parts using CSS shadow parts:

```css
/* External CSS file */
my-component::part(header) {
  background: var(--header-bg);
  color: var(--header-color);
}

my-component::part(content) {
  padding: var(--content-padding);
}
```

## Responsive Design

```java
public class ResponsiveComponent extends ElementComposite {
    public ResponsiveComponent() {
        addClassName("responsive-component");
    }
}
```

```css
/* External CSS */
.responsive-component {
  padding: 8px;
}

@media (min-width: 768px) {
  .responsive-component {
    padding: 16px;
  }
}

@media (min-width: 1024px) {
  .responsive-component {
    padding: 24px;
  }
}
```

---

## Best Practices

1. **Use CSS Custom Properties** - For dynamic theming and component configuration
2. **Class-based Styling** - For complex styling rules and state changes
3. **Shadow Parts** - For styling internal component structure
4. **Responsive Design** - Use media queries for device adaptation
5. **Theme Support** - Design components to support multiple themes

## Common Patterns

### Dynamic Theme Switching

```java
public class ThemeAwareApp {
    public void switchTheme(Theme theme) {
        // Update global CSS custom properties
        App.getCurrent().getPage().executeJs("""
            document.documentElement.style.setProperty('--app-theme', arguments[0]);
            document.body.className = 'theme-' + arguments[0];
            """, theme.getName());
    }
}
```

### Component-Specific Styling

```java
public class CustomStyledComponent extends ElementComposite {
    public CustomStyledComponent() {
        // Component-specific CSS variables
        setStyle("--component-primary-color", "#007bff");
        setStyle("--component-border-width", "2px");
        setStyle("--component-border-radius", "4px");
        
        // Add component class for CSS targeting
        addClassName("custom-styled-component");
    }
}
```

## Navigation

- [← Previous: Third-Party Events](07-third-party-events.md)
- [Next: Testing →](09-testing.md)
- [Back to Index](00-index.md)