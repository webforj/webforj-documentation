---
sidebar_position: 10
title: Lifecycle Listeners
sidebar_class_name: new-content
_i18n_hash: 475cc2842226c605bbe7f2ee931955dd
---
<!-- vale off -->
# 生命周期监听器 <DocChip chip='since' label='25.02' />
<!-- vale on -->

`AppLifecycleListener` 接口允许外部代码观察并响应应用程序生命周期事件。通过实现此接口，您可以在应用程序启动和关闭的特定时刻执行代码，而无需修改 `App` 类本身。

生命周期监听器通过服务提供者配置文件在运行时自动发现和加载。每个应用程序实例接收其自己的监听器实例集，在同一环境中运行的不同应用程序之间保持隔离。

## 何时使用生命周期监听器 {#when-to-use-lifecycle-listeners}

在以下情况下使用生命周期监听器：
- 在应用程序运行之前初始化资源或服务
- 当应用程序终止时清理资源
- 添加跨切面关注点而不修改 `App` 类
- 构建插件架构

## `AppLifecycleListener` 接口 {#the-applifecyclelistener-interface}

```java title="AppLifecycleListener.java"
public interface AppLifecycleListener {
    default void onWillRun(App app) {}
    default void onDidRun(App app) {}
    default void onWillTerminate(App app) {}
    default void onDidTerminate(App app) {}
}
```

:::info 应用程序隔离
每个应用程序实例接收其自己的监听器实例集：
- 监听器在不同应用程序之间是孤立的
- 监听器中的静态字段不会在应用程序之间共享
- 监听器实例在应用程序启动时创建，在应用程序终止时销毁

如果需要在应用程序之间共享数据，请使用数据库或共享服务等外部存储机制。
:::

### 生命周期事件 {#lifecycle-events}

| 事件 | 何时调用 | 常见用途 |
|-------|-------------|-------------|
| `onWillRun` | 在 `app.run()` 执行之前 | 初始化资源，配置服务 |
| `onDidRun` | 在 `app.run()` 成功完成后 | 启动后台任务，记录成功启动 |
| `onWillTerminate` | 在应用程序终止前 | 保存状态，准备关闭 |
| `onDidTerminate` | 在应用程序终止后 | 清理资源，最终记录 |

## 创建生命周期监听器 {#creating-a-lifecycle-listener}

### 基本实现 {#basic-implementation}

```java title="StartupListener.java"
import com.webforj.App;
import com.webforj.AppLifecycleListener;

public class StartupListener implements AppLifecycleListener {
    
    @Override
    public void onWillRun(App app) {
        System.out.println("应用程序启动: " + app.getId());
    }
    
    @Override
    public void onDidRun(App app) {
        System.out.println("应用程序启动成功: " + app.getId());
    }
}
```

### 注册监听器 {#registering-the-listener}

创建一个服务提供者配置文件：

**文件**: `src/main/resources/META-INF/services/com.webforj.AppLifecycleListener`

```
com.example.listeners.StartupListener
```

:::tip 使用 AutoService
容易忘记更新服务描述符。可以使用谷歌的 [AutoService](https://github.com/google/auto/blob/main/service/README.md) 自动生成服务文件：

```java title="StartupListener.java"
import com.google.auto.service.AutoService;

@AutoService(AppLifecycleListener.class)
public class StartupListener implements AppLifecycleListener {
    // 实现
}
```
:::

## 控制执行顺序 {#controlling-execution-order}

当注册多个监听器时，可以使用 `@AppListenerPriority` 注释控制它们的执行顺序。这在监听器之间存在依赖关系或某些初始化必须在其他初始化之前发生时特别重要。

优先级值按升序工作 - **较低的数字优先执行**。默认优先级为 10，因此没有显式优先级注释的监听器将在优先级值较低的监听器之后执行。

```java title="SecurityListener.java"
@AutoService(AppLifecycleListener.class)
@AppListenerPriority(1)  // 首先执行 - 关键安全设置
public class SecurityListener implements AppLifecycleListener {
    @Override
    public void onWillRun(App app) {
        initializeSecurity();
    }
}

@AutoService(AppLifecycleListener.class)
@AppListenerPriority(10) // 默认优先级 - 一般记录
public class LoggingListener implements AppLifecycleListener {
    @Override
    public void onWillRun(App app) {
        initializeLogging();
    }
}
```

### 使用应用程序钩子的执行流 {#execution-flow-with-app-hooks}

在控制多个监听器之间的顺序之外，了解监听器如何与 `App` 类的生命周期钩子交互也很重要。对于每个生命周期事件，框架遵循特定的执行顺序，决定您的监听器相对于应用程序内置钩子运行的时机。

下面的图表展示了这种执行流，显示了 `AppLifecycleListener` 方法相对于相应 `App` 钩子的调用精确时机：

<div align="center">

![AppLifecycleListener 监听器与 `App` 钩子对比](/img/lifecycle-listeners.svg)

</div>

## 错误处理 {#error-handling}

监听器抛出的异常会被记录，但不会阻止其他监听器执行或应用程序运行。始终在监听器中处理异常：

```java title="错误处理示例"
@Override
public void onWillRun(App app) {
    try {
        riskyInitialization();
    } catch (Exception e) {
        logger.error("初始化失败", e);
    }
}
```
