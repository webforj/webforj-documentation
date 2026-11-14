---
title: 命名空间
sidebar_position: 40
description: >-
  Share thread-safe key-value state across sessions, thread groups, or the
  entire JVM using Private, Group, and Global namespaces.
_i18n_hash: f932a175d81df4147cae1335991db3d5
---
<DocChip chip='since' label='24.22' />
<JavadocLink type="foundation" location="com/webforj/environment/namespace/Namespace" top='true'/>

webforJ 中的命名空间提供了一种机制，可在 Web 应用的不同作用域之间存储和检索共享数据。它们支持组件间和跨会话的数据通信，而无需依赖会话属性或静态字段等传统存储技术。这种抽象让开发者能够以受控且线程安全的方式封装和访问状态。命名空间非常适合构建多用户协作工具，也可用于维护一致的全局设置，并让你安全、高效地协调数据。

## 什么是命名空间？ {#whats-a-namespace}

命名空间是一个用于存储键值对的具名容器。根据所用的命名空间类型，这些值可以在应用的不同部分访问和修改。可以把它看作一个线程安全的分布式映射，并内置了事件处理和锁定机制。

### 何时使用命名空间 {#when-to-use-namespaces}

在以下情况下使用命名空间：

- 需要在用户会话或应用组件之间共享值。
- 希望通过侦听器响应值的变化。
- 需要对关键代码段进行细粒度锁定。
- 需要在整个应用中高效地持久化和检索状态。

### 命名空间类型 {#types-of-namespaces}

webforJ 提供三种类型的命名空间：

| 类型        | 作用域                                                                                 | 典型用途               |
| ----------- | -------------------------------------------------------------------------------------- | ---------------------- |
| **Private** | 使用相同前缀和名称的客户端共享。当不再有引用时，内存会自动释放。                       | 相关用户会话之间共享状态。 |
| **Group**   | 由同一父线程派生的所有线程共享。                                                       | 协调线程组内的状态。    |
| **Global**  | 可由所有服务器线程访问（JVM 范围）。内存会一直保留，直到键被显式移除。                  | 应用范围内的共享状态。  |

:::tip 选择默认类型 - 优先使用 `PrivateNamespace`
如果不确定该选哪种类型，请使用 `PrivateNamespace`。它可以在相关会话之间安全地共享数据，并限定作用域，不会影响全局或服务器范围的状态。因此，它是大多数应用可靠的默认选择。
:::

## 创建和使用命名空间 {#creating-and-using-a-namespace}

通过实例化可用类型之一来创建命名空间。每种类型都定义了数据的共享方式和范围。以下示例演示如何创建命名空间并与其中的值交互。

### `Private` 命名空间 {#private-namespace}

私有命名空间名称由两部分组成：

- **前缀**：由开发者定义的标识符，应在应用或模块中保持唯一，以避免冲突。
- **基本名称**：要管理的共享上下文或数据的具体名称。

两者组合后，完整的命名空间名称格式如下：

```text
prefix + "." + baseName
```

例如，`"myApp.sharedState"`。

使用相同前缀和基本名称创建的命名空间始终指向_同一个底层实例_。因此，所有使用相同标识符调用 `PrivateNamespace` 的操作都能一致地访问共享数据。

```java
// 创建或获取私有命名空间
PrivateNamespace ns = new PrivateNamespace("myApp", "sharedState");
```

也可以在创建前检查命名空间是否存在：

```java
if (PrivateNamespace.isPresent("myApp.sharedState")) {
  PrivateNamespace ns = PrivateNamespace.ofExisting("myApp.sharedState");
}
```

:::tip 命名指南
为 `PrivateNamespace` 命名时，请遵循以下规则：

- 两部分都不能为空。
- 每部分都必须以字母开头。
- 只允许使用可打印字符。
- 不允许使用空白字符。

示例：
- ✓ mycrm.sessionData
- ✓ acme.analytics
- X shared.data（过于通用，可能发生冲突）
:::

### `Group` 和 `Global` 命名空间 {#group-and-global-namespaces}

除了 `PrivateNamespace`，webforJ 还提供另外两种适用于更广泛共享范围的类型。当状态需要超出单个会话或线程组的生命周期时，这些类型非常有用。

- **全局命名空间**：可由所有服务器线程访问（JVM 范围）。
- **组命名空间**：由同一父线程派生的线程共享。

```java
// 全局共享状态，可在整个应用中访问
GlobalNamespace globalNs = new GlobalNamespace();
globalNs.put("globalTheme", "dark");

// 组范围内的状态，仅限共享同一父线程的线程访问
GroupNamespace groupNs = new GroupNamespace();
groupNs.put("localCache", new HashMap<>());
```

## 使用值 {#working-with-values}

