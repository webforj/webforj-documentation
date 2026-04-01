---
sidebar_position: 55
title: Asynchronous Updates
_i18n_hash: ead192e1c1a415742cb0446e2d5c314c
---
<DocChip chip='since' label='25.02' />
<DocChip chip='experimental' />
<JavadocLink type="foundation" location="com/webforj/Environment" anchor="runLater(java.lang.Runnable)" top='true'/>

`Environment.runLater()` API 提供了一种安全地从 webforJ 应用程序的后台线程更新用户界面的机制。此实验性功能允许异步操作，同时保持用户界面修改的线程安全性。

:::warning 实验性 API
此 API 自 25.02 起被标记为实验性，可能会在未来版本中发生变化。API 的签名、行为和性能特征可能会有所修改。
:::

## 理解线程模型 {#understanding-the-thread-model}

webforJ 强制执行严格的线程模型，所有用户界面操作必须在 `Environment` 线程上进行。这一限制存在的原因是：

1. **webforJ API 限制**：底层的 webforJ API 绑定到创建会话的线程
2. **组件线程亲和性**：用户界面组件维护的状态不是线程安全的
3. **事件调度**：所有用户界面事件都是在单线程上按顺序处理的

这种单线程模型防止了竞争条件，并保持所有用户界面的状态一致性，但在与异步、长时间计算任务集成时会产生挑战。

## `RunLater` API {#runlater-api}

`Environment.runLater()` API 提供了两个方法来安排用户界面更新：

```java title="Environment.java"
// 安排一个没有返回值的任务
public static PendingResult<Void> runLater(Runnable task)

// 安排一个返回值的任务
public static <T> PendingResult<T> runLater(Supplier<T> supplier)
```

这两种方法返回一个 <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>，用于跟踪任务完成情况，并提供结果或发生的任何异常的访问。

## 线程上下文继承 {#thread-context-inheritance}

自动上下文继承是 `Environment.runLater()` 的一个关键特性。当在 `Environment` 中运行的线程创建子线程时，这些子线程会自动继承使用 `runLater()` 的能力。

### 继承如何工作 {#how-inheritance-works}

从 `Environment` 线程中创建的任何线程都会自动访问该 `Environment`。这种继承是自动发生的，因此您不需要传递任何上下文或配置任何内容。

```java
@Route
public class DataView extends Composite<Div> {
  private final ExecutorService executor = Executors.newCachedThreadPool();
  
  public DataView() {
    // 该线程具有 Environment 上下文
    
    // 子线程自动继承上下文
    executor.submit(() -> {
      String data = fetchRemoteData();
      
      // 可以使用 runLater，因为上下文被继承
      Environment.runLater(() -> {
        dataLabel.setText(data);
        loadingSpinner.setVisible(false);
      });
    });
  }
}
```

### 没有上下文的线程 {#threads-without-context}

在 `Environment` 上下文外创建的线程无法使用 `runLater()` 并会抛出 `IllegalStateException`：

```java
// 静态初始化器 - 没有 Environment 上下文
static {
  new Thread(() -> {
    Environment.runLater(() -> {});  // 抛出 IllegalStateException
  }).start();
}

// 系统定时器线程 - 没有 Environment 上下文  
Timer timer = new Timer();
timer.schedule(new TimerTask() {
  public void run() {
    Environment.runLater(() -> {});  // 抛出 IllegalStateException
  }
}, 1000);

// 外部库线程 - 没有 Environment 上下文
httpClient.sendAsync(request, responseHandler)
  .thenAccept(response -> {
    Environment.runLater(() -> {});  // 抛出 IllegalStateException
  });
```

## 执行行为 {#execution-behavior}

`runLater()` 的执行行为取决于哪个线程调用它：

### 从 UI 线程 {#from-the-ui-thread}

当从 `Environment` 线程本身调用时，任务 **同步并立即** 执行：

```java
button.onClick(e -> {
  System.out.println("Before: " + Thread.currentThread().getName());
  
  PendingResult<String> result = Environment.runLater(() -> {
    System.out.println("Inside: " + Thread.currentThread().getName());
    return "completed";
  });
  
  System.out.println("After: " + result.isDone());  // true
});
```

