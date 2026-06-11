---
title: AppNav
sidebar_position: 6
description: Build hierarchical side navigation menus with AppNav and AppNavItem, linking to routes, registered views, or external URLs.
---

<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-app-nav" />
<DocChip chip="name" label="dwc-app-nav-item" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="appnav" location="com/webforj/component/appnav/AppNav" top='true'/> 

The `AppNav` component creates a side navigation menu out of `AppNavItem` entries. Items can link to internal views or external resources, nest under parent items to form hierarchical menus, and carry icons, badges, or other components to give users more context at a glance.

<!-- INTRO_END -->

## Adding and nesting items {#adding-and-nesting-items}

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

<!--vale off-->
<ComponentDemo
path='/webforj/appnav/Social'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/appnav/AppNavView.java',
  'src/main/java/com/webforj/samples/views/appnav/AppNavPageView.java',
]}
/>
<!--vale on-->

## Linking Items {#linking-items}

Each `AppNavItem` can navigate to an internal view or an external link. You can define this using static paths or registered view classes.

### Static paths {#static-paths}

Use string paths to define links directly:

```java
AppNavItem docs = new AppNavItem("Docs", "/docs");
AppNavItem help = new AppNavItem("Help", "https://support.example.com");
```

### Registered views {#registered-views}

If your views are registered with the [router](../routing/overview), you can pass the class instead of a hardcoded URL:

```java
AppNavItem settings = new AppNavItem("Settings", SettingsView.class);
```

If your annotated route supports [route parameters](../routing/route-patterns#named-parameters), you can also pass a `ParametersBag`:

```java
ParametersBag params = ParametersBag.of("id=123");
AppNavItem advanced = new AppNavItem("User", UserView.class, params);
```

### With query parameters {#with-query-parameters}

Pass a `ParametersBag` to include query strings:

```java
ParametersBag params = ParametersBag.of("param1=value1&param2=value2");
AppNavItem advanced = new AppNavItem("Advanced", SettingsView.class, params);
advanced.setQueryParameters(params);
```

## Target behavior {#target-behavior}

Control how links open using `setTarget()`. This is especially useful for external links or pop-out views.

- **`SELF`** (default): Opens in the current view.
- **`BLANK`**: Opens in a new tab or window.
- **`PARENT`**: Opens in the parent browsing context.
- **`TOP`**: Opens in the top-level browsing context.

```java
AppNavItem help = new AppNavItem("Help", "https://support.example.com");
help.setTarget(AppNavItem.NavigationTarget.BLANK);
```

## Prefix and suffix {#prefix-and-suffix}

`AppNavItem` supports prefix and suffix components. Use these to provide visual clarity with icons, badges, or buttons.

- **Prefix**: appears before the label, useful for icons.
- **Suffix**: appears after the label, great for badges or actions.

```java
AppNavItem notifications = new AppNavItem("Alerts");
notifications.setPrefixComponent(TablerIcon.create("alert");
notifications.setSuffixComponent(TablerIcon.create("link"));
```

## Auto-opening groups {#auto-opening-groups}

Use `setAutoOpen(true)` on the `AppNav` component to automatically expand nested groups when the app is refreshed.

```java
nav.setAutoOpen(true);
```

## Pinning {#pinning}
 
Pinning lets a user lift the items they reach for most into a group at the top of the navigation, so a deep menu still keeps a short list of favorites within one click. It's off by default. Turn it on through the pinning configuration:
 
```java
AppNav nav = new AppNav();
nav.getPinning().setEnabled(true);
```
 
Once enabled, every navigable leaf item shows a pin toggle. The toggle is revealed on hover and on keyboard focus, so it stays reachable without a mouse. Activating it moves the item into the pinned group at the top of the nav.
 
A few rules govern what can be pinned and how the group behaves:
 
- Only navigable leaf items are pinnable. Group headers (items with children) are never pinnable.
- The pinned group appears only once something is pinned, and disappears again when the last item is unpinned.
- Unpinning returns an item to its exact original position, including items nested several levels deep inside groups.
- The item is moved, not copied, so any prefix or suffix content and any listeners attached to it keep working while it sits in the pinned group.

The demo below has pinning enabled with a custom group title and Dashboard pinned on load. Hover or focus a leaf item to reveal its pin toggle.
 
<!--vale off-->
<ComponentDemo
path='/webforj/appnavpinning/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/appnav/AppNavPinningView.java',
  'src/main/java/com/webforj/samples/views/appnav/AppNavPinningPageView.java',
]}
/>
<!--vale on-->
 
### Starting an item pinned {#starting-an-item-pinned}
 
Start an item in the pinned group by setting its pinned state. Use `isPinned()` to read the current state.
 
```java
AppNavItem reports = new AppNavItem("Reports", "/reports");
reports.setPinned(true);
```
 
