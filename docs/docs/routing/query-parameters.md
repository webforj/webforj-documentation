---
sidebar_position: 6  
title: Query Parameters
---

Query parameters allow you to pass additional data through URLs, using the format `?key1=value1&key2=value2`. While route parameters are used to pass required data within the URL path, query parameters provide a flexible mechanism for passing optional or additional data. They're especially useful when filtering content, sorting, or handling multiple values for the same key.

## Query parameters overview

Query parameters in webforJ follow the typical URL convention: key-value pairs separated by `=` and concatenated with `&`. They're appended to the URL after a `?` and provide a flexible way to pass optional data, such as filtering or sorting preferences.

For example:

```
/products?category=electronics&sort=price
```

## Retrieving query parameters

Query parameters are accessed through the `ParametersBag` object. To retrieve query parameters, use the `getQueryParameters()` method of the `Location` object.

Here’s how you can retrieve query parameters from a URL in a view:

```java
@Route(value = "products")
public class ProductView extends Composite<Div> implements DidEnterObserver {

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    ParametersBag queryParameters = event.getLocation().getQueryParameters();

    String category = queryParameters.get("category").orElse("all");
    String sort = queryParameters.get("sort").orElse("default");

    console().log("Category: " + category);
    console().log("Sort: " + sort);
  }
}
```

In this example:
- The `onDidEnter` method retrieves query parameters from the `Location` object provided by the `DidEnterEvent`.
- The `ParametersBag` allows you to retrieve specific query parameters using `get()`, which returns an `Optional<String>`. You can specify a default value using `orElse()` if the parameter isn't present.

:::tip `ParametersBag` getters
The `ParametersBag` provides several getter variations to help with casting the value of query parameters to specific types and filtering them. The following is the complete list of available getters:

- **`get(String key)`**: Retrieves the value of the parameter as a `String`.
- **`getAlpha(String key)`**: Returns only alphabetic characters from the parameter value.
- **`getAlnum(String key)`**: Returns only alphanumeric characters from the parameter value.
- **`getDigits(String key)`**: Returns only the numeric digits from the parameter value.
- **`getInt(String key)`**: Parses and returns the parameter value as an `Integer`.
- **`getFloat(String key)`**: Parses and returns the parameter value as a `Float`.
- **`getDouble(String key)`**: Parses and returns the parameter value as a `Double`.
- **`getBoolean(String key)`**: Parses and returns the parameter value as a `Boolean`.

These methods help you ensure the values are formatted and cast correctly, avoiding the need for manual parsing or validation.
:::

## Handling multiple values for a query parameter

Sometimes a query parameter might have multiple values for the same key, like in the following example:

```
/products?category=electronics,appliances&sort=price
```

The `ParametersBag` provides a method to handle this by retrieving values as a list:

```java
@Route(value = "products")
public class ProductView extends Composite<Div> implements DidEnterObserver {

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    ParametersBag queryParameters = event.getLocation().getQueryParameters();

    List<String> categories = queryParameters.getList("category").orElse(List.of("all"));
    String sort = queryParameters.get("sort").orElse("default");

    console().log("Categories: " + categories);
    console().log("Sort: " + sort);
  }
}
```

In this example:
- `getList("category")` retrieves all values associated with the `category` key, returning them as a list.

:::tip Multiple Values Delimiter
By default, the `getList()` method uses a comma (`,`) as the delimiter. You can customize the delimiter by passing a different character or a regular expression as second parameter to the `getList(String key, String regex)` method.
:::

## Use cases for query parameters

- **Filtering content**: Query parameters are often used to apply filters, like categories or search keywords.
- **Sorting data**: You can pass sorting preferences via query parameters, such as sorting by price, rating, or date.
- **Handling optional parameters**: When you need to pass data that isn't part of the required route structure, query parameters offer flexibility.
- **Passing multiple values**: Query parameters allow you to send multiple values for a single key, which is useful when users select multiple options, like product categories or filters.