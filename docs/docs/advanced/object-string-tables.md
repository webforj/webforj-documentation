---
title: Object and String Tables
sidebar_position: 45
---

The `ObjectTable`, `SessionObjectTable`, and `StringTable` provide static access to shared data in a webforJ environment. All are accessible from anywhere in your app and serve different purposes:

- `ObjectTable`: For storing and retrieving Java objects across your app.
- `SessionObjectTable`: For storing and retrieving Java objects in HTTP session scope.
- `StringTable`: For working with persistent key-value string pairs, often used for configuration or environment-style data.

These tables are available at the environment level and don't require instance management.

## `ObjectTable` {#objecttable}

`ObjectTable` is a globally accessible key-value map for storing any Java object. It provides simple access to shared state without the need to instantiate or configure anything. There is only one instance of ObjectTable and it's cleared when the app is refreshed or terminated.
it's useful for scenarios where you need to make data available across multiple components or contexts without maintaining a reference chain.


### Setting and retrieving objects {#setting-and-retrieving-objects}

```java
ObjectTable.put("userInfo", new User("Alice", "admin"));
User user = (User) ObjectTable.get("userInfo");
```

### Checking for presence {#checking-for-presence}

```java
if (ObjectTable.contains("userInfo")) {
  // Key exists
}
```

### Removing entries {#removing-entries}

```java
ObjectTable.clear("userInfo");
```

### Table size {#table-size}

```java
int total = ObjectTable.size();
```

## `SessionObjectTable` <DocChip chip='since' label='25.03' /> {#sessionobjecttable}

`SessionObjectTable` provides static access to HTTP session attributes when running in a Jakarta Servlet 6.1+ container. Unlike `ObjectTable` which is app-scoped, `SessionObjectTable` stores data in the user's HTTP session, making it persist across requests but unique to each user session.

It follows the same API pattern as `ObjectTable` for consistency.

:::warning
Objects stored in `SessionObjectTable` should implement `Serializable` to support session persistence, replication, and passivation in servlet containers.
:::

:::warning Availability in `BBjServices`
This feature isn't yet available when running with BBjServices in version 25.03.
:::

### Setting and retrieving session objects {#setting-and-retrieving-session-objects}

```java
// ShoppingCart should implement Serializable
SessionObjectTable.put("cart", new ShoppingCart());
ShoppingCart cart = (ShoppingCart) SessionObjectTable.get("cart");
```

### Checking for presence {#checking-for-presence-session}

```java
if (SessionObjectTable.contains("cart")) {
  // Session has cart
}
```

### Removing session entries {#removing-session-entries}

```java
SessionObjectTable.clear("cart");
```

### Session table size {#session-table-size}

```java
int total = SessionObjectTable.size();
```

## `StringTable` {#stringtable}

`StringTable` provides static access to global string variables. it's persistent and scoped to the current app. Values can be programmatically modified or injected via environment configuration.
This mechanism is particularly useful for storing configuration values, flags, and settings that must be accessible app-wide but don't need to carry complex data.

### Getting and setting string values {#getting-and-setting-string-values}

```java
StringTable.put("COMPANY", "Acme Corp");
String company = StringTable.get("COMPANY");
```

### Pre-configured values from config {#pre-configured-values-from-config}

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

### Checking for presence {#checking-for-presence-1}

```java
if (StringTable.contains("COMPANY")) {
  // Key is set
}
```

### Clearing a key {#clearing-a-key}

```java
StringTable.clear("COMPANY");
```