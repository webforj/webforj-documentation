---
title: Badge
sidebar_position: 8
sidebar_class_name: new-content
---

<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-badge" />
<DocChip chip='since' label='25.12' />
<JavadocLink type="badge" location="com/webforj/component/badge/Badge" top='true'/>

A `Badge` is a compact, visually distinct label used to convey status, counts, or short pieces of contextual information. Whether you need to flag a notification count, mark an item as "New", or call attention to a warning, badges give you a lightweight way to surface that information directly in the UI.

<!-- INTRO_END -->

:::tip Using a `Badge`
Badges work well for notification counts, status labels, and short metadata like version tags or release states. Keep badge text brief, one or two words at most, so the label reads at a glance.
:::

## Creating a badge {#creating-a-badge}

The simplest `Badge` takes a text string. You can also pass a `BadgeTheme` directly in the constructor to set the visual style right away. If you need to build a badge dynamically, populating the label or content after construction, the no-argument constructor works too.

```java
Badge badge = new Badge("New");

// With a theme
Badge primary = new Badge("Featured", BadgeTheme.SUCCESS);

// Built dynamically
Badge status = new Badge();
status.setLabel("Pending");
status.setTheme(BadgeTheme.WARNING);
```

## Label {#label}

In addition to setting the text of a `Badge` on construction, you can set or update a badge's text content at any time with `setLabel()`. The `setText()` method is an alias for the same operation; use whichever reads more naturally in context. Both have corresponding getters, `getLabel()` and `getText()`, if you need to read the current value.

```java
Badge badge = new Badge();
badge.setLabel("Updated");

// Equivalent
badge.setText("Updated");

// Read back the value
String current = badge.getLabel();
```

## Icons {#icons}

Sometimes a more visual approach is useful when conveying information with a `Badge`. Badges support slotted icon content, simply pass an icon alongside text using the `Badge(String, Component...)` constructor, or pass an icon alone to create an icon-only badge. When combined with text, the icon renders to the left of the label.

<!-- vale off -->
<ComponentDemo
path='/webforj/badgeicons?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/badge/BadgeIconsView.java'
height='320px'
/>
<!-- vale on -->

```java
// Icon with text
Badge done = new Badge("Done", FeatherIcon.CHECK_CIRCLE.create());
done.setTheme(BadgeTheme.SUCCESS);

// Icon only
Badge check = new Badge(FeatherIcon.CHECK.create());
check.setTheme(BadgeTheme.SUCCESS);
```

## Usage in other components {#usage-in-other-components}

### Buttons {#buttons}

Attach a `Badge` to a `Button` using `setBadge()`. The badge appears at the top-right corner of the button, overlapping the button edge—a common pattern for notification counts. The badge is independent of the button's own theme, so you can combine, for example, a primary button with a danger badge without any conflict.

<!-- vale off -->
<ComponentDemo
path='/webforj/badgebuttons?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/badge/BadgeButtonsView.java'
height='200px'
/>
<!-- vale on -->

### Tabbed pane {#tabbed-pane}

Add a `Badge` as a suffix on a `Tab` using `setSuffixComponent()`. This is a natural fit for inbox-style counts or status indicators on each tab. The badge sits at the trailing edge of the tab label and stays visible regardless of which tab is active.

<!-- vale off -->
<ComponentDemo
path='/webforj/badgetabbedpane?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/badge/BadgeTabbedPaneView.java'
height='220px'
/>
<!-- vale on -->

## Styling {#styling}

To style a `Badge` component to be applicable in various situations or convey different meanings, the following themes, sizes, and other styling info can be used:

### Themes {#themes}

As with many components in webforJ, the `Badge` comes in fourteen themes: seven filled and seven outlined variants. 

Filled themes use a solid background and automatically compute a text color that meets contrast requirements. Outlined variants instead use a tinted background with a colored border, making them a subtler option when you want the badge to complement surrounding content rather than dominate it.

Apply a theme using `setTheme()` or through the constructor.

<!-- vale off -->
<ComponentDemo
path='/webforj/badgethemes?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/badge/BadgeThemesView.java'
height='220px'
/>
<!-- vale on -->

### Custom color {#custom-color}

If the built-in themes don't match your palette, set a custom seed color using the `--dwc-badge-seed` CSS property. From this single value, the badge automatically derives the background, text, and border colors—ensuring the result stays accessible without requiring you to specify each individually.

```java
Badge badge = new Badge("Custom");
badge.setStyle("--dwc-badge-seed", "hsl(262, 52%, 47%)");
```

### Sizing {#sizing}

Use `setExpanse()` to control badge size. Nine sizes are available, ranging from `XXXSMALL` to `XXXLARGE`, and the default is `SMALL`. When a badge contains exactly one character, it automatically renders as a circle rather than a pill—useful for compact numeric indicators like unread counts.

<!-- vale off -->
<ComponentDemo
path='/webforj/badgesizes?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/badge/BadgeSizesView.java'
height='250px'
/>
<!-- vale on -->

### Parts and CSS variables {#parts-and-css-variables}

<TableBuilder name="Badge" />