命名空间通过键值对提供了一致的共享数据管理接口，包括设置、检索和移除值、同步访问，以及实时观察变化。

### 设置和移除值 {#setting-and-removing-values}

使用 `put()` 将值存储在指定键下。如果该键当前已锁定，此方法会等待锁释放或超时。

```java
// 最多等待 20ms（默认值）来设置值
ns.put("username", "admin");

// 指定自定义超时时间（毫秒）
ns.put("config", configObject, 100);
```

要从命名空间中移除键：

```java
ns.remove("username");
```

如果目标键已锁定，`put()` 和 `remove()` 都会阻塞。如果锁释放前发生超时，则会抛出 `NamespaceLockedException`。

如果需要安全地并发更新，且只需覆盖值，请使用 `atomicPut()`。它会锁定键、写入值并一次性释放锁：

```java
ns.atomicPut("counter", 42);
```

这样可以避免竞态条件，在简单的更新场景中也无需手动加锁。

### 获取值 {#getting-values}

使用 `get()` 检索值：

```java
Object value = ns.get("username");
```

如果键不存在，此方法会抛出 `NoSuchElementException`。若要避免异常，请使用 `getOrDefault()`：

```java
Object value = ns.getOrDefault("username", "guest");
```

要检查是否已定义某个键：

```java
if (ns.contains("username")) {
  // 键存在
}
```

如果希望仅在值缺失时才延迟初始化，请使用 `computeIfAbsent()`：

```java
Object token = ns.computeIfAbsent("authToken", key -> generateToken());
```

这适用于只需创建一次并重复使用的共享值，例如会话令牌、配置块或缓存数据。

### 手动锁定 {#manual-locking}

如果需要对同一个键执行多个操作，或协调多个键之间的操作，请使用手动锁定。

```java
ns.setLock("flag", 500); // 最多等待 500ms 获取锁

// 临界区开始
Object existing = ns.get("flag");
ns.put("flag", "in-progress");
// 临界区结束

ns.removeLock("flag");
```

当一系列读写操作必须以原子方式执行时，请使用此模式。务必确保释放锁，以免阻塞其他线程。

### 侦听变化 {#listening-for-changes}

命名空间支持事件侦听器，可用于响应值的访问或修改。以下场景都适合使用侦听器：

- 记录或审计对敏感键的访问
- 在配置值变化时触发更新
- 监控多用户应用中的共享状态变化

#### 可用的侦听器方法 {#available-listener-methods}

| 方法                    | 触发条件              | 作用范围         |
|-------------------------|-----------------------|------------------|
| `onAccess`              | 读取任意键            | 整个命名空间     |
| `onChange`              | 修改任意键            | 整个命名空间     |
| `onKeyAccess("key")`    | 读取指定键            | 单个键           |
| `onKeyChange("key")`    | 修改指定键            | 单个键           |

每个侦听器都会接收一个事件对象，其中包含：

- 键名称
- 旧值
- 新值
- 对命名空间的引用

#### 示例：响应任意键的变化 {#example-respond-to-any-key-change}

```java
ns.onChange(event -> {
  System.out.println("键已更改：" + event.getVariableName());
  System.out.println("旧值：" + event.getOldValue());
  System.out.println("新值：" + event.getNewValue());
});
```

#### 示例：跟踪对指定键的访问 {#example-track-access-to-a-specific-key}

```java
ns.onKeyAccess("sessionToken", event -> {
  System.out.println("令牌已被访问：" + event.getNewValue());
});
```

侦听器会返回一个 `ListenerRegistration` 对象，可用于之后注销侦听器：

```java
ListenerRegistration<NamespaceKeyChangeEvent> reg = ns.onKeyChange("status", event -> {
  // 逻辑
});
reg.remove();
```

## 示例：在井字棋游戏中共享游戏状态 {#example-sharing-game-state-in-tic-tac-toe}

[webforJ 井字棋演示](https://github.com/webforj/webforj-tictactoe)展示了一个简单的双人游戏，双方玩家共享回合状态。该项目演示了如何使用 `Namespace` 协调状态，而无需依赖数据库或 API 等外部工具。

在此示例中，一个共享的 Java 游戏对象存储在 `PrivateNamespace` 中，让多个客户端可以与同一套游戏逻辑交互。命名空间充当游戏状态的中央容器，确保：

- 双方玩家看到一致的棋盘更新
- 回合得到同步
- 游戏逻辑在不同会话之间共享

无需 REST 或 WebSockets 等外部服务。所有协调都通过命名空间完成，充分展示了命名空间如何以最少的基础设施实时管理共享状态。

查看代码：[webforj/webforj-tictactoe](https://github.com/webforj/webforj-tictactoe)

<VideoPlayer video="namespaces/tic-tac-toe" width={1660} height={902} title="Tic tac toe" />
