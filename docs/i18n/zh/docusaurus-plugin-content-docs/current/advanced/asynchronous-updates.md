---
sidebar_class_name: experimental-content
sidebar_position: 55
title: 异步更新
description: >-
  Run background work off the UI thread and push updates back to webforJ
  components safely with Environment.runLater and PendingResult.
_i18n_hash: 8dc68aced15dfd90481cd9ea98e4b2f2
---
<DocChip chip='since' label='25.02' />
<DocChip chip='experimental' />
<JavadocLink type="foundation" location="com/webforj/Environment" anchor="runLater(com.webforj.Environment,java.lang.Runnable)" top='true'/>

`Environment.runLater()` API 提供了一种机制，可在 webforJ 应用中从后台线程安全地更新 UI。此实验性功能支持异步操作，同时确保 UI 修改的线程安全。

<ExperimentalWarning />

<AISkillTip skill="webforj-handling-timers-and-async" />

## 了解线程模型 {#understanding-the-thread-model}

webforJ 强制采用严格的线程模型，所有 UI 操作都必须在 `Environment` 线程上执行。此限制的原因包括：

1. **webforJ API 的限制**：底层 webforJ API 会绑定到创建会话的线程
2. **组件的线程亲和性**：UI 组件维护的状态并非线程安全
3. **事件分发**：所有 UI 事件都在单个线程上按顺序处理

这种单线程模型可以避免竞态条件，并确保所有 UI 组件的状态一致，但在集成异步的长时间运行计算任务时会带来挑战。

每个用户会话都有自己的 `Environment` 和对应的 `Environment` 线程。后台线程不属于任何会话，因此必须指定要更新的 `Environment`。

## `RunLater` API {#runlater-api}

`Environment.runLater()` API 提供了两种用于安排 UI 更新的方法。两种方法的第一个参数都是目标 `Environment`：

```java title="Environment.java"
// Schedule a task with no return value
public static PendingResult<Void> runLater(Environment target, Runnable task)

// Schedule a task that returns a value
public static <T> PendingResult<T> runLater(Environment target, Supplier<T> supplier)
```

两种方法都会返回一个 <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>，用于跟踪任务完成状态，并提供访问结果或所发生异常的方式。

:::warning[不带目标参数的重载]
`runLater(Runnable)` 和 `runLater(Supplier)` 这两个重载会根据创建当前调用线程的线程来推测目标。如果线程池由多个用户共享，那么每个任务都会在创建该池线程的用户所对应的 `Environment` 中运行。这些重载自 26.10 起已弃用，并计划在下一个主要版本中移除。请始终传入目标参数。
:::

## 传入目标 {#passing-the-target}

`Environment.getCurrent()` 返回当前线程的 `Environment`。在 `Environment` 线程上（构造函数、事件处理程序和生命周期方法都在该线程上运行），它会返回会话的 `Environment`。在后台线程上，它会返回 `null`。

应在目标可用时，也就是后台工作开始之前读取目标，并将其传递给后台工作：

```java
@Route
public class DataView extends Composite<Div> {
  private final ExecutorService executor = Executors.newCachedThreadPool();

  public DataView() {
    // The constructor runs on the Environment thread
    Environment env = Environment.getCurrent();

    executor.submit(() -> {
      String data = fetchRemoteData();

      Environment.runLater(env, () -> {
        dataLabel.setText(data);
        loadingSpinner.setVisible(false);
      });
    });
  }
}
```

由于目标作为参数传入，因此可以从任何线程调用此方法：视图创建的线程、Spring `@Async` 线程、公共 `ForkJoinPool`、计时器线程，或 HTTP 客户端的回调线程。

### 在错误的线程上读取目标 {#reading-the-target-on-the-wrong-thread}

需要避免的错误是在后台工作中调用 `Environment.getCurrent()`。此时它会返回 `null`，导致 `runLater()` 抛出 `NullPointerException`：

```java
executor.submit(() -> {
  String data = fetchRemoteData();

  // Wrong: getCurrent() returns null on a background thread
  Environment.runLater(Environment.getCurrent(), () -> dataLabel.setText(data));
});
```

静态初始化器、`java.util.Timer` 线程和外部库回调也有同样的问题。它们都没有 `Environment`，因此目标必须来自字段、参数，或在 `Environment` 线程上赋值后捕获的局部变量。

## 执行行为 {#execution-behavior}

`runLater()` 的执行行为取决于调用它的线程：

### 从 UI 线程调用 {#from-the-ui-thread}

