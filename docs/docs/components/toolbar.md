---
title: Toolbar
description: A horizontal container component that holds a set of action buttons, icons, or other controls, typically used for performing tasks related to the current context.
---

<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-toolbar" />
<JavadocLink type="toolbar" location="com/webforj/component/layout/toolbar/Toolbar" top='true'/>

Toolbars offer users quick access to core actions and navigation elements. The webforJ `Toolbar` component is a horizontal container that can hold a set of action buttons, icons, or other components. It's well-suited for managing page controls and housing key functions like a search bar or a notification button.

:::tip Sectioned Content
Consider using the [`TabbedPane`](./tabbedpane) component for sectioned content, such as a document viewer.
:::

## Basics

The `Toolbar` saves time by organizing essential components in an easily accessible and consistent layout. By default, a `Toolbar` takes the full width of its parent element.

Components can be added to a `Toolbar` in one of four slots: start, title, content, and end. Each slot has a corresponding method for adding components: `addToStart()`, `addToTitle()`, `addToContent()`, and `addToEnd()`. Additionally, each slot can have multiple components.

```Java
Toolbar toolbar = new Toolbar();
IconButton settings = new IconButton(TablerIcon.create("settings"));
IconButton profile = new IconButton(TablerIcon.create("user-circle"));

toolbar.addToTitle(new H1("webforJ App"));
toolbar.addToEnd(settings, profile);
```

### Toolbar slots

To create a well-organized `Toolbar`, consider the purpose of each component added and determine which slot would be best suited for it:

- **Start**: The most leftmost slot of a `Toolbar`. It's an ideal place for an `AppDrawerToggle` for apps that have a `Drawer`, or for a home button using an `IconButton`.

- **Title**: The slot that comes after the start slot. It could be used for an app's name or a company logo.

- **Content**: The content slot takes up the majority of the `Toolbar`. This slot is for attention seeking actions, such as site navigation or a search bar.

  :::tip Center Alignment
  The components inside the content slot can be aligned center by also adding a component to the title slot.
  :::

- **End**: The rightmost slot of a `Toolbar`. This is a spot for components that won't have as much interaction as those inside other slots, like access to profile settings, a help button, or a link to additional resources.

By thoughtfully choosing the slot for each component, developers can create a well-structured `Toolbar` that makes an app easy to navigate.

<ComponentDemo
path='/webforj/toolbarslots?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toolbar/ToolbarSlotsView.java'
height='300px'
/>

## Compact mode

Screen space is a valuable resource for any app. So, developers who want to use multi-row options with multiple `Toolbar` components can use the compact mode. By using the `setCompact()` method, the vertical whitespace is eliminated around a `Toolbar`, effectively freeing up screen space for additional content.

```Java
Toolbar toolbar = new Toolbar();
toolbar.setCompact(true);
```

### Using a `ProgressBar`

Compact mode can also compartmentalize components. The following example shows a `ProgressBar` inside a `Toolbar` that's using compact mode.
This can be helpful in an app to provide visual cues for a process running in the background, downloading files, or tracking multi-step forms.

<AppLayoutViewer path='/webforj/toolbarcompact?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toolbar/ToolbarCompactView.java'
/>

## Inside an `AppLayout`

Incorporating a `Toolbar` within an `AppLayout` can strengthen an app's cohesion. 

One of the primary benefits is ideal placement. A `Toolbar` can be positioned in either the header or footer of an `Applayout`, allowing for flexibility based on the app's design and user workflow. This strategic positioning can allow key actions and navigational elements to always be within reach, especially for mobile users.

```Java
AppLayout layout = new AppLayout();
Toolbar headerToolbar = new Toolbar();
Toolbar footerToolbar = new Toolbar();
AppDrawerToggle toggle = new AppDrawerToggle();

headerToolbar.addToStart(toggle);
footerToolbar.addToContent(new H1("webforJ"));

layout.addToHeader(headerToolbar);
layout.addToFooter(footerToolbar);
```

Another significant advantage of using a `Toolbar` within an `AppLayout` is changing visibility based on scrolling behavior. Hiding the `Toolbar` while scrolling can give the user more screen space and reduce cognitive load.

To read more about implementing toolbars within an `AppLayout`, go to [Sticky toolbars](./app-layout#sticky-toolbars) and [Mobile navigation layout](./app-layout#mobile-navigation-layout).

## Styling

### Toolbar themes
`Toolbar` components come with <JavadocLink type="foundation" location="com/webforj/component/Theme">seven discrete themes </JavadocLink> built in for quick styling without the use of CSS. These themes are pre-defined styles that can be applied to a `Toolbar` to change its appearance and visual presentation. They offer a quick and consistent way to customize the look of a `Toolbar` within an app.

  - **Danger**
  - **Default**
  - **Primary**
  - **Success**
  - **Warning**
  - **Gray**
  - **Info**