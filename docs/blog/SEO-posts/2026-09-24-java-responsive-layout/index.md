---
title: "Java responsive web app layout: side navigation in webforJ"
description: "Build a responsive Java web app layout in webforJ with AppLayout, a side navigation drawer that collapses on mobile, AppNav tied to routes and ColumnsLayout."
slug: java-responsive-layout
date: 2026-09-24
authors: Matthew Hawkins
tags: [components, layout, responsive design]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-09-24-java-responsive-layout/cover.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-09-24-java-responsive-layout/cover.jpg)

The quickest way to build a responsive Java web app layout in webforJ is `AppLayout` for the shell, `AppNav` for the side navigation, and `ColumnsLayout` or `FlexLayout` for the content. `AppLayout` gives you a fixed header and footer plus a drawer that turns into a popover on narrow screens. Everything is plain Java and every view is a route.

This guide builds the whole shell in one pass: the drawer and its breakpoint, navigation linked to routes, a content grid that changes column count with the viewport, and what happens on a phone.

<!-- truncate -->

## Which layout component does what {#which-component}

Each component covers one job, and they nest inside each other.

| Component | Job | Responsive behavior |
|---|---|---|
| `AppLayout` | App shell with header, drawer, footer and content area | Drawer becomes a popover at a configurable breakpoint |
| `AppNav` | Hierarchical side navigation | Lives in the drawer, so it follows the drawer |
| `ColumnsLayout` | Multi-column forms | Column count changes at breakpoints you define |
| `FlexLayout` | Row or column of items | Items can wrap when they no longer fit |

## Build the app shell with AppLayout {#app-shell}

`AppLayout` is a ready-made page structure. The header and footer are fixed, the drawer slides in and out, and the content area scrolls. Each part is a `Div` that can hold any webforJ component. It works with webforJ dark mode, and the [AppLayout docs](/docs/components/app-layout) list every option.

Make the shell a route. Views then render inside it through the `outlet` parameter of `@Route`, so the header and drawer persist while the content changes. This pattern is a [layout route](/docs/routing/route-hierarchy/nested-routes).

```java
@AppMeta(name = "viewport",
    content = "width=device-width, initial-scale=1.0, viewport-fit=cover")
@Route
public class Shell extends Composite<AppLayout> {

  public Shell() {
    AppLayout layout = getBoundComponent();
    layout.setDrawerBreakpoint("(max-width: 700px)");
    layout.setHeaderOffscreen(false);

    layout.addToDrawerTitle(new Div("Acme"));
    layout.addToHeader(new AppDrawerToggle(), new H1("Orders"));
    layout.addToFooter(new Div("Acme Orders"));

    AppNav nav = new AppNav();
    nav.add(new AppNavLabel("Sales"));
    nav.addItem(new AppNavItem("Inbox", InboxView.class, TablerIcon.create("inbox")));
    nav.addItem(new AppNavItem("Outbox", OutboxView.class, TablerIcon.create("send-2")));
    layout.addToDrawer(nav);
  }
}
```

Three details matter here:

- The `viewport` meta tag with `viewport-fit=cover` scales the page to fill the device display.
- By default the header and footer sit beside an open drawer. `setHeaderOffscreen(false)` makes the header take the full width and pushes the drawer below it. `setFooterOffscreen(false)` does the same for the footer.
- `AppDrawerToggle` is a hamburger button. It works without an event listener.

## Set the drawer breakpoint {#drawer-breakpoint}

On a wide screen the drawer stays open next to the content. At 800px or narrower the drawer switches to popover mode by default, which means it slides over the content with an overlay. That switch point is the drawer breakpoint.

