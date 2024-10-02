---
sidebar_position: 4
title: Route Navigation
---

In webforJ, navigating between routes is the core mechanism for switching views and components based on user actions or URL changes. Navigation allows users to move seamlessly between different parts of the app without refreshing the page. This client-side navigation keeps the app responsive and smooth while preserving the app's state.

## Programmatic navigation

You can trigger navigation from anywhere in your app by using the `Router` class. This allows dynamic changes in the displayed components based on events such as button clicks or other user interactions.

Here’s an example of how to navigate to a specific route:

```java
@Route(value = "dashboard")
public class DashboardView extends Composite<Div> {
  // Component logic here
}
```

```java
// navigate to the view
Router.getCurrent().navigate(DashboardView.class);
```

In this example, navigating to `DashboardView` component programmatically  causes the `DashboardView` component to be rendered
and the browser's URL to updated to `/dashboard`.

It's also possible to navigate to the view by passing a new `Location`

```java
Router.getCurrent().navigate(new Location("/dashboard"));
```

:::tip Class vs. Location: Methods for View Routing
When navigating between views, developers have two options: they can either pass the view or route class, allowing the router to automatically generate the URL and render the view, or pass the location directly. Both methods are valid, but **using the view class is the preferred approach** because it offers better flexibility for future changes. For instance, if you later decide to update the route, you only need to modify the `@Route` annotation, without having to change any code that uses the view class for navigation.
:::

### Navigation with parameters

When you need to pass parameters along with the route, webforJ allows you to embed parameters in the URL. Here’s how you can navigate to a route with parameters:

```java
@Route("user/:id")
public class UserProfileView extends Composite<Div> implements DidEnterObserver {
  H1 title = new H1();

  public UserProfileView() {
    getBoundComponent().add(title);
  }

  public void setTile(String title) {
    this.title.setText(title);
  }

  public String getTitle() {
    return title.getText();
  }

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    String id = parameters.getAlpha("id").orElse("Unknown");
    setTile(id);
  }
}
```

```java
// navigate to view and pass the user id
Router.getCurrent().navigate(
  UserProfileView.class,
  ParametersBag.of("id=JohnDoe")
);
```

This navigates to `/user/JohnDoe`, where `JohnDoe` might represent a user ID. The component for this route can then extract the parameter and use it accordingly.

## Created view instance

The `navigate` method accepts a Java `Consumer` that's invoked once navigation is complete. The `Consumer` receives the instance of the created view component, wrapped in a java `Optional`, allowing the developer to interact with the view after a successful navigation.

```java
Router.getCurrent().navigate(
    UserProfileView.class,
    ParametersBag.of("id=JohnDoe"), (component) -> {
      component.ifPresent(view -> {
        console().log("The new title is: " + view.getTitle());
      });
    });
```

:::info Null instances
The consumer receives a Java `Optional` for the component because it might be `null`, or not created for various reasons. For example, the component may not be rendered if the navigation observers veto the navigation and stop the process.
:::

## Navigation options

The `NavigationOptions` class allows developers to fine-tune how navigation is handled within the app. By setting specific options, you can control the behavior of navigation, such as whether to update the browser's history, invoke lifecycle observers, or even fire navigation events.

```java
NavigationOptions options = new NavigationOptions();
options.setUpdateHistory(false);

Router.getCurrent().navigate(
  new Location("user/JohnDoe"), options);
```

### Setting navigation options

The `NavigationOptions` class provides several methods for customizing navigation behavior. These include controlling how routes are handled, whether observers are notified, and how the browser's history is updated.

Here are the main configuration options available within `NavigationOptions`:

1. **Navigation Type (`setNavigationType`)**  
   This option defines whether the new route should be added to the browser's history or replace the current route.

   - **`PUSH`**: Adds the new route to the history stack, preserving the current location.
   - **`REPLACE`**: Replaces the current route in the history stack with the new location, preventing the back button from navigating to the previous route.

2. **Fire Events (`setFireEvents`)**  
   Determines whether navigation [lifecycle events](./navigation-lifecycle/navigation-events) should be fired during navigation. By default, this is set to `true`, and events are fired. If set to `false`, no events will be fired, which is useful for silent navigation.

3. **Invoke Observers (`setInvokeObservers`)**  
   This flag controls whether the navigation should trigger [observers](./navigation-lifecycle/observers) within the navigated components. Observers typically handle events like route entry or exit. Setting this to `false` prevents observers from being invoked.

4. **Update History (`setUpdateHistory`)**  
   When set to `false`, this option prevents the history location from being updated. This is useful when you want to change the view without affecting the browser’s back or forward navigation. It only affects history management, not the component lifecycle or route handling.

5. **State Object (`setState`)**  
   [The state object](./state-managmenet#saving-and-restoring-state-in-browser-history) allows you to pass additional information when updating the browser’s history. This object is stored in the browser's history state and can be used later for custom purposes, like saving the state of the app during navigation.

## Generating locations for views

The router can generate the location for views based on the route pattern defined in the view. You can also provide additional parameters for dynamic and required segments in the URL. This can be useful when constructing links or sharing direct access points to specific views in the app.

Here’s how to generate a `Location` based on a view class and route parameters:

```java
Class<UserProfileView> userProfileView = UserProfileView.class;
ParametersBag params = ParametersBag.of("id=JohnDoe");

Optional<Location> location = Router.getCurrent().getLocation(userProfileView, params);
console().log(location.get());
```

This generates a `Location` object with the path `/user/JohnDoe`, the complete URI as a string.
