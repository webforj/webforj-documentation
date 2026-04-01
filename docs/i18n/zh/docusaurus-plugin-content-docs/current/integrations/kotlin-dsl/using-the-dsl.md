---
title: Using the DSL
sidebar_position: 10
_i18n_hash: cde3a82377e800021761e5d430328ed9
---
Kotlin DSL 提供了 webforJ 组件的构建器函数。每个函数创建一个组件，将其添加到父容器中，并运行一个配置块。该页面介绍了在使用 DSL 构建用户界面时您将使用的模式和约定。

## 命名约定 {#naming-conventions}

DSL 函数适用于所有标准 webforJ 组件，包括按钮、字段、布局、对话框、抽屉、列表和 HTML 元素。每个函数使用组件类名的 **camelCase**。`Button` 变为 `button()`，`TextField` 变为 `textField()`，`FlexLayout` 变为 `flexLayout()`。

```kotlin
div {
  button("点击我")
  textField("用户名")
  flexLayout {
    // 嵌套内容
  }
}
```

:::important 使用 `Break` 组件
一个例外：`Break` 使用反引号，因为 `break` 是 Kotlin 的关键字：

```kotlin
div {
  span("第一行")
  `break`()
  span("第二行")
}
```
:::

## 创建组件 {#creating-components}

通过将其 DSL 函数添加到父块中，以及可选参数和配置块来创建组件，如下所示：

```kotlin
div {
  // 创建一个按钮，将其添加到此 div 中，然后运行块
  button("提交") {
    theme = ButtonTheme.PRIMARY
    onClick { handleSubmit() }
  }
}
```

使用组件的 DSL 函数时，它会创建组件，将其添加到父级，然后运行配置块。
配置块接收组件作为其接收者（`this`），因此您可以直接访问属性和方法：

```kotlin
textField("电子邮件") {
  placeholder = "you@example.com"   // this.placeholder
  required = true                   // this.required
  onModify { validate() }           // this.onModify(...)
}
```

## 嵌套组件 {#nesting-components}

可以包含子元素的组件在其块内接受嵌套的 DSL 调用：

```kotlin
flexLayout {
  direction = FlexDirection.COLUMN

  h1("仪表板")

  div {
    paragraph("欢迎回来!")
    button("查看报告")
  }

  flexLayout {
    direction = FlexDirection.ROW
    button("设置")
    button("注销")
  }
}
```

### 范围安全 {#scope-safety}

DSL 强制执行正确的作用域。您只能将子元素添加到支持它们的组件，编译器会防止意外引用外部作用域：

```kotlin
div {
  button("提交") {
    // 这看起来像是将段落添加到按钮内部，
    // 但实际上它会将其添加到外部 div。
    // DSL 在编译时捕获了这个错误。
    paragraph("提交中...") // 无法编译
  }
}
```

如果您需要添加到外部作用域，请使用标记的 `this` 使意图明确：

```kotlin
div {
  button("提交") {
    this@div.add(Paragraph("提交中..."))  // 明确是允许的
  }
}
```

这使得 UI 代码的跳转范围可见，从而保持可预测性。

## 组件样式 {#styling-components}

Kotlin DSL 提供了一个 `styles` 扩展属性，允许通过类似于地图的括号访问 CSS 属性，相当于 Java 中的 `setStyle()` 和 `getStyle()`：

```kotlin
button("样式化") {
  styles["背景颜色"] = "#007bff"
  styles["颜色"] = "白色"
  styles["内边距"] = "12px 24px"
  styles["边框半径"] = "4px"
}
```

:::tip[CSS 类]
对于可重用的样式，添加 CSS 类而不是内联样式。`HasClassName` 扩展允许使用 `+=` 添加类名：

```kotlin
button("主要操作") {
  classNames += "btn-primary"
}
```
:::

## 事件处理 {#event-handling}

组件几乎总是需要响应用户交互。DSL 提供了简洁的事件监听器语法，使用接受 lambda 的 `on` 前缀方法：

```kotlin
button("保存") {
  onClick {
    saveData()
    showNotification("已保存！")
  }
}

textField("搜索") {
  onModify { event ->
    performSearch(event.text)
  }
}
```

## 常见参数 {#common-parameters}

除了配置块之外，大多数 DSL 函数在块之前也接受常见参数，以便于使用的选项：

```kotlin
// 标签/内容的文本参数
button("点击我")
h1("页面标题")
paragraph("正文文本")

// 字段的标签和占位符
textField("用户名", placeholder = "输入用户名")
passwordField("密码", placeholder = "输入密码")

// 输入的值参数
numberField("数量", value = 1.0) {
  min = 0.0
  max = 100.0
}
```

:::tip 指定名称的参数
命名参数让您可以以任何顺序传递参数，而不管它们在函数签名中的出现顺序。
:::

## 构建完整视图 {#building-a-complete-view}

掌握这些模式后，下面是一个将它们结合起来的完整表单：

```kotlin
@Route("contact")
class ContactView : Composite<Div>() {

  init {
    boundComponent.apply {
      styles["最大宽度"] = "400px"
      styles["内边距"] = "20px"

      h2("联系我们")

      val nameField = textField("姓名", placeholder = "你的名字") {
        styles["宽度"] = "100%"
        styles["下边距"] = "16px"
      }

      val emailField = textField("电子邮件", placeholder = "you@example.com") {
        styles["宽度"] = "100%"
      }

      val messageField = textArea("信息", placeholder = "我们能帮您什么？") {
        styles["宽度"] = "100%"
      }

      button("发送信息") {
        theme = ButtonTheme.PRIMARY
        styles["宽度"] = "100%"

        onClick {
          submitForm(
            name = nameField.text,
            email = emailField.text,
            message = messageField.text
          )
        }
      }
    }
  }

  private fun submitForm(name: String, email: String, message: String) {
    // 处理表单提交
  }
}
```

DSL 保持了 UI 结构的可读性，同时为您提供了对组件配置的完全访问。
