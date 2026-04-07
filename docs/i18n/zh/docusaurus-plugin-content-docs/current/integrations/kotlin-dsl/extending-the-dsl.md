---
title: Extending the DSL
sidebar_position: 20
_i18n_hash: 73b71a500428fdbc51cd490f19d1eef9
---
Kotlin DSL 是可扩展的，允许为自定义组件或第三方库添加 DSL 函数。您可以构建内部使用 DSL 的复合组件。

## 添加组件到 DSL {#adding-components-to-the-dsl}

要使任何组件可用于 DSL，请在 `HasComponents` 上创建一个扩展函数，该函数使用 `init` 辅助函数。

### 基本 DSL 函数 {#basic-dsl-function}

以下是简单组件的模式。该示例假设您有一个自定义的 `Badge` 组件：

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

`init` 函数执行三项操作：
1. 将组件添加到父容器
2. 运行配置块
3. 返回配置好的组件

现在您可以在 DSL 代码中使用该组件：

```kotlin
div {
  badge {
    text = "新"
    variant = Badge.Variant.PRIMARY
  }
}
```

### 添加参数 {#adding-parameters}

大多数 DSL 函数在配置块之前接受常见参数：

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

用法变得更加简洁：

```kotlin
div {
  badge("新", Badge.Variant.PRIMARY)
  badge("促销") {
    styles["font-size"] = "12px"
  }
}
```

## 创建复合组件 {#creating-composite-components}

`Composite` 将多个组件包装成一个可重用的单元。DSL 非常适合定义复合结构。

### 基本复合 {#basic-composite}

```kotlin
class SearchBox : Composite<Div>() {

  val searchField: TextField
  val searchButton: Button

  init {
    boundComponent.apply {
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

复合组件暴露组件引用以供外部访问，并提供常见操作的便捷方法。

### 添加 DSL 支持 {#adding-dsl-support}

创建一个 DSL 函数，以便复合组件可以像内置组件那样使用：

```kotlin
fun @WebforjDsl HasComponents.searchBox(
  block: @WebforjDsl SearchBox.() -> Unit = {}
): SearchBox {
  return init(SearchBox(), block)
}
```

现在它自然集成：

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

以下是一个状态指示器复合的完整示例：

```kotlin
class StatusIndicator : Composite<Div>() {

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
    boundComponent.apply {
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
