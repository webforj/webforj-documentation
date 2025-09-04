---
sidebar_position: 55
title: Asynchronous Updates
sidebar_class_name: new-content
---

<DocChip chip='since' label='25.02' />
<DocChip chip='experimental' />
<JavadocLink type="foundation" location="com/webforj/Environment" anchor="runLater(java.lang.Runnable)" top='true'/>

The `Environment.runLater()` API provides a mechanism for safely updating the UI from background threads in webforJ applications. This experimental feature enables asynchronous operations while maintaining thread safety for UI modifications.

:::warning Experimental API
This API is marked as experimental since 25.02 and may change in future releases. The API signature, behavior, and performance characteristics are subject to modification.
:::

## Understanding the thread model {#understanding-the-thread-model}

webforJ enforces a strict threading model where all UI operations must occur on the `Environment` thread. This restriction exists because:

1. **webforJ API constraints**: The underlying webforJ API binds to the thread that created the session
2. **Component thread affinity**: UI components maintain state that's not thread-safe
3. **Event dispatch**: All UI events are processed sequentially on a single thread

This single-threaded model prevents race conditions and maintains a consistent state for all UI components, but creates challenges when integrating with asynchronous, long-running computation tasks.

## `RunLater` API {#runlater-api}

The `Environment.runLater()` API provides two methods for scheduling UI updates:

```java title="Environment.java"
// Schedule a task with no return value
public static PendingResult<Void> runLater(Runnable task)

// Schedule a task that returns a value
public static <T> PendingResult<T> runLater(Supplier<T> supplier)
```

Both methods return a <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> that tracks task completion and provides access to the result or any exceptions that occurred.

## Thread context inheritance {#thread-context-inheritance}

Automatic context inheritance is a critical feature of `Environment.runLater()`. When a thread running in an `Environment` creates child threads, those children automatically inherit the ability to use `runLater()`.

### How inheritance works {#how-inheritance-works}

Any thread created from within an `Environment` thread automatically has access to that `Environment`. This inheritance happens automatically, so you don't need to pass any context or configure anything.

```java
@Route
public class DataView extends Composite<Div> {
    private final ExecutorService executor = Executors.newCachedThreadPool();
    
    public DataView() {
        // This thread has Environment context
        
        // Child threads inherit the context automatically
        executor.submit(() -> {
            String data = fetchRemoteData();
            
            // Can use runLater because context was inherited
            Environment.runLater(() -> {
                dataLabel.setText(data);
                loadingSpinner.setVisible(false);
            });
        });
    }
}
```

### Threads without context {#threads-without-context}

Threads created outside the `Environment` context can't use `runLater()` and will throw an `IllegalStateException`:

```java
// Static initializer - no Environment context
static {
    new Thread(() -> {
        Environment.runLater(() -> {});  // Throws IllegalStateException
    }).start();
}

// System timer threads - no Environment context  
Timer timer = new Timer();
timer.schedule(new TimerTask() {
    public void run() {
        Environment.runLater(() -> {});  // Throws IllegalStateException
    }
}, 1000);

// External library threads - no Environment context
httpClient.sendAsync(request, responseHandler)
    .thenAccept(response -> {
        Environment.runLater(() -> {});  // Throws IllegalStateException
    });
```

## Execution behavior {#execution-behavior}

The execution behavior of `runLater()` depends on which thread calls it:

### From the UI thread {#from-the-ui-thread}

When called from the `Environment` thread itself, tasks execute **synchronously and immediately**:

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

With this synchronous behavior, UI updates from event handlers are applied immediately and don't incur any unnecessary queueing overhead.

### From background threads {#from-background-threads}

When called from a background thread, tasks are **queued for asynchronous execution**:

```java
@Override
public void onDidCreate() {
    CompletableFuture.runAsync(() -> {
        // This runs on ForkJoinPool thread
        System.out.println("Background: " + Thread.currentThread().getName());
        
        PendingResult<Void> result = Environment.runLater(() -> {
            // This runs on Environment thread
            System.out.println("UI Update: " + Thread.currentThread().getName());
            statusLabel.setText("Processing complete");
        });
        
        // result.isDone() would be false here
        // The task is queued and will execute asynchronously
    });
}
```

webforJ processes tasks submitted from background threads in **strict FIFO order**, preserving the sequence of operations even when submitted from multiple threads concurrently. With this ordering guarantee, UI updates are applied in the exact order they were submitted. So if thread A submits task 1, and then thread B submits task 2, task 1 will always execute before task 2 on the UI thread. Processing tasks in FIFO order prevents inconsistencies in the UI.

