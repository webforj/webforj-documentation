---
title: Using the DSL
sidebar_position: 10
sidebar_class_name: new-content
_i18n_hash: 54b936e846c3049cd3d6528e37c864d6
---
Kotlin DSL 提供了用于 webforJ 组件的构建函数。每个函数创建一个组件，将其添加到父容器，并运行配置块。本页涵盖了在使用 DSL 构建用户界面时所使用的模式和约定。

## 命名约定 {#naming-conventions}

为所有标准 webforJ 组件提供了 DSL 函数，包括按钮、字段、布局、对话框、抽屉、列表和 HTML 元素。每个函数使用组件类名的 **驼峰式命名**。`Button` 变为 `button()`，`TextField` 变为 `textField()`，`FlexLayout` 变为 `flexLayout()`。

```kotlin
div {
    button("Click me")
    textField("Username")
    flexLayout {
        // 嵌套内容
    }
}
```
:::important `Header` 和 `Footer` 方法
`header` 和 `footer` DSL 方法已更名为 `nativeHeader` 和 `nativeFooter`，以避免与其他组件的头部和底部插槽发生冲突。
:::

:::important 使用 `Break` 组件
一个例外：`Break` 使用反引号，因为 `break` 是 Kotlin 的关键字：

```kotlin
div {
    span("Line one")
    `break`()
    span("Line two")
}
```
:::

## 创建组件 {#creating-components}

通过将其 DSL 函数添加到父块中，并结合可选参数和配置块来创建组件，如下所示：

```kotlin
div {
    // 创建一个按钮，将其添加到此 div，然后运行块
    button("Submit") {
        theme = ButtonTheme.PRIMARY
        onClick { handleSubmit() }
    }
}
```

当使用组件的 DSL 函数时，它会创建组件，将其添加到父级，然后运行配置块。配置块将组件作为接收者（`this`），因此可以直接访问属性和方法：

```kotlin
textField("Email") {
    placeholder = "you@example.com"   // this.placeholder
    required = true                   // this.required
    onModify { validate() }           // this.onModify(...)
}
```

## 嵌套组件 {#nesting-components}

可以容纳子项的组件在其块内接受嵌套的 DSL 调用：

```kotlin
flexLayout {
    direction = FlexDirection.COLUMN

    h1("Dashboard")

    div {
        paragraph("Welcome back!")
        button("View Reports")
    }

    flexLayout {
        direction = FlexDirection.ROW
        button("Settings")
        button("Logout")
    }
}
```

### 范围安全 {#scope-safety}

DSL 强制实施正确的作用域。您只能将子项添加到支持它们的组件中，并且编译器会防止意外引用外部作用域：

```kotlin
div {
    button("Submit") {
        // 这看起来像是在按钮内添加段落，
        // 但实际上它会将其添加到外部 div 中。
        // DSL 在编译时捕获此错误。
        paragraph("Submitting...") // 不会编译
    }
}
```

如果需要添加到外部作用域，请使用标记的 `this` 来明确意图：

```kotlin
div {
    button("Submit") {
        this@div.add(Paragraph("Submitting..."))  // 显式允许
    }
}
```

这使得 UI 代码在作用域跳转时可预测。

## 样式组件 {#styling-components}

Kotlin DSL 提供了一个 `styles` 扩展属性，提供类似于地图的括号访问 CSS 属性，相当于 Java 中的 `setStyle()` 和 `getStyle()`：

```kotlin
button("Styled") {
    styles["background-color"] = "#007bff"
    styles["color"] = "white"
    styles["padding"] = "12px 24px"
    styles["border-radius"] = "4px"
}
```

:::tip[CSS 类]
对于可重用的样式，请添加 CSS 类而不是内联样式。`HasClassName` 扩展允许通过 `+=` 添加类名：

```kotlin
button("Primary Action") {
    classNames += "btn-primary"
}
```
:::

## 事件处理 {#event-handling}

组件几乎总是需要响应用户交互。DSL 提供了简洁的事件监听器语法，使用 `on` 前缀方法，接受 lambda：

```kotlin
button("Save") {
    onClick {
        saveData()
        showNotification("Saved!")
    }
}

textField("Search") {
    onModify { event ->
        performSearch(event.text)
    }
}
```

## 常见参数 {#common-parameters}

除了配置块，大多数 DSL 函数还接受在块之前用于常用选项的常见参数：

```kotlin
// 标签/内容的文本参数
button("Click me")
h1("Page Title")
paragraph("Body text")

// 字段的标签和占位符
textField("Username", placeholder = "Enter username")
passwordField("Password", placeholder = "Enter password")

// 输入的值参数
numberField("Quantity", value = 1.0) {
    min = 0.0
    max = 100.0
}
```

:::tip 指定名称的参数
命名参数允许您以任何顺序传递参数，而不管它们在函数签名中的出现顺序。
:::

## 构建完整视图 {#building-a-complete-view}

掌握这些模式后，这里是一个将它们结合在一起的完整表单：

``` kotlin
@Route("contact")
class ContactView : Composite<Div>() {

    init {
        boundComponent.apply {
            styles["max-width"] = "400px"
            styles["padding"] = "20px"

            h2("Contact Us")

            val nameField = textField("Name", placeholder = "Your name") {
                styles["width"] = "100%"
                styles["margin-bottom"] = "16px"
            }

            val emailField = textField("Email", placeholder = "you@example.com") {
                styles["width"] = "100%"
            }

            val messageField = textArea("Message", placeholder = "How can we help?") {
                styles["width"] = "100%"
            }

            button("Send Message") {
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

DSL 保持 UI 结构的可读性，同时允许您完全访问组件配置。
