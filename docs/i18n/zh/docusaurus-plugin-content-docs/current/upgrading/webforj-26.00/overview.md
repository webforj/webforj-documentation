---
title: Upgrade to 26.00
description: Upgrade from 25.00 to 26.00
slug: /upgrading/webforj-26.00
pagination_next: null
sidebar_class_name: new-content
sidebar_position: 1
_i18n_hash: e62ee79be86c51d62fe19d10af89cc1b
---
此文档作为将webforJ应用程序从25.00升级到26.00的指南。  
以下是现有应用程序为了继续顺利运行所需的更改。  
和往常一样，请查看[GitHub发布概述](https://github.com/webforj/webforj/releases)，获取更全面的版本更改列表。

<!-- INTRO_END -->

<AutomatedUpgradeTip />

## POM文件更改 {#pom-file-changes}

### Java 21和25 {#java-21-and-25}

webforJ 25.12是与Java 17兼容的最后一个版本。  
从webforJ 26.00开始，您需要使用Java 21或Java 25版本，具体取决于您的设置。

请安装[前提条件](/docs/introduction/prerequisites)中列出的所需Java版本，然后更新您的pom.xml文件：

```xml {3-4}
<properties>
  ....
  <maven.compiler.target>21</maven.compiler.target>
  <maven.compiler.source>21</maven.compiler.source>
</properties>
```

### Maven仓库URL {#maven-repository-url}

快照构件的托管位置已更改。在项目的pom.xml文件中，您需要从[Central Portal](https://central.sonatype.com/)下载依赖项。

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

:::tip 移除对Tomcat版本的覆盖
使用Spring Boot 4.x，Tomcat 11.x现在作为依赖项包含，因此您可以移除对Tomcat版本的任何项目特定覆盖。
:::

## 表API更改 {#table-api-changes}

### `IconRenderer`基于字符串的构造函数 {#iconrenderer-string-based-constructors}

26.00中移除了以下基于字符串的构造函数；请改用基于`IconDefinition`的构造函数：

| v25 | v26 |
|---|---|
| `IconRenderer(String name, String pool, EventListener)` | `IconRenderer(IconDefinition, EventListener)` |
| `IconRenderer(String name, String pool)` | `IconRenderer(IconDefinition)` |
| `IconRenderer(String name, EventListener)` | `IconRenderer(IconDefinition, EventListener)` |
| `IconRenderer(String name)` | `IconRenderer(IconDefinition)` |

### 已弃用选择方法 {#deprecated-selection-methods}

从webforJ 26.00开始，您可以通过项键在`Table`中选择项目，而不是基于索引选择。您可以使用`setKeyProvider()`方法为表中的项目提供自定义键。

| v25 | v26 |
|---|---|
| `selectIndex(int...)` | `select(items)`或`selectKey(keys)` |
| `deselectIndex(int...)` | `deselect(items)`，`deselectKey(keys)`或`deselectAll()` |
| `getSelectedIndex()` | `getSelected()`或`getSelectedItem()` |
| `getSelectedIndices()` | `getSelectedItems()` |

### 选择事件 {#selection-events}

为了进一步加强在`Table`中选择项目的方式的转变，`TableItemSelectionChange`不再实现`SelectEvent`。

| v25 | v26 |
|---|---|
| `event.getSelectedIndex()` | `event.getSelectedItem()` |
| `event.getSelectedIndices()` | `event.getSelectedItems()` |

## 不支持的Webswing引导选项 {#unsupported-webswing-bootstrap-options}

以下`WebswingOptions`方法在26.00中已被弃用并移除，因为它们不再被Webswing API支持。

- `getAutoReconnect()` / `setAutoReconnect(Integer)`
- `isDisableLogout()` / `setDisableLogout(boolean)`
- `isDisableLogin()` / `setDisableLogin(boolean)`
- `isSyncClipboard()` / `setSyncClipboard(boolean)`
- `getJavaCallTimeout()` / `setJavaCallTimeout(int)`
- `getPingParams()` / `setPingParams(PingParams)`

`PingParams`类也已被弃用。使用这些方法或`PingParams`类的用户应改为使用Webswing管理控制台直接配置选项。

## `Repository`的过滤器 {#filters-for-repository}

`RetrievalCriteria`和`RetrievalBuilder`接口在webforJ 26.00中被删除。  
请改用`RepositoryCriteria<T, F>`或`CollectionRepository`用于简单过滤，或者用于更高级的过滤类型、排序和分页的[`QueryableRepository`](/docs/advanced/repository/querying-data)。

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

### 已弃用的存储库方法 {#deprecated-repository-methods}

请查看以下表格以查看已弃用的存储库方法以及将来应使用的方法。

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

从webforJ 25.11开始，WebforjBBjBridge及其所有API已被移除。现在webforJ使用直接的Java API来与任何所需的BBj API进行通信和访问。

## 设计系统更改（DWC 26） {#design-system-changes-dwc-26}

webforJ 26.00随DWC设计系统的版本26一起发布。  
该更新是增量的，而不是完全重写：大多数v25 CSS变量仍然可用，公共令牌API得以保留，现有自定义内容在没有更改的情况下继续工作。

本节列出了您可能需要采取行动的重大更改。有关概念概述，包括新颜色引擎的外观，`--dwc-dark-mode`的传播方式，为什么会去除涟漪，以及每个区域的机制，请参见[DWC 26设计系统](/docs/upgrading/webforj-26.00/design-system)。

### 快速评估 {#design-system-quick-verdict}

| 场景 | 预期结果 |
|---|---|
| 使用默认样式 | 视觉刷新。默认调色板的色调进行了调整（主要从`h: 211 / s: 100%`移至`h: 223 / s: 91%`），阴影看起来更分层，组件感受到更圆滑。无需代码更改。 |
| 覆盖`--dwc-color-{name}-h`和`-s` | 仍然有效。HSL种子路径得以保留。 |
| 覆盖单个调色板步骤（例如`--dwc-color-primary-40`） | 步骤编号可能解析为不同颜色。有关更多信息，请参见[颜色调色板机制](/docs/upgrading/webforj-26.00/design-system#the-color-system)。 |
| 依赖于`--dwc-color-{name}-c` | 删除。现在每个色调的明暗文本翻转是自动计算的。 |
| 引用命名字体大小令牌（`--dwc-font-size-m`，`-l`等） | 规模向下移动了一个桶。`m`现在是`14px`而不是`16px`。有关排版see [Typography](#design-system-typography)。 |
| 使用`--dwc-font-weight-semibold`来获得`500`的权重 | `semibold`现在是`600`。请改用新的`--dwc-font-weight-medium`来获取`500`。 |
| 用`--dwc-focus-ring-width`保留可聚焦元素周围的填充 | 环形现在有一个间隙。请添加`--dwc-focus-ring-gap`。有关更多信息，请参见[Focus ring](#design-system-focus-ring)。 |
| 自定义按钮悬停/涟漪效果 | 涟漪已消失。按下的反馈现在是轻微的缩小。 |

### `--dwc-color-{name}-c`已移除 {#design-system-c-removed}

如果您有任何`--dwc-color-{name}-c`覆盖，可以删除它们，它们没有效果。  
现在每个色调的明暗文本翻转是自动计算的。

### `--dwc-color-{name}-alt`语义改变 {#design-system-alt-changed}

| 令牌 | v25 | v26 |
|---|---|---|
| `--dwc-color-{name}-alt` | 调色板步骤`95`（接近白色背景） | 种子12%透明度（半透明色调） |

如果您将`-alt`用作实心接近白色的背景，它现在将作为半透明的色调叠加。选择一个特定步骤（`--dwc-color-{name}-95`）或围绕半透明语义进行设计。

### `--dwc-border-color-{name}`语义改变 {#design-system-border-color-changed}

| 令牌 | v25 | v26 |
|---|---|---|
| `--dwc-border-color-{name}` | 每个变体设置为`var(--dwc-color-{name})`（饱和色调） | 在生成器中计算：模式感知的种子明亮色调 |

如果您的CSS读取`--dwc-border-color-primary`并期望饱和的主色，视觉上现在是细微的分隔色调。如果您特别想要饱和的外观，请直接切换到`--dwc-color-primary`。

### `--dwc-shadow-color`格式改变 {#design-system-shadow-color-changed}

|  | v25 | v26 |
|---|---|---|
| `--dwc-shadow-color` | HSL三元组（`h，s%，l%`） | 完整的OKLCH颜色 |

如果您的CSS使用传统的三元组形式，例如`hsla(var(--dwc-shadow-color)，0.07)`，请切换到完整的阴影令牌（`var(--dwc-shadow-m)`）或重写为`oklch(from var(--dwc-shadow-color) l c h / 0.07)`。

### 排版 {#design-system-typography}

字体规模已进行调整，因此桶名称下移了一个步骤：

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

默认`--dwc-font-size`仍然解析为**14px**，它只是通过`--dwc-font-size-m`（v26）而不是`--dwc-font-size-s`（v25）到达。如果您的CSS根据名称引用字体大小令牌（例如`font-size: var(--dwc-font-size-l)`），在v26中可见结果将较小。请提升一个桶以保持v25大小。

字体权重获得了三个令牌（`thin`，`medium`，`black`）以及一个现有令牌的变化：

| 令牌 | v25 | v26 |
|---|---|---|
| `--dwc-font-weight-semibold` | `500` | `600` |
| `--dwc-font-weight-medium`   | （不存在） | `500` |

如果您使用`--dwc-font-weight-semibold`来获取500权重的文本，请切换为`--dwc-font-weight-medium`。

### 边框半径 {#design-system-border-radius}

|  | v25 | v26 |
|---|---|---|
| 单位 | `em`（与父字体大小缩放） | `rem`（与根字体大小缩放） |
| 默认`--dwc-border-radius` | `--dwc-border-radius-s`（`4px`） | `--dwc-border-radius-seed`（`8px`） |
| 可用步骤 | 最多`2xl` | 添加`3xl`，`4xl` |

组件的外观更圆滑。如果位于较大文本中的组件以前通过`em`继承更大的半径，则不再发生这种缩放，半径现在锚定到根。如果您希望返回v25的默认大小，请将种子减半：

```css
:root {
  --dwc-border-radius-seed: 0.25rem; /* 4px，将整个缩放减半 */
}
```

### 焦点环 {#design-system-focus-ring}

焦点环现在使用双环模式：小的表面颜色间隙，然后是有色环。

| 变量 | v25 | v26 |
|---|---|---|
| `--dwc-focus-ring-width` | `3px` | `2px` |
| `--dwc-focus-ring-a`     | `0.4` | `0.75` |
| `--dwc-focus-ring-gap`   | （无） | `2px` |
| `--dwc-focus-ring-l`     | `45%` | （已移除，亮度根据模式计算） |

如果您用`padding: var(--dwc-focus-ring-width)`来保留可聚焦元素的周围空间，请在填充中添加间隙，以便新的环有空间呈现：

```css
/* v25 */
dwc-button { padding: var(--dwc-focus-ring-width); }

/* v26 */
dwc-button {
  padding: calc(var(--dwc-focus-ring-width) + var(--dwc-focus-ring-gap));
}
```

### 涟漪效果已移除 {#design-system-ripples-removed}

任何DWC组件不再使用材料样式的涟漪效果。任何可点击元素的新反馈是小规模的缩小：

```css
--dwc-scale-press: 0.97;      /* 标准的3%缩小 */
--dwc-scale-press-deep: 0.93; /* 针对按钮的深层7%缩小 */
```

`ripple` SCSS混合和`--dwc-ripple-color` CSS变量仍然存在于构建中，但默认情况下没有任何导入它们。如果您自己的组件选择了该混合，请切换到press-scale令牌以匹配新的感觉。

### 过渡持续时间重新平衡 {#design-system-transitions}

| 变量 | v25 | v26 |
|---|---|---|
| `--dwc-transition-slow`   | `500ms` | `300ms` |
| `--dwc-transition-medium` | `250ms` | `250ms` |
| `--dwc-transition-fast`   | `150ms` | `150ms` |
| `--dwc-transition-x-fast` | `50ms`  | `100ms` |

如果您依赖于特定持续时间，请在`:root`中覆盖它。

### 实用升级检查表 {#design-system-checklist}

1. 搜索`--dwc-color-*-c`并删除那些声明。
2. 搜索`hsla(var(--dwc-shadow-color)`并用阴影令牌（`var(--dwc-shadow-m)`）替换或重写为`oklch(from ...)`。
3. 搜索直接调色板步骤引用（`--dwc-color-{name}-{number}`）。如果任何内容提供深色模式特定样式，请切换到变体令牌（`--dwc-color-{name}`，`-dark`，`-light`）。
4. 搜索命名字体大小引用（`--dwc-font-size-m`，`-l`等）。如果您希望获得v25大小，请上调一个桶。
5. 搜索`--dwc-font-weight-semibold`。如果您想要`500`，请切换到`--dwc-font-weight-medium`。
6. 如果您用`--dwc-focus-ring-width`保留可聚焦元素的周围空间，请将`--dwc-focus-ring-gap`添加到填充中。
7. 打开应用程序，点击四处。大多数应用程序不需要其他更改。