如果从目标自身的 `Environment` 线程调用，任务会**同步并立即**执行：

```java
button.onClick(e -> {
  Environment env = Environment.getCurrent();
  System.out.println("Before: " + Thread.currentThread().getName());

  PendingResult<String> result = Environment.runLater(env, () -> {
    System.out.println("Inside: " + Thread.currentThread().getName());
    return "completed";
  });

  System.out.println("After: " + result.isDone());  // true
});
```

借助这种同步行为，事件处理程序中的 UI 更新会立即应用，不会产生不必要的排队开销。

### 从后台线程调用 {#from-background-threads}

如果从任何其他线程调用，任务会**排队等待异步执行**：

```java
@Override
public void onDidCreate() {
  Environment env = Environment.getCurrent();

  CompletableFuture.runAsync(() -> {
    // This runs on a ForkJoinPool thread
    System.out.println("Background: " + Thread.currentThread().getName());

    PendingResult<Void> result = Environment.runLater(env, () -> {
      // This runs on the Environment thread
      System.out.println("UI Update: " + Thread.currentThread().getName());
      statusLabel.setText("Processing complete");
    });

    // result.isDone() would be false here
    // The task is queued and will execute asynchronously
  });
}
```

webforJ 会严格按照 **FIFO 顺序**处理从后台线程提交的任务，即使这些任务是由多个线程并发提交的，也会保留其操作顺序。借助这一顺序保证，UI 更新会严格按照提交顺序应用。因此，如果线程 A 提交任务 1，随后线程 B 提交任务 2，那么任务 1 总会先于任务 2 在 UI 线程上执行。按 FIFO 顺序处理任务可以避免 UI 状态不一致。

## 取消任务 {#task-cancellation}

`Environment.runLater()` 返回的 <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> 支持取消，因此可以阻止排队中的任务执行。取消待处理任务有助于避免内存泄漏，并防止不再需要的长时间运行操作继续更新 UI。

### 基本取消 {#basic-cancellation}

```java
PendingResult<Void> result = Environment.runLater(env, () -> {
  updateUI();
});

// Cancel if not yet executed
if (!result.isDone()) {
  result.cancel();
}
```

### 管理多个更新 {#managing-multiple-updates}

执行长时间运行的操作并频繁更新 UI 时，应跟踪所有待处理结果：

```java
public class LongRunningTask {
  private final List<PendingResult<?>> pendingUpdates = new ArrayList<>();
  private volatile boolean isCancelled = false;

  public void startTask() {
    Environment env = Environment.getCurrent();

    CompletableFuture.runAsync(() -> {
      for (int i = 0; i <= 100; i++) {
        if (isCancelled) return;

        final int progress = i;
        PendingResult<Void> update = Environment.runLater(env, () -> {
          progressBar.setValue(progress);
        });

        // Track for potential cancellation
        pendingUpdates.add(update);

        Thread.sleep(100);
      }
    });
  }

  public void cancelTask() {
    isCancelled = true;

    // Cancel all pending UI updates
    for (PendingResult<?> pending : pendingUpdates) {
      if (!pending.isDone()) {
        pending.cancel();
      }
    }
    pendingUpdates.clear();
  }
}
```

### 组件生命周期管理 {#component-lifecycle-management}

组件销毁时（例如导航期间），应取消所有待处理更新，以避免内存泄漏：

```java
@Route
public class CleanupView extends Composite<Div> {
  private final List<PendingResult<?>> pendingUpdates = new ArrayList<>();

  @Override
  protected void onDestroy() {
    super.onDestroy();

    // Cancel all pending updates to prevent memory leaks
    for (PendingResult<?> pending : pendingUpdates) {
      if (!pending.isDone()) {
        pending.cancel();
      }
    }
    pendingUpdates.clear();
  }
}
```

## 设计注意事项 {#design-considerations}

1. **目标要求**：在 `Environment` 线程上通过 `Environment.getCurrent()` 读取目标 `Environment`，并将其传递给后台工作。在后台线程上读取会返回 `null`。

2. **防止内存泄漏**：始终在组件生命周期方法中跟踪并取消 `PendingResult` 对象。排队中的 lambda 会捕获 UI 组件的引用；如果不取消，就会阻止垃圾回收。

3. **FIFO 执行**：所有任务都严格按照 FIFO 顺序执行，不考虑任务的重要性。此处没有优先级系统。

4. **取消的限制**：取消只能阻止尚在排队中的任务执行。已经开始执行的任务会正常完成。

## 完整案例研究：`LongTaskView` {#complete-case-study-longtaskview}

