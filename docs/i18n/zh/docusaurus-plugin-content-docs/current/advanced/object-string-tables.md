---
title: Object and String Tables
sidebar_position: 35
_i18n_hash: a20240ac42fa56a5a7044aaeb969faa7
---
`ObjectTable` 和 `StringTable` 提供了在 webforJ 环境中对共享数据的静态访问。它们可以在应用程序的任何位置访问，并且有不同的用途：

- `ObjectTable`：用于在应用程序中存储和检索 Java 对象。
- `StringTable`：用于处理持久的键值字符串对，通常用于配置或环境样式的数据。

这些表在环境级别可用，不需要实例管理。

## `ObjectTable` {#objecttable}

`ObjectTable` 是一个全局可访问的键值映射，用于存储任何 Java 对象。它提供了对共享状态的简单访问，而无需实例化或配置任何东西。只有一个 ObjectTable 实例，当应用程序被刷新或终止时，它会被清除。它在需要在多个组件或上下文中提供数据而不维护引用链的场景中非常有用。

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

`StringTable` 提供了对全局字符串变量的静态访问。它是持久的，并且作用于当前应用程序。可以通过编程方式修改或通过环境配置注入值。这个机制特别适合存储必须在应用程序范围内访问但不需要携带复杂数据的配置值、标志和设置。

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
