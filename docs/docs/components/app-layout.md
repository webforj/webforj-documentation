---
title: AppLayout
sidebar_position: 5
sidebar_class_name: updated-content
---

<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-app-layout" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppLayout" top='true'/>

The `AppLayout` is a comprehensive responsive layout component that provides a header, a footer, a drawer, and content section. The header and footer are fixed, the drawer slides in and out of the viewport, and the content is scrollable.

This component can be used to build common app layouts, such as a dashboard.

## Features

The webforJ App Layout is a component which allows for building common app layouts.

<ul>
    <li>Easy to use and customize</li>
    <li>Responsive design</li>
    <li>Multiple layout options</li>
    <li>Works with webforJ Dark Mode</li>
</ul>

It provides a header, footer , drawer, and content section all built into a responsive component which can be easily customized to quickly build common app layouts such as a dashboard. The header and footer are fixed, the drawer slides in and out of the viewport, and the content is scrollable.

Each part of the layout is a `Div`, which can contain any valid webforJ control. For best results, the app should include a viewport meta tag which contains viewport-fit=cover. The meta tag causes the viewport to be scaled to fill the device display.

```java
@AppMeta(name = "viewport", content = "width=device-width, initial-scale=1.0, viewport-fit=cover, user-scalable=no")
```

## Overview

The following code sample will result in an app with a collapsible sidebar that contains a logo and tabs for various content options and a header. The demo uses the dwc-icon-button web component to create a drawer toggle button. The button has the data-drawer-toggle attribute which instructs the DwcAppLayout to listen to click events coming from that component to toggle the drawer state.

<AppLayoutViewer path='/webforj/applayout/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/applayout/AppLayoutView.java'
cssURL='/css/applayout/applayout.css'
/>

## Full width navbar

By default, the AppLayout renders the header and the footer in the off-screen mode. The off-screen mode means that the header and the footer position will be shifted to fit beside the opened drawer. Disabling this mode will cause the header and footer to take the full available space and shift the drawer top and bottom position to fit with the header and the footer.

```java showLineNumbers
AppLayout myApp = new AppLayout();

myApp.setHeaderOffscreen(false);
myApp.setFooterOffscreen(false);
```

<AppLayoutViewer path='/webforj/applayoutfullnavbar/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/fullnavbar/AppLayoutFullNavbarView.java'
cssURL='/css/applayout/applayout.css'/>

## Multiple toolbars

The navbar has no limit to the number of toolbars you can add. A `Toolbar` is a horizontal container component that holds a set of action buttons, icons, or other controls. To add an additional toolbar, simply use the `addToHeader()` method to add another `Toolbar` component.

The following demo shows how to use two toolbars, The first one houses the drawer's toggle button and the app's title. The second toolbar houses a secondary navigation menu.

<AppLayoutViewer path='/webforj/applayoutmultipleheaders/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/multipleheaders/AppLayoutMultipleHeadersView.java'
cssURL='/css/applayout/applayout.css'/>

## Sticky toolbars

A sticky toolbar is a toolbar that remains visible at the top of the page when the user scrolls down but the navbar height is collapsed to make more space available for the page's content. Usually this kind of toolbar contains a fixed navigation menu which is relevant to the current page.

It's possible to create a sticky toolbars using the CSS custom property `--dwc-app-layout-header-collapse-height` and the `AppLayout.setHeaderReveal()` option.

When `AppLayout.setHeaderReveal(true)` is set called, the header will be visible on first render, and then hidden when the user starts scrolling down. Once the user starts scrolling up again the header will be revealed.

With the help of the CSS custom property `--dwc-app-layout-header-collapse-height` it's possible to control how much of the header navbar will be hidden.

<AppLayoutViewer path='/webforj/applayoutstickytoolbar/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/stickytoolbar/AppLayoutStickyToolbarView.java'
cssURL='/css/applayout/applayout.css'/>

## Mobile navigation layout

