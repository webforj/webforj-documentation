---
title: Packages and assets
sidebar_position: 30
sidebar_class_name: new-content
description: >-
  Declare npm packages, load a module straight from one, install build-only
  dependencies, rely on tree shaking, and import CSS and assets from a
  component's entry.
_i18n_hash: 3b538e3785ee74397f156dd38ef8506a
---
条目不仅依赖于自己的源。它会从 npm 导入包，直接加载一个模块，拉入样式表或图像，还可以来自您扩展的类或您依赖的库。此页面涵盖这些组件如何进入构建。

## 声明一个包 {#declaring-a-package}

`@BundlePackage` 声明了一个 npm 依赖项，该条目会导入。构建会收集类路径上的每个声明，并将其添加到您的项目的 [`package.json`](https://docs.npmjs.com/cli/v11/configuring-npm/package-json) 中，然后 [Bun](https://bun.sh/) 在编译之前安装它，因此在条目编译时包已存在。您对该文件的任何编辑都会得到保留，而声明没有包且没有自己 `package.json` 的项目则保持没有依赖，因此 npm 不会介入不需要它的构建。

Web 组件库是常见的情况。声明包，然后将 `@BundleEntry` 指向您想要的组件模块：

```java title="Ui5View.java"
@Route("/ui5")
@BundlePackage(value = "@ui5/webcomponents", version = "^2.0.0")
@BundleEntry("@ui5/webcomponents/dist/Button.js")
@BundleEntry("@ui5/webcomponents/dist/Input.js")
public class Ui5View extends Composite<FlexLayout> {

  private final FlexLayout self = getBoundComponent();

  public Ui5View() {
    self.setAlignment(FlexAlignment.CENTER)
      .setStyle("margin", "1em");

    Element input = new Element("ui5-input");
    input.setProperty("placeholder", "输入内容");

    Element button = new Element("ui5-button");
    button.setProperty("design", "Emphasized");
    button.setText("打招呼");
    button.addEventListener("click", e ->
      Toast.show("你好 " + input.getProperty("value"))
    );

    self.add(input, button);
  }
}
```

`version` 遵循 npm 版本语法，因此 `^2.0.0` 接受兼容的 2.x 版本。`@BundlePackage` 和 `@BundleEntry` 都是可重复的，因此一个类可以声明需要的多个包和加载多个模块。

### 文件条目或 npm 模块 {#a-file-entry-or-an-npm-module}

`@BundleEntry` 的值有两种情况：指向您在 `src/main/frontend` 下编写的文件的路径，或指向 npm 包内模块的路径。上述视图指定了 `@ui5/webcomponents` 内的模块路径，因此它并不携带自己的源文件。这些模块加载时会注册自己的自定义元素，这就是视图为何无需包装即消费 `ui5-input` 和 `ui5-button`。文件条目则指向您编写的 `.ts`、`.js` 或 `.css` 文件，以相同的方式编译。

### 构建依赖 {#build-dependencies}

仅用于编译源而非在运行时所需的包称为构建依赖。将 `dev = true` 设置在 `@BundlePackage` 上，构建将其安装为 `devDependency`：

```java
@BundlePackage(value = "typescript", version = "^5.0.0", dev = true)
```

策划的扩展使用此选项为其编译器所需的包，这就是 SCSS 源将 `sass` 作为构建依赖引入而在运行时不引入的原因。

## 仅导入您需要的内容 {#tree-shaking}

编译器仅包含一个条目实际导入的代码。命名 `@ui5/webcomponents/dist/Button.js` 会引入按钮组件及其所需内容，而不是库的其余部分。广泛的库仅对您所使用的部分收费，因此声明一个大型包并从中加载一个模块不会造成惩罚。

### 共享代码 {#shared-code}

当两个条目导入同一个包时，构建会将共享代码合并为两个都加载的块，而不是将其复制到每个条目中。几个组件构建在同一个库上，例如一组 Lit 元素，会在一个页面上共享该库的代码，而不是为每个元素支付一次。

## 条目的加载方式 {#how-entries-load}

一个条目生成一个脚本、一个样式表，或两者都有，而运行时在创建其类的组件时加载该输出，无论该组件被使用在哪里以及嵌套得多深。路由视图和布局是与其他任何组件一样的组件，因此条目绑定于组件的创建，而不是路由。来自类上注释的两个细节如下：

- **继承。** `@BundleEntry` 和 `@BundlePackage` 是可被继承的。基类声明了条目，即使子类没有添加任何自己的内容，仍会加载它。
- **调试条目。** 声明为 `@BundleEntry(value = "...", debug = true)` 的条目仅在应用处于调试模式时加载，这适合仅用于开发的诊断。

## 导入 CSS 和资源 {#importing-css-and-assets}

组件的条目通过导入来处理样式表和其他资产，无需注释和扩展。Bun 在编译时解析它们。

导入样式表以获得其副作用，打包器将其包含在条目的样式中。导入非代码资产，导入会给您一个可以使用的 URL：

```ts title="card/card.ts"
import './card.css';
import logoPath from './logo.svg';

const logo = new URL(logoPath, import.meta.url).href;
// 在元素内部使用 logo 作为图像源
```

将资产 URL 解析为 `import.meta.url`，而不是文档，因此它指向了无论输出在哪里提供的编译资产。

相反，将样式表导入为文本，并在影子根内部应用它以将样式范围限制到一个元素：

```ts title="swatch/swatch.ts"
import sheet from './swatch.css' with { type: 'text' };

class ColorSwatch extends HTMLElement {
  connectedCallback() {
    const root = this.attachShadow({ mode: 'open' });
    const style = document.createElement('style');
    style.textContent = sheet;
    root.append(style);
  }
}
```

条目也可以是一个普通的 `.css` 文件，没有脚本，绑定到与脚本条目相同的类。运行时将其作为视图的样式加载：

```java title="ThemeView.java"
@Route("/theme")
@BundleEntry("theme/theme.css")
public class ThemeView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public ThemeView() {
    self.add(new Div("由仅有的 CSS 打包项样式设置")
                 .addClassName("themed-label"));
  }
}
```
