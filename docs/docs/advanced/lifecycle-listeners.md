---
sidebar_position: 10
title: App Lifecycle Management
sidebar_class_name: new-content
---

<!-- vale off -->
# Lifecycle Listeners <DocChip chip='since' label='25.02' />
<!-- vale on -->

The `AppLifecycleListener` interface enables external code to observe and respond to app lifecycle events. By implementing this interface, you can execute code at specific points during app startup and shutdown without modifying the `App` class itself.

Lifecycle listeners are automatically discovered and loaded at runtime through service provider configuration files. Each app instance receives its own set of listener instances, maintaining isolation between different apps running in the same environment.

## When to use lifecycle listeners {#when-to-use-lifecycle-listeners}

Use lifecycle listeners when you need to:

- Initialize resources or services before an app runs
- Clean up resources when an app terminates
- Add cross-cutting concerns without modifying the `App` class
- Build plugin architectures

## The `AppLifecycleListener` interface {#the-applifecyclelistener-interface}

```java title="AppLifecycleListener.java"
public interface AppLifecycleListener {
    default void onWillCreate(Environment env) {}     // Since 25.03
    default void onDidCreate(App app) {}              // Since 25.03
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

### Lifecycle events {#lifecycle-events}

| Event             | When Called                                           | Common Uses                                         |
| ----------------- | ----------------------------------------------------- | --------------------------------------------------- |
| `onWillCreate`&nbsp;<DocChip chip='since' label='25.03' /> | After environment initialization, before app creation  | Modify configuration, merge external config sources |
| `onDidCreate`&nbsp;<DocChip chip='since' label='25.03' />  | After app instantiation, before initialization        | Early app-level setup, register services            |
| `onWillRun`       | Before `app.run()` executes                           | Initialize resources, configure services            |
| `onDidRun`        | After `app.run()` completes successfully              | Start background tasks, log successful startup      |
| `onWillTerminate` | Before app termination                                | Save state, prepare for shutdown                    |
| `onDidTerminate`  | After app termination                                 | Clean up resources, final logging                   |

## Creating a lifecycle listener {#creating-a-lifecycle-listener}

### Basic implementation {#basic-implementation}

```java title="StartupListener.java"
import com.webforj.App;
import com.webforj.AppLifecycleListener;
import com.webforj.Environment;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class StartupListener implements AppLifecycleListener {

    @Override
    public void onWillCreate(Environment env) {
        // Modify configuration before app creation
        Config additionalConfig = ConfigFactory.parseString(
            "myapp.feature.enabled = true"
        );
        env.setConfig(additionalConfig);
    }

    @Override
    public void onDidCreate(App app) {
        System.out.println("App created: " + app.getId());
    }

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

### Registering the listener {#registering-the-listener}

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

## Controlling execution order {#controlling-execution-order}

When multiple listeners are registered, you can control their execution order using the `@AppListenerPriority` annotation. This is particularly important when listeners have dependencies on each other or when certain initialization must happen before others.

Priority values work in ascending order - **lower numbers execute first**. The default priority is 10, so listeners without explicit priority annotations will execute after those with lower priority values.

```java title="SecurityListener.java"
@AutoService(AppLifecycleListener.class)
@AppListenerPriority(1)  // Executes first - critical security setup
public class SecurityListener implements AppLifecycleListener {
    @Override
    public void onWillRun(App app) {
        initializeSecurity();
    }
}

@AutoService(AppLifecycleListener.class)
@AppListenerPriority(10) // Default priority - general logging
public class LoggingListener implements AppLifecycleListener {
    @Override
    public void onWillRun(App app) {
        initializeLogging();
    }
}
```

### Execution flow with App hooks {#execution-flow-with-app-hooks}

Beyond controlling the order between multiple listeners, it's important to understand how listeners interact with the `App` class's own lifecycle hooks. For each lifecycle event, the framework follows a specific execution sequence that determines when your listeners run relative to the app's built-in hooks.

The diagram below illustrates this execution flow, showing the precise timing of when `AppLifecycleListener` methods are called in relation to the corresponding `App` hooks: 

<div align="center">

![AppLifecycleListener listeners VS `App` hooks  ](/img/lifecycle-listeners.svg)

</div>


## Error handling {#error-handling}

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