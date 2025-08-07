---
sidebar_position: 46
title: Asynchronous Updates
sidebar_class_name: new-content
_i18n_hash: a426166aa63471b0d9d84e6c4786c6db
---
```html
<DocChip chip='since' label='25.02' />
<DocChip chip='experimental' />
<JavadocLink type="foundation" location="com/webforj/Environment" anchor="runLater(java.lang.Runnable)" top='true'/>

`Environment.runLater()` API 提供了一种机制，可以安全地从 webforJ 应用程序的后台线程更新 UI。此实验性功能启用异步操作，同时保持 UI 修改的线程安全性。

:::warning 实验性 API
此 API 自 25.02 起被标记为实验性，未来版本中可能会发生变化。API 签名、行为和性能特征可能会修改。
:::

## 理解线程模型 {#understanding-the-thread-model}

webforJ 强制执行严格的线程模型，所有 UI 操作必须在 `Environment` 线程上进行。该限制存在的原因包括：

1. **webforJ API 限制**：底层 webforJ API 绑定到创建会话的线程
2. **组件线程亲和性**：UI 组件维护不具备线程安全性的状态
3. **事件调度**：所有 UI 事件在单个线程上顺序处理

这种单线程模型防止了竞争条件，并为所有 UI 组件维持了一致的状态，但在与异步、长时间运行的计算任务集成时会带来挑战。

## `RunLater` API {#runlater-api}

`Environment.runLater()` API 提供了两种方法来调度 UI 更新：

```java title="Environment.java"
// 调度无返回值的任务
public static PendingResult<Void> runLater(Runnable task)

// 调度返回值的任务
public static <T> PendingResult<T> runLater(Supplier<T> supplier)
```

这两种方法都会返回一个 <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>，该结果跟踪任务的完成情况，并提供对结果或发生的任何异常的访问。

## 线程上下文继承 {#thread-context-inheritance}

自动上下文继承是 `Environment.runLater()` 的一个关键特性。当在 `Environment` 中运行的线程创建子线程时，这些子线程会自动继承使用 `runLater()` 的能力。

### 继承工作原理 {#how-inheritance-works}

从 `Environment` 线程中创建的任何线程都会自动访问该 `Environment`。这种继承是自动发生的，因此您无需传递任何上下文或进行配置。

```java
@Route
public class DataView extends Composite<Div> {
    private final ExecutorService executor = Executors.newCachedThreadPool();
    
    public DataView() {
        // 此线程具有 Environment 上下文
        
        // 子线程自动继承上下文
        executor.submit(() -> {
            String data = fetchRemoteData();
            
            // 可以使用 runLater，因为上下文已被继承
            Environment.runLater(() -> {
                dataLabel.setText(data);
                loadingSpinner.setVisible(false);
            });
        });
    }
}
```

### 没有上下文的线程 {#threads-without-context}

在 `Environment` 上下文之外创建的线程无法使用 `runLater()`，将抛出 `IllegalStateException`：

```java
// 静态初始化器 - 没有 Environment 上下文
static {
    new Thread(() -> {
        Environment.runLater(() -> {});  // 抛出 IllegalStateException
    }).start();
}

// 系统计时器线程 - 没有 Environment 上下文  
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

`runLater()` 的执行行为取决于调用它的线程：

### 从 UI 线程 {#from-the-ui-thread}

当从 `Environment` 线程本身调用时，任务**同步且立即**执行：

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

使用这种同步行为，来自事件处理器的 UI 更新会立即应用，不会产生任何不必要的排队开销。

### 从后台线程 {#from-background-threads}

当从后台线程调用时，任务会被**排队以便异步执行**：

```java
@Override
public void onDidCreate() {
    CompletableFuture.runAsync(() -> {
        // 这在 ForkJoinPool 线程中运行
        System.out.println("Background: " + Thread.currentThread().getName());
        
        PendingResult<Void> result = Environment.runLater(() -> {
            // 这在 Environment 线程中运行
            System.out.println("UI Update: " + Thread.currentThread().getName());
            statusLabel.setText("处理完成");
        });
        
        // result.isDone() 在这里会是 false
        // 任务被排队并将异步执行
    });
}
```

webforJ 严格按照 FIFO 顺序处理从后台线程提交的任务，确保即使是从多个线程并发提交的操作仍保持顺序一致性。由于这个顺序保证，UI 更新会以它们被提交的确切顺序应用。所以如果线程 A 提交任务 1，然后线程 B 提交任务 2，任务 1 将总是在 UI 线程上执行任务 2 之前。按照 FIFO 顺序处理任务可以防止 UI 中出现不一致性。

## 任务取消 {#task-cancellation}

`Environment.runLater()` 返回的 <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> 支持取消，允许您防止排队任务的执行。通过取消待处理的任务，可以避免内存泄漏，并防止长时间运行的操作在不再需要时更新 UI。

### 基本取消 {#basic-cancellation}

```java
PendingResult<Void> result = Environment.runLater(() -> {
    updateUI();
});

// 如果尚未执行，则取消
if (!result.isDone()) {
    result.cancel();
}
```

### 管理多个更新 {#managing-multiple-updates}

