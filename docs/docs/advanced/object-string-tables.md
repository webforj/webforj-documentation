---
title: Object and String Tables
sidebar_class_name: new-content
---

The `ObjectTable` and `StringTable` provide static access to shared data in a webforJ environment. Both are accessible from anywhere in your app and serve different purposes:

- `ObjectTable`: For storing and retrieving Java objects across your app.
- `StringTable`: For working with persistent key-value string pairs, often used for configuration or environment-style data.

These tables are available at the environment level and don't require instance management.

## `ObjectTable`

`ObjectTable` is a globally accessible key-value map for storing any Java object. It provides simple access to shared state without the need to instantiate or configure anything. There is only one instance of ObjectTable and it's cleared when the app is refreshed or terminated.
it's useful for scenarios where you need to make data available across multiple components or contexts without maintaining a reference chain.


### Setting and retrieving objects

```java
ObjectTable.put("userInfo", new User("Alice", "admin"));
User user = (User) ObjectTable.get("userInfo");
```

### Checking for presence

```java
if (ObjectTable.contains("userInfo")) {
  // Key exists
}
```

### Removing entries

```java
ObjectTable.clear("userInfo");
```

### Table size

```java
int total = ObjectTable.size();
```

## `StringTable`

`StringTable` provides static access to global string variables. it's persistent and scoped to the current app. Values can be programmatically modified or injected via environment configuration.
This mechanism is particularly useful for storing configuration values, flags, and settings that must be accessible app-wide but don't need to carry complex data.

### Getting and setting string values

```java
StringTable.put("COMPANY", "Acme Corp");
String company = StringTable.get("COMPANY");
```

### Pre-configured values from config

You can define keys in your [`webforj.conf`](../configuration/properties#configuring-webforjconf) file:

```
webforj.stringTable = {
  COMPANY: 'Acme Corp'
}
```

Then access it in code:

```java
String val = StringTable.get("COMPANY");
```

### Checking for presence

```java
if (StringTable.contains("COMPANY")) {
  // Key is set
}
```

### Clearing a key

```java
StringTable.clear("COMPANY");
```