下面是一个完整且可用于生产环境的实现，展示了异步 UI 更新的所有最佳实践：

<!-- vale off -->

<ExpandableCode title="LongTaskView.java" language="java" startLine={91} endLine={161}>

```java
@Route("/")
public class LongTaskView extends Composite<FlexLayout> {
  // Use a single thread executor to prevent resource exhaustion
  // For production, consider using a shared application-wide thread pool
  private final ExecutorService executor = Executors.newSingleThreadExecutor(r -> {
    Thread t = new Thread(r, "LongTaskView-Worker");
    t.setDaemon(true);
    return t;
  });

  // Track the current task and pending UI updates
  private CompletableFuture<Void> currentTask = null;
  private final List<PendingResult<?>> pendingUIUpdates = new ArrayList<>();
  private volatile boolean isCancelled = false;

  // UI components
  private final FlexLayout self = getBoundComponent();
  private H2 titleLabel = new H2("Background UI Updates Demo");
  private Paragraph descriptionPara = new Paragraph(
      "This demo shows how Environment.runLater() enables safe UI updates from background threads. " +
          "Click 'Start Long Task' to run a 10-second background computation that updates the UI progress. " +
          "The 'Test UI' button proves the UI remains responsive during the background operation.");
  private TextField statusField = new TextField("Status");
  private ProgressBar progressBar = new ProgressBar();
  private TextField resultField = new TextField("Result");
  private Button startButton = new Button("Start Long Task");
  private Button cancelButton = new Button("Cancel Task");
  private Button testButton = new Button("Test UI - Click Me!");
  private Paragraph footerPara = new Paragraph(
      "Note: The task can be cancelled at any time, demonstrating proper cleanup of both the " +
          "background thread and queued UI updates.");
  private Toast globalToast = new Toast("", 3000, Theme.GRAY);
  private AtomicInteger clickCount = new AtomicInteger(0);

  public LongTaskView() {
    self.setDirection(FlexDirection.COLUMN);
    self.setMaxWidth(400);
    self.setStyle("margin", "1em auto");

    // Configure fields
    statusField.setReadOnly(true);
    statusField.setValue("Ready to start");
    statusField.setLabel("Status");

    // Configure progress bar
    progressBar.setMin(0);
    progressBar.setMax(100);
    progressBar.setValue(0);
    progressBar.setAnimated(true);
    progressBar.setStriped(true);
    progressBar.setText("Progress: {{x}}%");
    progressBar.setTheme(Theme.PRIMARY);

    resultField.setReadOnly(true);
    resultField.setValue("");
    resultField.setLabel("Result");

    // Configure buttons
    startButton.setTheme(ButtonTheme.PRIMARY);
    startButton.onClick(e -> startLongTask());

    cancelButton.setTheme(ButtonTheme.DANGER);
    cancelButton.setEnabled(false);
    cancelButton.onClick(e -> cancelTask());

    testButton.onClick(e -> {
      int count = clickCount.incrementAndGet();
      showToast("Click #" + count + " - UI is responsive!", Theme.GRAY);
    });

    // Add components
    self.add(titleLabel, descriptionPara, statusField, progressBar, resultField,
        startButton, cancelButton, testButton, footerPara);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();

    // Cancel any running task and pending UI updates
    cancelTask();

    // Clear task reference
    currentTask = null;

    // Shutdown the instance executor gracefully
    executor.shutdown();
  }

  private void startLongTask() {
    startButton.setEnabled(false);
    cancelButton.setEnabled(true);
    statusField.setValue("Starting background task...");
    progressBar.setValue(0);
    resultField.setValue("");

    // Reset cancelled flag and clear previous pending updates
    isCancelled = false;
    pendingUIUpdates.clear();

    // Read the target Environment on the UI thread, before the background task starts
    Environment env = Environment.getCurrent();

    // Start background task with explicit executor
    // Note: cancel(true) will interrupt the thread, causing Thread.sleep() to throw
    // InterruptedException
    currentTask = CompletableFuture.runAsync(() -> {
      double result = 0;

      // Simulate long task with 100 steps
      for (int i = 0; i <= 100; i++) {
        // Check if cancelled
        if (isCancelled) {
          PendingResult<Void> cancelUpdate = Environment.runLater(env, () -> {
            statusField.setValue("Task cancelled!");
            progressBar.setValue(0);
            resultField.setValue("");
            startButton.setEnabled(true);
            cancelButton.setEnabled(false);
            showToast("Task was cancelled", Theme.GRAY);
          });
          pendingUIUpdates.add(cancelUpdate);
          return;
        }

        try {
          Thread.sleep(100); // 10 seconds total
        } catch (InterruptedException e) {
          // Thread was interrupted - exit immediately
          Thread.currentThread().interrupt(); // Restore interrupted status
          return;
        }

        // Do some calculation (deterministic for demo)
        // Produces values between 0 and 1
        result += Math.sin(i) * 0.5 + 0.5;

        // Update progress from background thread
        final int progress = i;
        PendingResult<Void> updateResult = Environment.runLater(env, () -> {
          progressBar.setValue(progress);
          statusField.setValue("Processing... " + progress + "%");
        });
        pendingUIUpdates.add(updateResult);
      }

      // Final update with result (this code is only reached if the task completed without
      // cancellation)
      if (!isCancelled) {
        final double finalResult = result;
        PendingResult<Void> finalUpdate = Environment.runLater(env, () -> {
          statusField.setValue("Task completed!");
          resultField.setValue("Result: " + String.format("%.2f", finalResult));
          startButton.setEnabled(true);
          cancelButton.setEnabled(false);
          showToast("Background task finished!", Theme.SUCCESS);
        });
        pendingUIUpdates.add(finalUpdate);
      }
    }, executor);
  }

  private void cancelTask() {
    if (currentTask != null && !currentTask.isDone()) {
      // Set the cancelled flag
      isCancelled = true;

      // Cancel the main task (interrupts the thread)
      currentTask.cancel(true);

      // Cancel all pending UI updates
      for (PendingResult<?> pending : pendingUIUpdates) {
        if (!pending.isDone()) {
          pending.cancel();
        }
      }

      if (!statusField.isDestroyed() && !cancelButton.isDestroyed()) {
        statusField.setValue("Cancelling task...");
        cancelButton.setEnabled(false);

        showToast("Cancellation requested", Theme.GRAY);
      }
    }
  }

  private void showToast(String message, Theme theme) {
    if (!globalToast.isDestroyed()) {
      globalToast.setText(message);
      globalToast.setTheme(theme);
      globalToast.open();
    }
  }
}
```