:::info Pinning must be enabled
`setPinned(true)` only takes effect when pinning is enabled on the `AppNav` through `getPinning().setEnabled(true)`. Without it, the call has no effect.
:::
 
### Pinned group title {#pinned-group-title}
 
The pinned group is labeled `Pinned` by default. Change it to fit your app:
 
```java
nav.getPinning().setTitle("Favorites");
```
 
### Pin keys {#pin-keys}
 
Each pinnable item carries a key that identifies it for persistence and for the [pin event](#reacting-to-pin-changes). When you don't set one, the key falls back to the item's path, so `getPinKey()` always returns a usable value.
 
```java
AppNavItem reports = new AppNavItem("Reports", "/reports");
reports.setPinKey("reports");
```
 
Set an explicit key when the path can change at runtime. A stable key keeps a pin matched to the right item across reloads even if its URL moves.
 
### Autosave to local storage {#autosave}
 
Pins live only for the current page view unless you persist them. Autosave is the simplest option: it stores the set of pinned items in the browser's local storage and restores them on reload. It's off by default. It needs a stable `id` (or name) on the component for the storage key, and the `AppNav(String id)` constructor is the convenient way to set one:
 
```java
AppNav nav = new AppNav("main-nav"); // gives autosave a stable storage key
nav.getPinning().setAutosave(true);
```
 
:::info Autosave needs an id
With no `id` (or name) on the component, autosave silently does nothing, since it has no stable key to store under. Persistence is per browser, so pins do not follow a user to another device or browser.
:::
 
### Custom persistence {#custom-persistence}
 
For persistence you control, for example per user on the server, turn autosave off and drive it yourself through the [pin event](#reacting-to-pin-changes) and `setPinned`:
 
```java
nav.getPinning().setAutosave(false);
 
// persist the current set of pinned keys whenever it changes
nav.onPin(event -> savePins(event.getKeys()));
 
// on load, restore each saved key
restoredKeys.forEach(key -> findItem(key).setPinned(true));
```
 
### Reacting to pin changes {#reacting-to-pin-changes}
 
The pin event fires whenever an item is pinned or unpinned. It carries the item that changed, its key, the new pinned state, and the full ordered set of pinned keys:
 
```java
nav.onPin(event -> {
  AppNavItem item = event.getItem(); // the item that changed, or null if it is no longer in the nav
  boolean pinned = event.isPinned();
  String key = event.getKey();
  List<String> all = event.getKeys(); // every pinned key, in pinned order
});
```
 
`getItem()` resolves the item by matching its pin key, and returns `null` when the item is no longer part of the navigation.
 
### Pin icons {#pin-icons}
 
The toggle uses the built in `dwc:pin` icon while an item is unpinned and `dwc:pinned-off` while it's pinned. Swap in your own through `setUnpinnedIcon` and `setPinnedIcon`, which accept any `IconDefinition`:
 
```java
nav.getPinning()
   .setUnpinnedIcon(TablerIcon.create("pin"))
   .setPinnedIcon(TablerIcon.create("pinned-off"));
```
 
### Pin toggle on touch devices {#pin-toggle-on-touch}
 
Touch devices have no hover to reveal the pin, so the toggle is hidden there by default. Keep it visible and tappable on touch by turning on touch visibility:
 
```java
nav.getPinning().setTouchVisible(true);
```
 
## Search {#search}
 
The search field filters the menu by item label as the user types. It's off by default. You can show it and give it a placeholder through the search configuration:
 
```java
nav.getSearch().setFieldVisible(true);
nav.getSearch().setPlaceholder("Search");
```
 
As the user types, the nav filters items by label, opens any group that contains a match, and shows an empty message when nothing matches. Pinned shortcuts stay visible while searching, so a user's favorites remain one click away even mid filter.
 
<!--vale off-->
<ComponentDemo
path='/webforj/appnavsearch/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/appnav/AppNavSearchView.java',
  'src/main/java/com/webforj/samples/views/appnav/AppNavSearchPageView.java',
]}
/>
<!--vale on-->
 
### Empty message {#search-empty-message}
 
Set the message shown when a search returns no results. Plain text is rendered as text:
 
```java
nav.getSearch().setEmptyMessage("No items found");
```
 
To render rich content, wrap the message in `<html>...</html>`:
 
```java
nav.getSearch().setEmptyMessage("<html><strong>Nothing found</strong></html>");
```
 
### Driving search from your own field {#custom-search-box}
 
Hide the built in field and feed the filter from an input of your own. Push the current term in through `setTerm`:
 
```java
nav.getSearch().setFieldVisible(false);
 
myField.onModify(event -> nav.getSearch().setTerm(event.getText()));
```
 
To react to what the user types in the built in field, listen for the search event:
 
```java
nav.onSearch(event -> log(event.getTerm()));
```

## Styling `AppNavItem` {#styling-appnavitem}

<TableBuilder name="AppNavItem" />