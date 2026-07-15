---
sidebar_position: 5
title: Route Patterns
description: >-
  Define dynamic URL segments, optional parameters, wildcards, and regex
  constraints to match webforJ routes precisely.
_i18n_hash: a6c1267e034c1562652cc01d0f336640
---
**路由模式**用于定义如何将 URL 映射到特定视图，包括动态和可选段、正则表达式和通配符。路由模式使框架能够匹配 URL、提取参数并动态生成 URL。它们在基于浏览器位置构建应用程序的导航和组件渲染方面发挥了关键作用。

## 路由模式语法 {#route-pattern-syntax}

webforJ 中的路由模式非常灵活，支持以下功能：

- **命名参数：** 由 `:paramName` 表示，除非标记为可选，否则为必填项。
- **可选参数：** 由 `:paramName?` 表示，可以从 URL 中省略。
- **通配符段：** 由 `*` 表示，捕获 URL 的所有剩余段。
- **正则表达式约束：** 约束只能添加到命名参数（例如，`:id<[0-9]+>`）。

### 路由模式定义示例 {#example-of-route-pattern-definitions}

```java
@Route("customer/:id<[0-9]+>/named/:name/*")
public class CustomerView extends Composite<Div> implements DidEnterObserver {

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    int id = parameters.getInt("id").orElse(0);
    String name = parameters.getAlpha("name").orElse("未知");
    String extra = parameters.getAlpha("*").orElse("");

    String result =
        "客户 ID: " + id + "-" +
        "姓名: " + name + "-" +
        "*: " + extra;

    console().log(result);
  }
}
```

在这个示例中：

- `:id<[0-9]+>` 捕获一个数字客户 ID。
- `:name` 捕获一个名称。
- `*` 捕获位于 `named/:name` 之后的任何其他路径段。

## 命名参数 {#named-parameters}

命名参数通过在模式中将冒号 `:` 前缀添加到参数名称来定义。除非标记为可选，否则为必填项。命名参数还可以具有正则表达式 [约束](#regular-expression-constraints) 以验证值。

### 示例： {#example}

```java
@Route("product/:id")
public class ProductView extends Composite<Div> {
  // 组件逻辑在此
}
```

此模式匹配类似 `/product/123` 的 URL，其中 `id` 为 `123`。

## 可选参数 {#optional-parameters}

可选参数通过在参数名称后添加 `?` 进行指示。这些段不是必填的，可以从 URL 中省略。

### 示例： {#example-1}

```java
@Route("order/:id?<[0-9]+>")
public class OrderView extends Composite<Div> implements DidEnterObserver {

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    parameters.getInt("id").ifPresentOrElse(
      id -> console().log("订单 ID: " + id),
      () -> console().log("未提供订单 ID")
    );
  }
}
```

此模式匹配 `/order/123`，以包含一个数字值，同时也匹配 `/order`，允许在输入 `/order` 时省略数字值。

## 正则表达式约束 {#regular-expression-constraints}

您可以通过在角括号 `<>` 内添加它们来对参数应用正则表达式约束。这允许您为参数指定更严格的匹配规则。

### 示例： {#example-2}

```java
@Route("product/:code<[A-Z]{3}-[0-9]{4}>")
public class ProductView extends Composite<FlexLayout> implements DidEnterObserver {

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    parameters.get("code").ifPresentOrElse(
      code -> console().log("产品编码: " + code),
      () -> console().error("未找到产品编码"));
  }
}
```

此模式仅匹配格式为 `ABC-1234` 的产品编码。例如，`/product/XYZ-5678` 将匹配，但 `/product/abc-5678` 则不匹配。

## 通配符段 {#wildcard-segments}

通配符可用于捕获特定路由段后面的整个路径，但它们只能作为模式中的最后一个段出现，解析 URL 中的所有后续值。为了提高可读性，通配符段可以命名。然而，与命名参数不同，通配符段不能具有任何约束。

### 示例： {#example-3}

```java
@Route("files/:pathname*")
public class FileManagerView extends Composite<Div> implements DidEnterObserver {

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    parameters.get("pathname").ifPresentOrElse(
      pathname -> console().log("FileManagerView: " + pathname),
      () -> console().log("FileManagerView: 没有 pathname 参数")
    );
  }
}
```

此模式匹配以 `/files` 开头的任何 URL，并将其余路径捕获为通配符。

## 路由优先级 {#route-priority}

当多个路由匹配给定的 URL 时，路由的优先级属性决定了首先选择哪个路由。这在两个或多个路由的路径模式重叠时尤其有用，您需要一种方法来控制哪个路由具有优先权。优先级属性在 `@Route` 和 `@RouteAlias` 注解中均可用。

### 优先级系统的工作原理 {#how-the-priority-system-works}

优先级属性允许路由器确定在多个路由可以匹配给定的 URL 时评估路由的顺序。路由根据其优先级值进行排序，优先级较高（数值较低）的将首先被匹配。这确保了更具体的路由优先于更一般的路由。

如果两个路由共享相同的优先级，路由器通过选择首先注册的路由来解决冲突。这个机制确保即使在多个路由重叠其 URL 模式时，也能选择正确的路由。

:::info 默认优先级
默认情况下，所有路由的优先级为 `10`。
:::

### 示例：冲突路由 {#example-conflicting-routes}

考虑一个场景，其中两个路由匹配相似的 URL 模式：

```java
@Route(value = "products/:category", priority = 9)
public class ProductCategoryView extends Composite<Div> implements DidEnterObserver {
  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    String category = parameters.get("category").orElse("未知");
    console().log("查看类别: " + category);
  }
}

@Route(value = "products/:category/:productId?<[0-9]+>")
public class ProductView extends Composite<Div> implements DidEnterObserver {
  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    String productId = parameters.get("productId").orElse("未知");
    console().log("查看产品: " + productId);
  }
}
```

优先级系统如何帮助解决冲突：

- **`ProductCategoryView`** 匹配 `/products/electronics` 这种 URL。
- **`ProductView`** 匹配更具体的 URL，例如 `/products/electronics/123`，其中 `123` 是产品 ID。

在这种情况下，两个路由都可以匹配 URL `/products/electronics`。但是，由于 `ProductCategoryView` 的优先级较高（优先级 = 9），因此在 URL 中没有 `productId` 时将首先匹配它。对于 URL `/products/electronics/123`，由于存在 `productId` 参数，将匹配 `ProductView`。
