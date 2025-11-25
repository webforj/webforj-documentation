---
title: Route Registry Provider
sidebar_position: 60
sidebar_class_name: new-content
_i18n_hash: bb5bae3f60aa681bc30e2f317ac2c2d6
---
# 路由注册提供者 <DocChip chip='since' label='25.11' />

`RouteRegistryProvider` 是一个服务提供者接口 (SPI)，允许集成框架提供自定义路由发现机制。这使得框架能够将其自己的类路径扫描和依赖注入系统与 webforJ 的路由基础设施集成。

## 概述 {#overview}

webforJ 通过扫描打了 `@Route` 注解的组件的包来发现路由。`RouteRegistryProvider` SPI 允许框架用其自己的发现机制覆盖此默认行为。

在以下情况下使用此 SPI：

- 与依赖注入框架集成（Spring, CDI, ...）
- 支持专用环境（OSGi, 自定义类加载器, GraalVM）
- 构建需要管理路由组件生命周期的框架适配器
- 重用现有的类路径扫描以优化启动时间

## 工作原理 {#how-it-works}

当调用 `RouteRegistry.ofPackage()` 时，webforJ 通过 Java 的 `ServiceLoader` 检查注册的提供者。如果找到了提供者，则路由发现委托给该提供者。否则，将使用默认的扫描机制。

## 构建你的提供者 {#building-your-provider}

要创建自定义路由发现提供者，请实现 SPI 接口并通过 Java 的 ServiceLoader 机制注册它。

### 实现 SPI {#implement-the-spi}

创建一个实现 `RouteRegistryProvider` 的类：

```java
public class CustomRouteRegistryProvider implements RouteRegistryProvider {

  @Override
  public void registerRoutes(String[] packages, RouteRegistry registry) {
    // 扫描包并注册 @Route 组件
  }
}
```

### 启用发现 {#enable-discovery}

将提供者的完全限定类名添加到 `META-INF/services/com.webforj.router.RouteRegistryProvider` 中：

```text title="src/main/resources/META-INF/services/com.webforj.router.RouteRegistryProvider"
com.example.framework.CustomRouteRegistryProvider
```
