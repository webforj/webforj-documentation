---
title: AppNav
sidebar_position: 6
sidebar_class_name: new-content
---

<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-app-nav" />
<DocChip chip="name" label="dwc-app-nav-item" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="appnav" location="com/webforj/component/appnav/AppNav" top='true'/> 

The `AppNav` component in webforJ provides a flexible and organized side navigation menu with support for both flat and hierarchical structures. Each entry is an `AppNavItem`, which can represent a simple link or a group containing sub-items. Items can be linked to internal views or external resources, enhanced with icons, badges, or other components.

## Adding and nesting items

`AppNavItem` instances are used to populate the `AppNav` structure. These items can be simple links or nested group headers that contain child items. Group headers without links act as expandable containers.

Use `addItem()` to include items in the nav:

```java
AppNavItem dashboard = new AppNavItem("Dashboard", "/dashboard");
AppNavItem admin = new AppNavItem("Admin");
admin.addItem(new AppNavItem("Users", "/admin/users"));
admin.addItem(new AppNavItem("Settings", "/admin/settings"));

AppNav nav = new AppNav();
nav.addItem(dashboard);
nav.addItem(admin);
```

:::tip Linking Group Items
Top-level items in a navigation tree are typically meant to be expandable—not clickable links. Setting a `path` on such items can confuse users who expect them to reveal sub-items instead of navigating elsewhere.

If you want the group header to trigger a custom action (such as opening external docs), keep the group path empty and instead add an interactive control like an [`IconButton`](./icon#icon-buttons) to the item's suffix. This keeps the UX consistent and clean.
:::

<AppLayoutViewer 
path='/webforj/appnav/Social?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/appnav/AppNavView.java'
/>

## Linking Items

Each `AppNavItem` can navigate to an internal view or an external link. You can define this using static paths or registered view classes.

### Static paths

Use string paths to define links directly:

```java
AppNavItem docs = new AppNavItem("Docs", "/docs");
AppNavItem help = new AppNavItem("Help", "https://support.example.com");
```

### Registered views

If your views are registered with the [router](../routing/overview), you can pass the class instead of a hardcoded URL:

```java
AppNavItem settings = new AppNavItem("Settings", SettingsView.class);
```

If your annotated route supports [route parameters](../routing/route-patterns#named-parameters), you can also pass a `ParametersBag`:

```java
ParametersBag params = ParametersBag.of("id=123");
AppNavItem advanced = new AppNavItem("User", UserView.class, params);
```

### With query parameters

Pass a `ParametersBag` to include query strings:

```java
ParametersBag params = ParametersBag.of("param1=value1&param2=value2");
AppNavItem advanced = new AppNavItem("Advanced", SettingsView.class, params);
advanced.setQueryParameters(params);
```

## Target behavior

Control how links open using `setTarget()`. This is especially useful for external links or pop-out views.

- **`SELF`** (default): Opens in the current view.
- **`BLANK`**: Opens in a new tab or window.
- **`PARENT`**: Opens in the parent browsing context.
- **`TOP`**: Opens in the top-level browsing context.

```java
AppNavItem help = new AppNavItem("Help", "https://support.example.com");
help.setTarget(AppNavItem.NavigationTarget.BLANK);
```

## Prefix and suffix

`AppNavItem` supports prefix and suffix components. Use these to provide visual clarity with icons, badges, or buttons.

- **Prefix**: appears before the label, useful for icons.
- **Suffix**: appears after the label, great for badges or actions.

```java
AppNavItem notifications = new AppNavItem("Alerts");
notifications.setPrefixComponent(TablerIcon.create("alert");
notifications.setSuffixComponent(TablerIcon.create("link"));
```

## Auto-opening groups

Use `setAutoOpen(true)` on the `AppNav` component to automatically expand nested groups when the app is refreshed.

```java
nav.setAutoOpen(true);
```

## Styling `AppNavItem`

<TableBuilder name="AppNavItem" />