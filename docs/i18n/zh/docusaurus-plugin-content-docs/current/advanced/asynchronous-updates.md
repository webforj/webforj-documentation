---
sidebar_class_name: experimental-content
sidebar_position: 55
title: 异步更新
description: >-
  Run background work off the UI thread and push updates back to webforJ
  components safely with Environment.runLater and PendingResult.
_i18n_hash: 7fedd93e44d699bba0a959504c7bb40c
---
<DocChip chip='since' label='25.02' />
<DocChip chip='experimental' />
<JavadocLink type="foundation" location="com/webforj/Environment" anchor="runLater(com.webforj.Environment,java.lang.Runnable)" top='true'/>

`Environment.runLater()` API 提供了一种机制，可在 webforJ 应用中安全地从后台线程更新 UI。此预览功能支持异步操作，同时确保 UI 修改的线程安全。

<ExperimentalWarning />

<AISkillTip skill="webforj-handling-timers-and-async" />

## 了解线程模型 {#understanding-the-thread-model}

webforJ 强制采用严格的线程模型，所有 UI 操作都必须在 `Environment` 线程上执行。此限制的原因如下：

1. **webforJ API 约束**：底层 webforJ API 会绑定到创建会话的线程
2. **组件线程亲和性**：UI 组件维护的状态不是线程安全的
3. **事件分发**：所有 UI 事件都在单个线程上按顺序处理

这种单线程模型可避免竞态条件，并保持所有 UI 组件状态一致，但在集成异步且耗时较长的计算任务时会带来挑战。

每个用户会话都有自己的 `Environment` 和对应的 `Environment` 线程。后台线程不属于任何会话，因此必须明确指定要更新哪个 `Environment`。

## `RunLater` API {#runlater-api}

`Environment.runLater()` API 提供了两种用于安排 UI 更新的方法。两种方法都将目标 `Environment` 作为第一个参数：

```java title="Environment.java"
// Schedule a task with no return value
public static PendingResult<Void> runLater(Environment target, Runnable task)

// Schedule a task that returns a value
public static <T> PendingResult<T> runLater(Environment target, Supplier<T> supplier)
```

两种方法都会返回一个 <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>，用于跟踪任务完成状态，并提供任务结果或发生的异常。

:::warning[不带目标参数的重载]
`runLater(Runnable)` 和 `runLater(Supplier)` 这两个重载会根据创建当前调用线程的线程来猜测目标。如果线程池由多个用户共享，那么每个任务都会在创建该池线程的用户所对应的 `Environment` 中运行。这些重载自 26.10 起已弃用，并计划在下一个主要版本中移除。请始终传入目标。
:::

## 传入目标 {#passing-the-target}

`Environment.getCurrent()` 会返回当前线程的 `Environment`。在 `Environment` 线程上（构造函数、事件处理程序和生命周期方法都在该线程上运行），它会返回会话的 `Environment`。在后台线程上，它会返回 `null`。

应在目标可用时，也就是后台工作开始之前读取目标，并将其传给后台工作：

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

由于目标是作为参数传入的，因此无论从哪个线程调用都能正常工作：包括视图创建的线程、Spring `@Async` 线程、通用 `ForkJoinPool`、计时器线程或 HTTP 客户端的回调线程。

### 在错误的线程上读取目标 {#reading-the-target-on-the-wrong-thread}

应避免的一个错误是在后台工作中调用 `Environment.getCurrent()`。此时它会返回 `null`，而 `runLater()` 会抛出 `NullPointerException`：

```java
executor.submit(() -> {
  String data = fetchRemoteData();

  // Wrong: getCurrent() returns null on a background thread
  Environment.runLater(Environment.getCurrent(), () -> dataLabel.setText(data));
});
```

静态初始化器、`java.util.Timer` 线程和外部库回调也是如此。它们都没有 `Environment`，因此目标必须来自字段、参数，或在 `Environment` 线程上赋值并捕获的局部变量。

## 执行行为 {#execution-behavior}

`runLater()` 的执行行为取决于调用它的线程：

### 从 UI 线程调用 {#from-the-ui-thread}

从目标自身的 `Environment` 线程调用时，任务会**同步且立即**执行：

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

这种同步行为意味着事件处理程序中的 UI 更新会立即应用，不会产生不必要的排队开销。

### 从后台线程调用 {#from-background-threads}

从其他任何线程调用时，任务都会**排队等待异步执行**：

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

webforJ 会严格按照 **FIFO 顺序**处理从后台线程提交的任务，即使这些任务由多个线程并发提交，也能保持操作顺序。有了这一顺序保证，UI 更新会严格按照提交顺序应用。因此，如果线程 A 提交任务 1，然后线程 B 提交任务 2，那么任务 1 始终会先于任务 2 在 UI 线程上执行。按照 FIFO 顺序处理任务可以避免 UI 状态不一致。

## 任务取消 {#task-cancellation}

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

执行耗时较长且需要频繁更新 UI 的操作时，应跟踪所有待处理结果：

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

销毁组件时（例如，在导航期间），请取消所有待处理更新，以避免内存泄漏：

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

1. **目标要求**：在 `Environment` 线程上使用 `Environment.getCurrent()` 读取目标 `Environment`，并将其传给后台工作。在后台线程上读取会返回 `null`。

2. **防止内存泄漏**：始终在组件生命周期方法中跟踪并取消 `PendingResult` 对象。排队中的 lambda 会捕获 UI 组件的引用；如果不取消，就会阻止垃圾回收。

3. **FIFO 执行**：所有任务都严格按照 FIFO 顺序执行，不受重要性影响。系统没有优先级机制。

4. **取消限制**：取消操作只能阻止排队中的任务执行。已经开始执行的任务会正常完成。

## 完整案例分析：`LongTaskView` {#complete-case-study-longtaskview}

下面是一个完整的、适用于生产环境的实现，展示了异步 UI 更新的所有最佳实践：

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

<VideoPlayer video="async/long-tasks" width={1080} height={1288} title="Long tasks" />

<!-- vale on -->

### 案例分析 {#case-study-analysis}

此实现展示了几种关键模式：

#### 1. 线程池管理 {#1-thread-pool-management}
```java
private final ExecutorService executor = Executors.newSingleThreadExecutor(r -> {
  Thread t = new Thread(r, "LongTaskView-Worker");
  t.setDaemon(true);
  return t;
});
```
- 使用**单线程执行器**，避免资源耗尽
- 创建**守护线程**，不会阻止 JVM 关闭

#### 2. 指定目标 {#2-naming-the-target}
```java
Environment env = Environment.getCurrent();
```
`startLongTask()` 在 `Environment` 线程上运行，因此会在该线程上读取目标，并由后台任务将目标传给每次 `Environment.runLater()` 调用。即使改用应用全局共享线程池，同样的代码也无需修改。

#### 3. 跟踪待处理更新 {#3-tracking-pending-updates}
```java
private final List<PendingResult<?>> pendingUIUpdates = new ArrayList<>();
```
跟踪每次 `Environment.runLater()` 调用，以便：
- 在用户点击取消时取消更新
- 在 `onDestroy()` 中防止内存泄漏
- 在组件生命周期内正确清理

#### 4. 协作式取消 {#4-cooperative-cancellation}
```java
private volatile boolean isCancelled = false;
```
后台线程会在每次迭代时检查此标志，从而实现：
- 立即响应取消操作
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
此步骤对于防止内存泄漏至关重要，具体包括：
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
这证明了在后台操作期间，UI 线程仍能保持响应。
