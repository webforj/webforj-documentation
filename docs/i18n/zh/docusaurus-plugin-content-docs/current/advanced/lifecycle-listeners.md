---
sidebar_position: 10
title: Lifecycle Listeners
sidebar_class_name: new-content
_i18n_hash: 95e3a7e349b0cf54679daf76d2bf209c
---
<!-- vale off -->
# 生命周期监听器 <DocChip chip='since' label='25.02' />
<!-- vale on -->

`AppLifecycleListener` 接口允许外部代码观察和响应应用程序的生命周期事件。通过实现该接口，您可以在应用程序启动和关闭的特定时刻执行代码，而无需修改 `App` 类本身。

生命周期监听器通过服务提供者配置文件在运行时自动发现和加载。每个应用程序实例接收其自己的一组监听器实例，保持在同一环境中运行的不同应用程序之间的隔离。

## 何时使用生命周期监听器 {#when-to-use-lifecycle-listeners}

当您需要时，请使用生命周期监听器：
- 在应用程序运行之前初始化资源或服务
- 在应用程序终止时清理资源  
- 添加跨切关注点而无需修改 `App` 类
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
每个应用程序实例接收其自己的监听器实例：
- 监听器在不同应用程序之间是隔离的
- 监听器中的静态字段不会在应用程序之间共享
- 监听器实例在应用程序启动时创建，并在其终止时销毁

如果您需要在应用程序之间共享数据，请使用外部存储机制，如数据库或共享服务。
:::

### 生命周期事件 {#lifecycle-events}

| 事件 | 何时调用 | 常见用途 |
|-------|-------------|-------------|
| `onWillRun` | 在 `app.run()` 执行之前 | 初始化资源，配置服务 |
| `onDidRun` | 在 `app.run()` 成功完成后 | 启动后台任务，记录成功启动 |
| `onWillTerminate` | 在应用程序终止之前 | 保存状态，为关闭做准备 |
| `onDidTerminate` | 在应用程序终止后 | 清理资源，最终记录 |

## 创建生命周期监听器 {#creating-a-lifecycle-listener}

### 基本实现 {#basic-implementation}

```java title="StartupListener.java"
import com.webforj.App;
import com.webforj.AppLifecycleListener;

public class StartupListener implements AppLifecycleListener {
    
    @Override
    public void onWillRun(App app) {
        System.out.println("应用程序启动中: " + app.getId());
    }
    
    @Override
    public void onDidRun(App app) {
        System.out.println("应用程序已启动: " + app.getId());
    }
}
```

### 注册监听器 {#registering-the-listener}

创建服务提供者配置文件：

**文件**: `src/main/resources/META-INF/services/com.webforj.AppLifecycleListener`

```
com.example.listeners.StartupListener
```

:::tip 使用 AutoService
很容易忘记更新服务描述符。使用谷歌的 [AutoService](https://github.com/google/auto/blob/main/service/README.md) 自动生成服务文件：

```java title="StartupListener.java"
import com.google.auto.service.AutoService;

@AutoService(AppLifecycleListener.class)
public class StartupListener implements AppLifecycleListener {
    // 实现
}
```
:::

## 控制执行顺序 {#controlling-execution-order}

当注册多个监听器时，您可以使用 `@AppListenerPriority` 注释控制它们的执行顺序。当监听器之间相互依赖或某些初始化必须在其他初始化之前发生时，这尤为重要。

优先级值按升序工作 - **较低的数字优先执行**。默认优先级为 10，因此未显式标注优先级的监听器将在优先级较低的监听器之后执行。

```java title="SecurityListener.java"
@AutoService(AppLifecycleListener.class)
@AppListenerPriority(1)  // 优先执行 - 关键安全设置
public class SecurityListener implements AppLifecycleListener {
    @Override
    public void onWillRun(App app) {
        initializeSecurity();
    }
}

@AutoService(AppLifecycleListener.class)
@AppListenerPriority(10) // 默认优先级 - 一般日志记录
public class LoggingListener implements AppLifecycleListener {
    @Override
    public void onWillRun(App app) {
        initializeLogging();
    }
}
```

### 与 App 钩子一起执行流 {#execution-flow-with-app-hooks}

除了控制多个监听器之间的顺序，重要的是要了解监听器如何与 `App` 类本身的生命周期钩子交互。对于每个生命周期事件，框架遵循特定的执行顺序，以确定您的监听器在应用程序的内置钩子相对于何时运行。

下图说明了此执行流，显示了 `AppLifecycleListener` 方法相对于相应 `App` 钩子的调用确切时机：

<div align="center">

![AppLifecycleListener 监听器与 `App` 钩子 ](/img/lifecycle-listeners.svg)

</div>

## 错误处理 {#error-handling}

监听器抛出的异常会被记录，但不会阻止其他监听器执行或应用程序运行。始终在您的监听器中处理异常：

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
