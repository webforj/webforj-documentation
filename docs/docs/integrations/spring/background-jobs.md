---
title: Background Jobs
sidebar_position: 25
---

When users click a button to generate a report or process data, they expect the interface to remain responsive. Progress bars should animate, buttons should react to hover, and the app shouldn't freeze. Spring's `@Async` annotation makes this possible by moving long-running operations to background threads.

webforJ enforces thread safety for UI components - all updates must happen on the UI thread. This creates a challenge: how do background tasks update progress bars or display results? The answer is `Environment.runLater()`, which safely transfers UI updates from Spring's background threads to webforJ's UI thread.

## Enabling asynchronous execution

Spring's asynchronous method execution requires explicit configuration. Without it, methods annotated with `@Async` execute synchronously, defeating their purpose.

Add `@EnableAsync` to your Spring Boot app class:

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

The `@EnableAsync` annotation activates Spring's infrastructure for detecting `@Async` methods and executing them on background threads.

:::tip[Spring async guide]
For a quick introduction to Spring's `@Async` annotation and basic usage patterns, see [Creating Asynchronous Methods](https://spring.io/guides/gs/async-method).
:::

## Creating async services

Services annotated with `@Service` can have methods marked with `@Async` to run on background threads. These methods typically return `CompletableFuture` to enable proper completion handling and cancellation:

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

This service accepts a progress callback (`Consumer<Integer>`) that gets called from the background thread. The callback pattern allows the service to report progress without knowing about UI components. 

The method simulates a 5-second task with 10 progress updates. In production, this would be actual work like database queries or file processing. The exception handling restores the interrupt status to support proper task cancellation when `cancel(true)` is called.

## Using background tasks in views

The view receives the background service through constructor injection:

```java
@Route("/")
public class HelloWorldView extends Composite<FlexLayout> {
  private Button asyncBtn = new Button("Start Background Task");
  private ProgressBar progressBar = new ProgressBar();
  private CompletableFuture<String> currentTask;

  public HelloWorldView(BackgroundService backgroundService) {
    // Service is injected by Spring
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

Spring injects the `BackgroundService` into the view's constructor, just like any other Spring bean. The view then uses this service to start background tasks. The key concept: callbacks from the service execute on background threads, so any UI updates inside those callbacks must use `Environment.runLater()` to transfer execution to the UI thread.

Completion handling requires the same careful thread management:

```java
currentTask.whenComplete((result, error) -> {
    Environment.runLater(() -> {
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

The `whenComplete` callback also executes on a background thread. Every UI operation - enabling the button, hiding the progress bar, showing toasts - must be wrapped in `Environment.runLater()`. Without this wrapping, webforJ throws exceptions because background threads can't access UI components.

:::warning[Thread safety]
Every UI update from a background thread must be wrapped in `Environment.runLater()`. This rule has no exceptions. Direct component access from `@Async` methods always fails.
:::

:::tip[Learn more about thread safety]
For detailed information about webforJ's threading model, execution behavior, and which operations require `Environment.runLater()`, see [Asynchronous Updates](../../advanced/asynchronous-updates).
:::

## Task cancellation and cleanup

Proper lifecycle management prevents memory leaks and unwanted UI updates. The view stores the `CompletableFuture` reference:

```java
private CompletableFuture<String> currentTask;
```

When the view is destroyed, it cancels any running task:

```java
@Override
protected void onDestroy() {
    // Cancel the task if view is destroyed
    if (currentTask != null && !currentTask.isDone()) {
        currentTask.cancel(true);
    }
}
```

The `cancel(true)` parameter is crucial. It interrupts the background thread, causing blocking operations like `Thread.sleep()` to throw `InterruptedException`. This enables immediate task termination. Without the interrupt flag (`cancel(false)`), the task would continue running until it explicitly checks for cancellation.

This cleanup prevents several problems:
- Background threads continue consuming resources after the view is gone
- UI updates attempt to modify destroyed components
- Memory leaks from callbacks holding references to UI components