借助这种同步行为，事件处理程序中的用户界面更新会立即应用，不会产生任何不必要的队列开销。

### 从后台线程 {#from-background-threads}

当从后台线程调用时，任务会 **排队进行异步执行**：

```java
@Override
public void onDidCreate() {
  CompletableFuture.runAsync(() -> {
    // 这在 ForkJoinPool 线程上运行
    System.out.println("Background: " + Thread.currentThread().getName());
    
    PendingResult<Void> result = Environment.runLater(() -> {
      // 这在 Environment 线程上运行
      System.out.println("UI Update: " + Thread.currentThread().getName());
      statusLabel.setText("Processing complete");
    });
    
    // result.isDone() 此时为 false
    // 任务已排队并将异步执行
  });
}
```

webforJ 按 **严格的先进先出顺序** 处理从后台线程提交的任务，即使是从多个线程同时提交也会保持操作顺序。通过这种排序保证，用户界面更新将按提交的精确顺序应用。因此，如果线程 A 提交任务 1，然后线程 B 提交任务 2，则任务 1 始终会在用户界面线程上执行任务 2 之前。FIFO 顺序处理任务可以防止用户界面不一致。

## 任务取消 {#task-cancellation}

`Environment.runLater()` 返回的 <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> 支持取消，这使您能够阻止排队任务的执行。通过取消待定任务，您可以避免内存泄漏，并防止长时间运行的操作在不再需要时更新用户界面。

### 基本取消 {#basic-cancellation}

```java
PendingResult<Void> result = Environment.runLater(() -> {
  updateUI();
});

// 如果尚未执行则取消
if (!result.isDone()) {
  result.cancel();
}
```

### 管理多个更新 {#managing-multiple-updates}

当执行具有频繁用户界面更新的长时间操作时，跟踪所有待定结果：

```java
public class LongRunningTask {
  private final List<PendingResult<?>> pendingUpdates = new ArrayList<>();
  private volatile boolean isCancelled = false;
  
  public void startTask() {
    CompletableFuture.runAsync(() -> {
      for (int i = 0; i <= 100; i++) {
        if (isCancelled) return;
        
        final int progress = i;
        PendingResult<Void> update = Environment.runLater(() -> {
          progressBar.setValue(progress);
        });
        
        // 跟踪以便于取消
        pendingUpdates.add(update);
        
        Thread.sleep(100);
      }
    });
  }
  
  public void cancelTask() {
    isCancelled = true;
    
    // 取消所有待定的 UI 更新
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

当组件被销毁（例如，在导航过程中）时，取消所有待定更新以防止内存泄漏：

```java
@Route
public class CleanupView extends Composite<Div> {
  private final List<PendingResult<?>> pendingUpdates = new ArrayList<>();
  
