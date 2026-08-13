---
title: Background Jobs
sidebar_position: 25
description: >-
  Run Spring @Async services from webforJ views and marshal progress and results
  back to the UI thread with Environment.runLater.
_i18n_hash: 1b265d2e723c0f58c97fd2c4375f15a1
---
当用户点击按钮以生成报告或处理数据时，他们希望界面保持响应。进度条应该动画，按钮在悬停时应有反应，应用程序不应冻结。Spring 的 `@Async` 注解使这一切成为可能，因为它将长时间运行的操作转移到后台线程中。

webforJ 强制执行 UI 组件的线程安全 - 所有更新必须在 UI 线程上进行。这就带来了一个挑战：后台任务如何更新进度条或显示结果？答案是 `Environment.runLater()`，它安全地将 UI 更新从 Spring 的后台线程转移到 webforJ 的 UI 线程。

## 启用异步执行 {#enabling-asynchronous-execution}

Spring 的异步方法执行需要明确的配置。如果没有，它将以同步方式执行带有 `@Async` 注解的方法，从而失去其目的。

在你的 Spring Boot 应用类中添加 `@EnableAsync`：

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

`@EnableAsync` 注解激活 Spring 检测 `@Async` 方法并在后台线程上执行它们的基础架构。

:::tip[Spring 异步指南]
有关 Spring 的 `@Async` 注解和基本用法模式的快速介绍，请参阅 [创建异步方法](https://spring.io/guides/gs/async-method)。
:::

## 创建异步服务 {#creating-async-services}

带有 `@Service` 注解的服务可以具有用 `@Async` 标记的方法，以在后台线程上运行。这些方法通常返回 `CompletableFuture` 以便于正确的完成处理和取消：

```java
@Service
public class BackgroundService {

  @Async
  public CompletableFuture<String> performLongRunningTask(Consumer<Integer> progressCallback) {
    try {
      for (int i = 0; i <= 10; i++) {
          // 报告进度
          int progress = i * 10;
          if (progressCallback != null) {
              progressCallback.accept(progress);
          }

          // 模拟工作
          Thread.sleep(500);
      }

      return CompletableFuture.completedFuture(
        "任务成功完成来自后台服务！");
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      return CompletableFuture.failedFuture(e);
    }
  }
}
```

该服务接受一个进度回调 (`Consumer<Integer>`)，该回调从后台线程中被调用。回调模式允许服务报告进度，而不需要知道 UI 组件。

该方法模拟了一个 5 秒的任务，包含 10 次进度更新。在生产中，这将是数据库查询或文件处理等实际工作。异常处理恢复中断状态，以支持在调用 `cancel(true)` 时正确地取消任务。

## 在视图中使用后台任务 {#using-background-tasks-in-views}

视图通过构造函数注入接收后台服务：

```java
@Route("/")
public class HelloWorldView extends Composite<FlexLayout> {
  private Button asyncBtn = new Button("启动后台任务");
  private ProgressBar progressBar = new ProgressBar();
  private CompletableFuture<String> currentTask;

  public HelloWorldView(BackgroundService backgroundService) {
    // 服务由 Spring 注入
    asyncBtn.addClickListener(e -> {
      currentTask = backgroundService.performLongRunningTask(progress -> {
        Environment.runLater(() -> {
          progressBar.setValue(progress);
        });
      });
    });
  }
}
```

Spring 将 `BackgroundService` 注入视图的构造函数，就像其他 Spring bean 一样。视图然后使用该服务来启动后台任务。关键概念：来自服务的回调在后台线程中执行，因此这些回调中的任何 UI 更新必须使用 `Environment.runLater()` 来转移执行到 UI 线程。

完成处理需要相同的线程管理：

```java
currentTask.whenComplete((result, error) -> {
  Environment.runLater(() -> {
    asyncBtn.setEnabled(true);
    progressBar.setVisible(false);
    if (error != null) {
      Toast.show("任务失败: " + error.getMessage(), Theme.DANGER);
    } else {
      Toast.show(result, Theme.SUCCESS);
    }
  });
});
```

`whenComplete` 回调也在后台线程中执行。每一个 UI 操作 - 启用按钮、隐藏进度条、显示吐司 - 必须封装在 `Environment.runLater()` 中。没有这个封装，webforJ 会抛出异常，因为后台线程不能访问 UI 组件。

:::warning[线程安全]
来自后台线程的每一个 UI 更新必须封装在 `Environment.runLater()` 中。此规则没有例外。直接从 `@Async` 方法访问组件总是失败。
:::

:::tip[了解更多关于线程安全]
有关 webforJ 的线程模型、执行行为以及哪些操作需要 `Environment.runLater()` 的详细信息，请参阅 [异步更新](../../advanced/asynchronous-updates)。
:::

## 任务取消和清理 {#task-cancellation-and-cleanup}

正确的生命周期管理可以防止内存泄漏和不必要的 UI 更新。视图存储 `CompletableFuture` 引用：

```java
private CompletableFuture<String> currentTask;
```

当视图被销毁时，它取消任何正在运行的任务：

```java
@Override
protected void onDestroy() {
  // 如果视图被销毁，则取消任务
  if (currentTask != null && !currentTask.isDone()) {
    currentTask.cancel(true);
  }
}
```

`cancel(true)` 参数非常关键。它会中断后台线程，导致阻塞操作如 `Thread.sleep()` 抛出 `InterruptedException`。这使得任务能够立即终止。如果没有中断标志 (`cancel(false)`)，任务将继续运行，直到明确检查取消请求。

这个清理过程避免了几个问题：
- 后台线程在视图消失后继续消耗资源
- UI 更新尝试修改已销毁的组件
- 回调持有对 UI 组件的引用导致内存泄漏
