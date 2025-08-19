---
title: Dependency Injection  
sidebar_position: 15
---

Dependency injection (DI) is a process where objects define their dependencies through constructor arguments rather than creating or looking up dependencies themselves. The Spring container injects these dependencies when creating the object, resulting in cleaner code and better decoupling between components.

When using Spring Boot with webforJ, the framework detects the Spring context and adapts its object creation to use Spring's dependency injection. This means your route classes can declare dependencies on services, repositories, and other Spring beans through constructor parameters.

:::tip[Learn more about dependency injection]
For a comprehensive understanding of dependency injection patterns and Spring's IoC container, see [Spring's official dependency injection documentation](https://docs.spring.io/spring-framework/reference/core/beans/dependencies/factory-collaborators.html).
:::

## How webforJ creates routes with Spring {#how-webforj-creates-routes-with-spring}

webforJ handles route creation differently when Spring is present. The framework's object creation mechanism detects Spring Boot on the classpath and delegates to Spring's `AutowireCapableBeanFactory` for creating instances with dependency injection.

For classes discovered through `@Routify` scanning, webforJ always creates fresh instances and never reuses existing Spring beans. Each user navigation receives a clean route instance with no shared state. The creation process:

1. User navigates to a route
2. webforJ requests a new instance (ignoring any existing Spring bean definitions)
3. Spring's factory creates the instance and injects constructor dependencies
4. The route initializes with all dependencies satisfied

This behavior is intentional and differs from typical Spring beans. Even if you register a route as a Spring bean with `@Component`, webforJ ignores that bean definition and creates a fresh instance for each navigation.

## Using Spring beans in routes {#using-spring-beans-in-routes}

Your route classes don't require Spring annotations. The `@Route` annotation alone enables both route discovery and dependency injection:

```java
@Route("/dashboard")
public class DashboardView extends Composite<Div> {
  
  private final MetricsService metricsService;
  private final UserRepository userRepository;
  
  public DashboardView(MetricsService metricsService, UserRepository userRepository) {
    this.metricsService = metricsService;
    this.userRepository = userRepository;
    
    // Use injected services to build UI
    Div metricsPanel = new Div();
    metricsPanel.setText(metricsService.getCurrentMetrics());
    
    getBoundComponent().add(metricsPanel);
  }
}
```

The constructor can receive:
- Services annotated with `@Service`, `@Repository`, or `@Component`
- Spring Data repositories (`JpaRepository`, `CrudRepository`)
- Configuration values via `@Value` annotations
- Spring infrastructure beans (`ApplicationContext`, `Environment`)
- Any bean registered in the Spring context

## Working example {#working-example}

This example demonstrates a complete dependency injection scenario where a Spring service is injected into a webforJ route. The service manages business logic while the route handles UI presentation.

First, define a standard Spring service using the `@Service` annotation. This service will be managed by Spring's container and available for injection:

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
  private Button btn = new Button("Generate Random Number");

  public HelloWorldView(RandomNumberService service) {
    self.setDirection(FlexDirection.COLUMN);
    self.setMaxWidth(300);
    self.setStyle("margin", "1em auto");

    btn.setPrefixComponent(TablerIcon.create("dice"))
        .setTheme(ButtonTheme.GRAY)
        .addClickListener(e -> {
          Toast.show("The new random number is " + service.getRandomNumber(), Theme.SUCCESS);
        });

    self.add(btn);
  }
}
```

In this example, the `RandomNumberService` is a standard Spring service bean. The `HelloWorldView` route declares it as a constructor parameter, and Spring automatically provides the service instance when webforJ creates the route.

Notice that the route class only uses the `@Route` annotation - no Spring stereotypes like `@Component` or `@Controller` are needed. When a user navigates to the root path `/`, webforJ:

1. Creates a new instance of `HelloWorldView` 
2. Asks Spring to resolve the `RandomNumberService` dependency
3. Passes the service to the constructor
4. The route uses the injected service to handle button clicks

This pattern works with any Spring bean - services, repositories, configuration classes, or custom components. The dependency injection happens transparently, allowing you to focus on building your UI while Spring manages the wiring.