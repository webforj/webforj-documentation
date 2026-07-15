---
title: Upgrade to 26.00
description: Upgrade from 25.00 to 26.00
slug: /upgrading/webforj-26.00
pagination_next: null
sidebar_position: 1
_i18n_hash: 3b9827a67a81e207508d7db72a650b64
---
此文档作为将webforJ应用程序从25.00升级到26.00的指南。
以下是现有应用程序继续顺利运行所需的更改。
如往常一样，请参阅[GitHub发布概述](https://github.com/webforj/webforj/releases)，以获取更全面的版本之间更改列表。

<!-- INTRO_END -->

<AutomatedUpgradeTip />

## POM文件更改 {#pom-file-changes}

### Java 21和25 {#java-21-and-25}

webforJ 25.12是与Java 17兼容的最后一个版本。
从webforJ 26.00开始，您需要使用Java 21或Java 25的版本，具体取决于您的设置。

请按照[先决条件](/docs/introduction/prerequisites)中列出的要求安装所需的Java版本，然后更新您的pom.xml文件：

```xml {3-4}
<properties>
  ....
  <maven.compiler.target>21</maven.compiler.target>
  <maven.compiler.source>21</maven.compiler.source>
</properties>
```

### Maven存储库URL {#maven-repository-url}

快照工件的托管位置已更改。在项目的pom.xml文件中，您需要从[中央门户](https://central.sonatype.com/)下载依赖项。

**之前:**
```xml
<repositories>
  <repository>
    <id>snapshots-repo</id>
    <url>https://s01.oss.sonatype.org/content/repositories/snapshots</url>
    ....
  </repository>
</repositories>
```

**之后:**
```xml {3-5}
<repositories>
  <repository>
    <name>中央门户快照</name>
    <id>central-portal-snapshots</id>
    <url>https://central.sonatype.com/repository/maven-snapshots/</url>
    ....
  </repository>
</repositories>
```

### Spring Boot升级 {#spring-boot-upgrade}

webforJ 25.12是使用Spring Boot 3.x的最后一个版本。
从webforJ 26.00开始，您的项目需要使用Spring Boot 4.x。

```xml {4}
<parent>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-parent</artifactId>
  <version>4.0.5</version>
</parent>
```

:::tip 删除Tomcat版本的覆盖
使用Spring Boot 4.x，现在将Tomcat 11.x作为依赖项包含，因此您可以删除任何项目特定的Tomcat版本覆盖。
:::

## 表API更改 {#table-api-changes}

### `IconRenderer`基于字符串的构造函数 {#iconrenderer- string-based-constructors}

在26.00中，以下基于字符串的构造函数被移除；请使用基于`IconDefinition`的构造函数：

| v25 | v26 |
|---|---|
| `IconRenderer(String name, String pool, EventListener)` | `IconRenderer(IconDefinition, EventListener)` |
| `IconRenderer(String name, String pool)` | `IconRenderer(IconDefinition)` |
| `IconRenderer(String name, EventListener)` | `IconRenderer(IconDefinition,  EventListener)` |
| `IconRenderer(String name)` | `IconRenderer(IconDefinition)` |

### 已弃用的选择方法 {#deprecated-selection-methods}

从webforJ 26.00开始，您应根据项目的键选择`Table`中的项目，而不是根据索引选择。您可以使用`setKeyProvider()`方法为表中的项目提供自定义键。

| v25 | v26 |
|---|---|
| `selectIndex(int...)` | `select(items)`或`selectKey(keys)` |
| `deselectIndex(int...)` | `deselect(items)`，`deselectKey(keys)`或`deselectAll()` |
| `getSelectedIndex()` | `getSelected()`或`getSelectedItem()` |
| `getSelectedIndices()` | `getSelectedItems()` |

### 选择事件 {#selection-events}

为了进一步强化如何在`Table`中选择项目的变化，`TableItemSelectionChange`不再实现`SelectEvent`。

| v25 | v26 |
|---|---|
| `event.getSelectedIndex()` | `event.getSelectedItem()` |
| `event.getSelectedIndices()` | `event.getSelectedItems()` |

## 不支持的Webswing启动选项 {#unsupported-webswing-bootstrap-options}

以下`WebswingOptions`方法在26.00中被弃用并删除，因为它们不再受到Webswing API的支持。

- `getAutoReconnect()` / `setAutoReconnect(Integer)`
- `isDisableLogout()` / `setDisableLogout(boolean)`
- `isDisableLogin()` / `setDisableLogin(boolean)`
- `isSyncClipboard()` / `setSyncClipboard(boolean)`
- `getJavaCallTimeout()` / `setJavaCallTimeout(int)`
- `getPingParams()` / `setPingParams(PingParams)`

`PingParams`类也已弃用。使用这些方法或`PingParams`类的用户应改为使用Webswing管理控制台直接配置选项。

## `Repository`的筛选器 {#filters-for-repository}

在webforJ 26.00中，`RetrievalCriteria`和`RetrievalBuilder`接口被移除。您可以使用`RepositoryCriteria<T, F>`、`CollectionRepository`（适用于简单筛选）或[`QueryableRepository`](/docs/advanced/repository/querying-data)（适用于更高级的过滤类型、排序和分页）来替代使用通用的`Repository`接口。

**之前:**
```java
private String searchTerm = "";

Repository<CustomerRecord> repository = new Repository<>();

repository.setFilter((CustomerRecord r) -> {
  String title = r.getTitle();
  return title.toLowerCase().contains(this.searchTerm);
  });
```

**之后:**
```java {3,5}
private String searchTerm = "";

CollectionRepository<CustomerRecord> repository = new CollectionRepository<>();

repository.setBaseFilter((CustomerRecord r) -> {
  String title = r.getTitle();
  return title.toLowerCase().contains(this.searchTerm);
  });
```

### 已弃用的仓库方法 {#deprecated-repository-methods}

使用下表查看已弃用的仓库方法以及今后应该使用的方法。

| v25 | v26 |
|---|---|
| `setFilter(...)` | `setBaseFilter(...)` |
| `getFilter()` | `getBaseFilter()` |
| `getOrderBy()` | `getOrderCriteriaList()` |
| `setOrderBy(Comparator<T>)` | `getOrderCriteriaList().add(new OrderCriteria<>(keyExtractor, direction))` |
| `findOneBy(...)` | `findBy(...)`然后`.findFirst()` |
| `findBy(RetrievalCriteria<T>)` | `findBy(RepositoryCriteria<T, F>)` |
| `size(RetrievalCriteria<T>)` | `size(RepositoryCriteria<T, F>)` |
| `getIndex(T)` | `find(key)`或`findBy(criteria)` |
| `findByIndex(int)` | `find(key)`或`findBy(criteria)` |

## 移除`WebforjBBjBridge` {#removal-of-webforjbbjbridge}

从webforJ 25.11开始，WebforjBBjBridge及其所有API被移除。webforJ现在直接使用Java API进行通信并访问所需的BBj API。

## 设计系统更改（DWC 26） {#design-system-changes-dwc-26}

webforJ 26.00随DWC设计系统的第26版发布。此更新是增量的，而不是完全重写：大多数v25 CSS变量仍然可用，公共令牌API得到保留，现有的自定义仍然可以无变化地工作。

本节列出了您可能需要采取行动的重大变化。有关概念概述，包括新颜色引擎的外观、`--dwc-dark-mode`的传播方式、为什么去掉了涟漪效果以及按区域的机制，请参见[DWC 26设计系统](/docs/upgrading/webforj-26.00/design-system)。

### 快速判决 {#design-system-quick-verdict}

| 场景 | 预期效果 |
|---|---|
| 使用默认样式 | 视觉刷新。默认调色板色调经过调节（主要颜色从`h: 211 / s: 100%`移动到`h: 223 / s: 91%`），阴影看起来更有层次，组件感觉更圆润。无需代码更改。 |
| 覆盖`--dwc-color-{name}-h`和`-s` | 仍然可以使用。HSL种子路径得以保留。 |
| 覆盖单个调色板步骤（例如`--dwc-color-primary-40`） | 步骤编号可能会解析为不同的颜色。请参考[颜色调色板机制](/docs/upgrading/webforj-26.00/design-system#the-color-system)。 |
| 依赖于`--dwc-color-{name}-c` | 删除。现在每种阴影的浅色/深色文本切换是自动计算的。 |
| 引用命名的字体大小令牌（`--dwc-font-size-m`、`-l`等） | 规模向下移动一个桶。`m`现在是`14px`而不是`16px`。请参见[排版](#design-system-typography)。 |
| 使用`--dwc-font-weight-semibold`来获取`500`权重 | `semibold`现在是`600`。请切换到新的`--dwc-font-weight-medium`以获取`500`。 |
| 使用`--dwc-focus-ring-width`在可聚焦元素周围保留填充 | 环形现在有间隙。添加`--dwc-focus-ring-gap`。请参见[焦点环](#design-system-focus-ring)。 |
| 自定义按钮悬停/涟漪效果 | 涟漪已被删除。按下反馈现在是小幅缩小。 |

### `--dwc-color-{name}-c`被移除 {#design-system-c-removed}

如果您有任何`--dwc-color-{name}-c`的覆盖，可以删除这些声明，因为它们没有效果。现在每种阴影的浅色/深色文本切换是自动计算的。

### `--dwc-color-{name}-alt`语义更改 {#design-system-alt-changed}

| 令牌 | v25 | v26 |
|---|---|---|
| `--dwc-color-{name}-alt` | 调色板步骤`95`（接近白色背景） | 种子在12%不透明度（半透明色调） |

如果您将`-alt`用作坚固的接近白色背景，现在会作为半透明色调覆盖。选择特定步骤（`--dwc-color-{name}-95`）或围绕半透明语义设计。

### `--dwc-border-color-{name}`语义更改 {#design-system-border-color-changed}

| 令牌 | v25 | v26 |
|---|---|---|
| `--dwc-border-color-{name}` | 根据变化设置为`var(--dwc-color-{name})`（饱和色调） | 在生成器中计算：基于种子的模式感知淡化色调 |

如果您的CSS是期待饱和主色的`--dwc-border-color-primary`，视觉效果现在是微妙的分隔色调。如果您特别想要饱和的外观，请直接切换到`--dwc-color-primary`。

### `--dwc-shadow-color`格式更改 {#design-system-shadow-color-changed}

|  | v25 | v26 |
|---|---|---|
| `--dwc-shadow-color` | HSL三元组（`h, s%, l%`） | 完整的OKLCH颜色 |

如果您的CSS使用了遗留三元组形式，如`hsla(var(--dwc-shadow-color), 0.07)`，请切换到完整的阴影令牌（`var(--dwc-shadow-m)`）或重写为`oklch(from var(--dwc-shadow-color) l c h / 0.07)`。

### 排版 {#design-system-typography}

字体比例进行了重新调整，因此桶名称向下移动了一个步骤：

| 令牌 | v25 | v26 |
|---|---|---|
| `--dwc-font-size-3xs` | `10px` | `10px` |
| `--dwc-font-size-2xs` | `12px` | `11px` |
| `--dwc-font-size-xs`  | `13px` | `12px` |
| `--dwc-font-size-s`   | `14px` | `13px` |
| `--dwc-font-size-m`   | `16px` | `14px` |
| `--dwc-font-size-l`   | `18px` | `16px` |
| `--dwc-font-size-xl`  | `22px` | `20px` |
| `--dwc-font-size-2xl` | `28px` | `26px` |
| `--dwc-font-size-3xl` | `36px` | `34px` |

默认的`--dwc-font-size`仍然解析为**14px**，只是通过`--dwc-font-size-m`（v26）而不是`--dwc-font-size-s`（v25）到达。如果您的CSS按名称引用字体大小令牌（例如`font-size: var(--dwc-font-size-l)`），可见结果在v26中会更小。向上移动一个桶以保留v25的大小。

字体权重增加了三个令牌（`thin`、`medium`、`black`），并且一个现有令牌发生了变化：

| 令牌 | v25 | v26 |
|---|---|---|
| `--dwc-font-weight-semibold` | `500` | `600` |
| `--dwc-font-weight-medium`   | （不存在） | `500` |

如果您使用`--dwc-font-weight-semibold`获取500权重的文本，请切换到`--dwc-font-weight-medium`。

### 边框半径 {#design-system-border-radius}

|  | v25 | v26 |
|---|---|---|
| 单位 | `em`（与父字体大小缩放） | `rem`（与根字体大小缩放） |
| 默认`--dwc-border-radius` | `--dwc-border-radius-s`（`4px`） | `--dwc-border-radius-seed`（`8px`） |
| 可用步骤 | 高达`2xl` | 新增`3xl`、`4xl` |

组件在默认情况下感觉更圆润。如果嵌入较大文本的组件以前通过`em`继承了更大的半径，则现在不再发生这种缩放，半径现已锚定到根。如果您想要回v25的默认大小，请将种子减半：

```css
:root {
  --dwc-border-radius-seed: 0.25rem; /* 4px，将整个比例减半 */
}
```

### 焦点环 {#design-system-focus-ring}

焦点环现在使用双环形模式：首先是小表面颜色的间隙，然后是有色环。

| 变量 | v25 | v26 |
|---|---|---|
| `--dwc-focus-ring-width` | `3px` | `2px` |
| `--dwc-focus-ring-a`     | `0.4` | `0.75` |
| `--dwc-focus-ring-gap`   | （没有） | `2px` |
| `--dwc-focus-ring-l`     | `45%` | （移除，更亮度根据模式计算） |

如果您通过`padding: var(--dwc-focus-ring-width)`保留可聚焦元素周围的空间，请在填充中添加间隙，以便新环有足够的空间呈现：

```css
/* v25 */
dwc-button { padding: var(--dwc-focus-ring-width); }

/* v26 */
dwc-button {
  padding: calc(var(--dwc-focus-ring-width) + var(--dwc-focus-ring-gap));
}
```

### 涟漪效果已移除 {#design-system-ripples-removed}

材料风格的涟漪效果不再被任何DWC组件使用。任何可点击元素的新反馈是轻微的缩小：

```css
--dwc-scale-press: 0.97;      /* 标准3%的缩小 */
--dwc-scale-press-deep: 0.93; /* 按钮的深层7%的缩小 */
```

`ripple` SCSS混合和`--dwc-ripple-color` CSS变量仍然存在于构建中，但默认不会导入它们。如果您的组件选择了混合，则切换到按下缩放令牌以匹配新的感觉。

### 过渡持续时间重新平衡 {#design-system-transitions}

| 变量 | v25 | v26 |
|---|---|---|
| `--dwc-transition-slow`   | `500ms` | `300ms` |
| `--dwc-transition-medium` | `250ms` | `250ms` |
| `--dwc-transition-fast`   | `150ms` | `150ms` |
| `--dwc-transition-x-fast` | `50ms`  | `100ms` |

如果您依赖于特定的持续时间，请在`:root`中覆盖它。

### 实用升级清单 {#design-system-checklist}

1. 搜索`--dwc-color-*-c`并删除这些声明。
2. 搜索`hsla(var(--dwc-shadow-color)`并替换为阴影令牌（`var(--dwc-shadow-m)`）或重写为`oklch(from ...)`。
3. 搜索直接调色板步骤引用（`--dwc-color-{name}-{number}`）。如果有任何饲料深色模式特定样式，请切换到变化令牌（`--dwc-color-{name}`、`-dark`、`-light`）。
4. 搜索命名字体大小引用（`--dwc-font-size-m`、`-l`等）。如果您想要v25的大小，请向上移动一个桶。
5. 搜索`--dwc-font-weight-semibold`。如果您想要`500`，请切换到`--dwc-font-weight-medium`。
6. 如果您通过`--dwc-focus-ring-width`保留可聚焦元素周围的空间，请将`--dwc-focus-ring-gap`添加到填充中。
7. 打开应用程序，点击周围。大多数应用程序无需其他更改。
