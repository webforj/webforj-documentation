---
sidebar_position: 10
title: Lifecycle Listeners
sidebar_class_name: new-content
---

<!-- vale off -->
# Lifecycle Listeners <DocChip chip='since' label='25.02' />
<!-- vale on -->

The `AppLifecycleListener` interface enables external code to observe and respond to app lifecycle events. By implementing this interface, you can execute code at specific points during app startup and shutdown without modifying the `App` class itself.

Lifecycle listeners are automatically discovered and loaded at runtime through service provider configuration files. Each app instance receives its own set of listener instances, maintaining isolation between different apps running in the same environment.

## When to use lifecycle listeners

Use lifecycle listeners when you need to:
- Initialize resources or services before an app runs
- Clean up resources when an app terminates  
- Add cross-cutting concerns without modifying app code
- Build plugin architectures

## The `AppLifecycleListener` interface

```java title="AppLifecycleListener.java"
public interface AppLifecycleListener {
    default void onWillRun(App app) {}
    default void onDidRun(App app) {}
    default void onWillTerminate(App app) {}
    default void onDidTerminate(App app) {}
}
```

:::info App isolation
Each app instance receives its own set of listener instances:
- Listeners are isolated between different apps
- Static fields in listeners won't be shared between apps
- Listener instances are created when the app starts and destroyed when it terminates

If you need to share data between apps, use external storage mechanisms like databases or shared services.
:::

### Lifecycle events

| Event | When Called | Common Uses |
|-------|-------------|-------------|
| `onWillRun` | Before `app.run()` executes | Initialize resources, configure services |
| `onDidRun` | After `app.run()` completes successfully | Start background tasks, log successful startup |
| `onWillTerminate` | Before app termination | Save state, prepare for shutdown |
| `onDidTerminate` | After app termination | Clean up resources, final logging |

## Creating a lifecycle listener

### Basic implementation

```java title="StartupListener.java"
import com.webforj.App;
import com.webforj.AppLifecycleListener;

public class StartupListener implements AppLifecycleListener {
    
    @Override
    public void onWillRun(App app) {
        System.out.println("App starting: " + app.getId());
    }
    
    @Override
    public void onDidRun(App app) {
        System.out.println("App started: " + app.getId());
    }
}
```

### Registering the listener

Create a service provider configuration file:

**File**: `src/main/resources/META-INF/services/com.webforj.AppLifecycleListener`

```
com.example.listeners.StartupListener
```

:::tip Using AutoService
It's easy to forget to update service descriptors. Use Google's [AutoService](https://github.com/google/auto/blob/main/service/README.md) to automatically generate the service file:

```java title="StartupListener.java"
import com.google.auto.service.AutoService;

@AutoService(AppLifecycleListener.class)
public class StartupListener implements AppLifecycleListener {
    // Implementation
}
```
:::

## Controlling execution order

Use `@AppListenerPriority` to control the order when multiple listeners are registered. Lower priority values execute first, with a default priority of 10.

```java title="SecurityListener.java"
@AutoService(AppLifecycleListener.class)
@AppListenerPriority(1)  // Executes first
public class SecurityListener implements AppLifecycleListener {
    @Override
    public void onWillRun(App app) {
        initializeSecurity();
    }
}

@AutoService(AppLifecycleListener.class)
@AppListenerPriority(10) // Default priority
public class LoggingListener implements AppLifecycleListener {
    @Override
    public void onWillRun(App app) {
        initializeLogging();
    }
}
```

## Error handling

Exceptions thrown by listeners are logged but don't prevent other listeners from executing or the app from running. Always handle exceptions within your listeners:

```java title="Error handling example"
@Override
public void onWillRun(App app) {
    try {
        riskyInitialization();
    } catch (Exception e) {
        logger.error("Initialization failed", e);
    }
}
```