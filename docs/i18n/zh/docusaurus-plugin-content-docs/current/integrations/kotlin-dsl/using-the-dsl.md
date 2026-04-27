---
title: Using the DSL
sidebar_position: 10
_i18n_hash: 05d1319dd97f2d32392408b2e4ae9058
---
Kotlin DSL 提供了用于 webforJ 组件的构建器函数。每个函数创建一个组件，将其添加到父容器中，并运行一个配置块。本文涵盖了在使用 DSL 构建 UI 时的模式和约定。

## 命名约定 {#naming-conventions}

为所有标准 webforJ 组件提供了 DSL 函数，包括按钮、字段、布局、对话框、抽屉、列表和 HTML 元素。每个函数使用组件类名的 **camelCase**。`Button` 变成 `button()`，`TextField` 变成 `textField()`，`FlexLayout` 变成 `flexLayout()`。

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
一个例外：`Break` 使用反引号，因为 `break` 是一个 Kotlin 关键字：

```kotlin
div {
  span("第一行")
  `break`()
  span("第二行")
}
```
:::

## 创建组件 {#creating-components}

通过将其 DSL 函数添加到父块，以及可选的参数和配置块来创建组件，如下所示：

```kotlin
div {
  // 创建一个按钮，将其添加到这个 div，然后运行块
  button("提交") {
    theme = ButtonTheme.PRIMARY
    onClick { handleSubmit() }
  }
}
```

当您使用组件的 DSL 函数时，它会创建组件，将其添加到父级，然后运行配置块。
配置块将组件作为接收器 (`this`) ，因此您可以直接访问属性和方法：

```kotlin
textField("邮箱") {
  placeholder = "you@example.com"   // this.placeholder
  required = true                   // this.required
  onModify { validate() }           // this.onModify(...)
}
```

## 嵌套组件 {#nesting-components}

可以包含子组件的组件在其块中接受嵌套的 DSL 调用：

```kotlin
flexLayout {
  direction = FlexDirection.COLUMN

  h1("仪表板")

  div {
    paragraph("欢迎回来！")
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

DSL 强制执行正确的作用域。您只能向支持它们的组件添加子组件，编译器可以防止意外引用外部作用域：

```kotlin
div {
  button("提交") {
    // 这看起来像是将段落添加到按钮内部，
    // 但实际上会将其添加到外部 div。
    // DSL 在编译时捕获这个错误。
    paragraph("提交中...") // 无法编译
  }
}
```

如果您需要添加到外部作用域，请使用标记的 `this` 以明确意图：

```kotlin
div {
  button("提交") {
    this@div.add(Paragraph("提交中..."))  // 明确允许
  }
}
```

这使得 UI 代码的行为是可预测的，使作用域跳转可见。

## 样式组件 {#styling-components}

Kotlin DSL 提供了一个 `styles` 扩展属性，提供类似于地图的括号访问 CSS 属性，相当于 Java 中的 `setStyle()` 和 `getStyle()`：

```kotlin
button("样式按钮") {
  styles["background-color"] = "#007bff"
  styles["color"] = "white"
  styles["padding"] = "12px 24px"
  styles["border-radius"] = "4px"
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

组件几乎总是需要响应用户交互。DSL 提供了简洁的事件监听器语法，使用 `on` 前缀方法并接受 lambda：

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

除了配置块外，大多数 DSL 函数还在块之前接受常见参数，以便传递常用选项：

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

:::tip 带有指定名称的参数
命名参数允许您以任何顺序传递参数，而不必考虑它们在函数签名中的出现顺序。
:::

## 构建完整视图 {#building-a-complete-view}

掌握这些模式后，下面是一个完整的表单，将它们结合在一起：

```kotlin
@Route("contact")
class ContactView : Composite<Div>() {

  private val self = boundComponent

  init {
    self.apply {
      styles["max-width"] = "400px"
      styles["padding"] = "20px"

      h2("联系我们")

      val nameField = textField("姓名", placeholder = "您的名字") {
        styles["width"] = "100%"
        styles["margin-bottom"] = "16px"
      }

      val emailField = textField("邮箱", placeholder = "you@example.com") {
        styles["width"] = "100%"
      }

      val messageField = textArea("留言", placeholder = "我们该如何帮助您？") {
        styles["width"] = "100%"
      }

      button("发送留言") {
        theme = ButtonTheme.PRIMARY
        styles["width"] = "100%"

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

DSL 保持 UI 结构可读，同时让您完全访问组件的配置。
