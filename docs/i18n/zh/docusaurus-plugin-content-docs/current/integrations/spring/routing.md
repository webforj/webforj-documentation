---
title: Routing
sidebar_position: 15
_i18n_hash: a5b11fb9cf05e74bd347faae48f167dd
---
在webforJ与Spring的路由工作方式与普通webforJ应用程序完全相同。您仍然可以使用`@Route`注解定义路由、采用相同的导航模式和路由生命周期。唯一的区别是，当Spring存在时，您的路由还可以通过构造函数注入接收Spring Beans。

当您导航到一个路由时，webforJ会创建路由实例 - 但在classpath中有Spring时，它会使用Spring的容器来解析依赖关系。这意味着您的路由可以利用Spring生态系统的全部功能（服务、存储库、自定义Beans），同时保持熟悉的webforJ路由行为。

:::tip[了解更多关于依赖注入的信息]
有关依赖注入模式和Spring的IoC容器的全面理解，请参见[Spring的官方依赖注入文档](https://docs.spring.io/spring-framework/reference/core/beans/dependencies/factory-collaborators.html)。
:::

## webforJ如何使用Spring创建路由 {#how-webforj-creates-routes-with-spring}

在Spring存在时，webforJ处理路由创建的方式有所不同。框架的对象创建机制检测到classpath中有Spring Boot，并委托给Spring的`AutowireCapableBeanFactory`来创建具有依赖注入的实例。

对于通过`@Routify`扫描发现的类，webforJ总是创建新的实例，而不会重用已有的Spring Beans。每次用户导航都会接收到一个干净的路由实例，没有共享状态。创建过程如下：

1. 用户导航到一个路由
2. webforJ请求一个新实例（忽略任何现有的Spring Bean定义）
3. Spring的工厂创建实例并注入构造函数依赖
4. 路由初始化时所有依赖关系都得到满足

这种行为是故意的，区别于典型的Spring Beans。即使您使用`@Component`注册一个路由作为Spring Bean，webforJ也会忽略该Bean定义，为每次导航创建一个新的实例。

## 在路由中使用Spring Beans {#using-spring-beans-in-routes}

您的路由类不需要Spring注解。`@Route`注解本身就能启用路由发现和依赖注入：

```java
@Route("/dashboard")
public class DashboardView extends Composite<Div> {
  
  private final MetricsService metricsService;
  private final UserRepository userRepository;
  
  public DashboardView(MetricsService metricsService, UserRepository userRepository) {
    this.metricsService = metricsService;
    this.userRepository = userRepository;
    
    // 使用注入的服务构建UI
    Div metricsPanel = new Div();
    metricsPanel.setText(metricsService.getCurrentMetrics());
    
    getBoundComponent().add(metricsPanel);
  }
}
```

构造函数可以接收：
- 带有`@Service`、`@Repository`或`@Component`的服务
- Spring Data存储库（`JpaRepository`、`CrudRepository`）
- 通过`@Value`注解提供的配置值
- Spring基础设施Beans（`ApplicationContext`、`Environment`）
- 在Spring上下文中注册的任何Bean

## 工作示例 {#working-example}

此示例演示了在webforJ路由中注入Spring服务的完整依赖注入场景。服务管理业务逻辑，而路由处理UI展示。

首先，使用`@Service`注解定义标准的Spring服务。该服务将由Spring的容器管理，可用于注入：

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

在此示例中，`RandomNumberService`是一个标准的Spring服务Bean。`HelloWorldView`路由将其声明为构造函数参数，并且Spring在webforJ创建路由时自动提供服务实例。

请注意，路由类仅使用`@Route`注解 - 不需要`@Component`或`@Controller`等Spring特性。当用户导航到根路径`/`时，webforJ：

1. 创建一个新的`HelloWorldView`实例 
2. 请求Spring解析`RandomNumberService`依赖
3. 将服务传递给构造函数
4. 路由使用注入的服务处理按钮点击

此模式适用于任何Spring Bean - 服务、存储库、配置类或自定义组件。依赖注入是透明地发生的，使您能够专注于构建UI，而Spring则管理连接。
