---
title: Namespaces
sidebar_position: 40
description: >-
  Share thread-safe key-value state across sessions, thread groups, or the
  entire JVM using Private, Group, and Global namespaces.
_i18n_hash: 82037bcac961ffa8fefb90bf7579a3af
---
<DocChip chip='since' label='24.22' />
<JavadocLink type="foundation" location="com/webforj/environment/namespace/Namespace" top='true'/>

webforJ中的命名空间提供了一种机制，用于在Web应用程序中的不同作用域之间存储和检索共享数据。它们使得组件间和跨会话的数据通信成为可能，而无需依赖传统的存储技术，如会话属性或静态字段。这种抽象允许开发人员以受控的线程安全方式封装和访问状态。命名空间非常适合构建多用户协作工具，或简单地维护一致的全局设置，并让您能够安全高效地协调数据。

## 什么是命名空间？ {#whats-a-namespace}

命名空间是一个命名的容器，用于存储键值对。这些值可以根据您使用的命名空间类型在应用程序的不同部分之间访问和修改。可以将其视为具有内置事件处理和锁机制的线程安全分布式映射。

### 何时使用命名空间 {#when-to-use-namespaces}

在以下情况下使用命名空间：

- 您需要在用户会话或应用程序组件之间共享值。
- 您希望通过监听器对值的变化作出反应。
- 您需要对关键部分进行细粒度锁定。
- 您需要在应用程序中高效地持久化和检索状态。

### 命名空间的类型 {#types-of-namespaces}

webforJ提供三种类型的命名空间：

| 类型        | 作用域                                                                                                            | 典型用法                                 |
| ----------- | ---------------------------------------------------------------------------------------------------------------- | ----------------------------------------- |
| **私有**    | 在使用相同前缀和名称的客户端之间共享。当没有引用存在时，内存会自动释放。                                     | 相关用户会话之间的共享状态。                   |
| **组**      | 由来自同一父线程的所有线程共享。                                                                                 | 在线程组内协调状态。                        |
| **全局**    | 在所有服务器线程（JVM范围内）可访问。内存会保留，直到显式移除密钥。                                              | 应用程序范围内的共享状态。                     |

:::tip 选择默认 - 首选 `PrivateNamespace`
当不确定时，请使用 `PrivateNamespace`。它提供了安全的、作用域内的共享，避免影响全局或服务器范围内的状态。这使其成为大多数应用程序的可靠默认值。
:::

## 创建和使用命名空间 {#creating-and-using-a-namespace}

命名空间通过实例化可用类型之一来创建。每种类型定义数据共享的方式和位置。下面的示例演示如何创建命名空间并与其值进行交互。

### `Private` 命名空间 {#private-namespace}

私有命名空间名称由两个部分组成：

- **前缀**：由开发人员定义的标识符，应该对您的应用程序或模块唯一，以避免冲突。
- **基本名称**：您希望管理的共享上下文或数据的具体名称。

它们共同形成完整的命名空间名称，格式为：

```text
前缀 + "." + 基本名称
```

例如，`"myApp.sharedState"`。

使用相同前缀和基本名称创建的命名空间始终引用_同一底层实例_。这确保使用相同标识符的所有调用对 `PrivateNamespace` 具有一致的共享访问。

```java
// 创建或检索私有命名空间
PrivateNamespace ns = new PrivateNamespace("myApp", "sharedState");
```

您可以在创建之前检查是否存在：

```java
if (PrivateNamespace.isPresent("myApp.sharedState")) {
  PrivateNamespace ns = PrivateNamespace.ofExisting("myApp.sharedState");
}
```

:::tip 命名指南
命名 `PrivateNamespace` 时，请遵循以下规则：

- 两部分都必须非空。
- 每个部分必须以字母开头。
- 仅允许可打印字符。
- 不允许有空格。

示例：
- ✓ mycrm.sessionData
- ✓ acme.analytics
- X shared.data（太通用，可能冲突）
:::

### `Group` 和 `Global` 命名空间 {#group-and-global-namespaces}

除了 PrivateNamespace，webforJ 还提供另外两种类型的命名空间，用于更广泛的共享上下文。当状态需要在单个会话或线程组之外持久化时，这些都是有用的。

- **全局命名空间**：在所有服务器线程（JVM范围内）可访问。
- **组命名空间**：在来自相同父线程的线程之间共享。

```java
// 全局共享状态，应用程序范围内可访问
GlobalNamespace globalNs = new GlobalNamespace();
globalNs.put("globalTheme", "dark");

// 特定于组的状态，仅限于共享相同父线程的线程
GroupNamespace groupNs = new GroupNamespace();
groupNs.put("localCache", new HashMap<>());
```

## 处理值 {#working-with-values}