`setDrawerBreakpoint()` takes any valid [CSS media query](https://developer.mozilla.org/en-US/docs/Web/CSS/Media_Queries/Using_media_queries). The shell in the previous section uses `(max-width: 700px)`, so mid-size screens keep the docked drawer and phones get the popover.

`AppLayout` also supports three drawer positions: `DrawerPlacement.LEFT`, `DrawerPlacement.RIGHT` and `DrawerPlacement.HIDDEN`. Use `HIDDEN` for pages that need the full width and no navigation.

## Add side navigation with AppNav {#side-navigation}

`AppNav` builds the side menu out of `AppNavItem` entries. An item can link to a registered view class, a string path or an external URL. Passing the view class is the better choice for internal links:

```java
AppNavItem settings = new AppNavItem("Settings", SettingsView.class);
```

The item resolves its URL from the route registered on `SettingsView`. Rename the route and the link follows. A hard-coded path string would break instead. If the route has parameters, pass a `ParametersBag` as a third argument.

Items can nest. An `AppNavItem` without a path is an expandable group header, and `nav.setAutoOpen(true)` re-expands groups after a refresh. Prefix and suffix components add an icon or a badge to each item.

Long menus read better with section headings. `AppNavLabel` is a non-interactive heading that titles every item up to the next label, and the nav hides a label automatically when its section has no visible items. See the [AppNav docs](/docs/components/appnav) for labels and pinning.

## Make the content area responsive {#responsive-content}

The area inside the shell is where `ColumnsLayout` and `FlexLayout` come in.

### Change the column count with breakpoints {#columns-breakpoints}

`ColumnsLayout` is built mainly for forms. It places fields in columns that adapt to the width of its parent. You give it `Breakpoint` objects with an optional name, a minimum width, and a column count. Breakpoints are evaluated in ascending order of width. With none configured, the layout uses one column and switches to two at `40em`.

```java
@Route(outlet = Shell.class)
public class CustomerFormView extends Composite<ColumnsLayout> {

  public CustomerFormView() {
    ColumnsLayout form = getBoundComponent();
    form.setBreakpoints(List.of(
        new Breakpoint("small", 0, 1),
        new Breakpoint("medium", "40em", 2)));
    form.setHorizontalSpacing(16);
    form.setVerticalSpacing(16);

    TextArea notes = new TextArea("Notes");
    form.add(
        new TextField("First name"), new TextField("Last name"),
        new TextField("Email"), new TextField("Phone"),
        notes);
    form.setSpan(notes, "medium", 2);
  }
}
```

The fields stack in one column on a phone and sit side by side in two columns from `40em`. `setSpan(notes, "medium", 2)` makes the notes field take the full width once the `medium` breakpoint is active. `setColumn()` places an item in a specific column per breakpoint the same way.

Breakpoint widths take an integer for pixels or a string with units such as `em`, `vw` or `%`.

### Wrap action rows in a flex layout {#flex-wrap}

`FlexLayout` arranges children in a row or a column. For a row of actions that should drop to a second line instead of overflowing, enable wrapping:

```java
FlexLayout actions = FlexLayout.create(new Button("Filter"), new Button("Export"))
    .horizontal()
    .wrap()
    .build();
```

On an existing instance use `setWrap()`, and `setFlow()` sets direction and wrap together. Use `setJustifyContent()` and `setAlignment()` to position the items, and the `gap` property for minimum spacing between them. The [FlexLayout docs](/docs/components/flex-layout) cover the rest. Reach for `ColumnsLayout` for forms, and for `FlexLayout` when you want a flowing row or column.

## What users see on mobile {#mobile-behavior}

On a phone the header stays fixed and the drawer is closed. Tapping the `AppDrawerToggle` slides the drawer over the content with an overlay. Choosing an `AppNavItem` navigates to the route, and the content area renders the new view inside the same shell. Forms built with `ColumnsLayout` collapse to their single-column breakpoint.

To give small screens more room, `setHeaderReveal(true)` shows the header on first render, hides it when the user scrolls down and reveals it again on scroll up. `setFooterReveal(true)` does the same for the footer with the scroll directions reversed.

For a dashboard that puts this shell to work, see the [admin dashboard guide](/blog/java-admin-dashboard). For how routes, layouts and outlets fit together, see [route navigation in 10 minutes](/blog/master-webforj-routing-in-10-minutes).

## Build your own responsive shell {#next-step}

Create a project with [startforJ](https://docs.webforj.com/startforj), copy the `Shell` class from this guide, and add one view with `@Route(outlet = Shell.class)`. Resize the browser to watch the drawer collapse and the columns reflow. The [getting started guide](/docs/introduction/getting-started) covers the project setup.

## Frequently asked questions {#faq}

### How do I build a responsive layout? {#faq-responsive}

In webforJ, use `AppLayout` for the shell and a drawer breakpoint with `setDrawerBreakpoint()`. Use `ColumnsLayout` with `Breakpoint` objects for forms and `FlexLayout` with wrapping for rows. None of it needs custom CSS.

### How do I add side navigation? {#faq-side-navigation}

Create an `AppNav`, add `AppNavItem` entries that point to your view classes, and place it in the drawer with `addToDrawer()`. Each item resolves its URL from the route on its view class.

### Can the sidebar collapse on mobile? {#faq-mobile}

`AppLayout` switches the drawer to a popover at 800px or narrower by default. Change the switch point with `setDrawerBreakpoint()` and a media query, and add an `AppDrawerToggle` so users can open it.

### Can views share the same header and drawer? {#faq-shared-shell}

Yes. Make the shell a route and give each view `@Route(outlet = Shell.class)`. The router renders the view inside the shell, so the shell persists between pages.
