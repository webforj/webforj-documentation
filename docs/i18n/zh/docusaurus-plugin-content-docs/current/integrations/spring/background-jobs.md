---
title: 后台任务
sidebar_position: 25
description: >-
  Run Spring @Async services from webforJ views and marshal progress and results
  back to the UI thread with Environment.runLater.
_i18n_hash: 2760d6db6a7b7045e7612ca359d04a28
---
当用户点击按钮生成报告或处理数据时，他们希望界面保持响应。进度条应持续更新动画，按钮应对悬停做出响应，应用也不应冻结。Spring 的 `@Async` 注解通过将耗时操作移至后台线程，让这一切成为可能。

webforJ 强制要求 UI 组件具备线程安全性——所有更新都必须在 UI 线程上进行。这就带来了一个挑战：后台任务如何更新进度条或显示结果？答案是 `Environment.runLater()`，它可以安全地将 UI 更新从 Spring 的后台线程转交给启动该任务的用户所对应的 UI 线程。

## 启用异步执行 {#enabling-asynchronous-execution}

Spring 的异步方法执行需要显式配置。否则，使用 `@Async` 注解的方法会同步执行，无法发挥异步的作用。

在 Spring Boot 应用类上添加 `@EnableAsync`：

```java {2}
@SpringBootApplication
@EnableAsync
@Routify(packages = { "com.example.views" })
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
```

`@EnableAsync` 注解会启用 Spring 的基础设施，用于检测 `@Async` 方法并在后台线程上执行这些方法。

:::tip[Spring 异步指南]
如需快速了解 Spring 的 `@Async` 注解及其基本用法，请参阅[创建异步方法](https://spring.io/guides/gs/async-method)。
:::

## 创建异步服务 {#creating-async-services}

使用 `@Service` 注解的服务可以将方法标记为 `@Async`，使其在后台线程上运行。这些方法通常返回 `CompletableFuture`，以便妥善处理任务完成和取消：

```java
@Service
public class BackgroundService {

  @Async
  public CompletableFuture<String> performLongRunningTask(Consumer<Integer> progressCallback) {
    try {
      for (int i = 0; i <= 10; i++) {
          // Report progress
          int progress = i * 10;
          if (progressCallback != null) {
              progressCallback.accept(progress);
          }

          // Simulate work
          Thread.sleep(500);
      }

      return CompletableFuture.completedFuture(
        "Task completed successfully from background service!");
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      return CompletableFuture.failedFuture(e);
    }
  }
}
```

此服务接收一个进度回调（`Consumer<Integer>`），并在后台线程中调用它。借助回调模式，服务可以报告进度，而无需了解 UI 组件。

该方法模拟了一个包含 10 次进度更新、耗时 5 秒的任务。在生产环境中，这些操作会是数据库查询或文件处理等实际工作。异常处理会恢复线程的中断状态，以便在调用 `cancel(true)` 时正确取消任务。

## 在视图中使用后台任务 {#using-background-tasks-in-views}

视图通过构造函数注入接收后台服务：

```java
@Route("/")
public class HelloWorldView extends Composite<FlexLayout> {
  private Button asyncBtn = new Button("Start Background Task");
  private ProgressBar progressBar = new ProgressBar();
  private CompletableFuture<String> currentTask;

  public HelloWorldView(BackgroundService backgroundService) {
    // Service is injected by Spring
    asyncBtn.addClickListener(e -> {
      // The click handler runs on this user's UI thread
      Environment env = Environment.getCurrent();

      currentTask = backgroundService.performLongRunningTask(progress -> {
        Environment.runLater(env, () -> {
          progressBar.setValue(progress);
        });
      });
    });
  }
}
```

Spring 会像注入其他 Spring Bean 一样，将 `BackgroundService` 注入视图的构造函数。然后，视图使用此服务启动后台任务。关键在于：服务的回调在 Spring 的线程池中执行，而该线程池由应用的所有用户共享，因此池线程无法知道应该更新哪位用户的 UI。点击处理程序会在仍处于 UI 线程时，通过 `Environment.getCurrent()` 读取该用户的 `Environment`，并在回调中的每次 UI 更新时将其传递给 `Environment.runLater()`。

处理任务完成时也需要同样谨慎地管理线程：

```java
currentTask.whenComplete((result, error) -> {
  Environment.runLater(env, () -> {
    asyncBtn.setEnabled(true);
    progressBar.setVisible(false);
    if (error != null) {
      Toast.show("Task failed: " + error.getMessage(), Theme.DANGER);
    } else {
      Toast.show(result, Theme.SUCCESS);
    }
  });
});
```

`whenComplete` 回调同样会在后台线程上执行。每个 UI 操作——启用按钮、隐藏进度条、显示 toast 消息——都必须使用相同的 `env` 包装在 `Environment.runLater()` 中。若不这样包装，webforJ 会抛出异常，因为后台线程无法访问 UI 组件。

:::warning[线程安全]
来自后台线程的每次 UI 更新都必须包装在 `Environment.runLater()` 中，并且必须在任务启动前于 UI 线程上读取 `Environment`。此规则没有例外。直接从 `@Async` 方法访问组件始终会失败，而在池线程上调用 `Environment.getCurrent()` 会返回 `null`。
:::

:::tip[深入了解线程安全]
如需详细了解 webforJ 的线程模型、执行行为，以及哪些操作需要使用 `Environment.runLater()`，请参阅[异步更新](../../advanced/asynchronous-updates)。
:::

## 任务取消与清理 {#task-cancellation-and-cleanup}

妥善管理生命周期可以避免内存泄漏和不必要的 UI 更新。视图会保存 `CompletableFuture` 的引用：

```java
private CompletableFuture<String> currentTask;
```

视图销毁时，会取消所有正在运行的任务：

```java
@Override
protected void onDestroy() {
  // Cancel the task if view is destroyed
  if (currentTask != null && !currentTask.isDone()) {
    currentTask.cancel(true);
  }
}
```

`cancel(true)` 参数至关重要。它会中断后台线程，导致 `Thread.sleep()` 等阻塞操作抛出 `InterruptedException`，从而立即终止任务。若不设置中断标志（`cancel(false)`），任务会继续运行，直到显式检查是否已取消。

此清理操作可以避免以下问题：
- 视图销毁后，后台线程仍在运行并消耗资源
- UI 更新尝试修改已销毁的组件
- 回调持有 UI 组件引用而导致内存泄漏
