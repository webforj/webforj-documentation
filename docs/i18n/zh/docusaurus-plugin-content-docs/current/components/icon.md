---
title: Icon
sidebar_position: 55
description: >-
  Render scalable SVG icons with the Icon component from Tabler, Feather, Font
  Awesome, or custom pools loaded on demand from a CDN.
_i18n_hash: c526ee2878756d5dd13fa2972dfef56e
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-icon" />
<DocChip chip='since' label='24.11' />
<JavadocLink type="icons" location="com/webforj/component/icons/Icon" top='true'/>

`Icon` 组件显示可无损放大的图标，支持任意大小。您可以从三个内置图标池中选择，或创建自定义图标。图标作为导航和操作的视觉提示，减少了界面中对文本标签的需求。

每个 `Icon` 都作为可缩放矢量图形（SVG）图像呈现，通过内容分发网络（CDN）按需加载，以保持低延迟。要创建一个，选择一个图标池和图标名称。有些图标还提供通过 [variations](#variations) 选择轮廓或填充版本的选项。

<!-- INTRO_END -->

<ComponentDemo
path='/webforj/iconbasics'
files={['src/main/java/com/webforj/samples/views/icon/IconBasicsView.java']}
height='100px'
/>

:::tip 您知道吗？
一些组件，如 `PasswordField` 和 `TimeField`，内置图标以帮助传达给最终用户的含义。
:::

## 图标池 {#pools}

图标池是常用图标的集合，方便访问和重用。通过使用图标池中的图标，您可以确保应用中的图标是可识别的，并且风格一致。
使用 webforJ，您可以从三个池中选择，或实现自定义池。
每个池都有大量的开源图标，可以免费使用。
使用 webforJ，您可以灵活地从三个池中选择，并将其用作独特的类，而不必直接下载任何图标。


| 图标池                                         | webforJ 类 |
| --------                                          | ------- |
| [Tabler](https://tabler-icons.io/)                | `TablerIcon` 和 `DwcIcon`。<br/>`DwcIcon` 是 Tabler 图标的一个子集。|
| [Feather](https://feathericons.com/)              | `FeatherIcon`    |
| [Font Awesome](https://fontawesome.com/search)    | `FontAwesomeIcon`   |

:::tip

如果您有兴趣创建自己的图标池，请参阅 [Creating custom pools](#creating-custom-pools)。

:::

一旦选择了要在应用中包含的池，下一步是指定要使用的图标名称。

## 名称 {#names}

要在您的应用中包含图标，您只需要图标池和图标名称。浏览图标池网站以查找您希望使用的图标，并使用图标名称作为 `create()` 方法的参数。
此外，您还可以通过 `FeatherIcon` 和 `DwcIcon` 类的枚举来创建图标，使其在代码补全中可见。

```java
// 从字符串名称创建图标
Icon image = TablerIcon.create("image");
// 从枚举创建图标
Icon image = FeatherIcon.IMAGE.create();
```

## 变种 {#variations}

您可以通过使用变种个性化图标。
某些图标允许您在轮廓和填充版本之间进行选择，使您能够根据喜好强调特定图标。 `FontAwesomeIcon` 和 `Tabler` 图标提供变种。

### `FontAwesomeIcon` 变种 {#fontawesomeicon-variations}

1. `REGULAR`：图标的轮廓变种。这是默认。
2. `SOLID`：图标的填充变种。
3. `BRAND`：用于您正在使用品牌图标的变种。

### `TablerIcon` 变种 {#tablericon-variations}

1. `OUTLINE`：图标的轮廓变种。这是默认。
2. `FILLED`：图标的填充变种。

```java
// 从 Font Awesome 创建填充变种的图标
Icon music = FontAwesomeIcon.create("user", FontAwesomeIcon.Variate.SOLID);
```

以下演示展示了如何使用不同池中的图标，应用变种，并将它们无缝集成到组件中。

<ComponentDemo
path='/webforj/iconvariations'
files={['src/main/java/com/webforj/samples/views/icon/IconVariationsView.java']}
height='100px'
/>

## 将图标添加到组件中 {#adding-icons-to-components}

通过使用插槽将图标集成到您的组件中。插槽提供灵活的选项，使组件更加实用。向组件添加 `Icon` 以进一步明确用户的意图是有益的。
实现了 `HasPrefixAndSuffix` 接口的组件可以包含 `Icon` 或其他有效组件。添加的组件可以放置在 `prefix` 和 `suffix` 插槽中，增强整体设计和用户体验。

使用 `prefix` 和 `suffix` 插槽，您可以使用 `setPrefixComponent()` 和 `setSuffixComponent()` 方法确定图标是放在文本之前还是之后。

决定是将图标放在组件文本之前还是之后，主要取决于目的和设计背景。

### 图标放置：之前 VS 之后 {#icon-placement-before-vs-after}

放置在组件文本之前的图标帮助用户快速理解组件的主要操作或目的，尤其是对于像保存图标这样普遍认可的图标。
组件文本前的图标提供了逻辑的处理顺序，自然引导用户完成所需的操作，这对主要功能为立即操作的按钮很有益。

另一方面，将图标放在组件文本之后对于提供额外上下文或选项的操作是有效的，增强导航的清晰度和提示。
组件文本后面的图标适用于提供补充信息或引导用户方向流动的组件。

最终，一致性是关键。一旦您选择了一种风格，请在整个网站上保持其一致，以实现连贯且用户友好的设计。

<ComponentDemo
path='/webforj/iconprefixsuffix'
files={['src/main/java/com/webforj/samples/views/icon/IconPrefixSuffixView.java']}
height='100px'
/>️

## 创建自定义池 {#creating-custom-pools}

除了利用现有的图标集合之外，您还可以创建一个可用于自定义徽标或头像的自定义池。
自定义图标池可以存储在集中式目录或资源文件夹（上下文）中，从而简化图标管理流程。
拥有自定义池使应用程序的创建更加一致，减少了不同组件和模块之间的维护工作。

可以从包含 SVG 图像的文件夹创建自定义池，并通过使用 `IconPoolBuilder` 类。从此，您可以选择自定义池的名称，并使用该名称和 SVG 文件名来创建自定义图标组件。

```java
// 创建一个名为 "app-pool" 的自定义池，该池包含徽标和头像的图像。
IconPoolBuilder.fromDirectory("app-pool", "context://icons");
Icon customLogo = new Icon("logo", "app-pool");
Icon customAvatar = new Icon("avatar-default", "app-pool");
```

:::tip
确保以相等的宽度和高度设计图标，因为 `Icon` 组件设计为占用正方形空间。
:::

### 自定义池工厂 {#custom-pool-factory}

您还可以为 webforJ 中的自定义池创建工厂类，就像 `FeatherIcon` 一样。这使您能够在指定的池中创建和管理图标资源，并允许代码补全。
每个图标都可以通过 `create()` 方法实例化，该方法返回一个 `Icon`。工厂类应提供池特定的元数据，如池名称和图标标识符，采用图像文件名格式。
这种设计允许通过枚举常量轻松标准化访问自定义池中的图标资产，支持图标管理的可扩展性和可维护性。

```java
/// 为 app-pool 创建自定义池工厂
public enum AppPoolIcon implements IconFactory {
  LOGO, AVATAR_DEFAULT;

  public Icon create() {
    return new Icon(String.valueOf(this), this.getPool());
  }

  /**
   * @return 图标的池名称
   */
  @Override
  public String getPool() {
    return "app-pool";
  }

  /**
   * @return 图标名称
   */
  @Override
  public String toString() {
    return this.name().toLowerCase(Locale.ENGLISH).replace('_', '-');
  }
}
```

以下代码片段展示了使用自定义池的两种不同方式。

```java
IconPoolBuilder.fromDirectory("app-pool", "context://icons");

// 使用自定义池和图像文件名创建图标
Icon customLogo = new Icon("logo", "app-pool");

// 使用上一段代码中的自定义池工厂创建图标
Icon customLogo = AppPoolIcon.LOGO.create();
```

## 图标按钮 {#icon-buttons}
`Icon` 组件是不可选择的，但对于最佳用图标表示的操作，如通知或警示，您可以使用 `IconButton`。

 ```java
IconButton bell = new IconButton(FeatherIcon.BELL.create());
bell.onClick(e -> {
  showMessageDialog("您有新消息！", "叮咚！")
  });
```

## 最佳实践

- **可访问性：** 在图标上使用工具提示或标签，以使您的应用对依赖屏幕阅读器的视力受限用户可访问。
- **避免歧义：** 如果含义不清晰或不被广泛理解，请避免使用图标。如果用户不得不猜测图标的含义，那就失去了意义。
- **谨慎使用图标：** 图标过多可能会让用户感到不知所措，因此仅在图标能够增加清晰度或降低复杂性时使用。

## 样式
图标继承其直接父组件的主题，但您可以通过直接将主题应用于 `Icon` 来覆盖此行为。

### 主题
图标组件内置有七种离散主题，便于在不使用 CSS 的情况下快速样式化。这些主题是预定义的样式，可应用于图标，以改变其外观和视觉呈现。它们提供了在整个应用中自定义图标外观的快速且一致的方法。

虽然各种主题有许多使用案例，但一些示例用例包括：

- `DANGER`：最适合具有严重后果的操作，例如清除填写的信息或永久删除账户/数据。
- `DEFAULT`：适用于不需要特别关注的应用操作和通用操作，例如切换设置。
- `PRIMARY`：适用于页面上的主要 "号召行动"，例如注册、保存更改或继续到另一个页面。
- `SUCCESS`：非常适合可视化应用中元素的成功完成，例如表单提交或注册过程的完成。一旦成功操作完成，成功主题可以通过编程应用。
- `WARNING`：用于指示用户即将进行潜在风险操作，例如离开有未保存更改的页面。这些操作通常不如会使用危险主题的操作影响大。
- `GRAY`：适合微妙的操作，例如较小的设置或不属于主要功能的补充操作。
- `INFO`：适合向用户提供额外的澄清信息。

<TableBuilder name={['Icon', 'IconButton']} />
