---
title: Object and String Tables
sidebar_position: 35
_i18n_hash: aa2c014d8043f9ad53dfabcdc39844da
---
`ObjectTable` 和 `StringTable` 在 webforJ 环境中提供对共享数据的静态访问。这两个表在你的应用中的任何地方都可以访问，并且各自具有不同的用途：

- `ObjectTable`: 用于在应用中存储和检索 Java 对象。
- `StringTable`: 用于处理持久的键值字符串对，通常用于配置或环境风格的数据。

这些表在环境级别可用，无需实例管理。

## `ObjectTable` {#objecttable}

`ObjectTable` 是一个全局可访问的键值映射，用于存储任何 Java 对象。它提供简单的访问共享状态，而无需实例化或配置任何内容。只有一个 `ObjectTable` 实例，并且在应用刷新或终止时会被清除。它对于需要在多个组件或上下文之间共享数据的场景非常有用，而无需维护引用链。

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

## `StringTable` {#stringtable}

`StringTable` 提供对全局字符串变量的静态访问。它是持久的，并且作用域限定于当前应用。值可以通过程序修改或通过环境配置注入。这个机制特别适用于存储必须在整个应用中可访问但不需要携带复杂数据的配置值、标志和设置。

### 获取和设置字符串值 {#getting-and-setting-string-values}

```java
StringTable.put("COMPANY", "Acme Corp");
String company = StringTable.get("COMPANY");
```

### 从配置中预配置的值 {#pre-configured-values-from-config}

你可以在你的 [`webforj.conf`](../configuration/properties#configuring-webforjconf) 文件中定义键：

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
