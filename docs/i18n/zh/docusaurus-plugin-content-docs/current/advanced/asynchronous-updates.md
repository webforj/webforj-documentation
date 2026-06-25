---
sidebar_position: 55
title: Asynchronous Updates
_i18n_hash: 44d86e725d9228ead98794da8f6210ff
---
<DocChip chip='since' label='25.02' />
<DocChip chip='experimental' />
<JavadocLink type="foundation" location="com/webforj/Environment" anchor="runLater(java.lang.Runnable)" top='true'/>

`Environment.runLater()` API 提供了一种机制，可以安全地从 webforJ 应用程序中的后台线程更新 UI。此实验特性能够实现异步操作，同时保持 UI 修改的线程安全性。

<ExperimentalWarning />

<AISkillTip skill="webforj-handling-timers-and-async" />

## 理解线程模型 {#understanding-the-thread-model}

webforJ 强制执行严格的线程模型，所有 UI 操作必须在 `Environment` 线程上进行。此限制存在的原因：

1. **webforJ API 限制**：底层 webforJ API 绑定到创建会话的线程
2. **组件线程亲和性**：UI 组件维护的状态不是线程安全的
3. **事件调度**：所有 UI 事件都是在单线程上按顺序处理的

这种单线程模型防止了竞争条件，并维护了所有 UI 组件的一致状态，但在与异步、长时间计算任务集成时会带来挑战。

## `RunLater` API {#runlater-api}

`Environment.runLater()` API 提供了两种调度 UI 更新的方法：

```java title="Environment.java"
// 调度一个没有返回值的任务
public static PendingResult<Void> runLater(Runnable task)

// 调度一个返回值的任务
public static <T> PendingResult<T> runLater(Supplier<T> supplier)
```

这两种方法都返回一个 <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>，它跟踪任务完成情况，并提供对结果或发生的任何异常的访问。

## 线程上下文继承 {#thread-context-inheritance}

自动上下文继承是 `Environment.runLater()` 的一个关键特性。当在 `Environment` 中运行的线程创建子线程时，这些子线程会自动继承使用 `runLater()` 的能力。

### 继承如何工作 {#how-inheritance-works}

从 `Environment` 线程内部创建的任何线程自动获得对该 `Environment` 的访问权限。这种继承是自动发生的，因此您无需传递任何上下文或配置任何内容。

```java
@Route
public class DataView extends Composite<Div> {
  private final ExecutorService executor = Executors.newCachedThreadPool();
  
  public DataView() {
    // 此线程具有 Environment 上下文
    
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

在 `Environment` 上下文之外创建的线程无法使用 `runLater()`，会抛出 `IllegalStateException`：

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

`runLater()` 的执行行为取决于调用它的线程：

### 从 UI 线程 {#from-the-ui-thread}

当从 `Environment` 线程本身调用时，任务是 **同步并立即** 执行的：

```java
button.onClick(e -> {
  System.out.println("在之前: " + Thread.currentThread().getName());
  
  PendingResult<String> result = Environment.runLater(() -> {
    System.out.println("在内部: " + Thread.currentThread().getName());
    return "已完成";
  });
  
  System.out.println("在之后: " + result.isDone());  // true
});
```

由于这种同步行为，事件处理程序中的 UI 更新会立即应用，而不需要任何不必要的排队开销。

### 从后台线程 {#from-background-threads}

当从后台线程调用时，任务会被 **排队以进行异步执行**：

```java
@Override
public void onDidCreate() {
  CompletableFuture.runAsync(() -> {
    // 这在 ForkJoinPool 线程上运行
    System.out.println("后台: " + Thread.currentThread().getName());
    
    PendingResult<Void> result = Environment.runLater(() -> {
      // 这在 Environment 线程上运行
      System.out.println("UI 更新: " + Thread.currentThread().getName());
      statusLabel.setText("处理完成");
    });
    
    // result.isDone() 在这里是 false
    // 任务被排队，将异步执行
  });
}
```

webforJ 从后台线程提交的任务以 **严格的 FIFO 顺序** 处理，即使是从多个线程同时提交也能保持操作顺序的正确性。由于这一排序保证，UI 更新按照提交的确切顺序应用。所以如果线程 A 提交任务 1，然后线程 B 提交任务 2，任务 1 始终会在 UI 线程上执行在任务 2 之前。按 FIFO 顺序处理任务可以防止 UI 中出现不一致性。

## 任务取消 {#task-cancellation}

`Environment.runLater()` 返回的 <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> 支持取消，允许您防止排队的任务执行。通过取消待处理的任务，您可以避免内存泄漏，并防止长时间运行的操作在不再需要时更新 UI。

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

在执行长期运行操作且频繁更新 UI 时，跟踪所有待处理结果：

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
        
        // 跟踪以便可能的取消
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

当组件被销毁（例如，在导航期间）时，取消所有待处理的更新以防止内存泄漏：

```java
@Route
public class CleanupView extends Composite<Div> {
  private final List<PendingResult<?>> pendingUpdates = new ArrayList<>();
  
