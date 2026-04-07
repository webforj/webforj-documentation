---
title: Extending the DSL
sidebar_position: 20
_i18n_hash: e7878d00305e1d544efb6f9e6e8afe2e
---
Kotlin DSL是可扩展的，允许为自定义组件或第三方库添加DSL函数。您可以构建在内部使用DSL的复合组件。

## 将组件添加到DSL {#adding-components-to-the-dsl}

要使任何组件在DSL中可用，请在`HasComponents`上创建一个扩展函数，该函数使用`init`辅助函数。

### 基本DSL函数 {#basic-dsl-function}

这是一个简单组件的模式。这个例子假设您有一个自定义的`Badge`组件：

```kotlin
import com.webforj.concern.HasComponents
import com.webforj.kotlin.dsl.WebforjDsl
import com.webforj.kotlin.dsl.init
import com.example.component.Badge

fun @WebforjDsl HasComponents.badge(
  block: @WebforjDsl Badge.() -> Unit = {}
): Badge {
  return init(Badge(), block)
}
```

`init`函数执行三项操作：
1. 将组件添加到父容器
2. 执行配置块
3. 返回配置好的组件

现在您可以在DSL代码中使用该组件：

```kotlin
div {
  badge {
    text = "New"
    variant = Badge.Variant.PRIMARY
  }
}
```

### 添加参数 {#adding-parameters}

大多数DSL函数在配置块之前接受常见参数：

```kotlin
fun @WebforjDsl HasComponents.badge(
  text: String? = null,
  variant: Badge.Variant? = null,
  block: @WebforjDsl Badge.() -> Unit = {}
): Badge {
  val badge = Badge()
  text?.let { badge.text = it }
  variant?.let { badge.variant = it }
  return init(badge, block)
}
```

用法变得更简洁：

```kotlin
div {
  badge("New", Badge.Variant.PRIMARY)
  badge("Sale") {
    styles["font-size"] = "12px"
  }
}
```

## 创建复合组件 {#creating-composite-components}

`Composite`将多个组件包装成一个可重用的单元。DSL非常适合定义复合结构。

### 基本复合 {#basic-composite}

```kotlin
class SearchBox : Composite<Div>() {

  private val self = boundComponent
  val searchField: TextField
  val searchButton: Button

  init {
    self.apply {
      styles["display"] = "flex"
      styles["gap"] = "8px"

      searchField = textField(placeholder = "Search...") {
        styles["flex"] = "1"
      }

      searchButton = button("Search") {
        theme = ButtonTheme.PRIMARY
      }
    }
  }

  fun onSearch(handler: (String) -> Unit) {
    searchButton.onClick {
      handler(searchField.text)
    }
    searchField.onEnter {
      handler(searchField.text)
    }
  }
}
```

复合组件对外暴露组件引用，并提供常见操作的便利方法。

### 添加DSL支持 {#adding-dsl-support}

创建一个DSL函数，以便复合组件可以像内置组件一样使用：

```kotlin
fun @WebforjDsl HasComponents.searchBox(
  block: @WebforjDsl SearchBox.() -> Unit = {}
): SearchBox {
  return init(SearchBox(), block)
}
```

现在它自然而然地集成：

```kotlin
div {
  h1("Product Catalog")

  searchBox {
    onSearch { query ->
      filterProducts(query)
    }
  }

  // 产品列表...
}
```

### 示例：状态指示器 {#example-status-indicator}

以下是状态指示器复合组件的完整示例：

```kotlin
class StatusIndicator : Composite<Div>() {

  private val self = boundComponent
  private val dot: Div
  private val label: Span

  var status: Status = Status.INACTIVE
    set(value) {
      field = value
      updateDisplay()
    }

  var text: String = ""
    set(value) {
      field = value
      label.text = value
    }

  init {
    self.apply {
      styles["display"] = "flex"
      styles["align-items"] = "center"
      styles["gap"] = "8px"

      dot = div {
        styles["width"] = "10px"
        styles["height"] = "10px"
        styles["border-radius"] = "50%"
        styles["background"] = "gray"
      }

      label = span()
    }
    updateDisplay()
  }

  private fun updateDisplay() {
    dot.styles["background"] = when (status) {
      Status.ACTIVE -> "#22c55e"
      Status.WARNING -> "#f59e0b"
      Status.ERROR -> "#ef4444"
      Status.INACTIVE -> "#9ca3af"
    }
  }

  enum class Status { ACTIVE, WARNING, ERROR, INACTIVE }
}

// DSL函数
fun @WebforjDsl HasComponents.statusIndicator(
  text: String? = null,
  status: StatusIndicator.Status? = null,
  block: @WebforjDsl StatusIndicator.() -> Unit = {}
): StatusIndicator {
  val indicator = StatusIndicator()
  text?.let { indicator.text = it }
  status?.let { indicator.status = it }
  return init(indicator, block)
}
```

用法：

```kotlin
div {
  statusIndicator("Database", StatusIndicator.Status.ACTIVE)
  statusIndicator("Cache", StatusIndicator.Status.WARNING)
  statusIndicator("External API", StatusIndicator.Status.ERROR)
}
```
