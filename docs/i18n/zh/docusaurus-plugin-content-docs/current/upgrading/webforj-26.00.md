---
title: Upgrade to 26.00
description: Upgrade from 25.00 to 26.00
pagination_next: null
sidebar_class_name: new-content
_i18n_hash: 49ea3920d3dc3f1c76943e04f9b7e2c9
---
本文件作为将 webforJ 应用程序从 25.00 升级到 26.00 的指南。
以下是现有应用程序需要进行的更改，以便继续顺利运行。
如往常一样，请查看 [GitHub 发布概述](https://github.com/webforj/webforj/releases)，以获取版本之间更改的更全面列表。

<!-- INTRO_END -->

<AutomatedUpgradeTip />

## POM 文件更改 {#pom-file-changes}

### Java 21 和 25 {#java-21-and-25}

webforJ 25.12 是最后一个支持 Java 17 的版本。
从 webforJ 26.00 开始，您需要使用 Java 21 或 Java 25 的版本，具体取决于您的设置。

请按 [前提条件](/docs/introduction/prerequisites) 中列出的要求安装所需的 Java 版本，然后更新您的 pom.xml 文件：

```xml {3-4}
<properties>
  ....
  <maven.compiler.target>21</maven.compiler.target>
  <maven.compiler.source>21</maven.compiler.source>
</properties>
```

### Maven 存储库 URL {#maven-repository-url}

快照工件的托管位置已更改。在您项目的 pom.xml 文件中，请确保您的依赖关系是从 [Central Portal](https://central.sonatype.com/) 下载的。

**之前：**
```xml
<repositories>
  <repository>
    <id>snapshots-repo</id>
    <url>https://s01.oss.sonatype.org/content/repositories/snapshots</url>
    ....
  </repository>
</repositories>
```

**之后：**
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

### Spring Boot 升级 {#spring-boot-upgrade}

webforJ 25.12 是最后一个使用 Spring Boot 3.x 的版本。
从 webforJ 26.00 开始，您的项目需要使用 Spring Boot 4.x。

```xml {4}
<parent>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-parent</artifactId>
  <version>4.0.5</version>
</parent>
```

:::tip 移除对 Tomcat 版本的重写
在 Spring Boot 4.x 中，Tomcat 11.x 已作为依赖项包含，因此您可以删除对 Tomcat 版本的任何项目特定的重写。
:::

## 表格 API 更改 {#table-api-changes}

### `IconRenderer` 字符串构造函数 {#iconrenderer- string-based-constructors}

以下字符串构造函数在 26.00 中被移除；请使用基于 `IconDefinition` 的构造函数：

| v25 | v26 |
|---|---|
| `IconRenderer(String name, String pool, EventListener)` | `IconRenderer(IconDefinition, EventListener)` |
| `IconRenderer(String name, String pool)` | `IconRenderer(IconDefinition)` |
| `IconRenderer(String name, EventListener)` | `IconRenderer(IconDefinition,  EventListener)` |
| `IconRenderer(String name)` | `IconRenderer(IconDefinition)` |

### 已弃用的选择方法 {#deprecated-selection-methods}

从 webforJ 26.00 开始，不再根据索引选择 `Table` 中的项目，改为使用项目键选择 `Table` 中的项目。您可以使用 `setKeyProvider()` 方法为表中的项目提供自定义键。

| v25 | v26 |
|---|---|
| `selectIndex(int...)` | `select(items)` 或 `selectKey(keys)` |
| `deselectIndex(int...)` | `deselect(items)`、`deselectKey(keys)` 或 `deselectAll()` |
| `getSelectedIndex()` | `getSelected()` 或 `getSelectedItem()` |
| `getSelectedIndices()` | `getSelectedItems()` |

### 选择事件 {#selection-events}

为了进一步加强选择 `Table` 中项目的方式的转变，`TableItemSelectionChange` 不再实现 `SelectEvent`。

| v25 | v26 |
|---|---|
| `event.getSelectedIndex()` | `event.getSelectedItem()` |
| `event.getSelectedIndices()` | `event.getSelectedItems()` |

## 不支持的 Webswing 启动选项 {#unsupported-webswing-bootstrap-options}

以下 `WebswingOptions` 方法已被弃用并在 26.00 中移除，因为它们不再受到 Webswing API 的支持。

- `getAutoReconnect()` / `setAutoReconnect(Integer)`
- `isDisableLogout()` / `setDisableLogout(boolean)`
- `isDisableLogin()` / `setDisableLogin(boolean)`
- `isSyncClipboard()` / `setSyncClipboard(boolean)`
- `getJavaCallTimeout()` / `setJavaCallTimeout(int)`
- `getPingParams()` / `setPingParams(PingParams)`

`PingParams` 类也已弃用。那些使用这些方法或 `PingParams` 类的用户应改用 Webswing 管理控制台直接配置选项。

## `Repository` 的筛选器 {#filters-for-repository}

在 webforJ 26.00 中，`RetrievalCriteria` 和 `RetrievalBuilder` 接口已移除。您可以使用 `RepositoryCriteria<T, F>`、`CollectionRepository` 进行简单筛选，或使用 [`QueryableRepository`](/docs/advanced/repository/querying-data) 进行更高级的过滤类型、排序和分页。

**之前：**
```java
private String searchTerm = "";

Repository<CustomerRecord> repository = new Repository<>();

repository.setFilter((CustomerRecord r) -> {
  String title = r.getTitle();
  return title.toLowerCase().contains(this.searchTerm);
  });
```

**之后：**
```java {3,5}
private String searchTerm = "";

CollectionRepository<CustomerRecord> repository = new CollectionRepository<>();

repository.setBaseFilter((CustomerRecord r) -> {
  String title = r.getTitle();
  return title.toLowerCase().contains(this.searchTerm);
  });
```

### 已弃用的存储库方法

请查看以下表格以了解已弃用的存储库方法以及今后应使用的方法。

| v25 | v26 |
|---|---|
| `setFilter(...)` | `setBaseFilter(...)` |
| `getFilter()` | `getBaseFilter()` |
| `getOrderBy()` | `getOrderCriteriaList()` |
| `setOrderBy(Comparator<T>)` | `getOrderCriteriaList().add(new OrderCriteria<>(keyExtractor, direction))` |
| `findOneBy(...)` | `findBy(...)` 然后 `.findFirst()` |
| `findBy(RetrievalCriteria<T>)` | `findBy(RepositoryCriteria<T, F>)` |
| `size(RetrievalCriteria<T>)` | `size(RepositoryCriteria<T, F>)` |
| `getIndex(T)` | `find(key)` 或 `findBy(criteria)` |
| `findByIndex(int)` | `find(key)` 或 `findBy(criteria)` |

## 移除 `WebforjBBjBridge` {#removal-of-webforjbbjbridge`}

从 webforJ 25.11 开始，WebforjBBjBridge 及其所有 API 已被移除。webforJ 现在使用直接的 Java API 与任何所需的 BBj API 进行通信和访问。
