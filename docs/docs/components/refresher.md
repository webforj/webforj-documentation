---
title: Refresher
sidebar_position: 101
sidebar_class_name: new-content
---

<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-refresher" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="refresher" location="com/webforj/component/refresher/Refresher" top='true'/>

The `Refresher` component in webforJ enables a pull-to-refresh interaction within scrollable containers—ideal for dynamic data loading in mobile or tap-friendly interfaces. As users swipe downward past a configurable threshold, the refresher transitions through visual states: `pull`, `release`, and `refreshing`. Each state presents a customizable icon and localized text to clearly communicate feedback.

You can use `Refresher` in tandem with components like [`InfiniteScroll`](../components/infinitescroll) to reload content or reset state via simple gesture-based input. The component is fully configurable in terms of interaction behavior, appearance, localization, and integration with the rest of your UI.

## Instantiation and internationalization

Add a `Refresher` by instantiating it and registering a refresh listener. When refresh operations complete, call `finish()` to reset the component to its idle state.

:::info How to activate the `Refresher`
To activate the `Refresher`, **click and drag downward** from the top of the scrollable area. While this gesture is familiar on mobile, it's less common on desktop—make sure to hold and pull with your mouse.
:::

<AppLayoutViewer
path='/webforj/refresher?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/refresher/RefresherView.java'
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/css/refresher/refresher.css'
height = '400px'
mobile='true'
/>

This approach is commonly used to refresh paginated lists or restart infinite scroll loading.

### Internationalization

Each state label can also be localized using the `RefresherI18n` object. The three states are:

- Pull: Initial gesture text (e.g., "Pull down to refresh")
- Release: Trigger threshold reached (e.g., "Release to refresh")
- Refresh: Loading state (e.g., "Refreshing")

This allows multilingual support and branding adjustments as needed.

<AppLayoutViewer 
path='/webforj/refresheri18n?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/refresher/RefresherI18nView.java'
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/css/refresher/refresher.css'
height = '400px'
mobile='true'
/>

## Icon customization

You can change the [`Icons`](../components/icon) used for the `pull`/`release` and `refreshing` stages using either a predefined [`Icon`](../components/icon) or an [Icon URL](../managing-resources/assets-protocols). These are useful when you want to apply branding or a custom animation.

<AppLayoutViewer 
path='/webforj/refreshericon?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/refresher/RefresherIconView.java'
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/css/refresher/refresher.css'
height = '400px'
mobile='true'
/>

## Pull behavior configuration

### Threshold

Set how far the user must pull down (in pixels) before triggering the refresh:

```java
refresher.setThreshold(80); // default: 80px
```

### Threshold maximum

To define the maximum pull distance allowed, use the `setThresholdMax()` method:

```java
refresher.setThresholdMax(160);
```

These thresholds control the gesture sensitivity and resistance curve.

## State management

The `Refresher` component maintains its own internal state and communicates state changes through events. When a user pulls down past the defined threshold, the `Refresher` emits a refresh event that you can respond to by registering an `onRefresh()` listener.

Inside this listener, you’re expected to perform whatever operation is required—such as fetching new data or resetting a list—and then explicitly call:

```java
refresher.finish();
```
:::warning Missing `finish()`
If you forget to call `finish()`, the refresher will remain in the loading state indefinitely.
:::

You can also programmatically disable the `Refresher` at any time to prevent the user from triggering refresh behavior:

```java
refresher.setEnabled(false);
```

This is useful when refreshes should be temporarily disallowed—for instance, during a loading screen or while another critical process is running.

## Styling

### Themes

The `Refresher` component supports multiple themes to visually distinguish different states or to match your app's look and feel. Themes can be applied using the `setTheme()` method.

The following sample cycles through all available themes each time you pull to refresh, giving you a live preview of how the `Refresher` looks across different themes:

<AppLayoutViewer 
path='/webforj/refresherthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/refresher/RefresherThemesView.java'
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/css/refresher/refresher.css'
height = '400px'
mobile='true'
/>

<TableBuilder name="Refresher" />