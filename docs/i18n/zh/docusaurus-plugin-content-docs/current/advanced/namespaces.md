---
title: Namespaces
sidebar_position: 40
description: >-
  Share thread-safe key-value state across sessions, thread groups, or the
  entire JVM using Private, Group, and Global namespaces.
_i18n_hash: fb5d7a0ef2a65790f0692612c07d9044
---
<DocChip chip='since' label='24.22' />
<JavadocLink type="foundation" location="com/webforj/environment/namespace/Namespace" top='true'/>

webforJ中的命名空间提供了一种机制，用于在Web应用程序的不同范围之间存储和检索共享数据。它们使组件之间和跨会话的数据通信成为可能，而无需依赖传统的存储技术，例如会话属性或静态字段。这种抽象使开发人员能够以受控、线程安全的方式封装和访问状态。命名空间非常适合构建多用户协作工具或简单地维护一致的全局设置，并让您安全高效地协调数据。

## 什么是命名空间？ {#whats-a-namespace}

命名空间是一个命名的容器，用于存储键值对。这些值可以根据所使用的命名空间类型在应用程序的不同部分进行访问和修改。可以将其视为一个线程安全的分布式映射，具有内置的事件处理和锁定机制。

### 何时使用命名空间 {#when-to-use-namespaces}

在以下情况下使用命名空间：

- 您需要在用户会话或应用程序组件之间共享值。
- 您希望通过监听器对值变化做出反应。
- 您需要对关键区域进行细粒度锁定。
- 您需要高效地在应用程序中持久化和检索状态。

### 命名空间的类型 {#types-of-namespaces}

webforJ提供了三种类型的命名空间：

| 类型        | 范围                                                                                                               | 典型用途                                 |
| ----------- | ------------------------------------------------------------------------------------------------------------------- | ------------------------------------------- |
| **私有**    | 在使用相同前缀和名称的客户端之间共享。当没有引用时，内存会自动释放。                                              | 相关用户会话之间的共享状态。              |
| **组**      | 在从同一父线程生成的所有线程之间共享。                                                                               | 在线程组内协调状态。                      |
| **全局**    | 在所有服务器线程（JVM范围）中可访问。内存将保留，直到显式移除键。                                                 | 应用程序范围的共享状态。                  |

:::tip 选择默认值 - 优先使用 `PrivateNamespace`
当不确定时，使用 `PrivateNamespace`。它提供了相关会话之间的安全、范围共享，而不会影响全局或服务器范围的状态。这使其成为大多数应用程序的可靠默认值。
:::

## 创建和使用命名空间 {#creating-and-using-a-namespace}

通过实例化可用类型之一来创建命名空间。每种类型定义了数据共享的方式和位置。以下示例演示了如何创建命名空间并与其值进行交互。

### `Private` 命名空间 {#private-namespace}

私有命名空间名称由两部分组成：

- **前缀**：开发人员定义的标识符，应该是唯一的，以避免冲突。
- **基本名称**：您想要管理的共享上下文或数据的特定名称。

它们结合在一起，形成完整的命名空间名称，格式为：

```text
prefix + "." + baseName
```

例如，`"myApp.sharedState"`。

使用相同前缀和基本名称创建的命名空间始终引用相同的基础实例。这确保了对所有使用相同标识符调用的 `PrivateNamespace` 进行一致的共享访问。

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
为 `PrivateNamespace` 命名时，请遵循以下规则：

- 两部分都必须非空。
- 每部分必须以字母开头。
- 仅允许打印字符。
- 不允许空格。

示例：
- ✓ mycrm.sessionData
- ✓ acme.analytics
- X shared.data（过于通用，可能会冲突）
:::

### `Group` 和 `Global` 命名空间 {#group-and-global-namespaces}

除了PrivateNamespace，webforJ还提供了两种用于更广泛共享上下文的类型。当状态需要超越单个会话或线程组持久化时，它们非常有用。

- **全局命名空间**：在所有服务器线程（JVM范围）中可访问。
- **组命名空间**：在源自同一父线程的线程之间共享。

```java
// 全局共享状态，可在整个应用程序范围内访问
GlobalNamespace globalNs = new GlobalNamespace();
globalNs.put("globalTheme", "dark");

// 特定于组的状态，仅限于共享共同父线程的线程
GroupNamespace groupNs = new GroupNamespace();
groupNs.put("localCache", new HashMap<>());
```

## 处理值 {#working-with-values}

