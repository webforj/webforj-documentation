---
title: Icon
sidebar_position: 55
_i18n_hash: ab67367c75477c4366e5e86862dac630
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-icon" />
<DocChip chip='since' label='24.11' />
<JavadocLink type="icons" location="com/webforj/component/icons/Icon" top='true'/>

webforJ 的 `Icon` 组件允许您轻松地在用户界面中包含图标
图标是增强用户界面设计的基础部分，使用户能够更快地扫描屏幕上的可操作项目。
在您的应用中使用图标可以为导航和操作创建视觉提示，从而减少所需文本量并简化用户界面。您可以选择三种现有的图标库，webforJ 还提供从头创建新图标的选项。

:::tip 你知道吗？

一些组件，例如 `PasswordField` 和 `TimeField`，内置图标以帮助向最终用户传达含义。

:::

## 基础 {#basics}

每个 `Icon` 都被设计为可缩放矢量图形（SVG）图像，这意味着它可以轻松缩放到任何大小，而不会失去清晰度或质量。
此外，`Icon` 组件是通过内容分发网络（CDN）按需加载的，这有助于减少延迟并提高整体性能。

创建 `Icon` 时，您需要识别特定的图标库和图标的名称。
某些图标还提供通过 [variations](#variations) 在轮廓版或填充版之间的选择。

<ComponentDemo 
path='/webforj/iconbasics?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/icon/IconBasicsView.java'
height='100px'
/>

### 图标库 {#pools}

图标库是一组常用图标的集合，可实现轻松访问和重用。通过使用来自图标库的图标，您可以确保应用中的图标可识别且具有一致的风格。
使用 webforJ，您可以选择三种图标库，或实现自定义库。
每个库都有大量开源图标，免费使用。
使用 webforJ 可以让您有灵活性，从三种库中进行选择，并将它们用作独特的类，而无需直接下载任何图标。

| 图标库                                         | webforJ 类 |
| --------                                          | ------- |
| [Tabler](https://tabler-icons.io/)                | `TablerIcon` 和 `DwcIcon`。<br/>`DwcIcon` 是 Tabler 图标的子集。 |    
| [Feather](https://feathericons.com/)              | `FeatherIcon`    |
| [Font Awesome](https://fontawesome.com/search)    | `FontAwesomeIcon`   |

:::tip

如果您有兴趣创建自己的图标库，请参阅 [Creating custom pools](#creating-custom-pools)。

:::

一旦您选择了要在应用中包含的库，下一步就是指定您想要使用的图标名称。

### 名称 {#names}

要在您的应用中包含图标，您只需要图标库和图标名称。浏览图标库网站以找到您希望使用的图标，并将图标名称用作 `create()` 方法的参数。
此外，您还可以通过 `FeatherIcon` 和 `DwcIcon` 类的枚举来创建图标，使它们能够出现在代码补全中。

```java
// 从字符串名称创建图标
Icon image = TablerIcon.create("image");
// 从枚举创建图标
Icon image = FeatherIcon.IMAGE.create();
```

### 变体 {#variations}

您还可以通过利用变体进一步个性化图标。
某些图标允许您在轮廓版或填充版之间进行选择，从而根据您的偏好强调特定图标。`FontAwesomeIcon` 和 `Tabler` 图标提供变体。

#### `FontAwesomeIcon` 变体 {#fontawesomeicon-variations}

1. `REGULAR`：图标的轮廓版。这是默认值。
2. `SOLID`：图标的填充版。
3. `BRAND`：用于使用品牌图标的变体。

#### `TablerIcon` 变体 {#tablericon-variations}

1. `OUTLINE`：图标的轮廓版。这是默认值。
2. `FILLED`：图标的填充版。

```java
// Font Awesome 的填充变体图标
Icon music = FontAwesomeIcon.create("user", FontAwesomeIcon.Variate.SOLID);
```

以下演示展示了如何使用来自不同库的图标，应用变体并将它们无缝集成到组件中。

<ComponentDemo 
path='/webforj/iconvariations?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/icon/IconVariationsView.java'
height='100px'
/>

## 将图标添加到组件中 {#adding-icons-to-components}

通过使用插槽将图标集成到组件中。插槽提供灵活的选项来使组件更有用。向组件添加 `Icon` 有助于进一步向用户澄清预期的含义。
实现 `HasPrefixAndSuffix` 接口的组件可以包含 `Icon` 或其他有效组件。添加的组件可以放置在 `prefix` 和 `suffix` 插槽中，增强整体设计和用户体验。

使用 `prefix` 和 `suffix` 插槽，您可以使用 `setPrefixComponent()` 和 `setSuffixComponent()` 方法来确定您希望图标放在文本之前还是之后。

在组件上放置图标是否放在文本之前或之后，很大程度上取决于目的和设计上下文。

### 图标放置：前 VS 后 {#icon-placement-before-vs-after}

放置在组件文本之前的图标帮助用户快速理解组件的主要操作或目的，特别是对于像保存图标这样的通用图标。
组件文本之前的图标提供了逻辑的处理顺序，指导用户自然地进行预期的操作，这对主要功能是立即操作的按钮是有益的。

另一方面，将图标放置在组件文本之后，对于提供额外上下文或选项的操作是有效的，增强了清晰度和导航提示。
在组件文本之后的图标非常适合提供补充信息或指导用户进行方向性流程的组件。

最终，一致性是关键。一旦您选择了样式，请在您的网站上保持一致，以实现统一和用户友好的设计。

<ComponentDemo 
path='/webforj/iconprefixsuffix?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/icon/IconPrefixSuffixView.java'
height='100px'
/>️

## 创建自定义库 {#creating-custom-pools}

除了利用现有的图标集合之外，您还有机会创建可以用于自定义徽标或头像的自定义库。
自定义图标库可以存储在中央目录或资源文件夹（上下文）中，简化图标管理过程。
拥有自定义库可以使应用的创建更加一致，并减少不同组件和模块之间的维护。

自定义库可以通过包含 SVG 图像的文件夹创建，并使用 `IconPoolBuilder` 类。从那里，您可以选择自定义库的名称，并使用该名称与 SVG 文件名创建自定义图标组件。

```java
// 创建一个名为 "app-pool" 的自定义库，包含徽标和头像的图像。
IconPoolBuilder.fromDirectory("app-pool", "context://icons");
Icon customLogo = new Icon("logo", "app-pool");
Icon customAvatar = new Icon("avatar-default", "app-pool");
```

:::tip
确保设计图标具有相等的宽度和高度，因为 `Icon` 组件设计为占据正方形空间。
:::

### 自定义库工厂 {#custom-pool-factory}

您还可以像 `FeatherIcon` 一样在 webforJ 中为自定义库创建工厂类。这使您能够在指定的库中创建和管理图标资源，并允许代码补全。
每个图标可以通过 `create()` 方法实例化，返回一个 `Icon`。工厂类应提供库特定的元数据，例如库名称和图标的标识符，格式化为图像的文件名。
这种设计允许使用枚举常量轻松、标准化地访问自定义库中的图标资产，支持可扩展性和可维护性。

```java
/// 为 app-pool 创建自定义库工厂
public enum AppPoolIcon implements IconFactory {
  LOGO, AVATAR_DEFAULT;

  public Icon create() {
    return new Icon(String.valueOf(this), this.getPool());
  }

  /**
   * @return 图标的库名称
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

以下代码段展示了使用自定义库的两种不同方式。

```java
IconPoolBuilder.fromDirectory("app-pool", "context://icons");

// 使用自定义库和图像文件的名称创建图标
Icon customLogo = new Icon("logo", "app-pool");

// 使用前一个代码段中的自定义库工厂创建图标
Icon customLogo = AppPoolIcon.LOGO.create();
```

## 图标按钮 {#icon-buttons}
`Icon` 组件是不可选择的，但对于用图标最好表示的操作，例如通知或警报，您可以使用 `IconButton`。

 ```java
IconButton bell = new IconButton(FeatherIcon.BELL.create());
bell.onClick(e -> {
  showMessageDialog("您有一条新消息！", "叮咚！")
  });
```

## 最佳实践

- **可访问性：** 在图标上使用工具提示或标签，以使您的应用可供依赖屏幕阅读器的视障用户访问。
- **避免歧义：** 如果含义不清或未被广泛理解，尽量避免使用图标。如果用户必须猜测图标表示的内容，那么这个图标的存在就失去了意义。
- **适度使用图标：** 图标过多会让用户不知所措，因此只有在图标能够增加清晰度或减少复杂性时才使用。

## 样式
图标继承其直接父组件的主题，但您可以通过直接对 `Icon` 应用主题来覆盖这一点。

### 主题
图标组件内置七个离散主题，可快速进行样式设置，而无需使用 CSS。这些主题是可以应用于图标的预定义样式，以改变其外观和视觉呈现。它们提供了一种快速且一致的方式来定制应用中图标的外观。

针对各种主题有许多用例，以下是一些示例用法：

- `DANGER`：最适合后果严重的操作，例如清除填写的信息或永久删除账户/数据。
- `DEFAULT`：适合不需要特别关注的操作和通用操作，例如切换设置。
- `PRIMARY`：适合页面上的主要“行动号召”，例如注册、保存更改或继续到另一个页面。
- `SUCCESS`：非常适合可视化应用中元素的成功完成，例如表单提交或注册流程的完成。成功主题可以在成功操作完成后以编程方式应用。
- `WARNING`：用于指示用户即将执行可能风险的操作，例如在没有保存更改的情况下离开页面。这些操作通常影响较小，而非使用危险主题的操作。
- `GRAY`：适合微小的操作，例如次要设置或更补充页面的操作，而不是主要功能的一部分。
- `INFO`：适合向用户提供额外的澄清信息。

<TableBuilder name="Icon" />
