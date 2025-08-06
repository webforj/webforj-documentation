---
title: Namespaces
sidebar_position: 30
_i18n_hash: 7e34cfb824d0e1e4637bd40f4f1133cc
---
<DocChip chip='since' label='24.22' />
<JavadocLink type="foundation" location="com/webforj/environment/namespace/Namespace" top='true'/>

webforJ中的命名空间提供了一种在Web应用程序的不同范围之间存储和检索共享数据的机制。它们能够在不依赖于传统存储技术（如会话属性或静态字段）的情况下实现组件间和跨会话的数据通信。这种抽象允许开发人员以一种受控、线程安全的方式封装并访问状态。命名空间非常适合构建多用户协作工具或仅仅保持一致的全局设置，并让您能够安全高效地协调数据。

## 什么是命名空间？ {#whats-a-namespace}

命名空间是一个命名的容器，用于存储键值对。这些值可以根据您使用的命名空间类型在应用的不同部分之间访问和修改。您可以将其视为一个线程安全、分布式的映射，具有内置的事件处理和锁定机制。

### 何时使用命名空间 {#when-to-use-namespaces}

在以下情况下使用命名空间：

- 您需要在用户会话或应用组件之间共享值。
- 您希望通过监听器对值的变化做出反应。
- 您需要对关键区域进行细粒度锁定。
- 您需要在应用中高效地持久化和检索状态。

### 命名空间的类型 {#types-of-namespaces}

webforJ提供三种类型的命名空间：

| 类型         | 范围                                                                                                               | 典型用途                                   |
| ------------ | ------------------------------------------------------------------------------------------------------------------ | ---------------------------------------- |
| **私有**     | 在使用相同前缀和名称的客户端之间共享。当没有引用时，内存会自动释放。                                              | 相关用户会话之间的共享状态。                |
| **组**       | 由来自同一父线程的所有线程共享。                                                                                    | 在线程组内协调状态。                        |
| **全局**     | 在所有服务器线程间可访问（JVM范围）。内存会保留，直到显式移除键。                                              | 应用程序范围内的共享状态。                  |

:::tip 选择默认值 - 优先使用`PrivateNamespace`
如果不确定，请使用`PrivateNamespace`。它提供了相关会话之间安全、限定的共享，而不影响全局或服务器范围的状态。这使其成为大多数应用程序的可靠默认值。
:::

## 创建和使用命名空间 {#creating-and-using-a-namespace}

通过实例化可用类型之一创建命名空间。每种类型定义了数据共享的方式和位置。以下示例展示了如何创建一个命名空间并与其值进行交互。

### `Private`命名空间 {#private-namespace}

私有命名空间名称由两部分组成：

- **前缀**: 开发人员定义的标识符，应该在您的应用程序或模块中是唯一的，以避免冲突。
- **基本名称**: 您希望管理的共享上下文或数据的特定名称。

它们结合在一起使用以下格式形成完整的命名空间名称：

```text
前缀 + "." + 基本名称
```

例如，`"myApp.sharedState"`。

使用相同的前缀和基本名称创建的命名空间始终引用相同的底层实例。这确保了对所有使用相同标识符调用`PrivateNamespace`的调用提供一致的共享访问。

```java
// 创建或检索私有命名空间
PrivateNamespace ns = new PrivateNamespace("myApp", "sharedState");
```

您可以在创建之前检查存在性：

```java
if (PrivateNamespace.isPresent("myApp.sharedState")) {
  PrivateNamespace ns = PrivateNamespace.ofExisting("myApp.sharedState");
}
```

:::tip 命名指南
命名`PrivateNamespace`时，请遵循以下规则：

- 两部分都必须非空。
- 每一部分必须以字母开头。
- 仅允许可打印字符。
- 不允许空格。

示例：
- ✓ mycrm.sessionData
- ✓ acme.analytics
- X shared.data（过于通用，可能会冲突）
:::

### `Group`和`Global`命名空间 {#group-and-global-namespaces}

除了PrivateNamespace，webforJ还提供另外两种类型，以便于更广泛的共享上下文。当状态需要超出单个会话或线程组持久化时，这些类型很有用。

- **全局命名空间**: 在所有服务器线程之间可访问（JVM范围）。
- **组命名空间**: 在从同一父线程起源的线程之间共享。

```java
// 全局共享状态，在整个应用程序中可访问
GlobalNamespace globalNs = new GlobalNamespace();
globalNs.put("globalTheme", "dark");

// 特定于组的状态，限于共享公共父线程的线程
GroupNamespace groupNs = new GroupNamespace();
groupNs.put("localCache", new HashMap<>());
```

