---
title: AppLayout
---

import Tabs from '@theme/Tabs';
import TabItem from '@theme/TabItem';
import AppLayoutViewer from '@site/src/components/DocsTools/AppLayoutViewer';
import ComponentDemo from '@site/src/components/DocsTools/ComponentDemo';
import TableBuilder from '@site/src/components/DocsTools/TableBuilder';
import JavadocLink from '@site/src/components/DocsTools/JavadocLink';

<JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppLayout" top='true'/>

The `AppLayout` is a comprehensive responsive layout component that provides a header, a footer, a drawer, and content section. The header and footer are fixed, the drawer slides in and out of the viewport, and the content is scrollable.

This component can be used to build common application layouts, such as a dashboard.

### Features

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

### Overview

The following code sample will result in an application with a collapsible sidebar that contains a logo and tabs for various content options and a header. The demo uses the dwc-icon-button web component to create a drawer toggle button. The button has the data-drawer-toggle attribute which instructs the DwcAppLayout to listen to click events coming from that component to toggle the drawer state.

<AppLayoutViewer url='https://demo.webforj.com/webapp/controlsamples?class=layout_demos.applayout.AppLayoutDemo' mobile='false' />

<ComponentDemo 
frame="hidden"
javaE='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/layout_demos/applayout/AppLayoutDemo.java'
cssURL='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/resources/css/applayoutstyles/applayout_styles.css'
/>

### Full-Width Navbars

By default, the AppLayout renders the header and the footer in the off-screen mode. The off-screen mode means that the header and the footer position will be shifted to fit beside the opened drawer. Disabling this mode will cause the header and footer to take the full available space and shift the drawer top and bottom position to fit with the header and the footer.

```java showLineNumbers
AppLayout myApp = new AppLayout();

myApp.setHeaderOffscreen(false);
myApp.setFooterOffscreen(false);
```

<AppLayoutViewer url='https://demo.webforj.com/webapp/controlsamples?class=layout_demos.applayout.AppLayoutFullNavbar' mobile='false'/>


<ComponentDemo 
frame="hidden"
javaE='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/layout_demos/applayout/AppLayoutFullNavbar.java'
cssURL='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/resources/css/applayoutstyles/applayout_styles.css'
/>


### Multiple Toolbars

The navbar has no limit to the number of toolbars you can add. A toolbar is only a Div. To add an additional toolbar, simply add a new Div to the existing header div of the AppLayout.

The following demo shows how to use two toolbars, The first one houses the drawer's toggle button and the application's title. The second toolbar houses a secondary navigation menu.

<AppLayoutViewer url='https://demo.webforj.com/webapp/controlsamples?class=layout_demos.applayout.AppLayoutMultipleHeaders' mobile='false'/>

<ComponentDemo 
frame="hidden"
javaE='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/layout_demos/applayout/AppLayoutMultipleHeaders.java'
cssURL='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/resources/css/applayoutstyles/applayout_styles.css'
/>

### Sticky Toolbars

A sticky toolbar is a toolbar that remains visible at the top of the page when the user scrolls down but the navbar height is collapsed to make more space available for the page's content. Usually this kind of toolbar contains a fixed navigation menu which is relevant to the current page.

It is possible to create a sticky toolbars using the CSS custom property `--dwc-app-layout-header-collapse-height` and the `AppLayout.setHeaderReveal()` option.

When `AppLayout.setHeaderReveal(true)` is set called, the header will be visible on first render, and then hidden when the user starts scrolling down. Once the user starts scrolling up again the header will be revealed.

With the help of the CSS custom property `--dwc-app-layout-header-collapse-height` it is possible to control how much of the header navbar will be hidden.

<AppLayoutViewer url='https://demo.webforj.com/webapp/controlsamples?class=layout_demos.applayout.AppLayoutStickyToolbar' mobile='false'/>

<ComponentDemo 
frame="hidden"
javaE='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/layout_demos/applayout/AppLayoutStickyToolbar.java'
cssURL='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/resources/css/applayoutstyles/applayout_sticky_styles.css'
/>


### Mobile Navigation Layout

The bottom navbar can be used to provide a different version of the navigation at the bottom of application. This type of navigation is specifically popular in mobile apps.

Notice how the drawer is hidden in the following demo. The AppLayout widget supports three drawer positions: `DrawerPlacement.LEFT`, `DrawerPlacement.RIGHT`, and `DrawerPlacement.HIDDEN`.

Same as `AppLayout.setHeaderReveal()`, `AppLayout.setFooterReveal()` is supported. When `AppLayout.setFooterReveal(true)` is called, the footer will be visible at first render then hidden when the user starts scrolling up. Once the user starts scrolling down again the footer will be revealed.

Be default, when the screen width is 800px or less , the drawer will be switched to popover mode. This is called the breakpoint. The popover mode means that the drawer will pop over the content area with an overlay. It is possible to configure the breakpoint by using the DwcAppLayout:setDrawerBreakpoint method and the breakpoint must be a valid [media query](https://developer.mozilla.org/en-US/docs/Web/CSS/Media_Queries/Using_media_queries).

<AppLayoutViewer url='https://demo.webforj.com/webapp/controlsamples?class=layout_demos.applayout.AppLayoutMobile' mobile='true'/>

<ComponentDemo 
frame="hidden"
javaE='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/layout_demos/applayout/AppLayoutMobile.java'
cssURL='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/resources/css/applayoutstyles/applayout_mobile.css'
/>

### Drawer Breakpoint

Be default, when the screen width is 800px or less , the drawer will be switched to popover mode. This is called the breakpoint. Popover mode means that the drawer will pop over the content area with an overlay. It is possible to configure the breakpoint by using the DwcAppLayout:setDrawerBreakpoint method and the breakpoint must be a valid media query.

For instance, in the following sample. we configure the drawer breakpoint to be 500px or less.

```java
AppLayout demo = new AppLayout();
demo.setDrawerBreakpoint("(max-width:500px)");
```

<AppLayoutViewer url='https://demo.webforj.com/webapp/controlsamples?class=layout_demos.applayout.AppLayoutMobileDrawer' mobile='true'/>

<ComponentDemo 
frame="hidden"
javaE='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/layout_demos/applayout/AppLayoutMobileDrawer.java'
cssURL='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/resources/css/applayoutstyles/applayout_mobile.css'
/>


## Styling

### Shadow Parts

These are the various parts of the [shadow DOM](../glossary#shadow-dom) for the component, which will be required when styling via CSS is desired.

<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').AppLayout} table='parts'/>

### Slots

Listed below are the slots available for utilization within the `AppLayout` component. These slots act as placeholders within the component that control where the children of a customized element should be inserted within the shadow tree.

<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').AppLayout} table='slots'/>

### CSS Properties

These are the various CSS properties that are used in the component, with a short description of their use.

<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').AppLayout} table='properties'/>

### Reflected Attributes

The reflected attributes of a component will be shown as attributes in the rendered HTML element for the component in the DOM. This means that styling can be applied using these attributes.

<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').AppLayout} table="reflects"/>

### Dependencies

This component relies on the following components - see the related article for more detailed styling information:

<TableBuilder tag='dwc-app-layout' table="dependencies"/>

