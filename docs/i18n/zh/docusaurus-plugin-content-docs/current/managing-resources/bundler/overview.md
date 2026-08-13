---
title: Frontend bundler
hide_table_of_contents: true
hide_giscus_comments: true
sidebar_class_name: new-content
description: >-
  Understand how the webforJ frontend bundler turns class-level annotations into
  compiled, per-route frontend assets, when to reach for it, and how a class
  binds to the entry it needs.
_i18n_hash: 1276a78ae5197924d6577eae5bafe73b
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

# 前端打包器 <DocChip chip='since' label='26.01' /> {#frontend-bundler}

前端打包器允许 webforJ 类从 [npm](https://www.npmjs.com/) 生态系统中提取资源，编写 [React](https://react.dev/)、[Svelte](https://svelte.dev/) 或 [Lit](https://lit.dev/) 组件，并创作 [SCSS](https://sass-lang.com/)，所有这些都可以通过一个 Java 项目完成，而无需安装或运行 Node 工具链。一个类通过注解指定所需的前端，当创建该类的组件时，构建过程将自动安装包、编译源代码并加载结果。

打包器作为 [webforJ 构建插件](/docs/configuration/build-plugin) 的一部分运行，该插件只需添加一次到你的 Maven 或 Gradle 构建中。编译特定类型源（如 SCSS、[Less](https://lesscss.org/)、[Tailwind](https://tailwindcss.com/) 等）的机制由 [扩展](/docs/managing-resources/bundler/extensions/overview) 来完成。

## 何时需要打包器 {#when-you-need-the-bundler}

webforJ 可以在没有打包器的情况下运行。如果你已经有了脚本或样式表，并且希望将其附加到组件或应用中，[资源注解](/docs/managing-resources/importing-assets) 可以在没有构建步骤、没有 npm 和没有编译的情况下实现这一点。

当前端不仅仅是一个静态文件时，打包器就发挥作用：

- 你希望以名称和版本从 npm 安装并编译一个包到你的应用中。
- 你希望使用 React、Svelte 或 Lit 编写组件，并从 Java 中使用它。
- 你希望创作 SCSS、Sass 或 Less，或者编译 Tailwind 工具。
- 你希望在构建过程中运行前端测试。

打包器是该工作的默认路径，它还做了资源注解所做的一切，因此采用它的项目不会失去更简单的选项。

## 将其添加到现有项目中 {#adding-it-to-an-existing-project}

打包器是可选择的，因此你可以将其添加到已经使用 [资源注解](/docs/managing-resources/importing-assets) 的应用中，仅在需要的地方调用它。

1. 将 [webforJ 构建插件](/docs/configuration/build-plugin) 添加到你的 Maven 或 Gradle 构建中。它为你管理 Bun，因此无需安装 Node 工具链。
2. 在 `src/main/frontend` 下编写你的前端源代码。
3. 使用 `@BundlePackage` 和 `@BundleEntry` 声明类所需的内容。

你现有的 `@StyleSheet`、`@JavaScript` 和其他资源注解将正常工作，因此当你希望使用包、已编译源或测试时，可以将资源移到打包器上，其余的可以保持不变。

## 将类绑定到入口 {#binding-a-class-to-an-entry}

`@BundleEntry` 将类与前端入口绑定，而 `@BundlePackage` 声明该入口导入的 npm 包。两者均位于类上，因此视图所需的前端会随视图一起传递。

```java title="GreetingView.java"
@Route("/greeting")
@BundlePackage(value = "lit", version = "^2.0.0")
@BundleEntry("greeting/hello-greeting.ts")
public class GreetingView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public GreetingView() {
    self.add(new Element("hello-greeting"));
  }
}
```

```ts title="greeting/hello-greeting.ts"
import { LitElement, html } from 'lit';
import { customElement } from 'lit/decorators.js';

@customElement("hello-greeting")
class HelloGreeting extends LitElement {
  render() {
    return html`<p>来自 webforJ 的问候</p>`;
  }
}
```

该入口注册了 `hello-greeting` 自定义元素并负责其渲染。Java 端通过 `new Element("hello-greeting")` 使用它，并监听其事件。在 `/greeting` 渲染时，构建将编译入口，安装 `lit`，并加载输出结果。

## 主题 {#topics}

<DocCardList className="topics-section" />
