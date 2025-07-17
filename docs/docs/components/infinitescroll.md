---
title: InfiniteScroll
sidebar_position: 60
---

<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-infinite-scroll" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="infinite-scroll" location="com/webforj/component/infinitescroll/InfiniteScroll" top='true'/>

The `InfiniteScroll` component in webforJ automatically loads more content as users scroll down, eliminating the need for pagination. This creates a smooth experience for lists, feeds, and data-heavy views by loading content only when needed.

When users reach the bottom of scrollable content, `InfiniteScroll` triggers an event for loading more data. While new content loads, it displays a [`Spinner`](../components/spinner) with customizable text to indicate more items are on the way.

<AppLayoutViewer
path='/webforj/infinitescroll?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollView.java'
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/static/css/infinitescroll/infinitescroll.css'
height = '400px'
mobile='true'
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

:::tip Reset the Load Flag
You can reset this flag using `setCompleted(false)` if you later allow the user to load more content (e.g., after a refresh).
:::


## Loading indicator customization

By default, `InfiniteScroll` shows a built-in loading indicator - a small animated [`Spinner`](../components/spinner) along with a “Loading data” text. You can change the displayed text by passing a custom message to the `InfiniteScroll` constructor or by using `setText()`.

```java
InfiniteScroll infiniteScroll = new InfiniteScroll("Fetching more records...");
infiniteScroll.setText("Loading more items...");
```

Similarly, you can customize the [`Icon`](../components/icon) displayed during loading by using `setIcon()`.

<AppLayoutViewer
path='/webforj/infinitescrollloading?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollLoadingView.java'
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/static/css/infinitescroll/infinitescroll.css'
height = '400px'
mobile='true'
/>

### Full customization

If you want to completely replace both the [`Spinner`](../components/spinner) and the text with your own markup,
you can add content directly into the special content slot using `addToContent()`.

When you populate the content slot, it replaces the default loading layout entirely.

<AppLayoutViewer
path='/webforj/infinitescrollcustomloading?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollCustomLoadingView.java'
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/static/css/infinitescroll/infinitescrollcustom.css'
height = '400px'
mobile='true'
/>

## Styling

<TableBuilder name="InfiniteScroll" />