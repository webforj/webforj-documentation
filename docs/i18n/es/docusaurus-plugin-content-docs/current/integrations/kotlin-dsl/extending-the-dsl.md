---
title: Extending the DSL
sidebar_position: 20
sidebar_class_name: new-content
_i18n_hash: 04d9c37b735c8334be4ef4dd3540cb01
---
El Kotlin DSL es extensible, lo que permite la adición de funciones DSL para componentes personalizados o bibliotecas de terceros. Puedes construir componentes compuestos que utilicen el DSL internamente.

## Agregar componentes al DSL {#adding-components-to-the-dsl}

Para hacer que cualquier componente esté disponible en el DSL, crea una función de extensión en `HasComponents` que utilice la función auxiliar `init`.

### Función básica de DSL {#basic-dsl-function}

Aquí está el patrón para un componente simple. Este ejemplo supone que tienes un componente `Badge` personalizado:

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

La función `init` hace tres cosas:
1. Agrega el componente al contenedor padre
2. Ejecuta el bloque de configuración
3. Devuelve el componente configurado

Ahora puedes usar el componente en código DSL:

```kotlin
div {
    badge {
        text = "Nuevo"
        variant = Badge.Variant.PRIMARY
    }
}
```

### Agregar parámetros {#adding-parameters}

La mayoría de las funciones DSL aceptan parámetros comunes antes del bloque de configuración:

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

El uso se vuelve más conciso:

```kotlin
div {
    badge("Nuevo", Badge.Variant.PRIMARY)
    badge("Venta") {
        styles["font-size"] = "12px"
    }
}
```

## Crear componentes compuestos {#creating-composite-components}

Un `Composite` envuelve múltiples componentes en una sola unidad reutilizable. El DSL funciona bien para definir estructuras compuestas.

### Compuesto básico {#basic-composite}

```kotlin
class SearchBox : Composite<Div>() {

    val searchField: TextField
    val searchButton: Button

    init {
        boundComponent.apply {
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

### Agregar soporte para DSL {#adding-dsl-support}

Crea una función DSL para que el compuesto pueda ser utilizado como componentes integrados:

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
    h1("Catálogo de productos")

    searchBox {
        onSearch { query ->
            filterProducts(query)
        }
    }

    // Lista de productos...
}
```

### Ejemplo: Indicador de estado {#example-status-indicator}

Aquí tienes un ejemplo completo de un compuesto indicador de estado:

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
    statusIndicator("Base de datos", StatusIndicator.Status.ACTIVE)
    statusIndicator("Cache", StatusIndicator.Status.WARNING)
    statusIndicator("API externa", StatusIndicator.Status.ERROR)
}
```
