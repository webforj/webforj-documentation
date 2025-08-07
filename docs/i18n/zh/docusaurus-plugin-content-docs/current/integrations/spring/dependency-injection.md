---
title: Dependency Injection
sidebar_position: 15
_i18n_hash: 0942852e0373dae7b5539f612ede0709
---
依赖注入（DI）是一个过程，对象通过构造函数参数定义其依赖项，而不是自己创建或查找依赖项。Spring 容器在创建对象时注入这些依赖项，从而导致代码更简洁，组件之间的耦合度更低。

在使用 Spring Boot 和 webforJ 时，框架检测到 Spring 上下文，并调整其对象创建以使用 Spring 的依赖注入。这意味着您的路由类可以通过构造函数参数声明对服务、存储库和其他 Spring bean 的依赖。

:::tip[了解更多关于依赖注入的信息]
有关依赖注入模式和 Spring IoC 容器的全面理解，请参阅 [Spring 官方的依赖注入文档](https://docs.spring.io/spring-framework/reference/core/beans/dependencies/factory-collaborators.html)。
:::

## webforJ 如何使用 Spring 创建路由 {#how-webforj-creates-routes-with-spring}

当存在 Spring 时，webforJ 的路由创建方式有所不同。框架的对象创建机制检测到类路径上的 Spring Boot，并委托给 Spring 的 `AutowireCapableBeanFactory` 进行依赖注入的实例创建。

对于通过 `@Routify` 扫描发现的类，webforJ 始终创建新的实例，而不重用现有的 Spring bean。每次用户导航都会收到一个干净的路由实例，没有共享状态。创建过程如下：

1. 用户导航到一个路由
2. webforJ 请求一个新实例（忽略任何现有的 Spring bean 定义）
3. Spring 的工厂创建该实例并注入构造函数依赖项
4. 路由初始化时，所有依赖项都已满足

这种行为是有意为之，与典型的 Spring bean 不同。即使您将路由注册为带有 `@Component` 的 Spring bean，webforJ 也会忽略该 bean 定义，并为每次导航创建一个新的实例。

## 在路由中使用 Spring bean {#using-spring-beans-in-routes}

您的路由类不需要 Spring 注解。仅通过 `@Route` 注解就可以实现路由发现和依赖注入：

```java
@Route("/dashboard")
public class DashboardView extends Composite<Div> {
  
  private final MetricsService metricsService;
  private final UserRepository userRepository;
  
  public DashboardView(MetricsService metricsService, UserRepository userRepository) {
    this.metricsService = metricsService;
    this.userRepository = userRepository;
    
    // 使用注入的服务构建 UI
    Div metricsPanel = new Div();
    metricsPanel.setText(metricsService.getCurrentMetrics());
    
    getBoundComponent().add(metricsPanel);
  }
}
```

构造函数可以接收：
- 用 `@Service`、`@Repository` 或 `@Component` 注解的服务
- Spring Data 存储库（`JpaRepository`、`CrudRepository`）
- 通过 `@Value` 注解的配置值
- Spring 基础设施 bean（`ApplicationContext`、`Environment`）
- 在 Spring 上下文中注册的任何 bean

## 工作示例 {#working-example}

此示例演示了一个完整的依赖注入场景，在该场景中，一个 Spring 服务被注入到 webforJ 路由中。该服务管理业务逻辑，而路由处理 UI 展示。

首先，定义一个使用 `@Service` 注解的标准 Spring 服务。该服务将由 Spring 容器管理并可供注入：

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

  private FlexLayout self = getBoundComponent();
  private Button btn = new Button("生成随机数字");

  public HelloWorldView(RandomNumberService service) {
    self.setDirection(FlexDirection.COLUMN);
    self.setMaxWidth(300);
    self.setStyle("margin", "1em auto");

    btn.setPrefixComponent(TablerIcon.create("dice"))
        .setTheme(ButtonTheme.GRAY)
        .addClickListener(e -> {
          Toast.show("新的随机数字是 " + service.getRandomNumber(), Theme.SUCCESS);
        });

    self.add(btn);
  }
}
```

在此示例中，`RandomNumberService` 是一个标准的 Spring 服务 bean。`HelloWorldView` 路由将其声明为构造函数参数，并且当 webforJ 创建路由时，Spring 会自动提供服务实例。

请注意，路由类只使用了 `@Route` 注解——不需要像 `@Component` 或 `@Controller` 这样的 Spring 标识符。当用户导航到根路径 `/` 时，webforJ：

1. 创建一个新的 `HelloWorldView` 实例
2. 请求 Spring 解析 `RandomNumberService` 依赖
3. 将服务传递给构造函数
4. 路由使用注入的服务处理按钮点击

这种模式适用于任何 Spring bean——服务、存储库、配置类或自定义组件。依赖注入是透明的，让您可以专注于构建 UI，而 Spring 则处理连接。
