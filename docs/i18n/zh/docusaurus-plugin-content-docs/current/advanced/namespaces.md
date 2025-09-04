---
title: Namespaces
sidebar_position: 30
_i18n_hash: f3d79da01b17871bddf7543682a5e7e5
---
<DocChip chip='since' label='24.22' />
<JavadocLink type="foundation" location="com/webforj/environment/namespace/Namespace" top='true'/>

webforJ 中的命名空间提供了一种机制，用于在 Web 应用程序中的不同范围之间存储和检索共享数据。它们使得组件之间以及跨会话的数据通信无需依赖传统的存储技术，比如会话属性或静态字段。这种抽象使开发人员能够以受控的线程安全方式封装和访问状态。命名空间非常适合构建多用户协作工具或简单地保持一致的全局设置，并让您以安全高效的方式协调数据。

## 什么是命名空间？ {#whats-a-namespace}

命名空间是一个存储键值对的命名容器。这些值可以根据您使用的命名空间类型在应用程序的不同部分访问和修改。您可以将其视为一个线程安全的分布式映射，具有内置的事件处理和锁定机制。

### 何时使用命名空间 {#when-to-use-namespaces}

在以下情况下使用命名空间：

- 您需要在用户会话或应用组件之间共享值。
- 您想通过监听器对值的变化作出反应。
- 您需要对关键部分进行细粒度锁定。
- 您需要在应用程序中高效地持久化和检索状态。

### 命名空间类型 {#types-of-namespaces}

webforJ 提供三种类型的命名空间：

| 类型        | 范围                                                                                                               | 典型用法                                 |
| ----------- | ------------------------------------------------------------------------------------------------------------------- | --------------------------------------- |
| **私有**    | 在使用相同前缀和名称的客户端之间共享。当没有引用时，内存会自动释放。     | 相关用户会话之间的共享状态。               |
| **组**      | 由所有从同一父线程生成的线程共享。                                                                                  | 在线程组内协调状态。                       |
| **全局**    | 在所有服务器线程之间（JVM 范围内）可访问。内存会保留，直到显式移除键。              | 应用程序范围内的共享状态。                  |

:::tip 选择默认值 - 优先选择 `私有命名空间`
如有疑问，请使用 `私有命名空间`。它提供了相关会话之间安全的范围共享，而不会影响全局或服务器范围的状态。这使其成为大多数应用程序的可靠默认值。
:::

## 创建和使用命名空间 {#creating-and-using-a-namespace}

通过实例化可用类型之一来创建命名空间。每种类型定义了数据共享的方式和位置。下面的示例演示了如何创建命名空间并与其值进行交互。

### `私有` 命名空间 {#private-namespace}

私有命名空间名称由两部分组成：

- **前缀**：开发者定义的标识符，应该在您的应用程序或模块中是唯一的，以避免冲突。
- **基本名称**：要管理的共享上下文或数据的特定名称。

它们结合在一起形成完整的命名空间名称，格式为：

```text
prefix + "." + baseName
```

例如，`"myApp.sharedState"`。

使用相同前缀和基本名称创建的命名空间总是指向 _同一个底层实例_。这确保了对使用相同标识符的所有 `私人命名空间` 调用的一致共享访问。

```java
// 创建或检索一个私有命名空间
PrivateNamespace ns = new PrivateNamespace("myApp", "sharedState");
```

您可以在创建之前检查是否存在：

```java
if (PrivateNamespace.isPresent("myApp.sharedState")) {
  PrivateNamespace ns = PrivateNamespace.ofExisting("myApp.sharedState");
}
```

:::tip 命名指南
命名 `私人命名空间` 时，请遵循以下规则：

- 两部分必须非空。
- 每部分必须以字母开头。
- 仅允许可打印字符。
- 不允许空格。

示例：
- ✓ mycrm.sessionData
- ✓ acme.analytics
- X shared.data（过于通用，可能会冲突）
:::

### `组` 和 `全局` 命名空间 {#group-and-global-namespaces}

除了私有命名空间，webforJ 还提供两种类型，用于更广泛的共享上下文。当状态需要在单一会话或线程组之外持久化时，这些是非常有用的。

- **全局命名空间**：在所有服务器线程之间可访问（JVM 范围内）。
- **组命名空间**：在同一父线程产生的线程之间共享。

```java
// 全局共享状态，可在整个应用程序中访问
GlobalNamespace globalNs = new GlobalNamespace();
globalNs.put("globalTheme", "dark");

// 特定于组的状态，仅限于共享共同父线程的线程
GroupNamespace groupNs = new GroupNamespace();
groupNs.put("localCache", new HashMap<>());
```

## 使用值 {#working-with-values}

命名空间提供了一致的接口，用于通过键值对管理共享数据。这包括设置、检索、移除值，同步访问和实时观察变化。

