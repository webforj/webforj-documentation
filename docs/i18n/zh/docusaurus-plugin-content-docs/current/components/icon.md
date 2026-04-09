---
title: Icon
sidebar_position: 55
_i18n_hash: 5c32d2def53818005b15e22290fb3d52
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-icon" />
<DocChip chip='since' label='24.11' />
<JavadocLink type="icons" location="com/webforj/component/icons/Icon" top='true'/>

`Icon` 组件显示可以缩放到任何大小而不会失去质量的图标。您可以从三种内置图标池中选择或创建自定义图标。图标作为导航和操作的视觉提示，减少了界面中对文本标签的需求。

<!-- INTRO_END -->

## 基础 {#basics}

每个 `Icon` 设计为可缩放矢量图形（SVG）图像，这意味着它可以轻松缩放到任何大小而不会失去清晰度或质量。此外，`Icon` 组件根据需要从内容分发网络（CDN）加载，这有助于减少延迟并提高整体性能。

创建 `Icon` 时，您需要确定特定的图标池及图标名称。某些图标还提供通过 [variations](#variations) 选择轮廓或填充版本的选项。

<ComponentDemo 
path='/webforj/iconbasics?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/icon/IconBasicsView.java'
height='100px'
/>

:::tip 你知道吗？
一些组件，如 `PasswordField` 和 `TimeField`，有内置图标来帮助向最终用户传达含义。
:::

### 图标池 {#pools}

图标池是一系列常用图标的集合，使访问和重用变得简单。通过使用图标池中的图标，您可以确保应用中的图标是可识别的并且具有一致的风格。使用 webforJ，您可以选择三种池，或实现一个自定义池。每个池都有大量可供使用的开源图标。使用 webforJ 使您能够从三个池中进行选择，并将它们用作独特的类，而无需直接下载任何图标。

| 图标池                                         | webforJ 类 |
| --------                                          | ------- |
| [Tabler](https://tabler-icons.io/)                | `TablerIcon` 和 `DwcIcon`。<br/>`DwcIcon` 是 Tabler 图标的子集。 |    
| [Feather](https://feathericons.com/)              | `FeatherIcon`    |
| [Font Awesome](https://fontawesome.com/search)    | `FontAwesomeIcon`   |

:::tip

如果您有兴趣创建自己的图标池，请参阅 [创建自定义池](#creating-custom-pools)。

:::

一旦您选择了要在应用中包含的池，下一步是指定您想要使用的图标名称。

### 名称 {#names}

要在您的应用中包含图标，您只需要图标池和图标名称。浏览图标池网站，找到您希望使用的图标，并使用图标名称作为 `create()` 方法的参数。此外，您还可以通过 `FeatherIcon` 和 `DwcIcon` 类创建枚举来创建图标，使它们在代码补全中可见。

```java
// 从字符串名称创建一个图标
Icon image = TablerIcon.create("image");
// 从枚举创建一个图标
Icon image = FeatherIcon.IMAGE.create();
```

### 变体 {#variations}

您可以使用变体进一步个性化图标。某些图标允许您选择轮廓或填充版本，使您可以根据偏好突出某个特定图标。`FontAwesomeIcon` 和 `Tabler` 图标提供变体。

#### `FontAwesomeIcon` 变体 {#fontawesomeicon-variations}

1. `REGULAR`：图标的轮廓变体。这是默认值。
2. `SOLID`：图标的填充变体。
3. `BRAND`：当您使用品牌图标时的变体。

#### `TablerIcon` 变体 {#tablericon-variations}

1. `OUTLINE`：图标的轮廓变体。这是默认值。
2. `FILLED`：图标的填充变体。

```java
// 从 Font Awesome 创建图标的填充变体
Icon music = FontAwesomeIcon.create("user", FontAwesomeIcon.Variate.SOLID);
```

以下演示展示了如何使用来自不同池的图标，应用变体，并将它们无缝集成到组件中。

<ComponentDemo 
path='/webforj/iconvariations?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/icon/IconVariationsView.java'
height='100px'
/>

## 将图标添加到组件 {#adding-icons-to-components}

使用插槽将图标集成到您的组件中。插槽提供灵活的选项，使组件更有用。为组件添加 `Icon` 来进一步向用户阐明意图是有益的。实现 `HasPrefixAndSuffix` 接口的组件可以包含 `Icon` 或其他有效组件。添加的组件可以放置在 `prefix` 和 `suffix` 插槽中，可以增强整体设计和用户体验。

使用 `prefix` 和 `suffix` 插槽，您可以通过 `setPrefixComponent()` 和 `setSuffixComponent()` 方法来决定是否希望图标放在文本之前或之后。

决定在组件文本之前或之后放置图标在很大程度上取决于目的和设计上下文。

### 图标位置：之前 VS 之后 {#icon-placement-before-vs-after}

在组件文本之前放置的图标可以帮助用户快速理解组件的主要操作或目的，尤其是对于像保存图标这样的普遍认可的图标。组件文本之前的图标提供了一个逻辑处理顺序，自然引导用户完成预期操作，这对于主要功能是立即操作的按钮是有益的。

另一方面，在组件文本之后放置图标在提供附加上下文或选项的操作中很有效，这增强了清晰度和导航提示。组件文本之后的图标适合那些提供补充信息或引导用户沿着方向流动的组件。

最终，一致性是关键。一旦您选择了一种风格，请在整个网站上保持一致，以实现统一和用户友好的设计。

<ComponentDemo 
path='/webforj/iconprefixsuffix?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/icon/IconPrefixSuffixView.java'
height='100px'
/>️

## 创建自定义池 {#creating-custom-pools}

除了利用现有图标集合，您还可以创建一个可用于自定义徽标或头像的自定义池。自定义图标池可以存储在一个集中目录中或在资源文件夹（上下文）中，从而简化图标管理过程。拥有自定义池使应用创建更为一致，并减少不同组件和模块之间的维护工作。

自定义池可以从包含 SVG 图像的文件夹创建，并通过使用 `IconPoolBuilder` 类。然后，您可以选择自定义池的名称，并使用该名称与 SVG 文件名一起创建自定义图标组件。

```java
// 创建一个名为 "app-pool" 的自定义池，其中包含徽标和头像的图像。
IconPoolBuilder.fromDirectory("app-pool", "context://icons");
Icon customLogo = new Icon("logo", "app-pool");
Icon customAvatar = new Icon("avatar-default", "app-pool");
```

:::tip
确保设计图标具有相等的宽度和高度，因为 `Icon` 组件被设计为占用正方形空间。
:::

### 自定义池工厂 {#custom-pool-factory}

您还可以像 `FeatherIcon` 一样在 webforJ 中为自定义池创建工厂类。这使您能够在指定池内创建和管理图标资源，并允许代码补全。每个图标可以通过 `create()` 方法实例化，返回一个 `Icon`。工厂类应提供特定于池的元数据，例如池名称和图标的标识符，格式与图像的文件名相同。此设计允许通过枚举常量轻松、规范地访问自定义池中的图标资产，从而支持图标管理的可扩展性和可维护性。

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

以下代码段展示了使用自定义池的两种不同方式。

```java
IconPoolBuilder.fromDirectory("app-pool", "context://icons");

// 使用自定义池和图像文件的名称创建一个图标
Icon customLogo = new Icon("logo", "app-pool");

// 使用前面代码段中的自定义池工厂创建一个图标
Icon customLogo = AppPoolIcon.LOGO.create();
```

## 图标按钮 {#icon-buttons}
`Icon` 组件不可选择，但对于用图标表示的最佳操作，例如通知或警报，您可以使用 `IconButton`。

```java
IconButton bell = new IconButton(FeatherIcon.BELL.create());
bell.onClick(e -> {
  showMessageDialog("您有新消息！", "叮咚！")
});
```

## 最佳实践

- **可访问性**：在图标上使用工具提示或标签，以使您的应用对依赖屏幕阅读器的视觉障碍用户可访问。
- **避免模糊**：如果意思不明确或不广泛被理解，请避免使用图标。如果用户不得不猜测图标表示的内容，那就失去了目的。
- **适度使用图标**： quá nhiều图标可能会让用户感到不知所措，因此仅在图标能够增加清晰度或减少复杂性时使用图标。

## 样式
图标继承其直接父组件的主题，但您可以通过直接将主题应用于 `Icon` 来覆盖此设置。

### 主题
图标组件提供七种独特的主题，可快速样式化而无需使用 CSS。这些主题是可以应用于图标以改变其外观和视觉表现的预定义样式。它们提供了一种快速且一致的方式，以自定义应用中图标的外观。

虽然各种主题有许多用例，但一些示例用法如下：

- `DANGER`：最佳用于可能有严重后果的操作，例如清除填写的信息或永久删除帐户/数据。
- `DEFAULT`：适合整个应用中不需要特别关注和常规的操作，例如切换设置。
- `PRIMARY`：适合作为页面上的主要 "号召性用语"，例如注册、保存更改或继续到另一个页面。
- `SUCCESS`：极好地可视化应用中元素的成功完成，例如提交表单或完成注册过程。成功主题可以在成功操作完成后通过编程应用。
- `WARNING`：方便指示用户即将执行的潜在风险操作，例如导航到具有未保存更改的页面。这些操作的影响通常比使用 Danger 主题的操作要小得多。
- `GRAY`：适合于不太引人注目的操作，例如次要设置或对页面的补充操作，而不是主要功能的一部分。
- `INFO`：适合于向用户提供额外的说明性信息。 

<TableBuilder name={['Icon', 'IconButton']} />
