---
title: Extending the DSL
sidebar_position: 20
_i18n_hash: d9b9528f9a0fb3489ff11391012158f5
---
Kotlin DSL 是可扩展的，允许为自定义组件或第三方库添加 DSL 函数。您可以构建在内部使用 DSL 的复合组件。

## 添加组件到 DSL {#adding-components-to-the-dsl}

要使任何组件在 DSL 中可用，请在 `HasComponents` 上创建一个扩展函数，该函数使用 `init` 辅助函数。

### 基本 DSL 函数 {#basic-dsl-function}

这是一个简单组件的模式。此示例使用自定义 `StarRating` 组件：

```kotlin
import com.webforj.concern.HasComponents
import com.webforj.kotlin.dsl.WebforjDsl
import com.webforj.kotlin.dsl.init
import com.example.component.StarRating

fun @WebforjDsl HasComponents.starRating(
  block: @WebforjDsl StarRating.() -> Unit = {}
): StarRating {
  return init(StarRating(), block)
}
```

`init` 函数执行三项操作：
1. 将组件添加到父容器
2. 运行配置块
3. 返回配置后的组件

现在您可以在 DSL 代码中使用该组件：

```kotlin
div {
  starRating {
    value = 4
    max = 5
  }
}
```

### 添加参数 {#adding-parameters}

大多数 DSL 函数在配置块之前接受公共参数：

```kotlin
fun @WebforjDsl HasComponents.starRating(
  value: Int? = null,
  max: Int? = null,
  block: @WebforjDsl StarRating.() -> Unit = {}
): StarRating {
  val rating = StarRating()
  value?.let { rating.value = it }
  max?.let { rating.max = it }
  return init(rating, block)
}
```

用法变得更加简洁：

```kotlin
div {
  starRating(value = 4, max = 5)
  starRating(value = 3) {
    styles["color"] = "gold"
  }
}
```

## 创建复合组件 {#creating-composite-components}

`Composite` 将多个组件封装成一个可重用的单元。 DSL 非常适合定义复合结构。

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

      searchField = textField(placeholder = "搜索...") {
        styles["flex"] = "1"
      }

      searchButton = button("搜索") {
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

复合组件公开组件引用以供外部访问，并提供方便的常用操作方法。

### 添加 DSL 支持 {#adding-dsl-support}

创建一个 DSL 函数，使复合组件可以像内置组件一样使用：

```kotlin
fun @WebforjDsl HasComponents.searchBox(
  block: @WebforjDsl SearchBox.() -> Unit = {}
): SearchBox {
  return init(SearchBox(), block)
}
```

现在它可以自然地集成：

```kotlin
div {
  h1("产品目录")

  searchBox {
    onSearch { query ->
      filterProducts(query)
    }
  }

  // 产品列表...
}
```

### 示例：状态指示器 {#example-status-indicator}

这是一个状态指示器复合组件的完整示例：

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

// DSL 函数
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
  statusIndicator("数据库", StatusIndicator.Status.ACTIVE)
  statusIndicator("缓存", StatusIndicator.Status.WARNING)
  statusIndicator("外部 API", StatusIndicator.Status.ERROR)
}
```
