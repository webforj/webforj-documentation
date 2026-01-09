---
sidebar_position: 11
title: Debouncing
slug: debouncing
draft: false
---

<DocChip chip='since' label='25.11' />
<JavadocLink type="foundation" location="com/webforj/Debouncer" top='true'/>

Debouncing is a technique that delays executing an action until a specified time has elapsed since the last call. Each new call resets the timer. This is useful for scenarios like search-as-you-type, where you want to wait until the user stops typing before executing a search query.

<ComponentDemo
path='/webforj/debouncer?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/debouncer/DebouncerDemoView.java'
height='200px'
/>

## Basic usage {#basic-usage}

The `Debouncer` class provides a simple way to debounce actions. Create a `Debouncer` with a delay in seconds, then call `run()` with the action you want to debounce:

```java
Debouncer debounce = new Debouncer(0.3f);

textField.onModify(e -> {
  debounce.run(() -> search(textField.getText()));
});
```

In this example, the `search()` method is called only after the user stops typing for 300 milliseconds. Each keystroke resets the timer via the `onModify` event, so rapid typing won't trigger multiple searches.

## How it works {#how-it-works}

When you call `run()` with an action:

1. If no action is pending, the `Debouncer` schedules the action to run after the delay
2. If an action is already pending, the previous action is cancelled and the timer restarts with the new action
3. Once the delay elapses without another call, the action executes

The `Debouncer` runs on the UI thread using webforJ's [`Interval`](/docs/advanced/interval) mechanism, so you don't need to wrap UI updates in `Environment.runLater()`.

:::tip
The delay parameter uses seconds as the unit, not milliseconds. Use `0.3f` for 300ms or `1.5f` for 1.5 seconds.
:::

## API methods {#api-methods}

The `Debouncer` class provides the following methods:

| Method | Description |
|--------|-------------|
| `run(Runnable action)` | Schedules the action to run after the delay. If called again before the delay elapses, the previous action is cancelled and the timer restarts. |
| `cancel()` | Cancels any pending action without executing it. |
| `flush()` | Immediately executes the pending action if one exists, then clears it. |
| `isPending()` | Returns `true` if an action is scheduled but hasn't executed yet. |
| `getDelay()` | Returns the debounce delay in seconds. |

## Controlling execution {#controlling-execution}

The following methods can be used to more precisely handle the execution and use of the `Debouncer`:

### Cancelling a pending action {#cancelling-a-pending-action}

Use `cancel()` to stop a pending action from executing:

```java
Debouncer debounce = new Debouncer(1f);

debounce.run(() -> saveDocument());

// User navigates away before save executes
debounce.cancel();
```

### Forcing immediate execution {#forcing-immediate-execution}

Use `flush()` to execute a pending action immediately:

```java
Debouncer debounce = new Debouncer(0.5f);

textField.onModify(e -> {
  debounce.run(() -> validateInput(textField.getText()));
});

// Force validation before form submission
submitButton.onClick(e -> {
  debounce.flush();
  if (isValid()) {
    submitForm();
  }
});
```

### Checking pending status {#checking-pending-status}

Use `isPending()` to check if an action is waiting to execute:

```java
Debouncer debounce = new Debouncer(0.3f);

if (debounce.isPending()) {
  statusLabel.setText("Processing...");
}
```

## Use cases {#use-cases}

While there are many use cases for the `Debouncer`, the following are some of the most common:

### Search-as-you-type {#search-as-you-type}

One of the most common use cases is delaying search requests until the user pauses typing:

```java
public class SearchView extends Composite<FlexLayout> {
  private final Debouncer searchDebounce = new Debouncer(0.3f);
  private final TextField searchField = new TextField();
  private final Div results = new Div();

  public SearchView() {
    searchField.setPlaceholder("Search...");
    searchField.onModify(e -> {
      searchDebounce.run(() -> performSearch(searchField.getText()));
    });

    getBoundComponent().add(searchField, results);
  }

  private void performSearch(String query) {
    // Execute search and update results
    results.setText("Results for: " + query);
  }
}
```

### Auto-save {#auto-save}

Another common scenario is saving changes after the user stops editing, resulting in an auto-save behavior:

```java
TextArea editor = new TextArea();
Debouncer saveDebounce = new Debouncer(2f);

editor.onModify(e -> {
  saveDebounce.run(() -> {
    saveDocument(editor.getText());
    showNotification("Document saved");
  });
});
```

## Debouncer vs ElementEventOptions {#debouncer-vs-elementeventoptions}

webforJ provides two approaches to debouncing:

| Feature | `Debouncer` | `ElementEventOptions.setDebounce()` |
|---------|-------------|-------------------------------------|
| Scope | Any action | Element events only |
| Location | Server-side | Client-side |
| Unit | Seconds (float) | Milliseconds (int) |
| Flexibility | Full control with cancel/flush | Automatic with event |

Use `Debouncer` when you need programmatic control over debouncing, such as cancelling or flushing pending actions. Use `ElementEventOptions` when you want simple client-side debouncing for element events without additional server round-trips.

```java
// Using ElementEventOptions for client-side debouncing
ElementEventOptions options = new ElementEventOptions();
options.setDebounce(300);

element.addEventListener("input", e -> {
  // This handler is debounced on the client
}, options);
```

## Best practices {#best-practices}

1. **Choose appropriate delays**: Use shorter delays (200-300ms) for responsive interactions like search, and longer delays (1-2s) for expensive operations like auto-save.

2. **Reuse debouncer instances**: Create one `Debouncer` instance and reuse it rather than creating new instances on each event.

3. **Flush before critical actions**: Call `flush()` before form submission or navigation to ensure pending actions complete.

4. **Cancel on cleanup**: Cancel pending actions when components are destroyed to prevent memory leaks or errors from executing on destroyed components.

```java
public class SearchPanel extends Composite<Div> {
  private final Debouncer debounce = new Debouncer(0.3f);

  @Override
  protected void onDidDestroy() {
    debounce.cancel();
  }
}
```
