---
sidebar_position: 10
title: Lifecycle Listeners
sidebar_class_name: new-content
_i18n_hash: 8134c6a2d602b0d69733de9770b44afe
---
<!-- vale off -->
# 生命周期监听器 <DocChip chip='since' label='25.02' />
<!-- vale on -->

`AppLifecycleListener` 接口允许外部代码观察和响应应用程序的生命周期事件。通过实现此接口，您可以在应用程序启动和关闭的特定时刻执行代码，而无需修改 `App` 类本身。

生命周期监听器会通过服务提供者配置文件在运行时自动发现和加载。每个应用实例接收自己的一组监听器实例，从而维护在同一环境中运行的不同应用之间的隔离。

## 何时使用生命周期监听器 {#when-to-use-lifecycle-listeners}

当您需要：

- 在应用程序运行之前初始化资源或服务
- 在应用程序终止时清理资源
- 在不修改 `App` 类的情况下添加横切关注点
- 构建插件架构

## `AppLifecycleListener` 接口 {#the-applifecyclelistener-interface}

```java title="AppLifecycleListener.java"
public interface AppLifecycleListener {
    default void onWillCreate(Environment env) {}     // 自 25.03 起
    default void onDidCreate(App app) {}              // 自 25.03 起
    default void onWillRun(App app) {}
    default void onDidRun(App app) {}
    default void onWillTerminate(App app) {}
    default void onDidTerminate(App app) {}
}
```

:::info 应用隔离
每个应用实例接收自己的一组监听器实例：

- 监听器在不同应用之间是隔离的
- 监听器中的静态字段不会在应用之间共享
- 监听器实例在应用启动时创建，并在应用终止时销毁

如果您需要在应用之间共享数据，请使用外部存储机制，如数据库或共享服务。
:::

### 生命周期事件 {#lifecycle-events}

| 事件                | 何时调用                                               | 常见用途                                           |
| ------------------- | ----------------------------------------------------- | -------------------------------------------------- |
| `onWillCreate`&nbsp;<DocChip chip='since' label='25.03' /> | 在环境初始化后，应用创建之前                      | 修改配置，合并外部配置源                           |
| `onDidCreate`&nbsp;<DocChip chip='since' label='25.03' />  | 在应用实例化后，初始化之前                        | 早期应用级设置，注册服务                           |
| `onWillRun`       | 在 `app.run()` 执行之前                              | 初始化资源，配置服务                               |
| `onDidRun`        | 在 `app.run()` 成功完成后                            | 启动后台任务，记录成功启动                         |
| `onWillTerminate` | 在应用终止之前                                      | 保存状态，准备关闭                                 |
| `onDidTerminate`  | 在应用终止之后                                      | 清理资源，最后记录                                 |

## 创建生命周期监听器 {#creating-a-lifecycle-listener}

### 基本实现 {#basic-implementation}

```java title="StartupListener.java"
import com.webforj.App;
import com.webforj.AppLifecycleListener;
import com.webforj.Environment;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class StartupListener implements AppLifecycleListener {

    @Override
    public void onWillCreate(Environment env) {
        // 在应用创建之前修改配置
        Config additionalConfig = ConfigFactory.parseString(
            "myapp.feature.enabled = true"
        );
        env.setConfig(additionalConfig);
    }

    @Override
    public void onDidCreate(App app) {
        System.out.println("应用创建: " + app.getId());
    }

    @Override
    public void onWillRun(App app) {
        System.out.println("应用启动: " + app.getId());
    }

    @Override
    public void onDidRun(App app) {
        System.out.println("应用已启动: " + app.getId());
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
更新服务描述符时容易忘记。使用谷歌的 [AutoService](https://github.com/google/auto/blob/main/service/README.md) 自动生成服务文件：

```java title="StartupListener.java"
import com.google.auto.service.AutoService;

@AutoService(AppLifecycleListener.class)
public class StartupListener implements AppLifecycleListener {
    // 实现
}
```
:::

## 控制执行顺序 {#controlling-execution-order}

注册多个监听器时，您可以使用 `@AppListenerPriority` 注解控制它们的执行顺序。这在监听器之间有依赖关系或需要在其他初始化之前进行某些初始化时尤为重要。

优先级值按升序工作 - **较低的数字优先执行**。默认优先级为10，因此没有显式优先级注解的监听器将在优先级值较低的监听器之后执行。

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
@AppListenerPriority(10) // 默认优先级 - 一般日志记录
public class LoggingListener implements AppLifecycleListener {
    @Override
    public void onWillRun(App app) {
        initializeLogging();
    }
}
```

### 带 App 钩子的执行流程 {#execution-flow-with-app-hooks}

除了控制多个监听器之间的顺序之外，了解监听器与 `App` 类的生命周期钩子之间的交互也很重要。每个生命周期事件，框架遵循特定的执行顺序，确定您的监听器相对于应用程序内置钩子的运行时间。

下图展示了此执行流程，显示了在与相应 `App` 钩子相关的情况下，`AppLifecycleListener` 方法被调用的确切时机： 

<div align="center">

![AppLifecycleListener 监听器 VS `App` 钩子](/img/lifecycle-listeners.svg)

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
