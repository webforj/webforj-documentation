---
title: Extending the DSL
sidebar_position: 20
_i18n_hash: d9b9528f9a0fb3489ff11391012158f5
---
El DSL de Kotlin es extensible, lo que permite la adición de funciones DSL para componentes personalizados o bibliotecas de terceros. Puedes construir componentes compuestos que utilicen el DSL internamente.

## Agregar componentes al DSL {#adding-components-to-the-dsl}

Para hacer que cualquier componente esté disponible en el DSL, crea una función de extensión en `HasComponents` que utilice la función auxiliar `init`.

### Función DSL básica {#basic-dsl-function}

Este es el patrón para un componente simple. Este ejemplo usa un componente personalizado `StarRating`:

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

La función `init` hace tres cosas:
1. Agrega el componente al contenedor principal
2. Ejecuta el bloque de configuración
3. Devuelve el componente configurado

Ahora puedes usar el componente en el código DSL:

```kotlin
div {
  starRating {
    value = 4
    max = 5
  }
}
```

### Agregar parámetros {#adding-parameters}

La mayoría de las funciones DSL aceptan parámetros comunes antes del bloque de configuración:

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

El uso se vuelve más conciso:

```kotlin
div {
  starRating(value = 4, max = 5)
  starRating(value = 3) {
    styles["color"] = "gold"
  }
}
```

## Crear componentes compuestos {#creating-composite-components}

Un `Composite` envuelve múltiples componentes en una única unidad reutilizable. El DSL funciona bien para definir estructuras compuestas.

### Compuesto básico {#basic-composite}

```kotlin
class SearchBox : Composite<Div>() {

  private val self = boundComponent
  val searchField: TextField
  val searchButton: Button

  init {
    self.apply {
      styles["display"] = "flex"
      styles["gap"] = "8px"

      searchField = textField(placeholder = "Buscar...") {
        styles["flex"] = "1"
      }

      searchButton = button("Buscar") {
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

El compuesto expone referencias de componentes para acceso externo y proporciona métodos de conveniencia para operaciones comunes.

### Agregar soporte DSL {#adding-dsl-support}

Crea una función DSL para que el compuesto pueda usarse como componentes incorporados:

```kotlin
fun @WebforjDsl HasComponents.searchBox(
  block: @WebforjDsl SearchBox.() -> Unit = {}
): SearchBox {
  return init(SearchBox(), block)
}
```

Ahora se integra de manera natural:

```kotlin
div {
  h1("Catálogo de Productos")

  searchBox {
    onSearch { query ->
      filterProducts(query)
    }
  }

  // Lista de productos...
}
```

### Ejemplo: Indicador de estado {#example-status-indicator}

Aquí hay un ejemplo completo de un composite de indicador de estado:

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

// Función DSL
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

Uso:

```kotlin
div {
  statusIndicator("Base de Datos", StatusIndicator.Status.ACTIVE)
  statusIndicator("Cache", StatusIndicator.Status.WARNING)
  statusIndicator("API Externa", StatusIndicator.Status.ERROR)
}
```
