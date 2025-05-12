---
title: AppLayout
sidebar_position: 5
---

<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-app-layout" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppLayout" top='true'/>

The `AppLayout` is a comprehensive responsive layout component that provides a header, a footer, a drawer, and content section. The header and footer are fixed, the drawer slides in and out of the viewport, and the content is scrollable.

This component can be used to build common application layouts, such as a dashboard.

## Features

The webforJ App Layout is a component which allows for building common application layouts.

<ul>
    <li>Easy to use and customize</li>
    <li>Responsive design</li>
    <li>Multiple layout options</li>
    <li>Works with webforJ Dark Mode</li>
</ul>

It provides a header, footer , drawer, and content section all built into a responsive component which can be easily customized to quickly build common application layouts such as a dashboard. The header and footer are fixed, the drawer slides in and out of the viewport, and the content is scrollable.

Each part of the layout is a `Div`, which can contain any valid webforJ control. For best results, the application should include a viewport meta tag which contains viewport-fit=cover. The meta tag causes the viewport to be scaled to fill the device display.

```java
@AppMeta(name = "viewport", content = "width=device-width, initial-scale=1.0, viewport-fit=cover, user-scalable=no")
```

## Overview

The following code sample will result in an application with a collapsible sidebar that contains a logo and tabs for various content options and a header. The demo uses the dwc-icon-button web component to create a drawer toggle button. The button has the data-drawer-toggle attribute which instructs the DwcAppLayout to listen to click events coming from that component to toggle the drawer state.

<AppLayoutViewer path='/webforj/applayout/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/applayout/AppLayoutView.java'
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/css/applayout/applayout.css'
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
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/css/applayout/applayout.css'/>


## Multiple toolbars

The navbar has no limit to the number of toolbars you can add. A `Toolbar` is a horizontal container component that holds a set of action buttons, icons, or other controls. To add an additional toolbar, simply use the `addToHeader()` method to add another `Toolbar` component.

The following demo shows how to use two toolbars, The first one houses the drawer's toggle button and the application's title. The second toolbar houses a secondary navigation menu.

<AppLayoutViewer path='/webforj/applayoutmultipleheaders/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/multipleheaders/AppLayoutMultipleHeadersView.java'
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/css/applayout/applayout.css'/>

## Sticky toolbars

A sticky toolbar is a toolbar that remains visible at the top of the page when the user scrolls down but the navbar height is collapsed to make more space available for the page's content. Usually this kind of toolbar contains a fixed navigation menu which is relevant to the current page.

It is possible to create a sticky toolbars using the CSS custom property `--dwc-app-layout-header-collapse-height` and the `AppLayout.setHeaderReveal()` option.

When `AppLayout.setHeaderReveal(true)` is set called, the header will be visible on first render, and then hidden when the user starts scrolling down. Once the user starts scrolling up again the header will be revealed.

With the help of the CSS custom property `--dwc-app-layout-header-collapse-height` it is possible to control how much of the header navbar will be hidden.

<AppLayoutViewer path='/webforj/applayoutstickytoolbar/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/stickytoolbar/AppLayoutStickyToolbarView.java'
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/css/applayout/applayout.css'/>

## Mobile navigation layout

The bottom navbar can be used to provide a different version of the navigation at the bottom of application. This type of navigation is specifically popular in mobile apps.

Notice how the drawer is hidden in the following demo. The AppLayout widget supports three drawer positions: `DrawerPlacement.LEFT`, `DrawerPlacement.RIGHT`, and `DrawerPlacement.HIDDEN`.

Same as `AppLayout.setHeaderReveal()`, `AppLayout.setFooterReveal()` is supported. When `AppLayout.setFooterReveal(true)` is called, the footer will be visible at first render then hidden when the user starts scrolling up. Once the user starts scrolling down again the footer will be revealed.

Be default, when the screen width is 800px or less , the drawer will be switched to popover mode. This is called the breakpoint. The popover mode means that the drawer will pop over the content area with an overlay. It is possible to configure the breakpoint by using the DwcAppLayout:setDrawerBreakpoint method and the breakpoint must be a valid [media query](https://developer.mozilla.org/en-US/docs/Web/CSS/Media_Queries/Using_media_queries).

<AppLayoutViewer path='/webforj/applayoutmobile?' mobile='true'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AppLayoutMobileView.java'
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/css/applayout/applayoutMobile.css'
/>

## Drawer breakpoint

Be default, when the screen width is 800px or less , the drawer will be switched to popover mode. This is called the breakpoint. Popover mode means that the drawer will pop over the content area with an overlay. It is possible to configure the breakpoint by using the DwcAppLayout:setDrawerBreakpoint method and the breakpoint must be a valid media query.

For instance, in the following sample. we configure the drawer breakpoint to be 500px or less.

```java
AppLayout demo = new AppLayout();
demo.setDrawerBreakpoint("(max-width:500px)");
```

<AppLayoutViewer path='/webforj/applayoutmobiledrawer/?' mobile='true'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/mobiledrawer/AppLayoutMobileDrawerView.java'
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/css/applayout/applayoutMobile.css'
/>

## Drawer title in `AppLayout`

The `AppLayout` component provides a method `addToDrawerTitle(...)` for defining a custom title to display in the drawer header. 

```java
layout.addToDrawerTitle(new Div("Menu"));
```

## Drawer actions in `AppLayout`

The `AppLayout` component allows you to place custom components such as buttons or icons into the **drawer header actions area** using the `addToDrawerHeaderActions(...)` method.

This is useful for adding:
- Settings or preferences buttons
- Notifications
- Profile or logout actions

### Multiple actions

You can pass multiple components as arguments:

```java
layout.addToDrawerHeaderActions(
    new IconButton(TablerIcon.create("bell")),
    new Button("Profile")
);
```

### Visual placement

Drawer actions appear in the **right-aligned section** of the drawer’s header, while the toggle appears on the left.


## `AppDrawerToggle`

The `AppDrawerToggle` component is a server-side WebforJ class that represents a button used to toggle the visibility of a navigation drawer in an [`AppLayout`](./app-layout.md). It maps to the client-side `<dwc-app-drawer-toggle>` element and is styled to behave like a traditional hamburger menu icon.

### Overview

This component extends `IconButton` and uses the "menu-2" icon from the Tabler icon set by default. It automatically applies the `data-drawer-toggle` attribute to integrate with the client-side drawer behavior.

### Behavior

- Toggles visibility of a `dwc-drawer`.
- Should be used inside or associated with an `AppLayout`.
- Integrates automatically by setting `data-drawer-toggle`.

## Styling

<TableBuilder name="AppLayout" />