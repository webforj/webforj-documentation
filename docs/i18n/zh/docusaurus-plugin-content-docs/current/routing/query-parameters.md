---
sidebar_position: 6
title: Query Parameters
_i18n_hash: 5a8313b16d83bfbef6e8d43589430f90
---
查询参数允许您通过 URL 传递附加数据，使用格式 `?key1=value1&key2=value2`。而路由参数用于在 URL 路径中传递必需的数据，查询参数则提供了一种灵活的机制来传递可选或附加数据。当过滤内容、排序或处理同一键的多个值时，它们特别有用。

## 查询参数概述 {#query-parameters-overview}

webforJ 中的查询参数遵循典型的 URL 约定：由 `=` 分隔的键值对，并用 `&` 连接。它们在 `?` 之后附加到 URL，并提供了一种灵活的方式来传递可选数据，例如过滤或排序偏好。

例如：

```
/products?category=electronics&sort=price
```

## 检索查询参数 {#retrieving-query-parameters}

查询参数通过 `ParametersBag` 对象访问。要检索查询参数，请使用 `Location` 对象的 `getQueryParameters()` 方法。

以下是在视图中从 URL 检索查询参数的方式：

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

在此示例中：
- `onDidEnter` 方法从 `DidEnterEvent` 提供的 `Location` 对象中检索查询参数。
- `ParametersBag` 允许您使用 `get()` 检索特定的查询参数，该方法返回一个 `Optional<String>`。如果参数不存在，您可以使用 `orElse()` 指定默认值。

:::tip `ParametersBag` 访问器
`ParametersBag` 提供了几种访问器变体，帮助将查询参数的值转换为特定类型并进行过滤。以下是可用访问器的完整列表：

- **`get(String key)`**：将参数的值作为 `String` 提取。
- **`getAlpha(String key)`**：仅返回参数值中的字母字符。
- **`getAlnum(String key)`**：仅返回参数值中的字母数字字符。
- **`getDigits(String key)`**：仅返回参数值中的数字。
- **`getInt(String key)`**：解析并返回参数值作为 `Integer`。
- **`getFloat(String key)`**：解析并返回参数值作为 `Float`。
- **`getDouble(String key)`**：解析并返回参数值作为 `Double`。
- **`getBoolean(String key)`**：解析并返回参数值作为 `Boolean`。

这些方法可以帮助您确保值格式正确并正确转换，避免手动解析或验证的需要。
:::

## 处理查询参数的多个值 {#handling-multiple-values-for-a-query-parameter}

有时，查询参数可能有多个相同键的值，如以下示例所示：

```
/products?category=electronics,appliances&sort=price
```

`ParametersBag` 提供了一种方法来处理此情况，通过将值作为列表检索：

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

在此示例中：
- `getList("category")` 检索与 `category` 键相关联的所有值，并将其作为列表返回。

:::tip 多个值分隔符
默认情况下，`getList()` 方法使用逗号 (`,`) 作为分隔符。您可以通过将不同的字符或正则表达式作为第二个参数传递给 `getList(String key, String regex)` 方法来自定义分隔符。
:::

## 查询参数的用例 {#use-cases-for-query-parameters}

- **过滤内容**：查询参数通常用于应用过滤器，如类别或搜索关键词。
- **排序数据**：您可以通过查询参数传递排序偏好，例如按价格、评分或日期排序。
- **处理可选参数**：当您需要传递不属于必需路由结构的数据时，查询参数提供了灵活性。
- **传递多个值**：查询参数允许您为单个键发送多个值，这在用户选择多个选项时非常有用，例如产品类别或过滤器。
