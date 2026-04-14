---
title: Upgrade to 26.00
description: Upgrade from 25.00 to 26.00
pagination_next: null
sidebar_class_name: new-content
---

This documentation serves as a guide to upgrade webforJ apps from 25.00 to 26.00.
Here are the changes needed for existing apps to continue running smoothly.
As always, see the [GitHub release overview](https://github.com/webforj/webforj/releases) for a more comprehensive list of changes between releases.

## POM file changes {#pom-file-changes}

### Java 21 and 25 {#java-21-and-25}

webforJ 25.12 is the last version that works with Java 17.
Starting with webforJ 26.00, you need a Java version that’s either Java 21 or Java 25, depending on your setup.

Install the required Java version as listed in the [prerequisites](/docs/introduction/prerequisites), then update your pom.xml file:

```xml {3-4}
<properties>
  ....
  <maven.compiler.target>21</maven.compiler.target>
  <maven.compiler.source>21</maven.compiler.source>
</properties>
```

### Maven repository URL {#maven-repository-url}

The location where the snapshot artifacts are hosted has changed. In your project’s pom.xml file, have you downloaded your dependencies from the [Central Portal](https://central.sonatype.com/).

**Before:**
```xml
<repositories>
  <repository>
    <id>snapshots-repo</id>
    <url>https://s01.oss.sonatype.org/content/repositories/snapshots</url>
    ....
  </repository>
</repositories>
```

**After:**
```xml {3-5}
<repositories>
  <repository>
    <name>Central Portal Snapshots</name>
    <id>central-portal-snapshots</id>
    <url>https://central.sonatype.com/repository/maven-snapshots/</url>
    ....
  </repository>
</repositories>
```

### Spring Boot upgrade {#spring-boot-upgrade}

webforJ 25.12 is the last version that uses Spring Boot 3.x. 
Starting with webforJ 26.00, your project needs to use Spring Boot 4.x.

```xml {4}
<parent>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-parent</artifactId>
  <version>4.0.5</version>
</parent>
```

:::tip Removing overrides for the Tomcat version
With Spring Boot 4.x, Tomcat 11.x is now included as a dependency, so you can remove any project-specific override for the Tomcat version.
:::

## Table API changes {#table-api-changes}

### `IconRenderer` string-based constructors {#iconrenderer- string-based-constructors}

The following string-based constructors are removed in 26.00; use `IconDefinition`-based constructors instead:

| v25 | v26 |
|---|---|
| `IconRenderer(String name, String pool, EventListener)` | `IconRenderer(IconDefinition, EventListener)` |
| `IconRenderer(String name, String pool)` | `IconRenderer(IconDefinition)` |
| `IconRenderer(String name, EventListener)` | `IconRenderer(IconDefinition,  EventListener)` |
| `IconRenderer(String name)` | `IconRenderer(IconDefinition)` |

### Deprecated selection methods {#deprecated-selection-methods}

Starting in webforJ 26.00, instead of selecting items in a `Table` based on indices, select items in a Table using the item key. You can use the `setKeyProvider()` method to provide custom keys for the items in the table.

| v25 | v26 |
|---|---|
| `selectIndex(int...)` | `select(items)` or `selectKey(keys)` |
| `deselectIndex(int...)` | `deselect(items)`, `deselectKey(keys)` or `deselectAll()` |
| `getSelectedIndex()` | `getSelected()` or `getSelectedItem()` |
| `getSelectedIndices()` | `getSelectedItems()` |

### Selection events {#selection-events}

To further reinforce the shift in how to select items in a `Table`, `TableItemSelectionChange` no longer implements `SelectEvent`.

| v25 | v26 |
|---|---|
| `event.getSelectedIndex()` | `event.getSelectedItem()` |
| `event.getSelectedIndices()` | `event.getSelectedItems()` |

## Unsupported Webswing bootstrap options {#unsupported-webswing-bootstrap-options}

The following `WebswingOptions` methods are deprecated and removed in 26.00 because they're no longer supported by the Webswing API.

- `getAutoReconnect()` / `setAutoReconnect(Integer)`
- `isDisableLogout()` / `setDisableLogout(boolean)`
- `isDisableLogin()` / `setDisableLogin(boolean)`
- `isSyncClipboard()` / `setSyncClipboard(boolean)`
- `getJavaCallTimeout()` / `setJavaCallTimeout(int)`
- `getPingParams()` / `setPingParams(PingParams)`

The `PingParams` class is also deprecated. Those who were using these methods or the `PingParams` class should instead use the Webswing Admin Console to directly configure the options.

## Filters for `Repository` {#filters-for-repository}

The `RetrievalCriteria` and `RetrievalBuilder` interfaces are removed in webforJ 26.00. Instead of using the generic `Repository` interface, use either `RepositoryCriteria<T, F>`, `CollectionRepository` for simple filters, or [`QueryableRepository`](/docs/advanced/repository/querying-data) for more advanced filtering types, sorting, and pagination.

**Before:**
```java
private String searchTerm = "";

Repository<CustomerRecord> repository = new Repository<>();

repository.setFilter((CustomerRecord r) -> {
  String title = r.getTitle();
  return title.toLowerCase().contains(this.searchTerm);
  });
```

**After:**
```java {3,5}
private String searchTerm = "";

CollectionRepository<CustomerRecord> repository = new CollectionRepository<>();

repository.setBaseFilter((CustomerRecord r) -> {
  String title = r.getTitle();
  return title.toLowerCase().contains(this.searchTerm);
  });
```

### Deprecated repository methods

Use the following table to see the deprecated repository methods and what methods to use going forward.

| v25 | v26 |
|---|---|
| `setFilter(...)` | `setBaseFilter(...)` |
| `getFilter()` | `getBaseFilter()` |
| `getOrderBy()` | `getOrderCriteriaList()` |
| `setOrderBy(Comparator<T>)` | `getOrderCriteriaList().add(new OrderCriteria<>(keyExtractor, direction))` |
| `findOneBy(...)` | `findBy(...)` then `.findFirst()` |
| `findBy(RetrievalCriteria<T>)` | `findBy(RepositoryCriteria<T, F>)` |
| `size(RetrievalCriteria<T>)` | `size(RepositoryCriteria<T, F>)` |
| `getIndex(T)` | `find(key)` or `findBy(criteria)` |
| `findByIndex(int)` | `find(key)` or `findBy(criteria)` |

## Removal of `WebforjBBjBridge` {#removal-of-webforjbbjbridge`}

Starting with webforJ 25.11, WebforjBBjBridge and all of its APIs have been removed. Instead of accessing the bridge, webforJ now uses the direct Java API to communicate with and access any required BBj APIs.
