---
sidebar_position: 5
title: Route Patterns
_i18n_hash: 2f1668e34197bb2f4bb6c5b3ec6e87e5
---
**路由模式**用于定义URL如何映射到特定视图，包括动态和可选片段、正则表达式和通配符。路由模式使框架能够匹配URL、提取参数以及动态生成URL。它们在根据浏览器的位置结构化应用程序的导航和组件渲染方面起着关键作用。

## 路由模式语法 {#route-pattern-syntax}

webforJ中的路由模式非常灵活，支持以下特性：

- **命名参数：** 用 `:paramName` 表示，除非标记为可选，否则是必需的。
- **可选参数：** 用 `:paramName?` 表示，可以在URL中省略。
- **通配符片段：** 用 `*` 表示，用于捕获URL的所有剩余片段。
- **正则表达式约束：** 约束仅可添加到命名参数上（例如，`:id<[0-9]+>`）。

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
        "名称: " + name + "-" +
        "*: " + extra;

    console().log(result);
  }
}
```

在此示例中：

- `:id<[0-9]+>` 捕获一个数字客户ID。
- `:name` 捕获一个名称。
- `*` 捕获在 `named/:name` 之后的任何附加路径片段。

## 命名参数 {#named-parameters}

命名参数通过在模式中的参数名称前加冒号 `:` 来定义。除非标记为可选，否则它们是必需的。命名参数还可以具有正则表达式 [约束](#regular-expression-constraints) 来验证值。

### 示例: {#example}

```java
@Route("product/:id")
public class ProductView extends Composite<Div> {
  // 组件逻辑在此
}
```

该模式匹配如 `/product/123` 这样的URL，其中 `id` 是 `123`

## 可选参数 {#optional-parameters}

可选参数通过在参数名称后添加 `?` 来表示。这些片段不是必需的，可以在URL中省略。

### 示例: {#example-1}

```java
@Route("order/:id?<[0-9]+>")
public class OrderView extends Composite<Div> implements DidEnterObserver {

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    parameters.getInt("id").ifPresentOrElse(
      id -> console().log("订单 ID: " + id),
      () -> console().log("未提供订单ID")
    );
  }
}
```

该模式同时匹配 `/order/123`，以包含一个数字值，并且匹配 `/order`，允许在输入 `/order` 时省略数字值。

## 正则表达式约束 {#regular-expression-constraints}

您可以通过将约束添加在尖括号 `<>` 内部，施加正则表达式约束到参数。这允许您为参数指定更严格的匹配规则。

### 示例: {#example-2}

```java
@Route("product/:code<[A-Z]{3}-[0-9]{4}>")
public class ProductView extends Composite<FlexLayout> implements DidEnterObserver {

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    parameters.get("code").ifPresentOrElse(
      code -> console().log("产品代码: " + code),
      () -> console().error("未找到产品代码"));
  }
}
```

该模式仅匹配格式为 `ABC-1234` 的产品代码。例如，`/product/XYZ-5678` 将匹配，但 `/product/abc-5678` 将不匹配。

## 通配符片段 {#wildcard-segments}

通配符可用于捕获特定路由片段后的整个路径，但它们只能出现在模式的最后一个片段中，解析URL中的所有后续值。为了更好地可读性，通配符片段可以命名。然而，与命名参数不同，通配符片段不能具有任何约束。

### 示例: {#example-3}

```java
@Route("files/:pathname*")
public class FileManagerView extends Composite<Div> implements DidEnterObserver {

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    parameters.get("pathname").ifPresentOrElse(
      pathname -> console().log("FileManagerView: " + pathname),
      () -> console().log("FileManagerView: 没有pathname参数")
    );
  }
}
```

该模式匹配以 `/files` 开头的任何URL，并将其余路径捕获为通配符。

## 路由优先级 {#route-priority}

当多个路由匹配给定的URL时，路由的优先级属性决定选定哪个路由。这在两个或多个路由的路径模式重叠时尤其有用，您需要一种方法来控制哪个路由被优先选择。优先级属性在 `@Route` 和 `@RouteAlias` 注释中都可用。

### 优先级系统如何工作 {#how-the-priority-system-works}

优先级属性允许路由器在多个路由可能匹配给定URL时，确定评估路由的顺序。路由根据其优先级值进行排序，较高的优先级（较低的数值）将首先匹配。这确保了更特定的路由优先于更一般的路由。

如果两个路由共享相同的优先级，路由器通过选择首先注册的路由来解决冲突。该机制确保即使多个路由在其URL模式中重叠，正确的路由也会被选择。

:::info 默认优先级  
默认情况下，所有路由分配的优先级为 `10`。  
:::

### 示例：冲突路由 {#example-conflicting-routes}

考虑一个场景，其中两个路由匹配相似的URL模式：

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

以下是优先级系统如何帮助解决冲突：

- **`ProductCategoryView`** 匹配URLs，如 `/products/electronics`。
- **`ProductView`** 匹配如 `/products/electronics/123` 的更特定URL，其中 `123` 是产品ID。

在此情况下，两条路由都可以匹配URL `/products/electronics`。但是，由于 `ProductCategoryView` 具有更高的优先级（优先级 = 9），因此在没有 `productId` 的情况下，它将首先被匹配。对于像 `/products/electronics/123` 这样的URL，由于存在 `productId` 参数，`ProductView` 将会被匹配。
