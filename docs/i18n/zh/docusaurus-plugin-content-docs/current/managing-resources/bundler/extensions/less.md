---
title: Less
sidebar_position: 40
sidebar_class_name: new-content
description: >-
  Compile Less to CSS with the webforj-less extension, bind a class to the
  stylesheet, and pass Less options through bun.config.ts.
_i18n_hash: febd9ab468f73672d97e3a0048d6371b
---
[Less](https://lesscss.org/) 是一种样式表语言，可以为 CSS 添加变量、混合以及嵌套。当存在 `.less` 源文件时，Less 扩展会激活并将其编译为 CSS。

## 示例 {#example}

```less title="less/box.less"
@accent: #2e9e6b;

.box {
  padding: 1rem;
  border: 2px solid @accent;
}
```

```java title="LessView.java"
@Route("/less")
@BundleEntry("less/box.less")
public class LessView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public LessView() {
    Div box = new Div().addClassName("box");
    self.add(box);
  }
}
```

## 选项 {#options}

Less 扩展将其选项转发到 [Less](https://lesscss.org/usage/#less-options)，并调用它来渲染每个源文件。文件名由系统为您设置。其余部分请在 `bun.config.ts` 的 `webforj-less` 下进行设置：

```ts title="src/main/frontend/bun.config.ts"
export const options = {
  'webforj-less': { /* Less options */ }
};
```
