---
title: Namespaces
sidebar_position: 40
---

<DocChip chip='since' label='24.22' />
<JavadocLink type="foundation" location="com/webforj/environment/namespace/Namespace" top='true'/>

Namespaces in webforJ provide a mechanism for storing and retrieving shared data across different scopes in a web app. They enable inter-component and cross-session data communication without relying on traditional storage techniques like session attributes or static fields. This abstraction allows developers to encapsulate and access state in a controlled, thread-safe manner. Namespaces are ideal for building multi-user collaboration tools or simply maintaining consistent global settings, and let you coordinate data safely and efficiently.

## What's a namespace? {#whats-a-namespace}

A namespace is a named container that stores key-value pairs. These values can be accessed and modified across different parts of your app depending on the namespace type you use. You can think of it like a thread-safe, distributed map with built-in event handling and locking mechanisms.

### When to use namespaces {#when-to-use-namespaces}

Use namespaces when:

- You need to share values across user sessions or app components.
- You want to react to value changes via listeners.
- You require fine-grained locking for critical sections.
- You need to persist and retrieve state efficiently across your app.


### Types of namespaces {#types-of-namespaces}

webforJ offers three types of namespaces:

| Type        | Scope                                                                                                               | Typical Use                                 |
| ----------- | ------------------------------------------------------------------------------------------------------------------- | ------------------------------------------- |
| **Private** | Shared among clients that use the same prefix and name. Memory is released automatically when no references remain. | Shared state between related user sessions. |
| **Group**   | Shared by all threads spawned from the same parent thread.                                                          | Coordinating state within a thread group.   |
| **Global**  | Accessible across all server threads (JVM-wide). Memory is retained until keys are explicitly removed.              | Application-wide shared state.              |


:::tip Choosing a Default - Prefer `PrivateNamespace`
When in doubt, use a `PrivateNamespace`. It offers safe, scoped sharing between related sessions without impacting global or server-wide state. This makes it a reliable default for most applications. 
:::

## Creating and using a namespace {#creating-and-using-a-namespace}

Namespaces are created by instantiating one of the available types. Each type defines how and where the data is shared. The examples below demonstrate how to create a namespace and interact with its values.

### `Private` namespace {#private-namespace}

The private namespace name is made up of two parts:

- **Prefix**: A developer-defined identifier that should be unique to your app or module to avoid conflicts.
- **Base name** : The specific name for the shared context or data you want to manage.

Together, they form the full namespace name using the format:

```text
prefix + "." + baseName
```

For example, `"myApp.sharedState"`.

Namespaces created with the same prefix and base name always refer to the _same underlying instance_. This ensures consistent shared access across all calls to `PrivateNamespace` using the same identifiers.

```java
// Create or retrieve a private namespace
PrivateNamespace ns = new PrivateNamespace("myApp", "sharedState");
```

You can check for existence before creation:

```java
if (PrivateNamespace.isPresent("myApp.sharedState")) {
  PrivateNamespace ns = PrivateNamespace.ofExisting("myApp.sharedState");
}
```

:::tip Naming Guidelines
When naming a `PrivateNamespace`, follow these rules:

- Both parts must be non-empty.
- Each must start with a letter.
- Only printable characters are allowed.
- Whitespace isn't permitted.

Examples:
- ✓ mycrm.sessionData
- ✓ acme.analytics
- X shared.data (too generic, likely to conflict)
:::

### `Group` and `Global` namespaces {#group-and-global-namespaces}

In addition to PrivateNamespace, webforJ provides two other types for broader sharing contexts. These are useful when state needs to persist beyond a single session or thread group.

- **Global Namespace**: Accessible across all server threads (JVM-wide).
- **Group Namespace**: Shared among threads that originate from the same parent.

```java
// Global shared state, accessible application-wide
GlobalNamespace globalNs = new GlobalNamespace();
globalNs.put("globalTheme", "dark");

// Group-specific state, limited to threads sharing a common parent
GroupNamespace groupNs = new GroupNamespace();
groupNs.put("localCache", new HashMap<>());
```

## Working with values {#working-with-values}

Namespaces provide a consistent interface for managing shared data through key-value pairs. This includes setting, retrieving, removing values, synchronizing access, and observing changes in real time.

### Setting and removing values {#setting-and-removing-values}