The bottom navbar can be used to provide a different version of the navigation at the bottom of app. This type of navigation is specifically popular in mobile apps.

Notice how the drawer is hidden in the following demo. The AppLayout widget supports three drawer positions: `DrawerPlacement.LEFT`, `DrawerPlacement.RIGHT`, and `DrawerPlacement.HIDDEN`.

Same as `AppLayout.setHeaderReveal()`, `AppLayout.setFooterReveal()` is supported. When `AppLayout.setFooterReveal(true)` is called, the footer will be visible at first render then hidden when the user starts scrolling up. Once the user starts scrolling down again the footer will be revealed.

By default, when the screen width is 800px or less , the drawer will be switched to popover mode. This is called the breakpoint. The popover mode means that the drawer will pop over the content area with an overlay. It's possible to configure the breakpoint by using the `setDrawerBreakpoint()` method and the breakpoint must be a valid [media query](https://developer.mozilla.org/en-US/docs/Web/CSS/Media_Queries/Using_media_queries).

<AppLayoutViewer path='/webforj/applayoutmobiledrawer/?' mobile='true'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/mobiledrawer/AppLayoutMobileDrawerView.java'
cssURL='/css/applayout/applayoutMobile.css'
/>

## Drawer utilities

The `AppLayout` drawer utilities are designed for integrated navigation and contextual menus within the main app layout, while standalone [`Drawer`](https://docs.webforj.com/docs/components/drawer) components offer flexible, independent sliding panels that can be used anywhere in your app for additional content, filters, or notifications. This section focuses on the built-in drawer features and utilities provided by AppLayout.

### Drawer breakpoint

By default, when the screen width is 800px or less , the drawer will be switched to popover mode. This is called the breakpoint. Popover mode means that the drawer will pop over the content area with an overlay. It's possible to configure the breakpoint by using the `setDrawerBreakpoint()` method and the breakpoint must be a valid media query.

For instance, in the following sample the drawer breakpoint is configured to be 500px or less.

```java
AppLayout demo = new AppLayout();
demo.setDrawerBreakpoint("(max-width:500px)");
```

### Drawer title

The `AppLayout` component provides a `addToDrawerTitle()` method for defining a custom title to display in the drawer header. 

```java
layout.addToDrawerTitle(new Div("Menu"));
```

### Drawer actions

The `AppLayout` component allows you to place custom components such as buttons or icons into the **drawer header actions area** using the `addToDrawerHeaderActions()` method.

```java
layout.addToDrawerHeaderActions(
    new IconButton(TablerIcon.create("bell")),
);
```

It's possible to pass multiple components as arguments:

```java
layout.addToDrawerHeaderActions(
    new IconButton(TablerIcon.create("bell")),
    new Button("Profile")
);
```

Drawer actions appear in the **right-aligned section** of the drawer’s header.

<AppLayoutViewer path='/webforj/applayoutdrawerutility/content/Dashboard/?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/applayoutdrawerutility/AppLayoutDrawerUtilityView.java'
cssURL='/css/applayout/applayout.css'
/>

## `AppDrawerToggle` <DocChip chip='since' label='24.12' />

The [`AppDrawerToggle`](https://javadoc.io/doc/com.webforj/webforj-applayout/latest/com/webforj/component/layout/applayout/AppDrawerToggle.html) component is a server-side webforJ class that represents a button used to toggle the visibility of a navigation drawer in an `AppLayout`. It maps to the client-side `<dwc-app-drawer-toggle>` element and is styled by default to behave like a traditional hamburger menu icon, this behaviour can be customized.

### Overview

The `AppDrawerToggle` extends `IconButton` and uses the "menu-2" icon from the Tabler icon set by default. It automatically applies the `data-drawer-toggle` attribute to integrate with the client-side drawer behavior.

```java
// No event registration required:
AppLayout layout = new AppLayout();
layout.addToHeader(new AppDrawerToggle());
// The drawer toggle will work out of the box—no manual event listeners needed.
```
## Styling

<TableBuilder name="AppLayout" />