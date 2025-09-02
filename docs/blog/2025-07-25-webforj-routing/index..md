---
title: Master webforJ routing in 10 minutes
description: Everything you need to know about routing with webforJ in a quick breakdown
slug: Master webforJ routing in 10 minutes
date: 2025-07-25
authors: Eric Handtke
tags: [webforJ, Routing, SPA Routing, Web Development]
hide_table_of_contents: false
---

![Cover Image](/img/routingcover.png)
---
<!--vale off-->
We all probably know this situation; browsing a website or webapp, make a simple misclick and instinctively try to go back or undo it. Only to then be somewhere completely else, worst case not even on the site anymore. 

Seeing that user experience is one of the most important factors in modern web development, that scenario is one of the many reasons why a robust and well-maintained routing system is so important for our SPA.

In this article, I will give a quick overview on how to achieve that with webforJ. First we will take a look at movement between views, preserving different states, and in general intuitive interaction with our app. In around 10 minutes, you should be able to set up routes, handle advanced navigation scenarios, and follow the best practices for future maintenance.

## What is Routing? And what is an SPA?

Lets start off with the most important things first: what are we even talking about here? A Java SPA (Single Page Application) is using a single HTML page to dynamically update its content. This results in a faster and smoother user experience whilst posing the challenge of navigating these content updates.

That's where routing comes into play. To put it simple, we only replace the actual content of the page without reloading it, thus cutting out loading times for the user, making navigation seamless and overall keeping full control over our state.

With webforJ, you can handle routing entirely in Java, allowing you to define navigation paths, manage state and control how users move through your app.

## Basic route setup - @Route annotation usage

Setting up routes in webforJ is very straightforward. The core of routing is the `@Route` annotation, which you place on your view classes to map them to specific URL paths. When a user navigates to a given path, webforJ automatically displays the corresponding view - no manual wiring required.

For example, to create a simple home page and an about page:

```java
@Route("")
public class HomeView extends Composite<Div> {
    // Home page content
}

@Route("about")
public class AboutView extends Composite<Div> {
    // About page content
}
```

With this setup, visiting "/" will show you the HomeView, and "/about" will show you the AboutView. Its possible to add as many routes as you need, each mapped to its own Java class. 

## Advanced routing features - parameters, aliases, nested routes

Other than the very basic setup shown above you eventually come to the point where you need more advanced features. Of course webforJ also allows you implement those without much extra work. 

For route parameters, you can define those directly in the path, such as `@Route("user/:id")`. Using route parameters you can capture dynamic values from the URL and use them directly in your webforJ code. The way you do that is by utilizing the `ParametersBag` in navigation events so you can load data or update the UI based on the route.

Now i know that may sound very technical and complicated, but in reality - it isnt. Here you can see how such an implementation would look where the code automatically uses the id provided through the route. yoursite.com/user/eric would show my userprofile.

```java
import com.webforj.router.annotation.Route;
import com.webforj.router.event.DidEnterEvent;
import com.webforj.router.history.ParametersBag;
import com.webforj.component.Composite;

@Route(value = "user/:id")
public class UserProfileView extends Composite {
    @Override
    public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
        // Get the "id" parameter from the URL, e.g., /user/john → id = "john"
        String id = parameters.getAlpha("id").orElse("");
        // Use the id to fetch and display user data
        System.out.println("User ID: " + id);
    }
}
```

For even more flexibility, use the `@RouteAlias` annotation to map multiple URLs to the same view. For example, a profile page can be accessible via both `/profile` and `/user/me` by annotating the class with both `@Route` and `@RouteAlias`.

Nested routes are handled using the outlet parameter in the @Route annotation, letting you build parent-child hierarchies. This allows you to inject child views into specific areas of a parent layout, giving you free reign over your URL structures (e.g., /dashboard/settings).

## Browser integration and management

webforJ manages page titles and browser history automatically, additionally use the `@FrameTitle` annotation on your view classes to set the browser’s page title for each route. For example, `@FrameTitle("Dashboard")` ensures that when users navigate to the dashboard, the browser tab displays the correct title. If you set an app-wide title with `@AppTitle`, webforJ combines it with the frame title so everyone still knows where they currently are even if potentially tabbed away.

If you want your title to be dynamically set depending on things like route parameters or app state, you can simply implement the `HasFrameTitle` interface into your view. This will generate the title based on those things. This can be used for pages like user profiles, where the title should reflect the current user.

webforJ also manages browser history and deep linking out of the box. As users navigate, the URL updates without a full page reload, and the browser’s back/forward buttons work as expected. This directly prevents the user problems I described in my introduction and also makes sure your app is discoverable by search engines.

## Common patterns - Dashboard Routes, 404 Handling

