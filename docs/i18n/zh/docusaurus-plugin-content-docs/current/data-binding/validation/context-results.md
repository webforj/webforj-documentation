---
sidebar_position: 4
title: Context Results
_i18n_hash: 15fc4551d1ed2f2b5e35785975e66946
---
当您从用户界面写入数据到模型时，`BindingContext` 的 `write` 方法会触发验证。验证结果决定数据是否可接受。

## 处理验证结果 {#processing-validation-results}

您可以处理验证结果以向用户提供反馈。如果验证失败，您可以阻止模型中的数据更新，并显示与每个失败的验证相关的错误消息。

```java
ValidationResult result = context.write(hero);
if (!result.isValid()) {
    displayErrors(result.getMessages());
} else {
    proceedWithUpdate();
}
```

<!-- vale off -->
## 上下文验证状态 {#context-validation-state}
<!-- vale on -->

每当上下文验证组件时，它会触发 `BindingContextValidateEvent`。该事件传递所有同时更改的绑定的 `ValidationResult`。您可以使用这些结果来触发操作并做出适当响应，例如根据整体表单有效性启用或禁用提交按钮。

```java
BindingContext<User> context = new BindingContext<>(User.class);

// 监听在每次用户交互时触发的 BindingContextValidateEvent。
context.addValidateListener(event -> {
    submit.setEnabled(event.isValid());
});
```

## 自动聚焦违规 {#auto-focus-violation}

在处理需要跨多个字段验证的表单时，自动聚焦第一个有错误的字段可以显著改善用户体验。此功能帮助用户立即识别并纠正错误，从而简化表单填写过程。

`BindingContext` 简化了在第一个验证错误组件上设置自动聚焦的过程。通过使用 `setAutoFocusFirstViolation` 方法，您可以用最少的代码启用此功能，确保用户界面对输入错误更加直观和响应。

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.setAutoFocusFirstViolation(true);
```

:::info 注意焦点
此功能仅适用于实现 `FocusAcceptorAware` 关注点的组件。
:::
