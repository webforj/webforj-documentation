---
sidebar_position: 5
title: Client/Server Interaction
---

The following section discussed various performance qualities and best practices for webforj, as well as implementation details for the framework.

When creating an application in webforj, the client and the server work together to manipulate data between client and server can be broken down into the broad categories:

### 1. Server to Client

webforJ methods such as `setText()` are included in this category. webforJ application running on the server sends data to the client without waiting for a response. webforJ automatically optimizes batches of operations in this category to improve performance.   

### 2. Client to Server

This category covers event traffic, such as a `Button.onClick()` method. For the most part, the client sends events to the server without waiting for any response. The event object typically contains additional parameters relating to the event, such as the hashcode. Because this information is delivered to the server as part of the act of delivering the event, it is immediately available to the program as soon as the event is received. 

### 3. Server to Client to Server (Round Trip)

Round trips are performed when the application queries the client for some dynamic information that cannot be cached on the server. Methods such as `Label.getText()` and `Checkbox.isChecked()` fall into this category. When a webforj application executes a line such as `String title = myLabel.getText()`, it comes to a complete standstill while the server sends that request to the client, then waits for the client to send the response back.

If the application sends several messages to the client that don't require a response (category 1), followed by a single message that requires a round trip (category 3), the application must wait for the client to process all pending messages, then respond to the final message that requires a response. In some cases, this can add a delay. If that round trip had not been introduced, the client would have been able to continue working through processing those backlogged messages while the application running on the server moved on to new work. 

### Improve Performance

It is possible to significantly improve application responsiveness by avoiding the third category's round trips as much as possible. For example, changing the ComboBox's onSelect functionality from this:

```java
private void comboBoxSelect(ListSelectEvent ev){
    ComboBox component = (ComboBox) ev.getComponent();

    // Goes to the client
    int selected = component.getSelectedIndex();
}
```

to the following:

```java
private void comboBoxSelect(ListSelectEvent ev){
    //Gets value from the event
    int selected = ev.getSelectedIndex();
}
```

In the first snippet, `ComboBox.getSelectedIndex()` being performed on the component forces a round trip back to the client, introducing a delay. In the second version, using the event's `ListSelectEvent.getSelectedIndex()` method retrieves the value that was delivered to the server as part of the original event.

### Caching

webforJ further optimizes performance by utilizing caching. In general, two types of data exist in this context: data that the user can directly change, and data that cannot be changed by the user. In the first case, when retrieving the information that users will directly interact with, it is necessary to query the server for this information. 

However, information which cannot be changed by the user can be cached to avoid additional performance hits. This ensures that a round trip does not need to be made unnecessarily, providing a more efficient user experience. webforJ optimizes applications in this manner to ensure optimal performance. 

### Loading Time 

When the user launches a webforj app, it loads
just a tiny chunk (only about 2.5kB gzip) of JavaScript to bootstrap the session.
After that, it dynamically downloads individual messages, or chunks of
JavaScript, on-demand as the application uses the corresponding
functionality. For example, the server only sends the client the JavaScript
necessary to build a webforj `Button` once — when the application creates its
first `Button` component. This results in measurable improvements to the initial
load time, which results in a better user experience.



