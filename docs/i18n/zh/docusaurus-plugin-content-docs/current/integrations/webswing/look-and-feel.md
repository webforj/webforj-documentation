---
title: Swing 外观和感觉
sidebar_position: 5
description: >-
  使用 DWC Look and Feel for Swing，为嵌入式 Swing 应用赋予 webforJ
  的配色、字体、按钮和焦点轮廓，并支持浅色或深色模式。
_i18n_title_hash: 3c0ddc5fa92ce0ba34ad9e57d896d42a
_i18n_description_hash: 8f86f1732f1d00ba4e1e87a16ff232f1
_i18n_hash: f7227a823be8a2f867fdf399bc56a1b4
---
当 `WebswingConnector` 在 webforJ 应用中嵌入 Swing 应用时，用户会在 webforJ 界面旁边看到 Swing 界面。使用 Swing 默认的外观时，两边看起来就像不同的应用。Swing 的 DWC 外观会让 Swing 应用使用 webforJ 所用 DWC 组件的字体、颜色、按钮和焦点轮廓。它提供浅色和深色两种调色板。

它是一个标准的 Swing 外观，打包在一个 JAR 中。将它添加到 Swing 应用的类路径，并在启动时选用即可。Swing 代码的其他部分无需更改。

:::info 尽力匹配
Swing 和 DWC 绘制控件的方式有所不同。两边可以使用相同的浅色或深色调色板，但在 Swing 界面和 webforJ 界面之间切换时，仍可能看到细微差异。
:::

## 试用演示 {#try-the-demo}

Order Desk 是一个已安装此主题的 Swing 应用。[下载 Order Desk 演示](https://dwc.style/docs/#/swing/downloads)，然后使用 Java 21 或更高版本运行：

```bash
java -jar dwc-swing-laf-demo.jar
```

选择 **View → DWC light** 或 **View → DWC dark**，然后选择 **View → Primary color**，并比较当前选中的订单、活动选项卡和 Save order 按钮。

Order Desk 中的每个控件都是标准 Swing 组件。切换截图下方的调色板和主色，然后选择图片即可放大。

<!-- vale off -->
<ThemeGallery alt="The Order Desk Swing demo" items={[
  { label: "Default", color: "#134ddf", light: "/img/swing-laf/order-desk-light-default.webp", dark: "/img/swing-laf/order-desk-dark-default.webp" },
  { label: "Violet", color: "#7c3aed", light: "/img/swing-laf/order-desk-light-violet.webp", dark: "/img/swing-laf/order-desk-dark-violet.webp" },
  { label: "Teal", color: "#0d9488", light: "/img/swing-laf/order-desk-light-teal.webp", dark: "/img/swing-laf/order-desk-dark-teal.webp" },
  { label: "Orange", color: "#ea580c", light: "/img/swing-laf/order-desk-light-orange.webp", dark: "/img/swing-laf/order-desk-dark-orange.webp" }
]} />
<!-- vale on -->

## 安装主题 {#install-the-theme}

[下载主题 JAR](https://dwc.style/docs/#/swing/downloads)，并将其添加到 Swing 应用的类路径中。它需要 Java 21 或更高版本，并包含两种调色板和 Inter 字体。

在 Swing 事件分派线程上，确保应用创建第一个窗口之前选用外观：

```java
try {
  UIManager.setLookAndFeel(new DwcLookAndFeel());
} catch (UnsupportedLookAndFeelException e) {
  throw new IllegalStateException(e);
}
```

若要使用深色调色板，请改用 `DwcDarkLookAndFeel`。这两个类都位于 `com.basis.dwc.swing.laf` 包中。如果应用通过类名选择外观，请使用 `com.basis.dwc.swing.laf.DwcLookAndFeel` 或 `com.basis.dwc.swing.laf.DwcDarkLookAndFeel`。

如果应用使用 Java 模块，请将 JAR 放在模块路径上，并声明对 `com.basis.dwc.swing.laf` 的依赖。

## 匹配 webforJ 主题 {#match-your-webforj-theme}

该主题读取与 [webforJ 主题](/docs/styling/themes)相同的令牌名称。在 Swing 应用中将令牌设为 webforJ 应用所用的值。

若要使用 webforJ 应用的主色，请在安装外观后设置主色种子：

```java
DwcLookAndFeel.setToken("--dwc-color-primary-seed", new Color(117, 67, 188));
```

主按钮、选中状态和焦点轮廓会采用该颜色。其他语义颜色（例如危险色）仍使用各自的调色板颜色。若要恢复调色板的主色，请将 `null` 作为值传入。

该主题默认使用 Inter。webforJ 通过 [`--dwc-font-family`](/docs/styling/typography) 使用平台字体。若要让 Swing 应用使用相同字体，请将令牌设为 `system-ui`：

```java
DwcLookAndFeel.setToken("--dwc-font-family", "system-ui");
```

更换字体可能会改变标签的宽度和字段的高度，因此请在构建完窗口内容后调用 pack。

## 设置单个控件的样式 {#style-one-control}

客户端属性可以更改单个控件的样式。此示例为字段添加标签，将其标记为必填，并将 Save 按钮设为主按钮：

```java
email.putClientProperty(DwcLookAndFeel.LABEL, "Delivery email");
email.putClientProperty(DwcLookAndFeel.REQUIRED, true);
save.putClientProperty(DwcLookAndFeel.THEME, ButtonTheme.PRIMARY);
```

若要在字段下方显示验证消息，请设置无效状态和辅助文本：

```java
boolean missing = email.getText().isBlank();
email.putClientProperty(DwcLookAndFeel.INVALID, missing);
email.putClientProperty(DwcLookAndFeel.HELPER_TEXT,
    missing ? "Enter a delivery email." : "");
```

[客户端属性参考](https://dwc.style/docs/#/swing/client-properties)列出了每种控件对应的键，包括表格、树、选项卡、滑块和对话框。

[完整主题指南](https://dwc.style/docs/#/swing/)介绍了对话框、键盘焦点和动效。
