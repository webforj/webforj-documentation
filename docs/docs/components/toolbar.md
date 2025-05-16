---
title: Toolbar
sidebar_position: 145
sidebar_class_name: new-content
---

<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-toolbar" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="toolbar" location="com/webforj/component/layout/toolbar/Toolbar" top='true'/>

Toolbars offer users quick access to core actions and navigation elements. The webforJ `Toolbar` component is a horizontal container that can hold a set of action buttons, icons, or other components. It's well-suited for managing page controls and housing key functions like a search bar or a notification button.

## Organizing toolbar content

The `Toolbar` organizes essential components in an easily accessible and consistent layout. By default, it takes the full width of its parent element and provides four placement areas, or _slots_, for organizing components:

- **Start**: Usually contains an <JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppDrawerToggle" code='true'>AppDrawerToggle</JavadocLink> or a home button.
- **Title**: Used for app names or logos.
- **Content**: For high-attention actions like search or navigation.
- **End**: Less frequent actions, such as user profile or help.

Each slot has a method for adding components: `addToStart()`, `addToTitle()`, `addToContent()`, and `addToEnd()`.

The following demo shows how to add a `Toolbar` to an [AppLayout](./app-layout) and utilize all supported slots effectively.
To read more about implementing toolbars within an `AppLayout`, see [Sticky toolbars](./app-layout#sticky-toolbars) and [Mobile navigation layout](./app-layout#mobile-navigation-layout).

<AppLayoutViewer
path='/webforj/toolbarslots?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toolbar/ToolbarSlotsView.java'
height='300px'
/>

## Compact mode

Use `setCompact(true)` to reduce the padding around a `Toolbar`. This is helpful when you need to fit more content on screen, especially in apps with stacked toolbars or limited space. The toolbar still behaves the same—only the height is reduced. This mode is commonly used in headers, sidebars, or layouts where space is tight.

```java
Toolbar toolbar = new Toolbar();
toolbar.setCompact(true);
```

<AppLayoutViewer path='/webforj/toolbarcompact?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toolbar/ToolbarCompactView.java'
/>

## `ProgressBar` in toolbars

A `ProgressBar` serves as a visual indicator for ongoing processes, such as loading data, uploading files, or completing steps in a flow. When placed inside a `Toolbar`, the `ProgressBar` aligns neatly along the bottom edge, making it unobtrusive while still clearly communicating progress to users.

You can combine it with other components in the toolbar like buttons or labels without disrupting the layout.

<AppLayoutViewer path='/webforj/toolbarprogressbar?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toolbar/ToolbarProgressbarView.java'
/>

## Styling

### Themes

`Toolbar` components include <JavadocLink type="foundation" location="com/webforj/component/Theme">seven built-in themes</JavadocLink> for quick visual customization:

<ComponentDemo 
path='/webforj/toolbartheme?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toolbar/ToolbarThemeView.java' 
height = '475px'
/>

### Shadow parts

These are the parts of the [shadow DOM](../glossary#shadow-dom) that can be targeted via CSS:

<TableBuilder tag={require('@site/docs/components/\_dwc_control_map.json').Toolbar} table='parts'/>

### Slots

Available slots within the `Toolbar` component:

<TableBuilder tag={require('@site/docs/components/\_dwc_control_map.json').Toolbar} table='slots'/>

### Reflected attributes

These are attributes visible in the DOM, allowing styling via attribute selectors:

<TableBuilder tag={require('@site/docs/components/\_dwc_control_map.json').Toolbar} table="reflects"/>
