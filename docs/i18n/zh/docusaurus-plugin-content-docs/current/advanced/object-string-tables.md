---
title: Object and String Tables
sidebar_position: 45
_i18n_hash: 2ec33737ccaf06670b4c1cd16369d858
---
`ObjectTable`、`SessionObjectTable` 和 `StringTable` 提供在 webforJ 环境中对共享数据的静态访问。它们可以在应用中的任何位置访问，并具有不同的用途：

- `ObjectTable`：用于跨应用存储和检索 Java 对象。
- `SessionObjectTable`：用于在 HTTP 会话范围内存储和检索 Java 对象。
- `StringTable`：用于处理持久性的键值字符串对，通常用于配置或环境风格的数据。

这些表在环境级别可用，不需要实例管理。

## `ObjectTable` {#objecttable}

`ObjectTable` 是一个全局可访问的键值映射，用于存储任何 Java 对象。它提供简单的访问共享状态，而无需实例化或配置任何内容。`ObjectTable` 只有一个实例，并在应用刷新或终止时被清空。它在需要在多个组件或上下文之间提供数据而无需维护引用链的场景中非常有用。

### 设置和检索对象 {#setting-and-retrieving-objects}

```java
ObjectTable.put("userInfo", new User("Alice", "admin"));
User user = (User) ObjectTable.get("userInfo");
```

### 检查存在性 {#checking-for-presence}

```java
if (ObjectTable.contains("userInfo")) {
  // 键存在
}
```

### 移除条目 {#removing-entries}

```java
ObjectTable.clear("userInfo");
```

### 表大小 {#table-size}

```java
int total = ObjectTable.size();
```

## `SessionObjectTable` <DocChip chip='since' label='25.03' /> {#sessionobjecttable}

`SessionObjectTable` 在 Jakarta Servlet 6.1+ 容器中运行时提供对 HTTP 会话属性的静态访问。与 `ObjectTable` 的应用范围不同，`SessionObjectTable` 在用户的 HTTP 会话中存储数据，使其在请求之间保持持久，但对每个用户会话是独特的。

它遵循与 `ObjectTable` 相同的 API 模式以保持一致性。

:::warning
存储在 `SessionObjectTable` 中的对象应实现 `Serializable` 以支持会话持久性、复制和在 servlet 容器中的被动化。
:::

:::warning 在 `BBjServices` 中的可用性
在版本 25.03 中，通过 BBjServices 运行时，此功能尚不可用。
:::

### 设置和检索会话对象 {#setting-and-retrieving-session-objects}

```java
// ShoppingCart 应实现 Serializable
SessionObjectTable.put("cart", new ShoppingCart());
ShoppingCart cart = (ShoppingCart) SessionObjectTable.get("cart");
```

### 检查存在性 {#checking-for-presence-session}

```java
if (SessionObjectTable.contains("cart")) {
  // 会话中有购物车
}
```

### 移除会话条目 {#removing-session-entries}

```java
SessionObjectTable.clear("cart");
```

### 会话表大小 {#session-table-size}

```java
int total = SessionObjectTable.size();
```

## `StringTable` {#stringtable}

`StringTable` 提供对全局字符串变量的静态访问。它是持久性的，作用于当前应用。可以通过编程方式修改或通过环境配置注入值。此机制特别适合存储必须在整个应用中可访问但不需要携带复杂数据的配置值、标志和设置。

### 获取和设置字符串值 {#getting-and-setting-string-values}

```java
StringTable.put("COMPANY", "Acme Corp");
String company = StringTable.get("COMPANY");
```

### 从配置中预配置的值 {#pre-configured-values-from-config}

您可以在 [`webforj.conf`](../configuration/properties#configuring-webforjconf) 文件中定义键：

```
webforj.stringTable = {
  COMPANY: 'Acme Corp'
}
```

然后在代码中访问它：

```java
String val = StringTable.get("COMPANY");
```

### 检查存在性 {#checking-for-presence-1}

```java
if (StringTable.contains("COMPANY")) {
  // 键已设置
}
```

### 清除键 {#clearing-a-key}

```java
StringTable.clear("COMPANY");
```