Use `put()` to store a value under a specific key. If the key is currently locked, the method waits until the lock is released or the timeout expires.

```java
// Waits up to 20ms (default) to set the value
ns.put("username", "admin");

// Specify custom timeout in milliseconds
ns.put("config", configObject, 100);
```

To remove a key from the namespace:

```java
ns.remove("username");
```

Both `put()` and `remove()` are blocking operations if the target key is locked. If the timeout expires before the lock is released, a `NamespaceLockedException` is thrown.

For safe concurrent updates where you only need to overwrite the value, use `atomicPut()`. It locks the key, writes the value, and releases the lock in one step:

```java
ns.atomicPut("counter", 42);
```

This prevents race conditions and avoids the need for manual locking in simple update scenarios.

### Getting values {#getting-values}

To retrieve a value, use `get()`:

```java
Object value = ns.get("username");
```

If the key doesn't exist, this throws a `NoSuchElementException`. To avoid exceptions, use `getOrDefault()`:

```java
Object value = ns.getOrDefault("username", "guest");
```

To check whether a key is defined:

```java
if (ns.contains("username")) {
  // key exists
}
```

If you want to lazily initialize a value only when it's missing, use `computeIfAbsent()`:

```java
Object token = ns.computeIfAbsent("authToken", key -> generateToken());
```

This is useful for shared values that are created once and reused, such as session tokens, configuration blocks, or cached data.

### Manual locking {#manual-locking}

If you need to perform multiple operations on the same key or coordinate across multiple keys, use manual locking.

```java
ns.setLock("flag", 500); // Wait up to 500ms for the lock

// Critical section starts
Object existing = ns.get("flag");
ns.put("flag", "in-progress");
// Critical section ends

ns.removeLock("flag");
```

Use this pattern when a sequence of operations must be performed atomically across reads and writes. Always ensure the lock is released to avoid blocking other threads.

### Listening for changes {#listening-for-changes}

Namespaces support event listeners that allow you to react to access or modification of values. This is useful for scenarios such as:

- Logging or auditing access to sensitive keys
- Triggering updates when a config value changes
- Monitoring shared state changes in multi-user apps

#### Available listener methods {#available-listener-methods}

| Method                    | Trigger                        | Scope              |
|---------------------------|--------------------------------|--------------------|
| `onAccess`                | Any key is read                | Whole namespace    |
| `onChange`                | Any key is modified            | Whole namespace    |
| `onKeyAccess("key")`      | A specific key is read         | Per key            |
| `onKeyChange("key")`      | A specific key is modified     | Per key            |

Each listener receives an event object containing:
- The key name
- The old value
- The new value
- A reference to the namespace

#### Example: Respond to any key change {#example-respond-to-any-key-change}

```java
ns.onChange(event -> {
  System.out.println("Key changed: " + event.getVariableName());
  System.out.println("Old value: " + event.getOldValue());
  System.out.println("New value: " + event.getNewValue());
});
```

#### Example: Track access to a specific key {#example-track-access-to-a-specific-key}

```java
ns.onKeyAccess("sessionToken", event -> {
  System.out.println("Token was accessed: " + event.getNewValue());
});
```

Listeners return a `ListenerRegistration` object that you can use to unregister the listener later:

```java
ListenerRegistration<NamespaceKeyChangeEvent> reg = ns.onKeyChange("status", event -> {
  // logic
});
reg.remove();
```

## Example: Sharing game state in Tic-Tac-Toe {#example-sharing-game-state-in-tic-tac-toe}

The [webforJ Tic-Tac-Toe demo](https://github.com/webforj/webforj-tictactoe) provides a simple two-player game where turns are shared between users. The project demonstrates how `Namespace` can be used to coordinate state without relying on external tools like databases or APIs.

In this example, a shared Java game object is stored in a `PrivateNamespace`, allowing multiple clients to interact with the same game logic. The namespace serves as a central container for the game state, ensuring that:

- Both players see consistent board updates
- Turns are synchronized
- The game logic is shared across sessions

No external services (like REST or WebSockets) are needed. All coordination is done through namespaces, highlighting their ability to manage shared state in real time with minimal infrastructure.

Explore the code: [webforj/webforj-tictactoe](https://github.com/webforj/webforj-tictactoe)

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tic-tac-toe.mp4" type="video/mp4"/>
  </video>
</div>
