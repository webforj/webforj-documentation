---
title: Using the DSL
sidebar_position: 10
_i18n_hash: cde3a82377e800021761e5d430328ed9
---
El Kotlin DSL proporciona funciones de constructor para los componentes de webforJ. Cada función crea un componente, lo añade a un contenedor padre y ejecuta un bloque de configuración. Esta página cubre los patrones y convenciones que utilizarás al construir interfaces de usuario con el DSL.

## Convenciones de nomenclatura {#naming-conventions}

Se proporcionan funciones DSL para todos los componentes estándar de webforJ, incluidos botones, campos, diseños, diálogos, cajones, listas y elementos HTML. Cada función utiliza el nombre de clase del componente en **camelCase**. `Button` se convierte en `button()`, `TextField` se convierte en `textField()`, y `FlexLayout` se convierte en `flexLayout()`.

```kotlin
div {
  button("Haz clic en mí")
  textField("Nombre de usuario")
  flexLayout {
    // contenido anidado
  }
}
```

:::important Usando el componente `Break`
Una excepción: `Break` usa acentos graves porque `break` es una palabra clave de Kotlin:

```kotlin
div {
  span("Línea uno")
  `break`()
  span("Línea dos")
}
```
:::

## Creando componentes {#creating-components}

Crea un componente al añadir la función DSL a un bloque padre, junto con los argumentos opcionales y el bloque de configuración, como se muestra a continuación:

```kotlin
div {
  // Crea un botón, lo añade a este div, y luego ejecuta el bloque
  button("Enviar") {
    theme = ButtonTheme.PRIMARY
    onClick { handleSubmit() }
  }
}
```

Cuando utilizas la función DSL de un componente, crea el componente, lo añade al padre y luego ejecuta el bloque de configuración. 
El bloque de configuración recibe el componente como su receptor (`this`), por lo que puedes acceder a propiedades y métodos directamente:

```kotlin
textField("Correo electrónico") {
  placeholder = "tú@ejemplo.com"   // this.placeholder
  required = true                   // this.required
  onModify { validate() }           // this.onModify(...)
}
```

## Anidando componentes {#nesting-components}

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
    button("Ajustes")
    button("Cerrar sesión")
  }
}
```

### Seguridad de alcance {#scope-safety}

El DSL aplica un alcance adecuado. Solo puedes añadir hijos a componentes que los soporten, y el compilador evita referencias accidentales a ámbitos externos:

```kotlin
div {
  button("Enviar") {
    // Esto parece que añade un párrafo dentro del botón,
    // pero en realidad lo añadiría al div externo.
    // El DSL captura este error en tiempo de compilación.
    paragraph("Enviando...") // No compilará
  }
}
```

Si necesitas añadir a un ámbito externo, usa `this` etiquetado para hacer la intención explícita:

```kotlin
div {
  button("Enviar") {
    this@div.add(Paragraph("Enviando..."))  // Explícito está permitido
  }
}
```

Esto mantiene el código de la interfaz de usuario predecible al hacer visibles los saltos de alcance.

## Estilizando componentes {#styling-components}

El Kotlin DSL proporciona una propiedad de extensión `styles` que permite un acceso de tipo mapa a las propiedades CSS, equivalente a `setStyle()` y `getStyle()` en Java:

```kotlin
button("Estilizado") {
  styles["background-color"] = "#007bff"
  styles["color"] = "white"
  styles["padding"] = "12px 24px"
  styles["border-radius"] = "4px"
}
```

:::tip[Clases CSS]
Para estilos reutilizables, añade clases CSS en lugar de estilos en línea. La extensión `HasClassName` permite añadir nombres de clase con `+=`:

```kotlin
button("Acción Principal") {
  classNames += "btn-primary"
}
```
:::

## Manejo de eventos {#event-handling}

Los componentes casi siempre necesitan responder a la interacción del usuario. El DSL proporciona una sintaxis concisa para los oyentes de eventos utilizando métodos prefijados con `on` que aceptan lambdas:

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

Además de bloques de configuración, la mayoría de las funciones DSL también aceptan parámetros comunes antes del bloque para opciones de uso frecuente:

```kotlin
// Parámetro de texto para etiquetas/contenido
button("Haz clic en mí")
h1("Título de la página")
paragraph("Texto del cuerpo")

// Etiqueta y marcador de posición para campos
textField("Nombre de usuario", placeholder = "Ingresa el nombre de usuario")
passwordField("Contraseña", placeholder = "Ingresa la contraseña")

// Parámetros valor para entradas
numberField("Cantidad", value = 1.0) {
  min = 0.0
  max = 100.0
}
```

:::tip Argumentos con nombres especificados
Los argumentos nombrados te permiten pasar parámetros en cualquier orden, independientemente de cómo aparezcan en la firma de la función.
:::

## Construyendo una vista completa {#building-a-complete-view}

Con estos patrones en mente, aquí tienes un formulario completo que los reúne:

``` kotlin
@Route("contacto")
class ContactView : Composite<Div>() {

  init {
    boundComponent.apply {
      styles["max-width"] = "400px"
      styles["padding"] = "20px"

      h2("Contáctenos")

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

      button("Enviar Mensaje") {
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

El DSL mantiene la estructura de la interfaz de usuario legible mientras te da acceso total a la configuración del componente.
