---
sidebar_position: 5
title: Route Patterns
---

**Route Patterns** are used to define how URLs map to specific views, including dynamic and optional segments, regular expressions, and wildcards. Route patterns enable the framework to match URLs, extract parameters, and generate URLs dynamically. They play a critical role in structuring an app's navigation and component rendering based on the browser's location.

## Route pattern syntax

Route patterns in webforJ are highly flexible, supporting the following features:

- **Named Parameters:** Denoted by `:paramName`, they are required unless marked as optional.
- **Optional Parameters:** Denoted by `:paramName?`, they can be omitted from the URL.
- **Wildcard Segments:** Represented by `*`, they capture all remaining segments of the URL.
- **Regular Expressions Constraints:** Constraints can be added only to named parameters (for example, `:id<[0-9]+>`).

### Example of route pattern definitions

```java
@Route("customer/:id<[0-9]+>/named/:name/*")
public class CustomerView extends Composite<Div> implements DidEnterObserver {

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    int id = parameters.getInt("id").orElse(0);
    String name = parameters.getAlpha("name").orElse("Unknown");
    String extra = parameters.getAlpha("*").orElse("");

    String result =
        "Customer ID: " + id + "-" +
        "Name: " + name + "-" +
        "*: " + extra;

    console().log(result);
  }
}
```

In this example:

- `:id<[0-9]+>` captures a numeric customer ID.
- `:name` captures a name.
- `*` captures any additional path segments beyond `named/:name`.

## Named parameters

Named parameters are defined by prefixing a colon `:` to the parameter name in the pattern. They're required unless marked as optional. Named parameters can also have regular expression [constraints](#regular-expression-constraints) to validate the values.

### Example:

```java
@Route("product/:id")
public class ProductView extends Composite<Div> {
  // Component logic here
}
```

This pattern matches URLs like `/product/123` where `id` is `123`

## Optional parameters

Optional parameters are indicated by adding a `?` after the parameter name. These segments aren't required and can be omitted from the URL.

### Example:

```java
@Route("order/:id?<[0-9]+>")
public class OrderView extends Composite<Div> implements DidEnterObserver {

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    parameters.getInt("id").ifPresentOrElse(
      id -> console().log("Order ID: " + id),
      () -> console().log("No Order ID was provided")
    );
  }
}
```

This pattern matches both `/order/123` to include a numeric value to be included, and `/order`, allowing the omission of a numeric value when `/order` is entered.

## Regular expression constraints

You can apply regular expression constraints to parameters by adding them within angle brackets `<>`. This allows you to specify stricter matching rules for parameters.

### Example:

```java
@Route("product/:code<[A-Z]{3}-[0-9]{4}>")
public class ProductView extends Composite<FlexLayout> implements DidEnterObserver {

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    parameters.get("code").ifPresentOrElse(
      code -> console().log("Product code: " + code),
      () -> console().error("Product code not found"));
  }
}
```

This pattern only matches product codes in the format `ABC-1234`. For example, `/product/XYZ-5678` will match, but `/product/abc-5678` won't.

## Wildcard segments

Wildcards can be used to capture entire paths following a specific route segment, but they can only appear as the final segment in the pattern, resolving all subsequent values in the URL. For better readability, wildcard segments can be named. However, unlike named parameters, wildcard segments can't have any constraints.

### Example:

```java
@Route("files/:pathname*")
public class FileManagerView extends Composite<Div> implements DidEnterObserver {

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    parameters.get("pathname").ifPresentOrElse(
      pathname -> console().log("FileManagerView: " + pathname),
      () -> console().log("FileManagerView: No pathname parameter")
    );
  }
}
```

This pattern matches any URL starting with `/files` and captures the rest of the path as a wildcard.

## Route priority

When multiple routes match a given URL, the priority attribute of a route determines which route is selected first. This is especially useful when two or more routes overlap in their path patterns, and you need a way to control which one is given precedence. The priority attribute is available in both `@Route` and `@RouteAlias` annotations.

### How the priority system works

The priority attribute allows the router to determine the order in which routes are evaluated when multiple routes could match a given URL. Routes are sorted based on their priority values, with higher priority (lower numeric values) being matched first. This ensures that more specific routes take precedence over more general ones.

If two routes share the same priority, the router resolves the conflict by selecting the route that was registered first. This mechanism ensures the correct route is chosen, even when multiple routes overlap in their URL patterns.

:::info Default Priority  
By default, all routes are assigned a priority of `10`.  
:::

### Example: Conflicting routes

Consider a scenario where two routes match similar URL patterns:

```java
@Route(value = "products/:category", priority = 9)
public class ProductCategoryView extends Composite<Div> implements DidEnterObserver {
  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    String category = parameters.get("category").orElse("unknown");
    console().log("Viewing category: " + category);
  }
}

@Route(value = "products/:category/:productId?<[0-9]+>")
public class ProductView extends Composite<Div> implements DidEnterObserver {
  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    String productId = parameters.get("productId").orElse("unknown");
    console().log("Viewing product: " + productId);
  }
}
```

Here’s how the priority system helps resolve conflicts:

- **`ProductCategoryView`** matches URLs like `/products/electronics`.
- **`ProductView`** matches more specific URLs like `/products/electronics/123`, where `123` is the product ID.

In this case, both routes could match the URL `/products/electronics`. However, because `ProductCategoryView` has a higher priority (priority = 9), it will be matched first when there is no `productId` in the URL. For URLs like `/products/electronics/123`, `ProductView` will be matched due to the presence of the `productId` parameter.