命名空间提供了一致的接口，用于通过键值对管理共享数据。这包括设置、检索、移除值、同步访问和实时观察变化。

### 设置和移除值 {#setting-and-removing-values}

使用 `put()` 方法在特定键下存储一个值。如果当前键被锁定，该方法将等待直到锁释放或超时到期。

```java
// 最多等待20毫秒（默认）以设置值
ns.put("username", "admin");

// 指定毫秒为单位的自定义超时
ns.put("config", configObject, 100);
```

要从命名空间中移除一个键：

```java
ns.remove("username");
```

`put()` 和 `remove()` 在目标键被锁定时都是阻塞操作。如果在锁释放之前超时，抛出 `NamespaceLockedException`。

为了安全地进行并发更新，只需覆盖值，使用 `atomicPut()`。它在一个步骤中锁定键、写入值并释放锁：

```java
ns.atomicPut("counter", 42);
```

这可以防止竞态条件，并避免在简单更新场景中手动锁定。

### 获取值 {#getting-values}

要检索一个值，使用 `get()`：

```java
Object value = ns.get("username");
```

如果键不存在，这会抛出 `NoSuchElementException`。为了避免异常，可以使用 `getOrDefault()`：

```java
Object value = ns.getOrDefault("username", "guest");
```

要检查一个键是否已定义：

```java
if (ns.contains("username")) {
  // 键存在
}
```

如果您希望在值缺失时惰性地初始化值，使用 `computeIfAbsent()`：

```java
Object token = ns.computeIfAbsent("authToken", key -> generateToken());
```

这对那些被创建一次并重复使用的共享值很有用，例如会话令牌、配置块或缓存数据。

### 手动锁定 {#manual-locking}

如果您需要在同一键上执行多个操作或跨多个键进行协调，请使用手动锁定。

```java
ns.setLock("flag", 500); // 最多等待500毫秒以获取锁

// 临界区开始
Object existing = ns.get("flag");
ns.put("flag", "in-progress");
// 临界区结束

ns.removeLock("flag");
```

在必须跨读写原子性地执行操作序列时使用此模式。始终确保释放锁以避免阻塞其他线程。

### 监听变化 {#listening-for-changes}

命名空间支持事件监听器，允许您对值的访问或修改做出反应。这对于以下场景很有用：

- 记录或审核对敏感键的访问
- 当配置值改变时触发更新
- 在多用户应用中监控共享状态变化

#### 可用监听器方法 {#available-listener-methods}

| 方法                       | 触发                               | 范围              |
|---------------------------|------------------------------------|--------------------|
| `onAccess`                | 任何键被读取                       | 整个命名空间      |
| `onChange`                | 任何键被修改                       | 整个命名空间      |
| `onKeyAccess("key")`      | 特定键被读取                       | 每个键            |
| `onKeyChange("key")`      | 特定键被修改                       | 每个键            |

每个监听器接收一个事件对象，其中包含：
- 键名称
- 旧值
- 新值
- 对命名空间的引用

#### 示例：响应任何键变化 {#example-respond-to-any-key-change}

```java
ns.onChange(event -> {
  System.out.println("键已改变: " + event.getVariableName());
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

监听器返回一个 `ListenerRegistration` 对象，您可以稍后使用该对象注销监听器：

```java
ListenerRegistration<NamespaceKeyChangeEvent> reg = ns.onKeyChange("status", event -> {
  // 逻辑
});
reg.remove();
```

## 示例：在井字棋中共享游戏状态 {#example-sharing-game-state-in-tic-tac-toe}

[webforJ Tic-Tac-Toe演示](https://github.com/webforj/webforj-tictactoe)提供了一个简单的双人游戏，轮流在用户之间共享。该项目演示了如何使用`Namespace`来协调状态，而无需依赖数据库或API等外部工具。

在此示例中，共享的Java游戏对象存储在`PrivateNamespace`中，允许多个客户端与相同的游戏逻辑进行交互。命名空间作为游戏状态的中心容器，确保：

- 两名玩家都看到一致的棋盘更新
- 回合被同步
- 游戏逻辑跨会话共享

不需要外部服务（如REST或WebSockets）。所有协调都是通过命名空间完成的，突显了它们以最少的基础设施实时管理共享状态的能力。

探索代码：[webforj/webforj-tictactoe](https://github.com/webforj/webforj-tictactoe)

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tic-tac-toe.mp4" type="video/mp4"/>
  </video>
</div>
