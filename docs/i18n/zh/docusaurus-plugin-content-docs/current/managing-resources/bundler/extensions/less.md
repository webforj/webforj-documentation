---
title: Less
sidebar_position: 40
description: 使用 webforj-less 扩展将 Less 编译为 CSS，将类绑定到样式表，并通过 bun.config.ts 传递 Less 选项。
_i18n_title_hash: 1cfdf0e8d0c87a228c1f40d9bee7888b
_i18n_description_hash: 4378822835500b71adce33d9479455dd
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