在执行长时间运行的操作时，频繁更新 UI，需要跟踪所有待处理结果：

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
                
                // 跟踪以便潜在取消
                pendingUpdates.add(update);
                
                Thread.sleep(100);
            }
        });
    }
    
    public void cancelTask() {
        isCancelled = true;
        
        // 取消所有待处理的 UI 更新
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

当组件被销毁（例如，在导航过程中），取消所有待处理更新以防止内存泄漏：

```java
@Route
public class CleanupView extends Composite<Div> {
    private final List<PendingResult<?>> pendingUpdates = new ArrayList<>();
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        
        // 取消所有待处理更新以防止内存泄漏
        for (PendingResult<?> pending : pendingUpdates) {
            if (!pending.isDone()) {
                pending.cancel();
            }
        }
        pendingUpdates.clear();
    }
}
```

## 设计考虑 {#design-considerations}

1. **上下文要求**：线程必须具有继承的 `Environment` 上下文。外部库线程、系统定时器和静态初始化器无法使用此 API。

2. **防止内存泄漏**：始终跟踪并在组件生命周期方法中取消 `PendingResult` 对象。排队的 lambda 捕获对 UI 组件的引用，如果不取消，将阻止垃圾收集。

3. **FIFO 执行**：所有任务严格按 FIFO 顺序执行，而不考虑重要性。没有优先级系统。

4. **取消限制**：取消只能防止排队任务的执行。已经执行的任务会正常完成。

## 完整案例研究：`LongTaskView` {#complete-case-study-longtaskview}

以下是一个完整、可生产使用的实现，演示异步 UI 更新的所有最佳实践：

<!-- vale off -->

<ExpandableCode title="LongTaskView.java" language="java" startLine={91} endLine={159}>
{`
@Route("/")
public class LongTaskView extends Composite<FlexLayout> {
  // 使用单线程执行器以防止资源耗尽
  // 对于生产，请考虑使用共享的应用程序范围线程池
  private final ExecutorService executor = Executors.newSingleThreadExecutor(r -> {
    Thread t = new Thread(r, "LongTaskView-Worker");
    t.setDaemon(true);
    return t;
  });

  // 跟踪当前任务和待处理的 UI 更新
  private CompletableFuture<Void> currentTask = null;
  private final List<PendingResult<?>> pendingUIUpdates = new ArrayList<>();
  private volatile boolean isCancelled = false;

  // UI 组件
  private FlexLayout self = getBoundComponent();
  private H2 titleLabel = new H2("后台 UI 更新演示");
  private Paragraph descriptionPara = new Paragraph(
      "本演示展示了 Environment.runLater() 如何实现从后台线程安全更新 UI。 " +
          "点击 '开始长任务' 来执行一个 10 秒的后台计算并更新 UI 进度。 " +
          "'测试 UI' 按钮证实在后台操作期间，UI 保持响应。");
  private TextField statusField = new TextField("状态");
  private ProgressBar progressBar = new ProgressBar();
  private TextField resultField = new TextField("结果");
  private Button startButton = new Button("开始长任务");
  private Button cancelButton = new Button("取消任务");
  private Button testButton = new Button("测试 UI - 点击我！");
  private Paragraph footerPara = new Paragraph(
      "注意：任务可以随时取消，演示后台线程和排队 UI 更新的正常清理。");
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
    progressBar.setText("进度: {{x}}%");
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

    // 取消任何正在运行的任务和待处理的 UI 更新
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

    // 重置取消标志并清空之前的待处理更新
    isCancelled = false;
    pendingUIUpdates.clear();

    // 使用显式执行器启动后台任务
    // 注意：cancel(true) 会中断线程，导致 Thread.sleep() 抛出
    // InterruptedException
    currentTask = CompletableFuture.runAsync(() -> {
      double result = 0;

      // 模拟具有 100 步的长任务
      for (int i = 0; i <= 100; i++) {
        // 检查是否取消
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
          Thread.sleep(100); // 共 10 秒
        } catch (InterruptedException e) {
          // 线程被中断 - 立即退出
          Thread.currentThread().interrupt(); // 恢复被中断状态
          return;
        }

        // 执行某些计算（确定性示例）
        // 产生的值在 0 和 1 之间
        result += Math.sin(i) * 0.5 + 0.5;

        // 在后台线程中更新进度
        final int progress = i;
        PendingResult<Void> updateResult = Environment.runLater(() -> {
          progressBar.setValue(progress);
          statusField.setValue("处理... " + progress + "%");
        });
        pendingUIUpdates.add(updateResult);
      }

      // 最终更新结果（此代码只会在任务未被取消的情况下到达）
      if (!isCancelled) {
        final double finalResult = result;
        PendingResult<Void> finalUpdate = Environment.runLater(() -> {
          statusField.setValue("任务完成！");
          resultField.setValue("结果: " + String.format("%.2f", finalResult));
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

      // 取消所有待处理的 UI 更新
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

该实现演示了几个关键模式：

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

#### 2. 跟踪待处理更新 {#2-tracking-pending-updates}
```java
private final List<PendingResult<?>> pendingUIUpdates = new ArrayList<>();
```
每次 `Environment.runLater()` 调用都会被跟踪，以便于：
- 用户点击取消时进行取消
- 在 `onDestroy()` 中防止内存泄漏
- 在组件生命周期内进行适当清理

#### 3. 协作取消 {#3-cooperative-cancellation}
```java
private volatile boolean isCancelled = false;
```
后台线程在每次迭代时检查该标志，从而启用：
- 对取消的立即响应
- 循环的干净退出
- 防止进一步的 UI 更新

#### 4. 生命周期管理 {#4-lifecycle-management}
```java
@Override
protected void onDestroy() {
    super.onDestroy();
    cancelTask();  // 复用取消逻辑
    currentTask = null;
    executor.shutdown();
}
```
通过：
- 取消所有待处理的 UI 更新
- 中断正在运行的线程
- 关闭执行器，来防止内存泄漏

#### 5. UI 响应测试 {#5-ui-responsiveness-testing}
```java
testButton.onClick(e -> {
    int count = clickCount.incrementAndGet();
    showToast("点击 #" + count + " - UI 是响应的！", Theme.GRAY);
});
```
证明在后台操作期间，UI 线程保持响应。
```
