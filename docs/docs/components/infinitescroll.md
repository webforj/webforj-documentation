---
title: InfiniteScroll
sidebar_position: 60
---

<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-infinitescroll" />
<JavadocLink type="foundation" location="com/webforj/component/infinitescroll/InfiniteScroll" top='true'/>

The `InfiniteScroll` component in webforJ automatically loads more content as users scroll down, eliminating the need for pagination. This creates a smooth experience for lists, feeds, and data-heavy views by loading content only when needed.

## Basic usage

When users reach the bottom of scrollable content, `InfiniteScroll` triggers an event for loading more data. While new content loads, it displays a `Spinner` with customizable text to indicate more items are on the way.

<ComponentDemo 
path='/webforj/infinitescroll?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaView.java'
height = '400px'
/>

## State management

The `InfiniteScroll` component emits events and maintains internal state to help manage how and when content is loaded.

To fetch more data when the user scrolls, use the `onScroll()` or `addScrollListener()` method to register a listener. Inside the listener, you typically load additional content and call `update()` to refresh the `InfiniteScroll` state.

```java
infiniteScroll.onScroll(event -> {
    infiniteScroll.add(new Paragraph("Loaded item"));
    infiniteScroll.update();
});
```

Once all content has been loaded, mark the scroll as completed to prevent further triggers. After setting completed, remember to call `update()` to apply the new state:

```java
infiniteScroll.setCompleted(true);
infiniteScroll.update();
```
This disables further infinite scrolling behavior.

:::tip 
You can reset this flag using `setCompleted(false)` if you later allow the user to load more content (e.g., after a refresh).
:::


## Loading indicator customization

By default, `InfiniteScroll` shows a built-in loading indicator — a small animated `Spinner` along with a “Loading data” text.

You can change the displayed text by passing a custom message to the `InfiniteScroll` constructor or by using `setText()`.

```java
InfiniteScroll infiniteScroll = new InfiniteScroll("Fetching more records...");
```

```java
infiniteScroll.setText("Loading more items...");
```

Similarly, you can customize the `Icon` displayed during loading by using `setIcon()`.

<ComponentDemo 
path='/webforj/infinitescrollloading?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaView.java'
height = '400px'
/>

:::info
See the [`Icon`](../components/icon) component for more information about managing [`Icons`](../components/icon) and custom images.
:::

### Full customization

If you want to completely replace both the `Spinner` and the text with your own markup,
you can add content directly into the special content slot using `addToContent()`.

When you populate the content slot, it replaces the default loading layout entirely.

<ComponentDemo 
path='/webforj/infinitescrollcustomloading?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaView.java'
height = '400px'
/>


## Styling

### Shadow parts

<TableBuilder tag='dwc-infinite-scroll' table="parts"/>

### Reflected Attributes

<TableBuilder tag='dwc-infinite-scroll' table='reflects'/>

### Dependencies

<TableBuilder tag='dwc-infinite-scroll' table="dependencies"/>