### 设置和移除值 {#setting-and-removing-values}

使用 `put()` 在特定键下存储值。如果当前锁定键，该方法将在锁定释放或超时到期之前等待。

```java
// 在最多 20 毫秒（默认）内等待设置值
ns.put("username", "admin");

// 指定自定义超时（以毫秒为单位）
ns.put("config", configObject, 100);
```

要从命名空间中移除键：

```java
ns.remove("username");
```

如果目标键被锁定，则 `put()` 和 `remove()` 都是阻塞操作。如果超时在锁定释放之前到期，则会抛出 `NamespaceLockedException`。

对于只需覆盖值的安全并发更新，请使用 `atomicPut()`。它会锁定键、写入值并在一步中释放锁：

```java
ns.atomicPut("counter", 42);
```

这可以防止竞争条件，并避免在简单更新情况下手动锁定的需要。

### 获取值 {#getting-values}

要检索值，请使用 `get()`：

```java
Object value = ns.get("username");
```

如果键不存在，这将抛出 `NoSuchElementException`。为避免异常，请使用 `getOrDefault()`：

```java
Object value = ns.getOrDefault("username", "guest");
```

要检查键是否已定义：

```java
if (ns.contains("username")) {
  // 键存在
}
```

如果您希望仅在缺失时延迟初始化值，请使用 `computeIfAbsent()`：

```java
Object token = ns.computeIfAbsent("authToken", key -> generateToken());
```

这对于仅创建一次并重用的共享值非常有用，例如会话令牌、配置块或缓存数据。

### 手动锁定 {#manual-locking}

如果您需要对同一键执行多个操作或在多个键之间进行协调，请使用手动锁定。

```java
ns.setLock("flag", 500); // 最多等待 500ms 以获取锁定

// 关键区块开始
Object existing = ns.get("flag");
ns.put("flag", "in-progress");
// 关键区块结束

ns.removeLock("flag");
```

当必须在读取和写入之间以原子方式执行一系列操作时，请使用此模式。始终确保释放锁，以避免阻塞其他线程。

### 监听变更 {#listening-for-changes}

命名空间支持事件监听器，允许您对值的访问或修改作出反应。这在以下场景中非常有用：

- 记录或审计对敏感键的访问
- 触发配置值变更时的更新
- 监控多用户应用中的共享状态变化

#### 可用的监听器方法 {#available-listener-methods}

| 方法                            | 触发                        | 范围              |
|---------------------------------|-----------------------------|--------------------|
| `onAccess`                      | 任何键被读取                | 整个命名空间      |
| `onChange`                      | 任何键被修改                | 整个命名空间      |
| `onKeyAccess("key")`           | 读取特定键                  | 每个键            |
| `onKeyChange("key")`           | 修改特定键                  | 每个键            |

每个监听器接收一个事件对象，其中包含：
- 键名称
- 旧值
- 新值
- 对命名空间的引用

#### 示例：响应任何键更改 {#example-respond-to-any-key-change}

```java
ns.onChange(event -> {
  System.out.println("键已更改: " + event.getVariableName());
  System.out.println("旧值: " + event.getOldValue());
  System.out.println("新值: " + event.getNewValue());
});
```

#### 示例：跟踪特定键的访问 {#example-track-access-to-a-specific-key}

```java
ns.onKeyAccess("sessionToken", event -> {
  System.out.println("令牌已被访问: " + event.getNewValue());
});
```

监听器返回一个 `ListenerRegistration` 对象，您可以使用该对象稍后取消注册监听器：

```java
ListenerRegistration<NamespaceKeyChangeEvent> reg = ns.onKeyChange("status", event -> {
  // 逻辑
});
reg.remove();
```

## 示例：共享 Tic-Tac-Toe 游戏状态 {#example-sharing-game-state-in-tic-tac-toe}

[webforJ Tic-Tac-Toe 演示](https://github.com/webforj/webforj-tictactoe) 提供了一个简单的双人游戏，其中用户之间共享回合。该项目演示了如何使用 `命名空间` 协调状态，而无需依赖数据库或 API 等外部工具。

在此示例中，共享的 Java 游戏对象存储在一个 `私有命名空间` 中，使多个客户端可以与相同的游戏逻辑进行交互。命名空间作为游戏状态的中央容器，确保：

- 两个玩家看到一致的棋盘更新
- 回合得到同步
- 游戏逻辑在会话之间共享

不需要外部服务（如 REST 或 WebSockets）。所有协调通过命名空间完成，突显了它们以最小的基础设施实时管理共享状态的能力。

探索代码： [webforj/webforj-tictactoe](https://github.com/webforj/webforj-tictactoe)

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tic-tac-toe.mp4" type="video/mp4"/>
  </video>
</div>
