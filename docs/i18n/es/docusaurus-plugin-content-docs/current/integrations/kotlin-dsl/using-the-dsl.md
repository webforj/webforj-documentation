---
title: Using the DSL
sidebar_position: 10
sidebar_class_name: new-content
_i18n_hash: 54b936e846c3049cd3d6528e37c864d6
---
El Kotlin DSL proporciona funciones de constructor para los componentes de webforJ. Cada función crea un componente, lo agrega a un contenedor padre y ejecuta un bloque de configuración. Esta página cubre los patrones y convenciones que utilizarás al construir UIs con el DSL.

## Convenciones de nomenclatura {#naming-conventions}

Se proporcionan funciones DSL para todos los componentes estándar de webforJ, incluidos botones, campos, diseños, diálogos, cajones, listas y elementos HTML. Cada función utiliza el nombre de la clase del componente en **camelCase**. `Button` se convierte en `button()`, `TextField` en `textField()` y `FlexLayout` en `flexLayout()`.

```kotlin
div {
    button("Haz clic en mí")
    textField("Nombre de usuario")
    flexLayout {
        // contenido anidado
    }
}
```
:::important Métodos `Header` y `Footer`
Los métodos DSL `header` y `footer` fueron renombrados a `nativeHeader` y `nativeFooter` para evitar conflictos con los slots de encabezado y pie de página de otros componentes.
:::

:::important Uso del componente `Break`
Una excepción: `Break` utiliza comillas invertidas porque `break` es una palabra clave de Kotlin:

```kotlin
div {
    span("Línea uno")
    `break`()
    span("Línea dos")
}
```
:::

## Creación de componentes {#creating-components}

Crea un componente agregando su función DSL a un bloque padre, junto con los argumentos opcionales y el bloque de configuración, como se muestra a continuación:

```kotlin
div {
    // Crea un botón, lo agrega a este div y luego ejecuta el bloque
    button("Enviar") {
        theme = ButtonTheme.PRIMARY
        onClick { handleSubmit() }
    }
}
```

Cuando usas la función DSL de un componente, se crea el componente, se agrega al padre y luego se ejecuta el bloque de configuración. El bloque de configuración recibe el componente como su receptor (`this`), por lo que puedes acceder a las propiedades y métodos directamente:

```kotlin
textField("Correo electrónico") {
    placeholder = "tú@ejemplo.com"   // this.placeholder
    required = true                   // this.required
    onModify { validate() }           // this.onModify(...)
}
```

## Anidación de componentes {#nesting-components}

Los componentes que pueden contener hijos aceptan llamadas DSL anidadas dentro de su bloque:

```kotlin
flexLayout {
    direction = FlexDirection.COLUMN

    h1("Tablero")

    div {
        paragraph("¡Bienvenido de nuevo!")
        button("Ver informes")
    }

    flexLayout {
        direction = FlexDirection.ROW
        button("Configuración")
        button("Cerrar sesión")
    }
}
```

### Seguridad del ámbito {#scope-safety}

El DSL impone un alcance adecuado. Solo puedes agregar hijos a componentes que los admitan, y el compilador previene referencias accidentales a ámbitos externos:

```kotlin
div {
    button("Enviar") {
        // Esto parece que agrega un párrafo dentro del botón,
        // pero en realidad lo añadiría al div exterior.
        // El DSL capta este error en el tiempo de compilación.
        paragraph("Enviando...") // No compilará
    }
}
```

Si necesitas agregar a un ámbito exterior, usa `this` etiquetado para hacer explícita la intención:

```kotlin
div {
    button("Enviar") {
        this@div.add(Paragraph("Enviando..."))  // Se permite lo explícito
    }
}
```

Esto mantiene el código de la UI predecible al hacer visibles los saltos de alcance.

## Estilización de componentes {#styling-components}

El Kotlin DSL proporciona una propiedad de extensión `styles` que da acceso tipo mapa a las propiedades CSS, equivalente a `setStyle()` y `getStyle()` en Java:

```kotlin
button("Estilizado") {
    styles["background-color"] = "#007bff"
    styles["color"] = "white"
    styles["padding"] = "12px 24px"
    styles["border-radius"] = "4px"
}
```

:::tip[Clases CSS]
Para estilos reutilizables, agrega clases CSS en lugar de estilos en línea. La extensión `HasClassName` permite agregar nombres de clase con `+=`:

```kotlin
button("Acción Principal") {
    classNames += "btn-primary"
}
```
:::

## Manejo de eventos {#event-handling}

Los componentes casi siempre necesitan responder a la interacción del usuario. El DSL proporciona una sintaxis concisa para los oyentes de eventos utilizando métodos con prefijo `on` que aceptan lambdas:

```kotlin
button("Guardar") {
    onClick {
        saveData()
        showNotification("¡Guardado!")
    }
}

textField("Buscar") {
    onModify { event ->
        performSearch(event.text)
    }
}
```

## Parámetros comunes {#common-parameters}

Además de los bloques de configuración, la mayoría de las funciones DSL también aceptan parámetros comunes antes del bloque para opciones de uso frecuente:

```kotlin
// Parámetro de texto para etiquetas/contenido
button("Haz clic en mí")
h1("Título de la página")
paragraph("Texto del cuerpo")

// Etiqueta y placeholder para campos
textField("Nombre de usuario", placeholder = "Ingresa el nombre de usuario")
passwordField("Contraseña", placeholder = "Ingresa la contraseña")

// Parámetros de valor para entradas
numberField("Cantidad", value = 1.0) {
    min = 0.0
    max = 100.0
}
```

:::tip Argumentos con nombres específicos
Los argumentos nombrados te permiten pasar parámetros en cualquier orden, sin importar cómo aparezcan en la firma de la función.
:::

## Construyendo una vista completa {#building-a-complete-view}

Con estos patrones en mano, aquí hay un formulario completo que los une:

``` kotlin
@Route("contacto")
class ContactView : Composite<Div>() {

    init {
        boundComponent.apply {
            styles["max-width"] = "400px"
            styles["padding"] = "20px"

            h2("Contáctanos")

            val nameField = textField("Nombre", placeholder = "Tu nombre") {
                styles["width"] = "100%"
                styles["margin-bottom"] = "16px"
            }

            val emailField = textField("Correo electrónico", placeholder = "tú@ejemplo.com") {
                styles["width"] = "100%"
            }

            val messageField = textArea("Mensaje", placeholder = "¿Cómo podemos ayudar?") {
                styles["width"] = "100%"
            }

            button("Enviar mensaje") {
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
        // Manejar la presentación del formulario
    }
}
```

El DSL mantiene la estructura de la UI legible mientras te da acceso completo a la configuración del componente.
