---
title: Icon
sidebar_position: 55
_i18n_hash: 8350df59fb9ce335776bc0556861cda5
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-icon" />
<DocChip chip='since' label='24.11' />
<JavadocLink type="icons" location="com/webforj/component/icons/Icon" top='true'/>

`Icon` 组件显示可以缩放到任何大小而不失去质量的图标。您可以从三个内置图标池中选择，或创建自定义图标池。图标作为导航和操作的视觉提示，减少了用户界面中文本标签的需求。

<!-- INTRO_END -->

## 基础 {#basics}

每个 `Icon` 都被设计为可缩放的矢量图形 (SVG) 图像，这意味着它可以轻松缩放到任何大小而不会失去清晰度或质量。此外，`Icon` 组件是按需从内容分发网络 (CDN) 加载的，这有助于减少延迟并提高整体性能。

创建 `Icon` 时，您需要确定特定的图标池和图标名称。有些图标还提供通过 [variations](#variations) 选择轮廓或填充版本的选项。

<ComponentDemo 
path='/webforj/iconbasics?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/icon/IconBasicsView.java'
height='100px'
/>

:::tip 您知道吗？
有些组件，如 `PasswordField` 和 `TimeField`，具有内置图标，可帮助向最终用户传达意义。
:::

### 图标池 {#pools}

图标池是一个常用图标的集合，便于访问和重用。通过使用图标池中的图标，您可以确保您应用程序中的图标易于识别且共享一致的风格。使用 webforJ 您可以选择三个池，或实现自定义池。每个池都拥有广泛的开源图标，可以自由使用。使用 webforJ 使您能够选择三个池并将其作为独特的类使用，而无需直接下载任何图标。

| 图标池                                         | webforJ 类 |
| --------                                          | ------- |
| [Tabler](https://tabler-icons.io/)                | `TablerIcon` 和 `DwcIcon`。<br/>`DwcIcon` 是 Tabler 图标的一个子集。|    
| [Feather](https://feathericons.com/)              | `FeatherIcon`    |
| [Font Awesome](https://fontawesome.com/search)    | `FontAwesomeIcon`   |

:::tip

如果您有兴趣创建自己的图标池，请参阅 [Creating custom pools](#creating-custom-pools)。

:::

一旦您选择了要纳入应用程序的池，下一步是指定您要使用的图标名称。

### 名称 {#names}

要在应用程序中包含图标，您只需要图标池和图标名称。浏览图标池网站，找到您希望使用的图标，并将图标名称用作 `create()` 方法的参数。此外，您还可以通过 `FeatherIcon` 和 `DwcIcon` 类的枚举创建图标，允许它们出现在代码完成中。

```java
// 从字符串名称创建图标
Icon image = TablerIcon.create("image");
// 从枚举创建图标
Icon image = FeatherIcon.IMAGE.create();
```

### 变体 {#variations}

您可以通过使用变体进一步个性化图标。某些图标允许您在轮廓或填充版本之间进行选择，允许您根据个人喜好强调特定图标。`FontAwesomeIcon` 和 `Tabler` 图标提供变体。

#### `FontAwesomeIcon` 变体 {#fontawesomeicon-variations}

1. `REGULAR`：图标的轮廓变体。这是默认选项。
2. `SOLID`：图标的填充变体。
3. `BRAND`：用于使用品牌图标的变体。

#### `TablerIcon` 变体 {#tablericon-variations}

1. `OUTLINE`：图标的轮廓变体。这是默认选项。
2. `FILLED`：图标的填充变体。

```java
// 从 Font Awesome 创建图标的填充变体
Icon music = FontAwesomeIcon.create("user", FontAwesomeIcon.Variate.SOLID);
```

以下演示展示了如何使用来自不同图标池的图标，应用变体，并将它们无缝集成到组件中。

<ComponentDemo 
path='/webforj/iconvariations?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/icon/IconVariationsView.java'
height='100px'
/>

## 将图标添加到组件 {#adding-icons-to-components}

使用插槽将图标集成到组件中。插槽提供灵活的选项，可以使组件更有用。将 `Icon` 添加到组件中有助于进一步向用户澄清预期含义。实现 `HasPrefixAndSuffix` 接口的组件可以包含 `Icon` 或其他有效组件。添加的组件可以放置在 `prefix` 和 `suffix` 插槽中，增强整体设计和用户体验。

通过使用 `prefix` 和 `suffix` 插槽，您可以确定是否希望将图标放在文本之前或之后，使用 `setPrefixComponent()` 和 `setSuffixComponent()` 方法。

在组件文本前后放置图标的决定在很大程度上取决于目的和设计上下文。

### 图标放置：前 VS 后 {#icon-placement-before-vs-after}

放置在组件文本前的图标帮助用户快速理解组件的主要操作或目的，特别是对于像保存图标这样的通用图标。组件文本前的图标提供了合乎逻辑的处理顺序，自然引导用户进行预期操作，这对于主要功能是立即操作的按钮尤其有益。

另一方面，放置在组件文本后的图标对于提供更多上下文或选项的操作有效，增强了清晰度和导航提示。文本后面的图标非常适合于提供补充信息或引导用户进行方向流动的组件。

最终，一致性是关键。一旦您选择了风格，请在您的网站上保持一致，以实现连贯且用户友好的设计。

<ComponentDemo 
path='/webforj/iconprefixsuffix?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/icon/IconPrefixSuffixView.java'
height='100px'
/>️

## 创建自定义池 {#creating-custom-pools}

除了利用现有图标集合外，您还可以选择创建一个可用于自定义徽标或头像的自定义池。自定义图标池可以存储在集中目录中或在资源文件夹 (context) 中，从而简化图标管理过程。拥有自定义池使应用程序创建更一致，减少不同组件和模块之间的维护。

自定义池可以从包含 SVG 图像的文件夹创建，使用 `IconPoolBuilder` 类。在此之后，您可以选择自定义池的名称，并使用该名称与 SVG 文件名组合创建自定义图标组件。

```java
// 创建一个名为 "app-pool" 的自定义池，该池包含徽标和头像的图像。
IconPoolBuilder.fromDirectory("app-pool", "context://icons");
Icon customLogo = new Icon("logo", "app-pool");
Icon customAvatar = new Icon("avatar-default", "app-pool");
```

:::tip
确保设计图标的宽度和高度相等，因为 `Icon` 组件设计为占据方形空间。
:::

### 自定义池工厂 {#custom-pool-factory}

您也可以在 webforJ 中为自定义池创建工厂类，就像 `FeatherIcon` 一样。这使您能够在指定的池中创建和管理图标资源，并允许代码完成。每个图标都可以通过 `create()` 方法实例化，该方法返回一个 `Icon`。工厂类应该提供特定于池的元数据，例如池名称和图标的标识符，这些名称格式化为图像的文件名。这种设计允许使用枚举常量轻松、标准化访问自定义池中的图标资产，从而支持图标管理的可扩展性和可维护性。

```java
/// 为应用程序池创建自定义池工厂
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

// 使用自定义池和图像文件的名称创建图标
Icon customLogo = new Icon("logo", "app-pool");

// 使用前一段代码中的自定义池工厂创建图标
Icon customLogo = AppPoolIcon.LOGO.create();
```

## 图标按钮 {#icon-buttons}
`Icon` 组件是不可选择的，但对于仅用图标表示的操作，如通知或警报，您可以使用 `IconButton`。

 ```java
IconButton bell = new IconButton(FeatherIcon.BELL.create());
bell.onClick(e -> {
  showMessageDialog("您有新消息！", "叮咚！");
});
```

## 最佳实践

- **可获取性：** 使用工具提示或标签在图标上，使您的应用程序对依赖屏幕阅读器的视觉障碍用户可访问。
- **避免模糊性：** 如果含义不明确或未广泛理解，请避免使用图标。如果用户必须猜测图标表示的内容，这就失去了目的。
- **适量使用图标：** 过多的图标可能会使用户感到不知所措，因此只有在增加清晰度或减少复杂性时才使用图标。

## 样式
`Icon` 继承其直接父组件的主题，但您可以通过直接应用主题到 `Icon` 来覆盖这一点。

### 主题
图标组件内置七个离散主题，便于快速样式设置，而无需使用 CSS。这些主题是预定义样式，可以应用于图标以改变其外观和视觉表现。它们提供了一种快速且一致的方式，以在整个应用程序中自定义图标的外观。

虽然每种主题都有许多用例，但以下是一些示例用途：

- `DANGER`：最佳用于具有严重后果的操作，如清除填写的信息或永久删除帐户/数据。
- `DEFAULT`：适用于不需要特别关注且较为通用的操作，例如切换设置。
- `PRIMARY`：适用于页面上主要的 "号召行动"，例如注册、保存更改或继续到另一个页面。
- `SUCCESS`：非常适合可视化应用程序中元素成功完成的功能，例如表单提交或注册流程完成。成功主题可以在成功操作完成后以编程方式应用。
- `WARNING`：有助于指示用户即将执行潜在风险的操作，例如离开具有未保存更改的页面。这些操作往往的影响小于使用危险主题的操作。
- `GRAY`：适合用于细微的操作，例如较小的设置或与页面的主要功能无关的操作。
- `INFO`：适合为用户提供额外的澄清信息。

<TableBuilder name="Icon" />