命名空间提供了一种一致的接口，以通过键值对管理共享数据。这包括设置、检索、移除值、同步访问和实时观察变化。

### 设置和移除值 {#setting-and-removing-values}

使用 `put()` 在特定键下存储一个值。如果该键当前被锁定，该方法会等到锁被释放或超时过期。

```java
// 最多等待20毫秒（默认）以设置值
ns.put("username", "admin");

// 指定自定义超时（毫秒）
ns.put("config", configObject, 100);
```

要从命名空间中移除一个键：

```java
ns.remove("username");
```

如果目标键被锁定，则 `put()` 和 `remove()` 都是阻塞操作。如果在超时到期之前锁未释放，则会抛出 `NamespaceLockedException`。

对于只需覆盖值的安全并发更新，使用 `atomicPut()`。它在一步中锁定键、写入值并释放锁：

```java
ns.atomicPut("counter", 42);
```

这样可以防止竞争条件，并避免在简单更新场景中手动锁定。

### 获取值 {#getting-values}

要检索一个值，请使用 `get()`：

```java
Object value = ns.get("username");
```

如果该键不存在，则会抛出 `NoSuchElementException`。为了避免异常，可以使用 `getOrDefault()`：

```java
Object value = ns.getOrDefault("username", "guest");
```

要检查一个键是否定义：

```java
if (ns.contains("username")) {
  // 键存在
}
```

如果您希望在缺少时延迟初始化值，请使用 `computeIfAbsent()`：

```java
Object token = ns.computeIfAbsent("authToken", key -> generateToken());
```

这在一次创建并重复使用的共享值的场景中（如会话令牌、配置块或缓存数据）非常有用。

### 手动锁定 {#manual-locking}

如果您需要对同一键执行多个操作或在多个键之间进行协调，请使用手动锁定。

```java
ns.setLock("flag", 500); // 最多等待500毫秒以获取锁

// 临界区开始
Object existing = ns.get("flag");
ns.put("flag", "in-progress");
// 临界区结束

ns.removeLock("flag");
```

当必须在读写之间以原子方式执行一系列操作时，请使用此模式。始终确保释放锁，以避免阻塞其他线程。

### 监听变化 {#listening-for-changes}

命名空间支持事件监听器，使您能够对值的访问或更改作出反应。这在以下场景中非常有用：

- 记录或审计对敏感键的访问
- 当配置值发生变化时触发更新
- 在多用户应用中监控共享状态的变化

#### 可用监听器方法 {#available-listener-methods}

| 方法                       | 触发                                 | 作用域              |
|----------------------------|--------------------------------------|---------------------|
| `onAccess`                 | 任何键被读取                         | 整个命名空间       |
| `onChange`                 | 任何键被修改                         | 整个命名空间       |
| `onKeyAccess("key")`      | 特定键被读取                        | 每个键             |
| `onKeyChange("key")`      | 特定键被修改                        | 每个键             |

每个监听器接收一个事件对象，其中包含：
- 键名称
- 旧值
- 新值
- 命名空间的引用

#### 示例：响应任何键的改变 {#example-respond-to-any-key-change}

```java
ns.onChange(event -> {
  System.out.println("键已更改: " + event.getVariableName());
  System.out.println("旧值: " + event.getOldValue());
  System.out.println("新值: " + event.getNewValue());
});
```

#### 示例：跟踪对特定键的访问 {#example-track-access-to-a-specific-key}

```java
ns.onKeyAccess("sessionToken", event -> {
  System.out.println("令牌被访问: " + event.getNewValue());
});
```

监听器返回一个 `ListenerRegistration` 对象，您可以使用它稍后注销监听器：

```java
ListenerRegistration<NamespaceKeyChangeEvent> reg = ns.onKeyChange("status", event -> {
  // 逻辑
});
reg.remove();
```

## 示例：在井字棋中共享游戏状态 {#example-sharing-game-state-in-tic-tac-toe}

[webforJ井字棋演示](https://github.com/webforj/webforj-tictactoe)提供了一个简单的双人游戏，用户之间共享回合。该项目演示了如何使用 `Namespace` 协调状态，而无需依赖数据库或API等外部工具。

在此示例中，共享的Java游戏对象被存储在 `PrivateNamespace` 中，允许多个客户端与相同的游戏逻辑进行交互。命名空间作为游戏状态的中央容器，确保：

- 两名玩家看到一致的棋盘更新
- 回合被同步
- 游戏逻辑跨会话共享

不需要外部服务（如REST或WebSockets）。所有协调都是通过命名空间完成的，突显了它们在实时管理共享状态方面的能力，基础设施需求极少。

探索代码：[webforj/webforj-tictactoe](https://github.com/webforj/webforj-tictactoe)

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tic-tac-toe.mp4" type="video/mp4"/>
  </video>
</div>