## Task cancellation {#task-cancellation}

The <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> returned by `Environment.runLater()` supports cancellation, allowing you to prevent queued tasks from executing. By cancelling pending tasks, you can avoid memory leaks and prevent long-running operations from updating the UI after they're no longer needed.

### Basic cancellation {#basic-cancellation}

```java
PendingResult<Void> result = Environment.runLater(() -> {
    updateUI();
});

// Cancel if not yet executed
if (!result.isDone()) {
    result.cancel();
}
```

### Managing multiple updates {#managing-multiple-updates}

When performing long-running operations with frequent UI updates, track all pending results:

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

### Component lifecycle management {#component-lifecycle-management}

When components are destroyed (e.g., during navigation), cancel all pending updates to prevent memory leaks:

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

## Design considerations {#design-considerations}

1. **Context requirement**: Threads must have inherited an `Environment` context. External library threads, system timers, and static initializers can't use this API.

2. **Memory leak prevention**: Always track and cancel `PendingResult` objects in component lifecycle methods. Queued lambdas capture references to UI components, preventing garbage collection if not cancelled.

3. **FIFO execution**: All tasks execute in strict FIFO order regardless of importance. There's no priority system.

4. **Cancellation limitations**: Cancellation only prevents execution of queued tasks. Tasks already executing will complete normally.

## Complete case study: `LongTaskView` {#complete-case-study-longtaskview}

The following is a complete, production-ready implementation demonstrating all best practices for asynchronous UI updates:

<!-- vale off -->

<ExpandableCode title="LongTaskView.java" language="java" startLine={91} endLine={159}>
{`
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
  private FlexLayout self = getBoundComponent();
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

    // Start background task with explicit executor
    // Note: cancel(true) will interrupt the thread, causing Thread.sleep() to throw
    // InterruptedException
    currentTask = CompletableFuture.runAsync(() -> {
      double result = 0;

      // Simulate long task with 100 steps
      for (int i = 0; i <= 100; i++) {
        // Check if cancelled
        if (isCancelled) {
          PendingResult<Void> cancelUpdate = Environment.runLater(() -> {
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
        PendingResult<Void> updateResult = Environment.runLater(() -> {
          progressBar.setValue(progress);
          statusField.setValue("Processing... " + progress + "%");
        });
        pendingUIUpdates.add(updateResult);
      }

      // Final update with result (this code is only reached if the task completed without
      // cancellation)
      if (!isCancelled) {
        final double finalResult = result;
        PendingResult<Void> finalUpdate = Environment.runLater(() -> {
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
`}
</ExpandableCode>

<div class="videos-container" style={{maxWidth: '400px', margin: '0 auto'}}>
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/webforj-long-tasks.mp4" type="video/mp4"/>
  </video>
</div>

<!-- vale on -->

### Case study analysis {#case-study-analysis}

This implementation demonstrates several critical patterns:

#### 1. Thread pool management {#1-thread-pool-management}
```java
private final ExecutorService executor = Executors.newSingleThreadExecutor(r -> {
    Thread t = new Thread(r, "LongTaskView-Worker");
    t.setDaemon(true);
    return t;
});
```
- Uses a **single thread executor** to prevent resource exhaustion
- Creates **daemon threads** that won't prevent JVM shutdown

#### 2. Tracking pending updates {#2-tracking-pending-updates}
```java
private final List<PendingResult<?>> pendingUIUpdates = new ArrayList<>();
```
Every `Environment.runLater()` call is tracked to enable:
- Cancellation when the user clicks cancel
- Memory leak prevention in `onDestroy()`
- Proper cleanup during component lifecycle

#### 3. Cooperative cancellation {#3-cooperative-cancellation}
```java
private volatile boolean isCancelled = false;
```
The background thread checks this flag at each iteration, enabling:
- Immediate response to cancellation
- Clean exit from the loop
- Prevention of further UI updates

#### 4. Lifecycle management {#4-lifecycle-management}
```java
@Override
protected void onDestroy() {
    super.onDestroy();
    cancelTask();  // Reuses cancellation logic
    currentTask = null;
    executor.shutdown();
}
```
Critical for preventing memory leaks by:
- Cancelling all pending UI updates
- Interrupting running threads
- Shutting down the executor

#### 5. UI responsiveness testing {#5-ui-responsiveness-testing}
```java
testButton.onClick(e -> {
    int count = clickCount.incrementAndGet();
    showToast("Click #" + count + " - UI is responsive!", Theme.GRAY);
});
```
Demonstrates that the UI thread remains responsive during background operations.
