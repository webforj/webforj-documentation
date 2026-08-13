---
title: SCSS and Sass
sidebar_position: 30
sidebar_class_name: new-content
description: >-
  Compile SCSS or Sass to CSS with the webforj-scss extension, bind a class to
  the stylesheet, and set a load path through bun.config.ts.
_i18n_hash: b278152eaf94d853a152b4d605c71981
---
[Sass](https://sass-lang.com/) 是一种样式表语言，它为 CSS 添加了变量、嵌套和函数，而 SCSS 是其类似 CSS 的语法。当存在 `.scss` 或 `.sass` 源文件时，SCSS 扩展就会激活，将其编译为 CSS，并作为样式加载。类绑定到编译后的样式表的方式与它绑定到脚本的方式相同。

## 示例 {#example}

```scss title="scss/box.scss"
@use 'tokens' as t;

.box {
  padding: 1rem;
  border: 2px solid t.$accent;
}
```

```java title="ScssView.java"
@Route("/scss")
@BundleEntry("scss/box.scss")
public class ScssView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public ScssView() {
    Div box = new Div().addClassName("box");
    self.add(box);
  }
}
```

## 选项 {#options}

SCSS 扩展将其选项转发到 [Dart Sass 编译器](https://sass-lang.com/documentation/js-api/interfaces/options/)。最常见的选项是加载路径，它允许一个入口 `@use` 一个位于其自身文件夹外部的部分：

```ts title="src/main/frontend/bun.config.ts"
export const options = {
  'webforj-scss': { loadPaths: ['styles'] }
};
```

通过该加载路径，`@use 'tokens'` 可以解析到 `src/main/frontend/styles` 下的一个部分。Sass 选项参考列表中列出了其余选项。
