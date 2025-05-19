---
title: Opening URLs 
sidebar_class_name: new-content
---

The <JavadocLink type="foundation" location="com/webforj/Page" code='true'>Page</JavadocLink> utility class comes with a few options to open URL's. 

The `Page.getCurrent().open(...)` method is used to programmatically open a new browser window or tab with a specified URL.

## `open(...)`

Opens the provided URL in a new window or tab. It's possible to supply both the window name and a string with parameters like height,
position, or window type.

```java
Page.getCurrent().open("https://example.com");

Page.getCurrent().open("https://example.com", "self");

Page.getCurrent().open("https://webforj.com/", "webforJ",
    "popup,left=100,top=100,width=600,height=600");
```

These features match those available in standard [window.open()](https://developer.mozilla.org/en-US/docs/Web/API/Window/open) behavior in browsers.
