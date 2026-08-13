---
title: Tailwind
sidebar_position: 60
sidebar_class_name: new-content
description: >-
  Turn on the webforj-tailwind extension, apply utility classes from a view, and
  understand how it generates and scans its own stylesheet.
_i18n_hash: f588624ebd738977bb8be4e9887141d1
---
[Tailwind CSS](https://tailwindcss.com/) 是一个实用优先的 CSS 框架，其类名对应一小组 CSS 声明。这是一个经过精心策划的扩展，因为大多数项目不使用它。你通过 id 启用它，方式和你打开任何扩展一样，见 [Enabled by id](/docs/managing-resources/bundler/extensions/overview#enabled-by-id)。当它开启时，它做一些其他扩展不做的事情：它生成自己的条目。

## 它是如何工作的 {#how-it-works}

Tailwind 扩展不是编译你编写的文件，而是扫描你的应用源代码以查找它们使用的实用类名，生成一个仅包含这些实用工具的样式表，并为每个视图加载它。视图随后将实用工具作为类名应用，无需导入：

```java title="TailwindView.java"
@Route("/tailwind")
public class TailwindView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public TailwindView() {
    Div card = new Div("由编译的 Tailwind 工具样式化");
    card.addClassName("flex", "items-center", "gap-4", "p-8", "rounded-lg",
        "bg-blue-500", "text-white");
    self.add(card);
  }
}
```

生成的样式表导入了 Tailwind 的主题和工具，但不包括其基础重置。重置，即 Tailwind 的预飞行（preflight），重样式化页面上的每个裸元素，这会覆盖 webforJ 已经应用于其组件的样式。省略它可以保持你添加的实用类正常工作，而不干扰你没有更改的组件。

由于实用工具来自于你视图使用的类名，[前端监视](/docs/configuration/deploy-reload/frontend-watch) 跟踪你的应用源代码以及 `src/main/frontend`。在视图中添加或移除一个实用类并保存，样式表会重新生成并直接补丁到页面，就像编辑你自己编写的样式表一样。

## 实用类的适用范围 {#where-utility-classes-reach}

:::warning 实用类为元素样式，而不是组件内部的内容
webforJ 组件使用阴影 DOM 渲染，保持其内部结构私密。添加到组件的实用类仅样式其外框，调整其间距、大小以及在布局中的位置，而不会影响内部绘制的元素。实用工具的应用方式是它们在布局容器或你构建的普通 `Div` 上的类名阅读方式，在那里没有边界可穿越，但不会影响构建组件的内部。

要为组件内部的内容着色，请使用组件暴露的样式：通过 `::part()` 和组件的 [CSS 自定义属性](/docs/styling/css-variables) 的 [shadow parts](/docs/styling/shadow-parts)，两者都在每个组件的样式参考中列出。保持实用工具用于布局和你自己的元素，使用组件自己的样式来重新调整组件。
:::

样式表携带在你的源代码中扫描到的实用类名，仅此而已。你在浏览器检查器中输入的类名尝试思想不会生效，因为它从未被编译进去。将该类放入视图并保存，监视将重新生成带有它的样式表。

当相同的实用工具组在许多视图中重复时，为其命名：定义一个 CSS 类一次，然后添加该类。少量的内联实用工具保持可读性，而手动重复的长字符串在你编辑时会漂移。

## 选项 {#options}

Tailwind 扩展不接受来自 `bun.config.ts` 的选项。它生成并扫描自己的样式表，而 Tailwind 本身是通过该样式表配置的，而不是通过扩展。