</ExpandableCode>

<div class="videos-container" style={{maxWidth: '400px', margin: '0 auto'}}>
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/webforj-long-tasks.mp4" type="video/mp4"/>
  </video>
</div>

<!-- vale on -->

### 案例分析 {#case-study-analysis}

此实现展示了几个关键模式：

#### 1. 线程池管理 {#1-thread-pool-management}
```java
private final ExecutorService executor = Executors.newSingleThreadExecutor(r -> {
  Thread t = new Thread(r, "LongTaskView-Worker");
  t.setDaemon(true);
  return t;
});
```
- 使用**单线程执行器**，避免资源耗尽
- 创建**守护线程**，使其不会阻止 JVM 关闭

#### 2. 指定目标 {#2-naming-the-target}
```java
Environment env = Environment.getCurrent();
```
`startLongTask()` 在 `Environment` 线程上运行，因此它会在该线程上读取目标，并由后台任务将目标传递给每次 `Environment.runLater()` 调用。相同代码也可以直接用于共享的应用级线程池。

#### 3. 跟踪待处理的更新 {#3-tracking-pending-updates}
```java
private final List<PendingResult<?>> pendingUIUpdates = new ArrayList<>();
```
跟踪每次 `Environment.runLater()` 调用，以便：
- 用户点击取消时取消任务
- 在 `onDestroy()` 中防止内存泄漏
- 在组件生命周期内执行适当的清理

#### 4. 协作式取消 {#4-cooperative-cancellation}
```java
private volatile boolean isCancelled = false;
```
后台线程会在每次迭代时检查此标志，从而实现：
- 立即响应取消请求
- 正常退出循环
- 防止继续更新 UI

#### 5. 生命周期管理 {#5-lifecycle-management}
```java
@Override
protected void onDestroy() {
  super.onDestroy();
  cancelTask();  // Reuses cancellation logic
  currentTask = null;
  executor.shutdown();
}
```
这是防止内存泄漏的关键措施，具体包括：
- 取消所有待处理的 UI 更新
- 中断正在运行的线程
- 关闭执行器

#### 6. UI 响应性测试 {#6-ui-responsiveness-testing}
```java
testButton.onClick(e -> {
  int count = clickCount.incrementAndGet();
  showToast("Click #" + count + " - UI is responsive!", Theme.GRAY);
});
```
此示例展示了在后台操作期间，UI 线程仍能保持响应。
