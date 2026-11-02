---
title: Icon
sidebar_position: 55
description: >-
  Render scalable SVG icons with the Icon component from Tabler, Feather, Font
  Awesome, or custom pools loaded on demand from a CDN.
_i18n_hash: f1572fcb6d1396a20fac32bfa282c091
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-icon" />
<DocChip chip='since' label='24.11' />
<JavadocLink type="icons" location="com/webforj/component/icons/Icon" top='true'/>

`Icon` 组件显示可缩放的图标，以任何大小显示而不失去质量。您可以从三种内置图标池中选择或创建自定义图标。图标用作导航和操作的视觉提示，减少接口中需要文本标签的需求。

每个 `Icon` 都作为可缩放矢量图形（SVG）图像呈现，按需从内容交付网络（CDN）加载，以保持低延迟。要创建一个图标，请选择一个图标池，并指定图标的名称。一些图标还提供通过 [variations](#variations) 在轮廓或填充版本之间选择的选项。

<!-- INTRO_END -->

<ComponentDemo
path='/webforj/iconbasics'
files={['src/main/java/com/webforj/samples/views/icon/IconBasicsView.java']}
height='100px'
/>

:::tip 你知道吗？
某些组件，如 `PasswordField` 和 `TimeField`，内置了图标以帮助传达意思给最终用户。
:::

## 图标池 {#pools}

图标池是一个常用图标的集合，使访问和重用变得简单。通过使用图标池中的图标，您可以确保应用中的图标是可识别的并且具有一致的风格。
使用 webforJ，您可以从三个池中进行选择，或者实现一个自定义池。
每个池都有大量开源图标，免费使用。
使用 webforJ 使您能够从三个池中选择并将其作为唯一类使用，无需直接下载任何图标。

| 图标池                                         | webforJ 类 |
| --------                                          | ------- |
| [Tabler](https://tabler-icons.io/)                | `TablerIcon` 和 `DwcIcon`.<br/>`DwcIcon` 是 Tabler 图标的子集。|
| [Feather](https://feathericons.com/)              | `FeatherIcon`    |
| [Font Awesome](https://fontawesome.com/search)    | `FontAwesomeIcon`   |

:::tip

如果您有兴趣创建自己的图标池，请参阅 [创建自定义池](#creating-custom-pools)。

:::

一旦您选择了要包含在应用中的池或池，下一步是指定要使用的图标名称。

## 图标名称 {#names}

要在应用中包含图标，您所需的仅是图标池和图标名称。浏览图标池网站寻找您希望使用的图标，并使用图标名称作为 `create()` 方法的参数。
此外，您可以通过 `FeatherIcon` 和 `DwcIcon` 类的枚举来创建图标，这些枚举可以在代码补全中出现。

```java
// 从字符串名称创建图标
Icon image = TablerIcon.create("image");
// 从枚举创建图标
Icon image = FeatherIcon.IMAGE.create();
```

## 变体 {#variations}

您可以通过利用变体进一步个性化图标。
某些图标允许您在轮廓或填充版本之间进行选择，使您可以根据个人喜好强调特定图标。`FontAwesomeIcon` 和 `Tabler` 图标提供变体。

### `FontAwesomeIcon` 变体 {#fontawesomeicon-variations}

1. `REGULAR`：图标的轮廓变体。这是默认值。
2. `SOLID`：图标的填充变体。
3. `BRAND`：在使用品牌图标时的变体。

### `TablerIcon` 变体 {#tablericon-variations}

1. `OUTLINE`：图标的轮廓变体。这是默认值。
2. `FILLED`：图标的填充变体。

```java
// 从 Font Awesome 创建填充变体的图标
Icon music = FontAwesomeIcon.create("user", FontAwesomeIcon.Variate.SOLID);
```

以下演示说明了如何使用不同池中的图标，应用变体，并将它们无缝集成到组件中。

<ComponentDemo
path='/webforj/iconvariations'
files={['src/main/java/com/webforj/samples/views/icon/IconVariationsView.java']}
height='100px'
/>

## 将图标添加到组件中 {#adding-icons-to-components}

使用插槽将图标集成到组件中。插槽提供灵活的选项，使组件更加实用。将 `Icon` 添加到组件中有助于进一步向用户明确意图。
实现 `HasPrefixAndSuffix` 接口的组件可以包括 `Icon` 或其他有效组件。添加的组件可以放置在 `prefix` 和 `suffix` 插槽中，并可以增强整体设计和用户体验。

使用 `prefix` 和 `suffix` 插槽，您可以通过 `setPrefixComponent()` 和 `setSuffixComponent()` 方法来确定图标是放在文本之前还是之后。

决定图标是放在文本之前还是之后，主要取决于目的和设计上下文。

### 图标放置：之前 VS 之后 {#icon-placement-before-vs-after}

放置在组件文本之前的图标有助于用户快速理解组件的主要功能或目的，尤其是对于像保存图标这样被广泛识别的图标。
放置在组件文本之前的图标提供了一个逻辑处理顺序，指导用户自然地进行预期的操作，这对于主要功能是立即执行的按钮是有利的。

另一方面，在组件文本之后放置图标对提供额外上下文或选项的操作是有效的，增强了导航的清晰度和提示。
放置在组件文本之后的图标非常适合提供附加信息或指导用户进行方向性流动的组件。

最终，一致性是关键。一旦您选择了一种风格，就应该在整个站点上保持这种风格，以实现一致的用户友好设计。

<ComponentDemo
path='/webforj/iconprefixsuffix'
files={['src/main/java/com/webforj/samples/views/icon/IconPrefixSuffixView.java']}
height='100px'
/>

## 创建自定义池 {#creating-custom-pools}

除了利用现有的图标集合，您还有选择创建可以用于自定义徽标或头像的自定义池的选项。
图标的自定义池可以存储在集中目录或资源文件夹（上下文）中，从而简化图标管理过程。
拥有自定义池使应用创建更一致，并减少不同组件和模块之间的维护。

自定义池可以通过包含 SVG 图像的文件夹和使用 `IconPoolBuilder` 类创建。从那里，您可以选择自定义池的名称，并使用 SVG 文件名称创建自定义图标组件。

```java
// 创建一个名为 "app-pool" 的自定义池，其中包含徽标和头像的图像。
IconPoolBuilder.fromDirectory("app-pool", "context://icons");
Icon customLogo = new Icon("logo", "app-pool");
Icon customAvatar = new Icon("avatar-default", "app-pool");
```

:::tip
确保设计图标的宽度和高度相等，因为 `Icon` 组件设计为占用方形空间。
:::

### 自定义池工厂 {#custom-pool-factory}

您还可以像 `FeatherIcon` 一样为 webforJ 创建一个自定义池的工厂类。这使您能够在指定的池中创建和管理图标资源，并允许代码补全。
每个图标可以通过 `create()` 方法实例化，该方法返回一个 `Icon`。工厂类应提供特定于池的元数据，例如池名称和图标标识符，格式为图像的文件名。
这种设计使得可以通过枚举常量从自定义池标准化地轻松访问图标资产，支持图标管理的可扩展性和可维护性。

```java
// 为 app-pool 创建自定义池工厂
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

以下代码显示了使用自定义池的两种不同方法。

```java
IconPoolBuilder.fromDirectory("app-pool", "context://icons");

// 使用自定义池和图像文件的名称创建图标
Icon customLogo = new Icon("logo", "app-pool");

// 使用前面片段中的自定义池工厂创建图标
Icon customLogo = AppPoolIcon.LOGO.create();
```

## 图标按钮 <DocChip chip="name" label="dwc-icon-button" /> {#icon-buttons}
`Icon` 组件是不可选择的，但对于仅用图标表示的操作，例如通知或警报，您可以使用 `IconButton`。

 ```java
IconButton bell = new IconButton(FeatherIcon.BELL.create());
bell.onClick(e -> {
  showMessageDialog("您有新消息！", "叮咚！");
});
```

## 最佳实践

- **可访问性：** 使用工具提示或图标标签，使您的应用对依赖屏幕阅读器的视觉障碍用户可用。
- **避免歧义：** 如果图标的含义不清楚或不广为人知，请避免使用图标。如果用户必须猜测图标的代表意义，会失去目的。
- **适度使用图标：** 过多的图标可能会让用户不知所措，因此仅在图标能增加清晰度或减少复杂性时使用图标。

## 样式
图标继承其直接父组件的主题，但您可以通过直接向 `Icon` 应用主题来覆盖这一点。

### 主题
图标组件内置七种独立主题，便于在不使用 CSS 的情况下快速样式化。这些主题是可以应用于图标的预定义样式，以更改其外观和视觉呈现。它们提供了一种快速和一致的方式来定制应用程序中图标的外观。

虽然每种主题有许多用例，但一些例子包括：

- `DANGER`：最适合后果严重的操作，如清除已填写的信息或永久删除帐户/数据。
- `DEFAULT`：适用于不需要特别关注或是通用的操作，例如切换设置。
- `PRIMARY`：适合作为页面上的主要 "行动呼吁"，例如注册、保存更改或继续到另一个页面。
- `SUCCESS`：非常适合可视化应用中元素的成功完成，例如表单提交或注册过程完成。成功主题可以在成功操作完成后以编程方式应用。
- `WARNING`：用于指示用户即将执行潜在风险的操作，例如在没有保存更改的情况下离开页面。这些操作通常不如会使用危险主题的操作影响大。
- `GRAY`：适合不起眼的操作，例如轻微的设置或对页面的补充性操作，而不是主功能的一部分。
- `INFO`：适合为用户提供额外的说明信息。

<TableBuilder name={['Icon', 'IconButton']} />