  @Override
  protected void onDestroy() {
    super.onDestroy();
    
    // 取消所有待定更新以防止内存泄漏
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

1. **上下文要求**：线程必须继承 `Environment` 上下文。外部库线程、系统定时器和静态初始化器无法使用此 API。

2. **内存泄漏防止**：始终在组件生命周期方法中跟踪并取消 `PendingResult` 对象。排队的 Lambda 表达式捕获对用户界面组件的引用，如果不取消，则会阻止垃圾回收。

3. **先进先出执行**：所有任务无论重要性如何，都以严格的 FIFO 顺序执行。没有优先级系统。

4. **取消限制**：取消只防止排队任务的执行。已在执行的任务将正常完成。

## 完整案例研究：`LongTaskView` {#complete-case-study-longtaskview}

以下是一个完整的、生产就绪的实现，展示了异步用户界面更新的所有最佳实践：

<!-- vale off -->

<ExpandableCode title="LongTaskView.java" language="java" startLine={91} endLine={159}>
{`
@Route("/")
public class LongTaskView extends Composite<FlexLayout> {
  // 使用单线程执行器以防止资源耗尽
  // 在生产中，考虑使用共享的全局线程池
  private final ExecutorService executor = Executors.newSingleThreadExecutor(r -> {
    Thread t = new Thread(r, "LongTaskView-Worker");
    t.setDaemon(true);
    return t;
  });

  // 跟踪当前任务和待定 UI 更新
  private CompletableFuture<Void> currentTask = null;
  private final List<PendingResult<?>> pendingUIUpdates = new ArrayList<>();
  private volatile boolean isCancelled = false;

  // UI 组件
  private final FlexLayout self = getBoundComponent();
  private H2 titleLabel = new H2("后台 UI 更新演示");
  private Paragraph descriptionPara = new Paragraph(
      "该演示展示了如何通过 Environment.runLater() 实现从后台线程安全更新用户界面。" +
          "单击“启动长任务”以运行一个 10 秒的后台计算，该计算更新 UI 进度。" +
          "‘测试 UI’按钮证明在后台操作期间 UI 保持响应。");
  private TextField statusField = new TextField("状态");
  private ProgressBar progressBar = new ProgressBar();
  private TextField resultField = new TextField("结果");
  private Button startButton = new Button("启动长任务");
  private Button cancelButton = new Button("取消任务");
  private Button testButton = new Button("测试 UI - 点击我！");
  private Paragraph footerPara = new Paragraph(
      "注意：该任务可以随时被取消，展示了后台线程和排队的 UI 更新的适当清理。");
  private Toast globalToast = new Toast("", 3000, Theme.GRAY);
  private AtomicInteger clickCount = new AtomicInteger(0);

  public LongTaskView() {
    self.setDirection(FlexDirection.COLUMN);
    self.setMaxWidth(400);
    self.setStyle("margin", "1em auto");

    // 配置字段
    statusField.setReadOnly(true);
    statusField.setValue("准备开始");
    statusField.setLabel("状态");

    // 配置进度条
    progressBar.setMin(0);
    progressBar.setMax(100);
    progressBar.setValue(0);
    progressBar.setAnimated(true);
    progressBar.setStriped(true);
    progressBar.setText("进度：{{x}}%");
    progressBar.setTheme(Theme.PRIMARY);

    resultField.setReadOnly(true);
    resultField.setValue("");
    resultField.setLabel("结果");

    // 配置按钮
    startButton.setTheme(ButtonTheme.PRIMARY);
    startButton.onClick(e -> startLongTask());

    cancelButton.setTheme(ButtonTheme.DANGER);
    cancelButton.setEnabled(false);
    cancelButton.onClick(e -> cancelTask());

    testButton.onClick(e -> {
      int count = clickCount.incrementAndGet();
      showToast("点击 #" + count + " - UI 是响应的！", Theme.GRAY);
    });

    // 添加组件
    self.add(titleLabel, descriptionPara, statusField, progressBar, resultField,
        startButton, cancelButton, testButton, footerPara);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();

    // 取消任何正在运行的任务和待定的 UI 更新
    cancelTask();

    // 清除任务引用
    currentTask = null;

    // 优雅地关闭实例执行器
    executor.shutdown();
  }

  private void startLongTask() {
    startButton.setEnabled(false);
    cancelButton.setEnabled(true);
    statusField.setValue("正在启动后台任务...");
    progressBar.setValue(0);
    resultField.setValue("");

    // 重置取消标志并清除之前的待定更新
    isCancelled = false;
    pendingUIUpdates.clear();

    // 使用显式执行器启动后台任务
    // 注意：cancel(true) 会中断线程，导致 Thread.sleep() 抛出
    // InterruptedException
    currentTask = CompletableFuture.runAsync(() -> {
      double result = 0;

      // 模拟长任务的 100 步
      for (int i = 0; i <= 100; i++) {
        // 检查是否已取消
        if (isCancelled) {
          PendingResult<Void> cancelUpdate = Environment.runLater(() -> {
            statusField.setValue("任务已取消！");
            progressBar.setValue(0);
            resultField.setValue("");
            startButton.setEnabled(true);
            cancelButton.setEnabled(false);
            showToast("任务已取消", Theme.GRAY);
          });
          pendingUIUpdates.add(cancelUpdate);
          return;
        }

        try {
          Thread.sleep(100); // 总共 10 秒
        } catch (InterruptedException e) {
          // 线程被中断 - 立即退出
          Thread.currentThread().interrupt(); // 恢复中断状态
          return;
        }

        // 执行一些计算（演示使用确定性）
        // 产生 0 到 1 之间的值
        result += Math.sin(i) * 0.5 + 0.5;

        // 从后台线程更新进度
        final int progress = i;
        PendingResult<Void> updateResult = Environment.runLater(() -> {
          progressBar.setValue(progress);
          statusField.setValue("处理... " + progress + "%");
        });
        pendingUIUpdates.add(updateResult);
      }

      // 最终更新结果（如果任务未被取消，则到达此代码）
      if (!isCancelled) {
        final double finalResult = result;
        PendingResult<Void> finalUpdate = Environment.runLater(() -> {
          statusField.setValue("任务完成！");
          resultField.setValue("结果：" + String.format("%.2f", finalResult));
          startButton.setEnabled(true);
          cancelButton.setEnabled(false);
          showToast("后台任务完成！", Theme.SUCCESS);
        });
        pendingUIUpdates.add(finalUpdate);
      }
    }, executor);
  }

  private void cancelTask() {
    if (currentTask != null && !currentTask.isDone()) {
      // 设置取消标志
      isCancelled = true;

      // 取消主任务（中断线程）
      currentTask.cancel(true);

      // 取消所有待定的 UI 更新
      for (PendingResult<?> pending : pendingUIUpdates) {
        if (!pending.isDone()) {
          pending.cancel();
        }
      }

      if (!statusField.isDestroyed() && !cancelButton.isDestroyed()) {
        statusField.setValue("正在取消任务...");
        cancelButton.setEnabled(false);

        showToast("请求取消", Theme.GRAY);
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
`}
</ExpandableCode>

<div class="videos-container" style={{maxWidth: '400px', margin: '0 auto'}}>
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/webforj-long-tasks.mp4" type="video/mp4"/>
  </video>
</div>

<!-- vale on -->

### 案例研究分析 {#case-study-analysis}

该实现展示了几个关键模型：

#### 1. 线程池管理 {#1-thread-pool-management}
```java
private final ExecutorService executor = Executors.newSingleThreadExecutor(r -> {
  Thread t = new Thread(r, "LongTaskView-Worker");
  t.setDaemon(true);
  return t;
});
```
- 使用 **单线程执行器** 防止资源耗尽
- 创建 **守护线程**，不会阻止 JVM 关闭

#### 2. 跟踪待定更新 {#2-tracking-pending-updates}
```java
private final List<PendingResult<?>> pendingUIUpdates = new ArrayList<>();
```
每个 `Environment.runLater()` 调用都会被跟踪，以便于：
- 当用户单击取消时取消任务
- 在 `onDestroy()` 中防止内存泄漏
- 在组件生命周期中进行适当清理

#### 3. 协作取消 {#3-cooperative-cancellation}
```java
private volatile boolean isCancelled = false;
```
后台线程在每次迭代时检查此标志，实现：
- 对取消的快速响应
- 清理退出循环
- 防止进一步的用户界面更新

#### 4. 生命周期管理 {#4-lifecycle-management}
```java
@Override
protected void onDestroy() {
  super.onDestroy();
  cancelTask();  // 重用取消逻辑
  currentTask = null;
  executor.shutdown();
}
```
通过：
- 取消所有待定的 UI 更新
- 中断正在运行的线程
- 关闭执行器 防止内存泄漏。

#### 5. UI 响应性测试 {#5-ui-responsiveness-testing}
```java
testButton.onClick(e -> {
  int count = clickCount.incrementAndGet();
  showToast("点击 #" + count + " - UI 是响应的！", Theme.GRAY);
});
```
演示在后台操作期间用户界面线程保持响应。