## 处理值 {#working-with-values}

命名空间通过键值对提供了管理共享数据的一致接口。这包括设置、检索、移除值、同步访问以及实时观察变化。

### 设置和移除值 {#setting-and-removing-values}

使用`put()`在特定键下存储值。如果当前键被锁定，该方法会等待直到锁被释放或超时。

```java
// 等待最多20毫秒（默认）以设置值
ns.put("username", "admin");

// 指定自定义超时（以毫秒为单位）
ns.put("config", configObject, 100);
```

要从命名空间中移除一个键：

```java
ns.remove("username");
```

如果目标键被锁定，则`put()`和`remove()`都将是阻塞操作。如果在锁被释放之前超时，将抛出`NamespaceLockedException`。

对于安全的并发更新，其中您只需要覆盖值，使用`atomicPut()`。它锁定键，写入值，并在一步中释放锁：

```java
ns.atomicPut("counter", 42);
```

这可以防止竞争条件，并避免在简单更新场景中手动加锁的需要。

### 获取值 {#getting-values}

要检索一个值，使用`get()`：

```java
Object value = ns.get("username");
```

如果该键不存在，将抛出`NoSuchElementException`。为了避免异常，使用`getOrDefault()`：

```java
Object value = ns.getOrDefault("username", "guest");
```

要检查一个键是否已定义：

```java
if (ns.contains("username")) {
  // 键存在
}
```

如果您希望仅在缺少时延迟初始化一个值，使用`computeIfAbsent()`：

```java
Object token = ns.computeIfAbsent("authToken", key -> generateToken());
```

这对于只创建一次并重复使用的共享值非常有用，例如会话令牌、配置块或缓存数据。

### 手动锁定 {#manual-locking}

如果您需要在同一键上执行多个操作或跨多个键进行协调，使用手动锁定。

```java
ns.setLock("flag", 500); // 等待最多500毫秒以获取锁

// 关键部分开始
Object existing = ns.get("flag");
ns.put("flag", "in-progress");
// 关键部分结束

ns.removeLock("flag");
```

在必须跨读写原子性执行操作序列时使用此模式。始终确保释放锁，以避免阻塞其他线程。

### 监听变化 {#listening-for-changes}

命名空间支持事件监听器，使您能够对值的访问或修改做出反应。这在以下场景中非常有用：

- 记录或审计对敏感键的访问
- 在配置值更改时触发更新
- 在多用户应用中监控共享状态变化

#### 可用的监听方法 {#available-listener-methods}

| 方法                      | 触发                    | 范围                |
|---------------------------|-------------------------|---------------------|
| `onAccess`                | 读取任何键              | 整个命名空间        |
| `onChange`                | 修改任何键              | 整个命名空间        |
| `onKeyAccess("key")`      | 读取特定键             | 每个键              |
| `onKeyChange("key")`      | 修改特定键             | 每个键              |

每个监听器接收一个事件对象，包含：
- 键名
- 旧值
- 新值
- 对命名空间的引用

#### 示例：响应任何键的变化 {#example-respond-to-any-key-change}

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
  System.out.println("令牌被访问: " + event.getNewValue());
});
```

监听器返回一个`ListenerRegistration`对象，您可以使用它在以后注销监听器：

```java
ListenerRegistration<NamespaceKeyChangeEvent> reg = ns.onKeyChange("status", event -> {
  // 逻辑
});
reg.remove();
```

## 示例：在井字棋中共享游戏状态 {#example-sharing-game-state-in-tic-tac-toe}

[webforJ井字棋演示](https://github.com/webforj/webforj-tictactoe)提供了一个简单的两人游戏，在用户之间共享回合。该项目演示了如何使用`Namespace`协调状态，而无需依赖于外部工具，如数据库或API。

在此示例中，一个共享的Java游戏对象存储在`PrivateNamespace`中，允许多个客户端与相同的游戏逻辑进行交互。命名空间作为游戏状态的中心容器，确保：

- 两个玩家看到一致的板块更新
- 回合同步
- 游戏逻辑在会话之间共享

不需要外部服务（如REST或WebSockets）。所有协调都通过命名空间完成，突显了它们在实时管理共享状态方面的能力，基础设施需求最低。

查看代码：[webforj/webforj-tictactoe](https://github.com/webforj/webforj-tictactoe)

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tic-tac-toe.mp4" type="video/mp4"/>
  </video>
</div>
