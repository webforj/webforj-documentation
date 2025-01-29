---
sidebar_position: 1
title: Routing
hide_table_of_contents: true
---

<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<!-- vale off -->
import DocCardList from '@theme/DocCardList';

<!-- vale on -->

In modern web applications, **routing** refers to the process of managing navigation between different views or components based on the current URL or path. In webforJ, routing establishes a sophisticated framework for **client-side navigation**, where UI updates happen dynamically without requiring full page reloads, enhancing the performance of your app.

## Traditional vs client-side routing

In traditional server-side routing, when a user clicks a link, the browser sends a request to the server for a new document. The server responds by sending a fresh HTML page, which forces the browser to re-evaluate CSS and JavaScript, re-render the entire document, and reset the app state. This cycle introduces delays and inefficiencies, as the browser must reload resources and the page state. The process typically involves:

1. **Request**: The user navigates to a new URL, triggering a request to the server.
2. **Response**: The server sends back a new HTML document along with related assets (CSS, JS).
3. **Rendering**: The browser re-renders the entire page, often losing the state of previously loaded pages.

This approach can lead to performance bottlenecks and suboptimal user experiences due to repeated full-page reloads.

**Client-Side Routing** in webforJ solves this by enabling navigation directly in the browser, dynamically updating the UI without sending a new request to the server. Here's how it works:

1. **Single Initial Request**: The browser loads the app once, including all required assets (HTML, CSS, JavaScript).
2. **URL Management**: The router listens for URL changes and updates the view based on the current route.
3. **Dynamic Component Rendering**: The router maps the URL to a component and renders it dynamically, without refreshing the page.
4. **State Preservation**: The state of the app is maintained between navigations, ensuring smooth transition between views.

This design enables **deep linking** and **URL-driven state management**, allowing users to bookmark and share specific pages within the app while enjoying a smooth, single-page experience.

## Core principles

- **URL-Based Component Mapping**: In webforJ, routes are directly tied to UI components. A URL pattern is mapped to a specific component, dictating what content is displayed based on the current path.
- **Declarative Routing**: Routes are defined declaratively, typically using annotations. Each route corresponds to a component that is rendered when the route is matched.
- **Dynamic Navigation**: The router dynamically switches between views without reloading the page, keeping the app responsive and fast.

## Example of client-side routing in webforJ

Here’s an example of defining a route for a `UserProfileView` component to display user details based on the `id` parameter in the URL:

```java
@Route(value = "user/:id")
public class UserProfileView extends Composite<Div> implements DidEnterObserver {

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    String id = parameters.getAlpha("id").orElse("");
    refreshProfile(id);
  }
}
```

In this setup:

- Navigating to `/user/john` would render the `UserProfileView` component.
- The `id` parameter would capture `john` from the URL and allow you to use it within the component to fetch and display user data.

## Topics

<DocCardList className="topics-section" />