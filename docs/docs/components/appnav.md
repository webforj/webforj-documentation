---
title: AppNav
---

<DocChip chip="shadow" />

<DocChip chip="name" label="dwc-app-nav" />

<DocChip chip="name" label="dwc-app-nav-item" />

<JavadocLink type="appnav" location="com/webforj/component/appnav/AppNav" top='true'/> 

The `AppNav` component in webforJ provides a flexible and organized navigation container to structure menus, sidebars, or other navigational elements. Each menu item is represented by an `AppNavItem`, allowing developers to configure paths, customize appearance, and organize items into hierarchical structures.

## Basics

The `AppNav` component supports creating intuitive navigation menus with a combination of `AppNav` and `AppNavItem` elements. Developers can easily add items, configure their behavior, and customize their appearance to fit the app’s requirements.

<AppLayoutViewer 
path='/webforj/appnav?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/appnav/AppNavView.java'
height='200px'
/>

## Adding and organizing items

`AppNav` allows you to add navigation items and organize them into hierarchical structures. Each `AppNavItem` can represent a standalone link or a container for nested items.

### Adding items

You can create navigation items by initializing `AppNavItem` instances and adding them to an AppNav:

```java
AppNavItem homeItem = new AppNavItem("Home", "/home");
AppNavItem profileItem = new AppNavItem("Profile", "/profile");

appNav.addItem(homeItem);
appNav.addItem(profileItem);
```

### Organizing nested items

For hierarchical navigation, use `AppNavItem` to create groups of sub-items. The parent item acts as a container, and its sub-items can be expanded or collapsed dynamically.

:::warning URLs on Top-Level Items with Nested Children 
Leave the parent item’s path empty when adding nested items to avoid unintended navigation.
:::

<AppLayoutViewer 
path='/webforj/appnavhierarchy?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/appnav/AppNavHierarchyView.java'
height='200px'
/>

## Automatic group expansion
With `setAutoOpen()`, the `AppNav` component will automatically expand groups of items when the user navigates. This behavior is helpful for hierarchical menus where you want to expose sub-items dynamically.

```java
appNav.setAutoOpen(true); // Automatically opens selected groups
```

## Linking items

The `AppNav` component's navigation feature relies on each `AppNavItem` being configured with a specific path or route, which enables it to link to different sections or views within an app. 

### Defining paths

Each `AppNavItem` requires a path or route that specifies where the navigation item will take users. This can be configured in two main ways:

<<<<<<< HEAD
**Direct Path**: you can assign a direct URL or path to an `AppNavItem`. This is helpful for static routes or URLs.
=======
**Direct Path**: You can assign a direct URL or path to an `AppNavItem`. This is helpful for static routes or URLs.
>>>>>>> 5bcec13e (new appnav article and demos)

```java
AppNavItem dashboardItem = new AppNavItem("Dashboard", "/dashboard");
AppNavItem helpItem = new AppNavItem("Help", "https://support.example.com");

appNav.addItem(dashboardItem);
appNav.addItem(helpItem);
```

<<<<<<< HEAD
**Registered View Class**: alternatively, if you have views registered with your app’s router, you can pass a class reference for the view. This approach provides flexibility, as it allows navigation to specific views without hardcoding URLs. The router automatically resolves the view’s path based on the registered configuration.
=======
**Registered View Class**: Alternatively, if you have views registered with your app’s router, you can pass a class reference for the view. This approach provides flexibility, as it allows navigation to specific views without hardcoding URLs. The router automatically resolves the view’s path based on the registered configuration.
>>>>>>> 5bcec13e (new appnav article and demos)

```java
AppNavItem settingsItem = new AppNavItem("Settings", SettingsView.class);
appNav.addItem(settingsItem);
```

:::tip Query Parameters
To navigate to specific sections, use the `setQueryParameters()` method with a `ParametersBag` to define key-value pairs. 
:::
 

## Setting navigation targets

The `AppNavItem` component provides the `setTarget()` method, which allows you to control the behavior of each navigation item when it's clicked. By default, items open in the current browsing context, but you can customize this with the `NavigationTarget` options:

- **SELF**: This is the default option and opens in the current browsing context.
- **BLANK**: Opens the item in a new tab or window based on browser settings.
- **PARENT**: Opens in the parent browsing context; if there’s no parent, it behaves like SELF.
- **TOP**: Opens in the top-level browsing context. If there’s no higher-level context, it also behaves like SELF.

```java
AppNavItem dashboardItem = new AppNavItem("Dashboard", "/dashboard");
dashboardItem.setTarget(AppNavItem.NavigationTarget.SELF); // Opens in the current view

AppNavItem helpItem = new AppNavItem("Help", "https://support.example.com");
helpItem.setTarget(AppNavItem.NavigationTarget.BLANK); // Opens in a new tab

AppNavItem parentItem = new AppNavItem("Parent View", "/parent");
parentItem.setTarget(AppNavItem.NavigationTarget.PARENT); // Opens in the parent context

AppNavItem topItem = new AppNavItem("Top View", "/top");
topItem.setTarget(AppNavItem.NavigationTarget.TOP); // Opens in the top-level context
```

## Item prefix and suffix

The `AppNavItem` component allows you to customize its appearance by adding prefixes and suffixes. These are useful for enhancing the visual design or providing additional context, such as icons, badges, or other indicators, alongside the item's text.

- **Prefixes**: Add elements or icons to appear before the item's label. For example, you can use an icon to represent the item's purpose visually.

- **Suffixes**: Add elements or icons to appear after the item's label. This is often used for secondary information, such as a badge showing a count or a status indicator.

Here's an example of adding a prefix and suffix:

```java
AppNavItem notificationsItem = new AppNavItem("Notifications");
notificationsItem.setPrefix(new Icon(TablerIcon.BELL));
notificationsItem.setSuffix(new Badge("3", Badge.Theme.INFO));
```

Prefixes and suffixes improve clarity, ensuring a more intuitive user experience for your app's navigation.

## Parts and CSS properties

<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').AppNavItem} />
