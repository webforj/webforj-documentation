---
sidebar_position: 2
title: Lifecycle Observers
---

Observers allow components to react to lifecycle events by implementing interfaces for specific stages. This pattern ensures a clean separation of concerns and simplifies handling navigation logic.

## Available observers {#available-observers}

- **`WillEnterObserver`**: Allows you to handle tasks before a route is entered, such as fetching necessary data or blocking navigation.
- **`DidEnterObserver`**: Ideal for handling actions after the component has been attached, such as rendering data or triggering animations.
- **`WillLeaveObserver`**: Provides a way to manage logic before a user leaves a route, such as checking for unsaved changes.
- **`DidLeaveObserver`**: Used for cleanup actions or other tasks that should run after a component is detached from the DOM.
- **`ActivateObserver`** (since 25.03): Triggered when a cached component is reactivated, such as when navigating to the same route with different parameters.

## Example: authentication with `WillEnterObserver` {#example-authentication-with-willenterobserver}

```java
@Route(value = "dashboard")
public class DashboardView extends Composite<Div> implements WillEnterObserver {

  @Override
  public void onWillEnter(WillEnterEvent event, ParametersBag parameters) {
    boolean isAuthenticated = authService.isAuthenticated();
    event.veto(!isAuthenticated);

    if (!isAuthenticated) {
      event.getRouter().navigate(LoginView.class);
    }
  }
}
```

Here, `onWillEnter` checks if the user is authenticated. If not, the navigation is vetoed, preventing the navigation from being complete and redirecting to the login page instead.

:::warning Example of Authenticated Routes - Not Production-Ready
This previous is just an example of how to use authenticated routes.
This **Isn't** an example how you'd write a production-level authentication system.
You'll need to take the concepts and patterns used in this example and adapt then to work with your authentication flow/system for your app.
:::

## Example: fetching data on route entry with `DidEnterObserver` {#example-fetching-data-on-route-entry-with-didenterobserver}

```java
@Route(value = "profile")
public class ProfileView extends Composite<Div> implements DidEnterObserver {

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    String userId = parameters.get("userId").orElseThrow();
    UserService.fetchProfile(userId).thenAccept(
        profile -> updateProfileUI(profile));
  }

  private void updateProfileUI(Profile profile) {
    // Code to update the UI with profile data
  }
}
```

This example demonstrates using `DidEnterObserver` to fetch and display profile data once the component is attached to the DOM.

## Example: Handling unsaved changes with `WillLeaveObserver` {#example-handling-unsaved-changes-with-willleaveobserver}

```java
@Route(value = "edit-profile")
public class EditProfileView extends Composite<Div> implements WillLeaveObserver {
  private boolean hasUnsavedChanges = false;

  public EditProfileView() {
    // Logic to detect unsaved changes
  }

  @Override
  public void onWillLeave(WillLeaveEvent event, ParametersBag parameters) {
    event.veto(hasUnsavedChanges);

    if(hasUnsavedChanges) {
      ConfirmDialog.Result result = showConfirmDialog(
          "There are unsaved changes. Do you want to discard or save them?",
          "Unsaved Changes",
          ConfirmDialog.OptionType.OK_CANCEL,
          ConfirmDialog.MessageType.WARNING);
    }
  }
}
```

In this example, `onWillLeave` prompts the user with a confirmation dialog if there are unsaved changes, vetoing the navigation if the user chooses to stay.

:::info Navigation Blocking and Veto Handling
For more information about blocking navigation, see [Navigation Blocking and Veto Handling](./navigation-blocking)
:::

## Example: Cleanup with `DidLeaveObserver` {#example-cleanup-with-didleaveobserver}

```java
@Route(value = "notifications")
public class NotificationsView extends Composite<Div> implements DidLeaveObserver {

  @Override
  public void onDidLeave(DidLeaveEvent event, ParametersBag parameters) {
    NotificationService.clearActiveNotifications();
  }
}
```

This example clears notifications after the user leaves the `NotificationsView`, using the `DidLeaveObserver` for cleanup.

## Example: Refreshing data with `ActivateObserver` {#example-refreshing-data-with-activateobserver}

:::info Since 25.03
The `ActivateObserver` and `ActivateEvent` are available starting from webforJ version `25.03`.
:::

```java
@Route(value = "product/:id")
public class ProductView extends Composite<Div> implements ActivateObserver {
  private String currentProductId;

  @Override
  public void onActivate(ActivateEvent event, ParametersBag parameters) {
    String productId = parameters.get("id").orElseThrow();
    
    // Component is being reused with different parameters
    if (!productId.equals(currentProductId)) {
      currentProductId = productId;
      refreshProductData(productId);
    }
  }

  private void refreshProductData(String productId) {
    // Code to fetch and display new product data
    ProductService.fetchProduct(productId).thenAccept(
        product -> updateProductUI(product));
  }
}
```

This example demonstrates using `ActivateObserver` to refresh data when navigating to the same route with different parameters. The component remains cached and is reactivated rather than recreated, improving performance while ensuring the UI displays the correct data.

:::tip Activation in Component Hierarchies
When navigating to a route, the `Activate` event fires for **all cached components in the hierarchy** that remain in the current path. For example, when navigating from `/products/123` to `/products/456`, both the parent `ProductsLayout` component and the child `ProductView` component receive the `Activate` event if they're cached and remain in the route hierarchy.
:::
