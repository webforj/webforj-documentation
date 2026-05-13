---
title: Icon
sidebar_position: 55
_i18n_hash: ae46080226d89087113b901c748f0942
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-icon" />
<DocChip chip='since' label='24.11' />
<JavadocLink type="icons" location="com/webforj/component/icons/Icon" top='true'/>

`Icon` 组件显示图标，可缩放到任何大小而不会失去质量。 您可以选择三种内置图标池或创建自定义图标。 图标作为导航和操作的视觉提示，减少了您界面中对文本标签的需求。

<!-- INTRO_END -->

## 基础 {#basics}

每个 `Icon` 都设计为可缩放矢量图形（SVG）图像，这意味着它可以轻松缩放到任何大小，而不会失去清晰度或质量。 此外， `Icon` 组件按需从内容分发网络（CDN）加载，这有助于降低延迟并提高整体性能。

创建 `Icon` 时，您需要确定特定的图标池和图标名称。 某些图标还提供通过 [variations](#variations) 选择轮廓或填充版本的选项。

<ComponentDemo
path='/webforj/iconbasics'
files={['src/main/java/com/webforj/samples/views/icon/IconBasicsView.java']}
height='100px'
/>

:::tip 您知道吗？
某些组件，如 `PasswordField` 和 `TimeField`，具有内置图标，以帮助传达含义给最终用户。
:::

### 图标池 {#pools}

图标池是一组常用图标的集合，使访问和重用变得简单。 通过使用图标池中的图标，您可以确保应用程序中的图标是可识别的并且具有一致的样式。 使用 webforJ，您可以选择三种池，或实现自定义池。 每个池都有大量的开源图标，免费使用。 使用 webforJ 使您能够从三个池中选择，并将它们用作独特的类，而不必直接下载任何图标。

| 图标池                                         | webforJ 类 |
| --------                                          | ------- |
| [Tabler](https://tabler-icons.io/)                | `TablerIcon` 和 `DwcIcon`。<br/>`DwcIcon` 是 Tabler 图标的一个子集。 |    
| [Feather](https://feathericons.com/)              | `FeatherIcon`    |
| [Font Awesome](https://fontawesome.com/search)    | `FontAwesomeIcon`   |

:::tip

如果您有兴趣创建自己的图标池，请参阅 [Creating custom pools](#creating-custom-pools)。

:::

选择要包含在应用程序中的池或池后，下一步是指定要使用的图标名称。

### 名称 {#names}

要在应用程序中包含图标，您只需要图标池和图标名称。 浏览图标池网站以查找您要使用的图标，并使用图标名称作为 `create()` 方法的参数。 此外，您可以通过 `FeatherIcon` 和 `DwcIcon` 类的枚举来创建图标，使其出现在代码完成中。

```java
// 从字符串名称创建图标
Icon image = TablerIcon.create("image");
// 从枚举创建图标
Icon image = FeatherIcon.IMAGE.create();
```

### 变体 {#variations}

您还可以通过利用变体进一步个性化图标。 某些图标允许您选择轮廓或填充版本，允许您根据个人偏好强调特定图标。 `FontAwesomeIcon` 和 `Tabler` 图标提供变体。

#### `FontAwesomeIcon` 变体 {#fontawesomeicon-variations}

1. `REGULAR`：图标的轮廓变体。 这是默认值。
2. `SOLID`：图标的填充变体。
3. `BRAND`：使用品牌图标时的变体。

#### `TablerIcon` 变体 {#tablericon-variations}

1. `OUTLINE`：图标的轮廓变体。 这是默认值。
2. `FILLED`：图标的填充变体。

```java
// Font Awesome 的图标填充变体
Icon music = FontAwesomeIcon.create("user", FontAwesomeIcon.Variate.SOLID);
```

以下演示说明了如何使用来自不同池的图标，应用变体，并将其无缝集成到组件中。

<ComponentDemo
path='/webforj/iconvariations'
files={['src/main/java/com/webforj/samples/views/icon/IconVariationsView.java']}
height='100px'
/>

## 将图标添加到组件 {#adding-icons-to-components}

通过使用插槽将图标集成到您的组件中。 插槽提供灵活的选项，可以使组件更有用。 向组件添加 `Icon` 很有益，以进一步向用户澄清预期含义。 实现 `HasPrefixAndSuffix` 接口的组件可以包括 `Icon` 或其他有效组件。 添加的组件可以放置在 `prefix` 和 `suffix` 插槽中，增强整体设计和用户体验。

通过使用 `prefix` 和 `suffix` 插槽，您可以通过 `setPrefixComponent()` 和 `setSuffixComponent()` 方法来确定您想要图标在文本之前还是之后。

决定在组件文本之前或之后放置图标在很大程度上取决于目的和设计上下文。

### 图标放置：之前与之后 {#icon-placement-before-vs-after}

放置在组件文本之前的图标可以帮助用户迅速理解组件的主要操作或目的，尤其是对于像保存图标这样的公认图标。 组件文本之前的图标提供逻辑处理顺序，引导用户自然地进行预期操作，这对主要功能是立即操作的按钮很有帮助。

另一方面，将图标放在组件文本之后对于提供附加上下文或选项的操作效果很好，增强了清晰度和导航提示。 组件文本后的图标非常适合提供补充信息或引导用户进行方向流动的组件。

最终，一致性是关键。 一旦您选择了一种样式，请在整个网站上保持一致，以实现统一和用户友好的设计。

<ComponentDemo
path='/webforj/iconprefixsuffix'
files={['src/main/java/com/webforj/samples/views/icon/IconPrefixSuffixView.java']}
height='100px'
/>️

## 创建自定义池 {#creating-custom-pools}

除了利用现有图标集合，您还可以创建一个可以用于自定义徽标或头像的自定义池。 自定义图标池可以存储在中心目录或资源文件夹中（上下文），简化图标管理过程。 拥有一个自定义池可使应用程序创建更一致，并减少不同组件和模块之间的维护。

可以从包含 SVG 图像的文件夹创建自定义池，并使用 `IconPoolBuilder` 类。 从那里，您可以选择自定义池的名称，并使用该名称和 SVG 文件名称创建自定义图标组件。

```java
// 创建一个名为 "app-pool" 的自定义池，包含徽标和头像的图像。
IconPoolBuilder.fromDirectory("app-pool", "context://icons");
Icon customLogo = new Icon("logo", "app-pool");
Icon customAvatar = new Icon("avatar-default", "app-pool");
```

:::tip
确保设计图标具有相等的宽度和高度，因为 `Icon` 组件旨在占据一个正方形空间。
:::

### 自定义池工厂 {#custom-pool-factory}

您还可以在 webforJ 中为自定义池创建工厂类，就像 `FeatherIcon` 一样。 这使您能够在指定池中创建和管理图标资源，并允许代码完成。 每个图标可以通过 `create()` 方法实例化，该方法返回一个 `Icon`。 工厂类应提供特定于池的元数据，例如池名称和图标标识符，格式化为图像的文件名。 这种设计允许通过使用枚举常量轻松、标准化地访问自定义池中的图标资产，支持图标管理的可扩展性和可维护性。

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

以下代码显示了使用自定义池的两种不同方式。

```java
IconPoolBuilder.fromDirectory("app-pool", "context://icons");

// 使用自定义池和图像文件的名称创建图标
Icon customLogo = new Icon("logo", "app-pool");

// 使用前面代码片段中的自定义池工厂创建图标
Icon customLogo = AppPoolIcon.LOGO.create();
```

## 图标按钮 {#icon-buttons}
`Icon` 组件不可选择，但对于仅用图标最好表示的操作，例如通知或警报，您可以使用 `IconButton`。

```java
IconButton bell = new IconButton(FeatherIcon.BELL.create());
bell.onClick(e -> {
  showMessageDialog("您有新消息！", "叮当叮当！")
});
```

## 最佳实践

- **无障碍性：** 在图标上使用工具提示或标签，以使您的应用程序对依赖于屏幕阅读器的视力障碍用户可访问。
- **避免歧义：** 如果含义不明确或不广为人知，则避免使用图标。 如果用户不得不猜测图标代表什么，那就违背了目的。
- **适量使用图标：** 过多的图标可能会使用户感到不知所措，因此仅在增加清晰度或减少复杂性时使用图标。

## 风格
`Icon` 继承其直接父组件的主题，但您可以通过直接将主题应用到 `Icon` 来覆盖此主题。

### 主题
图标组件配备七种离散主题，可在不使用 CSS 的情况下快速样式设置。 这些主题是可以应用于图标的预定义样式，以改变其外观和视觉呈现。 它们提供了一种快速且一致的方法，可以自定义应用程序中图标的外观。

虽然每种主题都有很多用例，但一些示例用途包括：

- `DANGER`：最适合后果严重的操作，例如清除已填写的信息或永久删除帐户/数据。
- `DEFAULT`：适用于应用程序中不需要特别关注且较为通用的操作，例如切换设置。
- `PRIMARY`：适合作为页面的主要“号召性用语”，例如注册、保存更改或继续转到另一个页面。
- `SUCCESS`：非常适合可视化应用程序中元素的成功完成，例如表单的提交或注册过程的完成。 成功主题可以在成功操作完成后以编程方式应用。
- `WARNING`：用于指示用户即将执行可能存在风险的操作，例如在有未保存更改的情况下离开页面。 这些操作通常没有使用危险主题的操作那么重要。
- `GRAY`：适用于细微的操作，例如较小的设置或补充页面的操作，而不是主要功能。
- `INFO`：适合向用户提供额外的说明信息。

<TableBuilder name={['Icon', 'IconButton']} />
