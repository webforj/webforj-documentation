---
title: Routing
sidebar_position: 15
description: >-
  Inject Spring services and repositories into webforJ @Route classes through
  constructor injection while keeping a fresh instance per navigation.
_i18n_hash: 4bef970301ebc7072162c3dc95b6e544
---
在 webforJ 中使用 Spring 的路由与纯 webforJ 应用程序中的工作方式完全相同。您仍然使用 `@Route` 注解来定义路由，使用相同的导航模式和相同的路由生命周期。唯一的区别是，当 Spring 存在时，您的路由还可以通过构造函数注入接收 Spring Bean。

当您导航到一个路由时，webforJ 创建路由实例 - 但当 Spring 在类路径上时，它使用 Spring 的容器来解析依赖关系。这意味着您的路由可以利用 Spring 生态系统的全部功能（服务、存储库、自定义 Bean），同时保持熟悉的 webforJ 路由行为。

:::tip[了解更多关于依赖注入]
要全面理解依赖注入模式和 Spring 的 IoC 容器，请参阅 [Spring 的官方依赖注入文档](https://docs.spring.io/spring-framework/reference/core/beans/dependencies/factory-collaborators.html)。
:::

## webforJ 如何与 Spring 创建路由 {#how-webforj-creates-routes-with-spring}

当 Spring 存在时，webforJ 在处理路由创建时采用不同的方式。框架的对象创建机制检测到类路径上的 Spring Boot，并委托给 Spring 的 `AutowireCapableBeanFactory` 来创建具有依赖注入的实例。

对于通过 `@Routify` 扫描发现的类，webforJ 总是创建新的实例而从不重用现有的 Spring Bean。每次用户导航都会接收一个干净的路由实例，且没有共享状态。创建过程如下：

1. 用户导航到一个路由
2. webforJ 请求一个新的实例（忽略任何现有的 Spring Bean 定义）
3. Spring 的工厂创建该实例并注入构造函数依赖关系
4. 路由初始化时，所有依赖项都得到满足

这种行为是故意的，与典型的 Spring Bean 不同。即使您通过 `@Component` 将路由注册为 Spring Bean，webforJ 也会忽略该 Bean 定义，并为每次导航创建一个新的实例。

## 在路由中使用 Spring Bean {#using-spring-beans-in-routes}

您的路由类不需要 Spring 注解。仅仅使用 `@Route` 注解就可以启用路由发现和依赖注入：

```java
@Route("/dashboard")
public class DashboardView extends Composite<Div> {

  private final Div self = getBoundComponent();
  private final MetricsService metricsService;
  private final UserRepository userRepository;

  public DashboardView(MetricsService metricsService, UserRepository userRepository) {
    this.metricsService = metricsService;
    this.userRepository = userRepository;

    // 使用注入的服务来构建 UI
    Div metricsPanel = new Div();
    metricsPanel.setText(metricsService.getCurrentMetrics());

    self.add(metricsPanel);
  }
}
```

构造函数可以接收：
- 带有 `@Service`、`@Repository` 或 `@Component` 注解的服务
- Spring 数据存储库（`JpaRepository`、`CrudRepository`）
- 通过 `@Value` 注解的配置值
- Spring 基础设施 Bean（`ApplicationContext`、`Environment`）
- 在 Spring 上下文中注册的任何 Bean

## 示例 {#working-example}

此示例演示了一个完整的依赖注入场景，其中一个 Spring 服务被注入到 webforJ 路由中。该服务管理业务逻辑，而路由处理 UI 展示。

首先，使用 `@Service` 注解定义一个标准的 Spring 服务。该服务将由 Spring 的容器管理，并可供注入：

```java title="RandomNumberService.java"
@Service
public class RandomNumberService {

  public Integer getRandomNumber() {
    return (int) (Math.random() * 100);
  }
}
```

```java title="HelloWorldView.java"
@Route("/")
public class HelloWorldView extends Composite<FlexLayout> {

  private final FlexLayout self = getBoundComponent();
  private Button btn = new Button("生成随机数");

  public HelloWorldView(RandomNumberService service) {
    self.setDirection(FlexDirection.COLUMN);
    self.setMaxWidth(300);
    self.setStyle("margin", "1em auto");

    btn.setPrefixComponent(TablerIcon.create("dice"))
        .setTheme(ButtonTheme.GRAY)
        .addClickListener(e -> {
          Toast.show("新的随机数是 " + service.getRandomNumber(), Theme.SUCCESS);
        });

    self.add(btn);
  }
}
```

在这个例子中，`RandomNumberService` 是一个标准的 Spring 服务 Bean。`HelloWorldView` 路由将其声明为构造函数参数，Spring 在 webforJ 创建路由时自动提供服务实例。

请注意，路由类只使用 `@Route` 注解 - 不需要像 `@Component` 或 `@Controller` 这样的 Spring 注解。当用户导航到根路径 `/` 时，webforJ：

1. 创建一个新的 `HelloWorldView` 实例
2. 请求 Spring 解析 `RandomNumberService` 依赖
3. 将服务传递给构造函数
4. 路由使用注入的服务来处理按钮点击

这种模式适用于任何 Spring Bean - 服务、存储库、配置类或自定义组件。依赖注入是透明进行的，让您专注于构建用户界面，而 Spring 管理连接。
