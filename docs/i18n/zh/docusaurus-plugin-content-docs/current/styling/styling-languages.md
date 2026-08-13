---
title: Styling languages
sidebar_position: 11
sidebar_class_name: new-content
description: >-
  Author your styles in CSS, compile them from Sass or Less, or generate them
  with Tailwind, and load the result into a webforJ app.
_i18n_hash: 98eca77023e33bac367a1a250da900d7
---
您的样式以 CSS 的形式到达页面，但您不必将其写为 CSS。 webforJ 加载您编写的样式表，或从 Sass 或 Less 等预处理器编译出样式表，或从 Tailwind 生成样式表，结果是无论来自何处，都以相同的方式样式化您的视图。本节其余部分涵盖的 DWC 令牌、[CSS 自定义属性](/docs/styling/css-variables) 和 [阴影部分](/docs/styling/shadow-parts) 可以在其中任何一个应用。

## 普通 CSS {#plain-css}

您编写的样式表无需构建。将其附加到组件或应用中，使用 [`@StyleSheet`](/docs/managing-resources/importing-assets#importing-css-files)。当您已经运行 [前端打包器](/docs/managing-resources/bundler/overview) 时，您可以通过 `@BundleEntry` 将 `.css` 文件绑定到一个类，这样就将其作为该视图的样式加载。

## Sass 和 Less {#sass-and-less}

要使用变量、嵌套和函数在 [Sass](https://sass-lang.com/) 或 [Less](https://lesscss.org/) 中编写样式，请编写源文件，并让 [前端打包器](/docs/managing-resources/bundler/overview) 将其编译为 CSS。编译器是一个 [扩展](/docs/managing-resources/bundler/extensions/overview)，当存在其类型的源时，会自行开启，因此编写 `.scss`、`.sass` 或 `.less` 文件是它所需的唯一信号。将源绑定到类的方式与绑定样式表相同：

```java title="StyledView.java"
@Route("/styled")
@BundleEntry("styles/view.scss")
public class StyledView extends Composite<FlexLayout> {
  // 构建视图
}
```

该扩展将 `view.scss` 编译为 CSS，并为视图加载它。有关文件布局、加载路径和每个文件接受的选项，请参见 [SCSS 和 Sass](/docs/managing-resources/bundler/extensions/scss) 和 [Less](/docs/managing-resources/bundler/extensions/less)。

## Tailwind {#tailwind}

[Tailwind](https://tailwindcss.com/) 根据您的视图使用的实用类名称生成样式表，而不是根据您编写的文件生成样式表。启用扩展，然后以类名添加实用工具，而无需导入内容。 webforJ 省略了 Tailwind 的基础重置，以避免与您组件已有的样式冲突，实用工具样式应用于您放置它的元素，而不是组件内部。有关它如何生成和作用于其样式表，以及实用类适用和不适用的位置，请参见 [Tailwind 扩展](/docs/managing-resources/bundler/extensions/tailwind)。

## 另一种语言 {#another-language}

每种语言的编译器都是一个打包器扩展，并且该模型是开放的。要在 webforJ 不提供编译器的语言中编写样式，请编写一个小扩展来贡献该编译器，使用与 Sass 和 Less 相同的约定。有关详细信息，请参见 [编写自己的扩展](/docs/managing-resources/bundler/extensions/writing-your-own)。