  @Override
  protected void onDestroy() {
    super.onDestroy();
    
    // 取消所有待处理的更新以防止内存泄漏
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

1. **上下文要求**：线程必须继承了 `Environment` 上下文。外部库线程、系统定时器和静态初始化器无法使用此 API。

2. **内存泄漏预防**：始终跟踪和取消组件生命周期方法中的 `PendingResult` 对象。排队的 lambda 表达式捕获了对 UI 组件的引用，如果不取消则会阻止垃圾回收。

3. **FIFO 执行**：所有任务按照严格的 FIFO 顺序执行，无论优先级如何。没有优先级系统。

4. **取消限制**：取消仅防止排队任务的执行。已经在执行的任务会正常完成。

## 完整案例研究：`LongTaskView` {#complete-case-study-longtaskview}

以下是一个完整的、生产就绪的实现，演示了异步 UI 更新的所有最佳实践：

<!-- vale off -->

<ExpandableCode title="LongTaskView.java" language="java" startLine={91} endLine={159}>
{`
@Route("/")
public class LongTaskView extends Composite<FlexLayout> {
  // 使用单线程执行器以防止资源耗尽
  // 对于生产，考虑使用一个共享的全局线程池
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
  private final FlexLayout self = getBoundComponent();
  private H2 titleLabel = new H2("后台 UI 更新演示");
  private Paragraph descriptionPara = new Paragraph(
      "此演示表明如何 Environment.runLater() 允许安全地从后台线程更新 UI。 " +
          "单击 '开始长任务' 以运行一个 10 秒的后台计算，该计算更新 UI 进度。 " +
          " '测试 UI' 按钮证明 UI 在后台操作时保持响应。");
  private TextField statusField = new TextField("状态");
  private ProgressBar progressBar = new ProgressBar();
  private TextField resultField = new TextField("结果");
  private Button startButton = new Button("开始长任务");
  private Button cancelButton = new Button("取消任务");
  private Button testButton = new Button("测试 UI - 点击我!");
  private Paragraph footerPara = new Paragraph(
      "注意：任务可以在任何时候取消，证明后台线程和排队的 UI 更新能够正确清理。");
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
      showToast("点击 #" + count + " - UI 响应正常！", Theme.GRAY);
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

    // 重置取消标志并清除之前的待处理更新
    isCancelled = false;
    pendingUIUpdates.clear();

    // 使用显式执行器启动后台任务
    // 注意：cancel(true) 将中断线程，导致 Thread.sleep() 抛出
    // InterruptedException
    currentTask = CompletableFuture.runAsync(() -> {
      double result = 0;

      // 模拟长任务， 100 步
      for (int i = 0; i <= 100; i++) {
        // 检查是否已取消
        if (isCancelled) {
          PendingResult<Void> cancelUpdate = Environment.runLater(() -> {
            statusField.setValue("任务已取消！");
            progressBar.setValue(0);
            resultField.setValue("");
            startButton.setEnabled(true);
            cancelButton.setEnabled(false);
            showToast("任务被取消", Theme.GRAY);
          });
          pendingUIUpdates.add(cancelUpdate);
          return;
        }

        try {
          Thread.sleep(100); // 总共 10 秒
        } catch (InterruptedException e) {
          // 线程被中断 - 立即退出
          Thread.currentThread().interrupt(); // 恢复被中断状态
          return;
        }

        // 执行一些计算（演示用确定性）
        // 产生 0 到 1 的值
        result += Math.sin(i) * 0.5 + 0.5;

        // 从后台线程更新进度
        final int progress = i;
        PendingResult<Void> updateResult = Environment.runLater(() -> {
          progressBar.setValue(progress);
          statusField.setValue("处理中... " + progress + "%");
        });
        pendingUIUpdates.add(updateResult);
      }

      // 最后更新结果（此代码只会在任务未被取消的情况下到达）
      if (!isCancelled) {
        final double finalResult = result;
        PendingResult<Void> finalUpdate = Environment.runLater(() -> {
          statusField.setValue("任务完成！");
          resultField.setValue("结果: " + String.format("%.2f", finalResult));
          startButton.setEnabled(true);
          cancelButton.setEnabled(false);
          showToast("后台任务已完成！", Theme.SUCCESS);
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

        showToast("已请求取消", Theme.GRAY);
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

这个实现展示了几个关键模式：

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
每次 `Environment.runLater()` 调用都会被跟踪，以便：
- 用户点击取消时的取消
- `onDestroy()` 中防止内存泄漏
- 组件生命周期期间的正确清理

#### 3. 协作取消 {#3-cooperative-cancellation}
```java
private volatile boolean isCancelled = false;
```
后台线程在每次迭代中检查此标志，从而能够：
- 立即响应取消
- 从循环中清洁退出
- 防止进一步的 UI 更新

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
对于防止内存泄漏至关重要：
- 取消所有待处理的 UI 更新
- 中断正在运行的线程
- 关闭执行器

#### 5. UI 响应性测试 {#5-ui-responsiveness-testing}
```java
testButton.onClick(e -> {
  int count = clickCount.incrementAndGet();
  showToast("点击 #" + count + " - UI 响应正常！", Theme.GRAY);
});
```
证明了 UI 线程在后台操作期间保持响应。