A typical pattern in webforJ apps is to use a dedicated layout route for dashboards, with nested view routes for each dashboard section. For example, you might have a MainLayout class annotated with `@Route`, and then a `DashboardView` with `@Route(value = "/", outlet = MainLayout.class)` and `@RouteAlias(value = "/dashboard")`. This way you can utilize shared UI elements, like a drawer for navigation or headers, in the layout whilst you can freely change the content which is injected as children.

Unknown or invalid routes are not directly part of the routing features i talked about so far but instead are managed through the error handling webforJ offers. Since it is not part of what i want to talk about here i wont go into too much detail but will instead give a brief sample of how a replacement to the default 404 can be done and more can be read up in our dedicated  [documentation](https://docs.webforj.com/docs/advanced/error-handling#handler-selection). 

To replace the default 404 page that will appear when you try to reach a faulty route you can simply add an `ErrorHandler` implementation to your code, in this case a `NotFoundExceptionErrorHandler`. Register that handler according to the aforementioned documentation and replace the body with whatever html you want to display and you are ready to go.

```java
package com.example.error;

import com.webforj.error.ErrorHandler;

public class NotFoundExceptionErrorHandler implements ErrorHandler {
  @Override
  public void onError(Throwable throwable, boolean debug){
    String title = "Page not found";
    String content = "";
    /*Add the html content you want to show on 404 to the previous String */
    showErrorPage(title,content);
  }

}

```

- **Consistent Naming:** Name your route classes with clear, descriptive names that end in `View` for standard views and `Layout` for layout components. This helps webforJ auto-detect route types and keeps your code self-explanatory.
- **Logical Folder Structure:** Organize your views and layouts into folders by feature or section (e.g., `views/dashboard/`, `views/admin/`). This makes it easy to locate and manage related routes as your app grows.
- **Declarative Routing:** Use the `@Route` annotation for all routable components, and prefer static registration over dynamic unless you have advanced needs. Keep route paths simple, lowercase, and hyphenated (e.g., `/user-profile`).
- **Route Hierarchy:** Structure your routes hierarchically, using parent layouts for shared UI and child views for specific content.
- **Single Responsibility:** Each view or layout should have a focused purpose. Avoid mixing unrelated logic in a single component.
- **Type-Safe Navigation:** When navigating programmatically, use the view class rather than hardcoded strings. 

## Comparison to angular

Now a big question i can already sense is what makes webforJ routing different from other well known web dev frameworks like react for example and for that i have a side by side comparison.

<div style={{ display: 'flex', gap: '2rem', alignItems: 'flex-start' }}>
  <div style={{ flex: 1 }}>
    <h3>webforJ Routing</h3>
    <ul>
      <li><b>Server-side, Java-based</b>: Routes are defined using <code>@Route</code> annotations on Java classes.</li>
      <li><b>Component Mapping</b>: Each route maps to a Java component; navigation instantiates new route objects.</li>
      <li><b>Browser History</b>: webforJ manages browser history and navigation events on the server.</li>
      <li><b>Deep Linking</b>: Supported, handled by the server.</li>
      <li><b>404 Handling</b>: Managed via Java error handlers (e.g., <code>NotFoundExceptionErrorHandler</code>).</li>
      <li><b>Integration</b>: Can be used standalone or with Spring Boot (with dependency injection for routes).</li>
      <li><b>UI Updates</b>: Navigation triggers server-side rendering and updates the client UI.</li>
    </ul>
  </div>
  <div style={{ flex: 1 }}>
    <h3>React Router</h3>
    <ul>
      <li><b>Client-side, JavaScript-based</b>: Routes are defined in JSX using <code>&lt;Route&gt;</code> components.</li>
      <li><b>Component Rendering</b>: Navigation swaps React components in the browser without a full page reload.</li>
      <li><b>Browser History</b>: Managed in the browser using the History API.</li>
      <li><b>Deep Linking</b>: Supported, handled by the client; server must serve the SPA for all routes.</li>
      <li><b>404 Handling</b>: Handled by a catch-all <code>&lt;Route path=\"*\"&gt;</code> in the route config.</li>
      <li><b>Integration</b>: Works with any React app, often combined with state management libraries.</li>
      <li><b>UI Updates</b>: Navigation and view updates happen instantly in the browser.</li>
    </ul>
  </div>
</div>

## Where to go now?

We have now gone over how to build up routing in webforJ that's both easily maintainable and implemented as well as some structures to follow so that users can navigate through your app without any problems. Of course, this article can't cover everything there is to know about routing, and there would be more topics we could delve into like data binding and security, but that would go beyond the scope of what we set out to do here.

If you want to check those topics out, feel free to look through our official [documentation](https://webforj.dev/docs/routing/overview), where you will find a plethora of different articles detailing everything you need to know about those topics as well.

As a next step, try building a small multi-view app to reinforce these concepts. Once you’re comfortable, explore more advanced topics like authentication flows, dynamic route registration, and custom navigation guards.

<!--vale on-->