---
sidebar_position: 5
title: Route Patterns
_i18n_hash: d18952e5072af2c542c1459e3f65d787
---
**路由模式**用于定义URL如何映射到特定视图，包括动态和可选段、正则表达式和通配符。路由模式使框架能够匹配URL、提取参数并动态生成URL。它们在根据浏览器位置构建应用程序的导航和组件渲染中发挥着关键作用。

## 路由模式语法 {#route-pattern-syntax}

webforJ中的路由模式具有高度灵活性，支持以下功能：

- **命名参数：** 由 `:paramName` 表示，除非标记为可选，否则是必需的。
- **可选参数：** 由 `:paramName?` 表示，可以从URL中省略。
- **通配符段：** 由 `*` 表示，捕获URL的所有剩余段。
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

- `:id<[0-9]+>` 捕获一个数字客户ID。
- `:name` 捕获一个姓名。
- `*` 捕获超过 `named/:name` 的任何附加路径段。

## 命名参数 {#named-parameters}

命名参数通过在模式中的参数名称前加冒号 `:` 来定义。除非标记为可选，否则它们是必需的。命名参数还可以具有正则表达式 [约束](#regular-expression-constraints) 以验证值。

### 示例： {#example}

```java
@Route("product/:id")
public class ProductView extends Composite<Div> {
  // 组件逻辑在这里
}
```

该模式匹配类似 `/product/123` 的URL，其中 `id` 为 `123`。

## 可选参数 {#optional-parameters}

可选参数通过在参数名称后添加一个 `?` 来指示。这些段不是必需的，可以从URL中省略。

### 示例： {#example-1}

```java
@Route("order/:id?<[0-9]+>")
public class OrderView extends Composite<Div> implements DidEnterObserver {

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    parameters.getInt("id").ifPresentOrElse(
      id -> console().log("订单 ID: " + id),
      () -> console().log("没有提供订单 ID")
    );
  }
}
```

该模式匹配 `/order/123` 来包含一个数字值，并且 `/order` 允许省略数字值。

## 正则表达式约束 {#regular-expression-constraints}

您可以通过在尖括号 `<>` 内部添加正则表达式约束来应用它们。这使您能够为参数指定更严格的匹配规则。

### 示例： {#example-2}

```java
@Route("product/:code<[A-Z]{3}-[0-9]{4}>")
public class ProductView extends Composite<FlexLayout> implements DidEnterObserver {

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    parameters.get("code").ifPresentOrElse(
      code -> console().log("产品代码: " + code),
      () -> console().error("找不到产品代码"));
  }
}
```

该模式只匹配格式为 `ABC-1234` 的产品代码。例如，`/product/XYZ-5678` 将匹配，但 `/product/abc-5678` 将不匹配。

## 通配符段 {#wildcard-segments}

通配符可以用于捕获特定路由段后面的整个路径，但它们只能出现在模式的最后一个段，解析URL中的所有后续值。为了更好的可读性，通配符段可以命名。然而，与命名参数不同，通配符段不能有任何约束。

### 示例： {#example-3}

```java
@Route("files/:pathname*")
public class FileManagerView extends Composite<Div> implements DidEnterObserver {

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    parameters.get("pathname").ifPresentOrElse(
      pathname -> console().log("FileManagerView: " + pathname),
      () -> console().log("FileManagerView: 没有路径名称参数")
    );
  }
}
```

该模式匹配任何以 `/files` 开头的URL，并将其余路径捕获为通配符。

## 路由优先级 {#route-priority}

当多个路由匹配给定URL时，路由的优先级属性决定哪个路由首先被选择。这在两个或多个路由在其路径模式上重叠时尤其有用，您需要一种方式来控制哪个路由优先。优先级属性在 `@Route` 和 `@RouteAlias` 注解中均可用。

### 优先级系统如何工作 {#how-the-priority-system-works}

优先级属性允许路由器确定在多个路由可能匹配给定URL时评估路由的顺序。路由依据其优先级值进行排序，优先级更高（较低的数字值）的路由将首先匹配。这确保了更具体的路由优先于更一般的路由。

如果两个路由具有相同的优先级，则路由器通过选择第一个注册的路由来解决冲突。这一机制确保即使在多个路由重叠的情况下，正确的路由也被选中。

:::info 默认优先级  
默认情况下，所有路由的优先级均为 `10`。  
:::

### 示例：冲突路由 {#example-conflicting-routes}

考虑以下两条匹配类似URL模式的路由的场景：

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

以下是优先级系统帮助解决冲突的方式：

- **`ProductCategoryView`** 匹配URL如 `/products/electronics`。
- **`ProductView`** 匹配更具体的URL如 `/products/electronics/123`，其中 `123` 是产品ID。

在这种情况下，这两条路由都可以匹配 `/products/electronics` 的URL。然而，因为 `ProductCategoryView` 具有更高的优先级（优先级 = 9），所以在URL中没有 `productId` 时，将首先匹配它。对于像 `/products/electronics/123` 的URL，由于存在 `productId` 参数，将匹配 `ProductView`。
