---
title: Icon
sidebar_position: 55
_i18n_hash: 2da7d4e8288df67fc46f2a3ba84e12ee
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-icon" />
<DocChip chip='since' label='24.11' />
<JavadocLink type="icons" location="com/webforj/component/icons/Icon" top='true'/>

webforJ `Icon` 组件允许您在用户界面中轻松包含图标。  
图标是增强用户界面设计的基本部分，使用户能够更快地扫描屏幕上的可操作项目。  
在您的应用程序中使用图标为导航和操作创建了视觉线索，这可以减少所需的文本量并简化用户界面。您可以选择三种现有的图标池，webforJ 还允许您从头开始创建新的图标池。

:::tip 您知道吗？

某些组件，如 `PasswordField` 和 `TimeField`，内置图标以帮助传达信息给最终用户。

:::

## 基础 {#basics}

每个 `Icon` 都被设计为可缩放矢量图形（SVG）图像，这意味着它可以轻松地缩放到任何大小而不会失去清晰度或质量。  
此外，`Icon` 组件根据需要从内容分发网络（CDN）加载，这有助于减少延迟并改善整体性能。

在创建 `Icon` 时，您需要确定特定的图标池和图标的名称。某些图标也提供通过 [variations](#variations) 选择轮廓或填充版本的选项。

<ComponentDemo 
path='/webforj/iconbasics?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/icon/IconBasicsView.java'
height='100px'
/>

### 图标池 {#pools}

图标池是一个常用图标的集合，便于访问和重用。通过使用图标池中的图标，您可以确保您的应用程序中的图标是可识别的，并且具有一致的样式。  
使用 webforJ，您可以从三个池中选择，或实现一个自定义池。  
每个池都有一个包含大量开源图标的广泛集合，您可以自由使用。  
使用 webforJ，您可以灵活地从三个池中选择，并将它们用作唯一的类，而无需直接下载任何图标。

| 图标池                                           | webforJ 类 |
| --------                                        | ------- |
| [Tabler](https://tabler-icons.io/)              | `TablerIcon` 和 `DwcIcon`.<br/>`DwcIcon` 是 Tabler 图标的一个子集。 |    
| [Feather](https://feathericons.com/)            | `FeatherIcon`    |
| [Font Awesome](https://fontawesome.com/search)  | `FontAwesomeIcon`   |

:::tip

如果您有兴趣创建自己的图标池，请参阅 [创建自定义池](#creating-custom-pools)。

:::

一旦选择了要包含在应用程序中的池，下一步是指定您想要使用的图标名称。

### 名称 {#names}

要将图标包含在应用程序中，您只需要图标池和图标名称。浏览图标池网站以查找您希望使用的图标，并将图标名称用作 `create()` 方法的参数。  
此外，您可以通过枚举为 `FeatherIcon` 和 `DwcIcon` 类创建图标，使它们在代码补全中显示。

```java
// 从字符串名称创建图标
Icon image = TablerIcon.create("image");
// 从枚举创建图标
Icon image = FeatherIcon.IMAGE.create();
```

### 变化 {#variations}

您可以通过利用变化进一步个性化图标。  
某些图标允许您选择轮廓或填充版本，让您根据偏好强调特定图标。`FontAwesomeIcon` 和 `Tabler` 图标提供变化。

#### `FontAwesomeIcon` 变化 {#fontawesomeicon-variations}

1. `REGULAR`: 图标的轮廓变化。这是默认的。
2. `SOLID`: 图标的填充变化。
3. `BRAND`: 使用品牌图标时的变化。

#### `TablerIcon` 变化 {#tablericon-variations}

1. `OUTLINE`: 图标的轮廓变化。这是默认的。
2. `FILLED`: 图标的填充变化。

```java
// 从 Font Awesome 创建图标的填充变化
Icon music = FontAwesomeIcon.create("user", FontAwesomeIcon.Variate.SOLID);
```

以下演示展示了如何使用来自不同池的图标，应用变化，并将其无缝集成到组件中。

<ComponentDemo 
path='/webforj/iconvariations?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/icon/IconVariationsView.java'
height='100px'
/>

## 将图标添加到组件 {#adding-icons-to-components}

通过使用插槽将图标集成到组件中。插槽提供灵活的选项，使组件更有用。  
在组件中添加 `Icon` 是有益的，以进一步澄清用户的意图。实现 `HasPrefixAndSuffix` 接口的组件可以包含 `Icon` 或其他有效组件。添加的组件可以放置在 `prefix` 和 `suffix` 插槽中，并可以增强整体设计和用户体验。

使用 `prefix` 和 `suffix` 插槽，您可以通过 `setPrefixComponent()` 和 `setSuffixComponent()` 方法确定要将图标放置在文本之前还是之后。

决定是否在组件的文本之前或之后放置图标主要取决于目的和设计上下文。

### 图标放置：之前 VS 之后 {#icon-placement-before-vs-after}

放置在组件文本之前的图标帮助用户快速理解组件的主要操作或目的，尤其是对于诸如保存图标等通用图标。  
在组件的文本之前的图标提供了一个逻辑处理顺序，指导用户自然地进行预期操作，这对于立即采取行动的按钮非常有益。

另一方面，放置在组件文本之后的图标对于提供额外的上下文或选项的操作是有效的，提高了清晰度和导航线索。  
放置在组件文本之后的图标对于提供补充信息或引导用户方向流动的组件是理想的。

最终，一致性是关键。一旦选择了一种样式，请在您的网站上保持一致，以实现连贯且用户友好的设计。

<ComponentDemo 
path='/webforj/iconprefixsuffix?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/icon/IconPrefixSuffixView.java'
height='100px'
/>️

## 创建自定义池 {#creating-custom-pools}

除了使用现有的图标集，您还可以创建一个自定义池，用于自定义徽标或头像。  
自定义图标池可以存储在中央目录或资源文件夹（上下文）中，从而简化图标管理过程。  
拥有自定义池可以使应用创建更一致，并减少不同组件和模块之间的维护。

可以通过包含 SVG 图像的文件夹和使用 `IconPoolBuilder` 类来创建自定义池。然后，您可以选择自定义池的名称并将其与 SVG 文件名一起使用，以创建自定义图标组件。

```java
// 创建一个名为 "app-pool" 的自定义池，具有徽标和头像的图像。
IconPoolBuilder.fromDirectory("app-pool", "context://icons");
Icon customLogo = new Icon("logo", "app-pool");
Icon customAvatar = new Icon("avatar-default", "app-pool");
```

:::tip
确保设计具有相等宽度和高度的图标，因为 `Icon` 组件设计为占据正方形空间。
:::

### 自定义池工厂 {#custom-pool-factory}

您还可以在 webforJ 中为自定义池创建一个工厂类，就像 `FeatherIcon` 一样。这使您能够在指定的池中创建和管理图标资源，并允许代码补全。  
每个图标可以通过 `create()` 方法实例化，该方法返回一个 `Icon`。工厂类应提供池特定的元数据，例如池名称和图标标识符，格式化为图像的文件名称。  
该设计允许通过使用枚举常量轻松、标准化地访问自定义池中的图标资产，从而支持图标管理的可扩展性和可维护性。

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

// 使用自定义池和图像文件的名称创建图标
Icon customLogo = new Icon("logo", "app-pool");

// 使用前面代码片段中的自定义池工厂创建图标
Icon customLogo = AppPoolIcon.LOGO.create();
```

## 图标按钮 {#icon-buttons}
`Icon` 组件不可选择，但对于仅用图标表示的操作，例如通知或警报，可以使用 `IconButton`。

 ```java
IconButton bell = new IconButton(FeatherIcon.BELL.create());
bell.onClick(e -> {
  showMessageDialog("您有新消息！", "叮当声！")
  });
```

## 最佳实践

- **可访问性：** 对图标使用工具提示或标签，以使您的应用程序对依赖屏幕阅读器的视觉障碍用户可访问。
- **避免歧义：** 如果意义不清楚或未广泛理解，请避免使用图标。如果用户必须猜测图标的含义，则失去了目的。
- **节约使用图标：** 过多的图标可能使用户不知所措，因此仅在添加清晰度或减少复杂性时使用图标。

## 样式
图标继承其直接父组件的主题，但您可以通过直接对 `Icon` 应用主题来覆盖此主题。

### 主题
图标组件内置七个独立的主题，便于快速样式而无需使用 CSS。这些主题是可以应用于图标的预定义样式，以改变它们的外观和视觉表现。它们提供了一种快速且一致的方法来定制整个应用程序中图标的外观。

虽然各种主题有很多使用场景，以下是其中一些示例：

- `DANGER`： 最适合后果严重的操作，例如清除填写的信息或永久删除帐户/数据。
- `DEFAULT`： 适合应用程序中不需要特别关注且通用的操作，例如切换设置。
- `PRIMARY`： 适合页面上的主要“号召性用语”，如注册、保存更改或继续到另一个页面。
- `SUCCESS`： 非常适合在应用中可视化成功的元素，例如表单的提交或注册过程的完成。成功主题可以在成功操作完成后以编程方式应用。
- `WARNING`： 有助于指示用户即将执行潜在风险的操作，例如从未保存更改的页面导航。此类操作通常不如使用危险主题的操作那样影响深远。
- `GRAY`： 适合微小的操作，例如次要设置或更附属的页面操作，而不是主要功能的一部分。
- `INFO`： 适合向用户提供额外的澄清信息。

<TableBuilder name="Icon" />
