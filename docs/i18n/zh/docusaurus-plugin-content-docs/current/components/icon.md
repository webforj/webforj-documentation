---
title: Icon
sidebar_position: 55
description: >-
  Render scalable SVG icons with the Icon component from Tabler, Feather, Font
  Awesome, or custom pools loaded on demand from a CDN.
_i18n_hash: 0e51ecab262c62fb63cd767ba8167084
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-icon" />
<DocChip chip='since' label='24.11' />
<JavadocLink type="icons" location="com/webforj/component/icons/Icon" top='true'/>

`Icon` 组件显示的图标可以缩放到任何大小而不会失去质量。你可以从三个内置图标池中选择，或者创建自定义图标。图标作为视觉提示用于导航和操作，减少了在界面中需要文本标签的必要性。

<!-- INTRO_END -->

## 基础 {#basics}

每个 `Icon` 被设计为可缩放矢量图形（SVG）图像，这意味着它可以轻松缩放到任何大小而不会失去清晰度或质量。此外，`Icon` 组件是按需从内容分发网络（CDN）加载的，有助于减少延迟并改善整体性能。

在创建 `Icon` 时，你需要确定一个特定的图标池及图标的名称。有些图标也提供通过 [variations](#variations) 在轮廓或填充版本之间进行选择。

<ComponentDemo
path='/webforj/iconbasics'
files={['src/main/java/com/webforj/samples/views/icon/IconBasicsView.java']}
height='100px'
/>

:::tip 你知道吗？
一些组件，如 `PasswordField` 和 `TimeField`，具有内置图标，以帮助传达给最终用户的含义。
:::

### 图标池 {#pools}

图标池是一个常用图标的集合，便于访问和重用。通过使用图标池中的图标，可以确保你应用中的图标是可识别的，并且风格一致。使用 webforJ 允许你从三个池中选择，或实现一个自定义池。每个池都有一个广泛的开源图标集合，免费使用。使用 webforJ 使你能够选择三个池并将它们作为唯一的类使用，而无需直接下载任何图标。

| 图标池                                            | webforJ 类 |
| --------                                          | ------- |
| [Tabler](https://tabler-icons.io/)                | `TablerIcon` 和 `DwcIcon`。<br/>`DwcIcon` 是 Tabler 图标的一个子集。|
| [Feather](https://feathericons.com/)              | `FeatherIcon`    |
| [Font Awesome](https://fontawesome.com/search)    | `FontAwesomeIcon`   |

:::tip

如果你有兴趣创建自己的图标池，请查看 [创建自定义池](#creating-custom-pools)。

:::

一旦选择了要在应用中包含的池，下一步是指定你想要使用的图标名称。

### 名称 {#names}

要在应用中包含图标，你只需知道图标池和图标名称。浏览图标池网站以找到想要使用的图标，并将图标名称作为 `create()` 方法的参数。此外，你可以通过 `FeatherIcon` 和 `DwcIcon` 类的枚举创建图标，从而允许它们在代码补全中出现。

```java
// 从字符串名称创建一个图标
Icon image = TablerIcon.create("image");
// 从枚举创建一个图标
Icon image = FeatherIcon.IMAGE.create();
```

### 变体 {#variations}

你可以通过使用变体进一步个性化图标。某些图标允许你在轮廓或填充版本之间进行选择，基于你的偏好强调特定图标。`FontAwesomeIcon` 和 `Tabler` 图标提供变体。

#### `FontAwesomeIcon` 变体 {#fontawesomeicon-variations}

1. `REGULAR`: 图标的轮廓变体。这是默认的。
2. `SOLID`: 图标的填充变体。
3. `BRAND`: 当你使用品牌图标时的变体。

#### `TablerIcon` 变体 {#tablericon-variations}

1. `OUTLINE`: 图标的轮廓变体。这是默认的。
2. `FILLED`: 图标的填充变体。

```java
// 从 Font Awesome 创建一个填充变体的图标
Icon music = FontAwesomeIcon.create("user", FontAwesomeIcon.Variate.SOLID);
```

以下演示展示了如何使用来自不同池的图标，应用变体，以及将它们无缝集成到组件中。

<ComponentDemo
path='/webforj/iconvariations'
files={['src/main/java/com/webforj/samples/views/icon/IconVariationsView.java']}
height='100px'
/>

## 将图标添加到组件 {#adding-icons-to-components}

通过使用插槽将图标集成到组件中。插槽提供灵活的选项，使组件更有用。为组件添加 `Icon` 有助于进一步向用户澄清意图。实现 `HasPrefixAndSuffix` 接口的组件可以包含一个 `Icon` 或其他有效组件。添加的组件可以放置在 `prefix` 和 `suffix` 插槽中，从而增强整体设计和用户体验。

使用 `prefix` 和 `suffix` 插槽，你可以决定是否希望图标位于文本之前或之后，使用 `setPrefixComponent()` 和 `setSuffixComponent()` 方法。

决定在组件文本之前或之后放置图标在很大程度上取决于目的和设计上下文。

### 图标放置：前 VS 后 {#icon-placement-before-vs-after}

位于组件文本之前的图标有助于用户快速理解组件的主要行动或目的，尤其是对于诸如保存图标等普遍公认的图标。在组件文本前的图标提供了逻辑处理顺序，自然引导用户完成预期的操作，这对主要功能是立即行动的按钮非常有用。

另一方面，将图标放置在组件文本之后，对于提供额外上下文或选项的操作是有效的，从而增强清晰性和导航提示。组件文本之后的图标非常适合那些提供补充信息或引导用户遵循方向流动的组件。

最终，一致性是关键。一旦选择了一种风格，请在整个站点上保持一致，以实现一致且用户友好的设计。

<ComponentDemo
path='/webforj/iconprefixsuffix'
files={['src/main/java/com/webforj/samples/views/icon/IconPrefixSuffixView.java']}
height='100px'
/>️

## 创建自定义池 {#creating-custom-pools}

除了利用现有的图标集合外，你还有选择创建可用于自定义徽标或头像的自定义池的选项。自定义图标池可以存储在集中目录或资源文件夹（上下文）中，从而简化图标管理过程。拥有自定义池使应用创建更一致，并减少不同组件和模块之间的维护工作。

可以通过包含 SVG 图像的文件夹以及使用 `IconPoolBuilder` 类创建自定义池。然后，你可以选择自定义池的名称，并将其与 SVG 文件名称一起使用以创建自定义图标组件。

```java
// 创建一个名为 "app-pool" 的自定义池，其中包含徽标和头像的图像。
IconPoolBuilder.fromDirectory("app-pool", "context://icons");
Icon customLogo = new Icon("logo", "app-pool");
Icon customAvatar = new Icon("avatar-default", "app-pool");
```

:::tip
确保图标设计具有相等的宽度和高度，因为 `Icon` 组件设计为占据一个方形空间。
:::

### 自定义池工厂 {#custom-pool-factory}

你还可以在 webforJ 中为自定义池创建一个工厂类，就像 `FeatherIcon` 一样。这使你能够在指定的池内创建和管理图标资源，并允许代码完成。每个图标都可以通过 `create()` 方法实例化，该方法返回一个 `Icon`。工厂类应提供池特定的元数据，如池名称和图标的标识符，格式化为图像的文件名。这种设计允许便捷、标准化地访问自定义池中的图标资产，支持图标管理的可扩展性和可维护性。

```java
/// 为 app-pool 创建一个自定义池工厂
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

// 使用自定义池和图像文件的名称创建 Icon
Icon customLogo = new Icon("logo", "app-pool");

// 使用前面的代码片段中的自定义池工厂创建 Icon
Icon customLogo = AppPoolIcon.LOGO.create();
```

## 图标按钮 {#icon-buttons}
`Icon` 组件不可选择，但对于仅用图标表示的操作，例如通知或警报，可以使用 `IconButton`。

 ```java
IconButton bell = new IconButton(FeatherIcon.BELL.create());
bell.onClick(e -> {
  showMessageDialog("你有一条新消息！", "叮当！");
});
```

## 最佳实践

- **可访问性：** 对于依赖屏幕阅读器的视障用户，请在图标上使用工具提示或标签，使你的应用可访问。
- **避免歧义：** 如果图标的含义不清或不被广泛理解，请避免使用图标。如果用户必须猜测图标所代表的内容，那就违背了图标的目的。
- **适度使用图标：** 图标过多可能会让用户感到不知所措，因此只在增加清晰度或减少复杂性时使用图标。

## 样式
Icon 继承其直接父组件的主题，但你可以通过直接将主题应用到 `Icon` 来覆盖这一点。

### 主题
Icon 组件内置七种独立主题，以便快速样式设置，无需使用 CSS。这些主题是可以应用于图标的预定义样式，以改变它们的外观和视觉表现。它们提供了一种快速一致的方式，以自定义整个应用中图标的外观。

虽然每种主题都有多种用途，但一些示例用途包括：

- `DANGER`：最适合具有严重后果的操作，例如清除已填写的信息或永久删除账户/数据。
- `DEFAULT`：适合在整个应用中不需要特殊注意且通用的操作，例如切换设置。
- `PRIMARY`：适合在页面上的主要 "号召行动"，例如注册、保存更改或继续到另一个页面。
- `SUCCESS`：非常适合可视化应用中元素成功完成的情况，例如表单的提交或注册过程的完成。成功主题可以在成功执行操作后以编程方式应用。
- `WARNING`：有助于指示用户即将执行可能存在风险的操作，例如在未保存更改的情况下离开页面。这些操作通常比使用 Danger 主题的操作影响小。
- `GRAY`：适合于细微操作，例如次要设置或更补充页面的操作，而不是主要功能的一部分。
- `INFO`：适合于向用户提供额外的澄清信息。

<TableBuilder name={['Icon', 'IconButton']} />
