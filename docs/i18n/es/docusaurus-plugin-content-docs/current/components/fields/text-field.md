---
sidebar_position: 35
title: TextField
slug: textfield
description: A single-line input component for entering and editing text data.
sidebar_class_name: updated-content
_i18n_hash: 71ebfc077bb8042752cbfea71a971e47
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/TextField" top='true'/>

<ParentLink parent="Field" />

El componente `TextField` permite a los usuarios ingresar y editar texto en una sola línea. Puedes configurar el campo para mostrar un teclado virtual específico, como un teclado numérico, entrada de correo electrónico, entrada telefónica o entrada de URL. El componente también proporciona validación integrada para rechazar valores que no se adhieran al tipo especificado.

## Usos {#usages}

El `TextField` es adecuado para una amplia gama de escenarios donde se requiere entrada o edición de texto. Aquí hay algunos ejemplos de cuándo usar el `TextField`:

1. **Entradas de Formulario**: Un `TextField` se utiliza comúnmente en formularios para capturar la entrada del usuario, como nombres, direcciones o comentarios. Es mejor usar un `TextField` en una aplicación cuando la entrada es generalmente lo suficientemente corta como para caber en una línea.

2. **Funcionalidad de Búsqueda**: El `TextField` se puede usar para proporcionar un cuadro de búsqueda, permitiendo a los usuarios ingresar consultas de búsqueda.

3. **Edición de Texto**: Un `TextField` es ideal para aplicaciones que requieren edición de texto o creación de contenido, como editores de documentos, aplicaciones de chat o aplicaciones de toma de notas.

## Tipos {#types}

Puedes especificar el tipo del TextField utilizando el método `setType()`. De manera similar, puedes recuperar el tipo utilizando el método `getType()`, que devolverá un valor de enumeración.

- `Type.TEXT`: Este es el tipo predeterminado para un `TextField` y elimina automáticamente los saltos de línea del valor de entrada.

- `Type.EMAIL`: Este tipo es para ingresar direcciones de correo electrónico. Valida la entrada de acuerdo con la sintaxis estándar de correo electrónico. Además, proporciona a los navegadores y dispositivos compatibles un teclado dinámico que simplifica el proceso de escritura al incluir teclas de uso común como <kbd>.com</kbd> y <kbd>@</kbd>.

  :::note
  Si bien esta validación confirma el formato de la dirección de correo electrónico, no garantiza que el correo electrónico exista.
  :::

- `Type.TEL`: Este tipo se utiliza para ingresar un número de teléfono. El campo mostrará un teclado telefónico en algunos dispositivos con teclados dinámicos.

- `Type.URL`: Este tipo es para ingresar URLs. Valida si un usuario ingresa una URL que incluya un esquema y un nombre de dominio, por ejemplo: https://webforj.com. Además, proporciona a los navegadores y dispositivos compatibles un teclado dinámico que simplifica el proceso de escritura al incluir teclas de uso común como <kbd>:</kbd>, <kbd>/</kbd>, y <kbd>.com</kbd>.

- `Type.SEARCH`: Un campo de texto de una sola línea para ingresar cadenas de búsqueda. Se eliminan automáticamente los saltos de línea del valor de entrada.

<ComponentDemo 
path='/webforj/textfield?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/textfield/TextFieldView.java'
/>

## Valor del campo {#field-value}

El valor de un `TextField` representa la entrada actual del usuario como una cadena. En webforJ, esto se puede acceder utilizando el método `getValue()`, o actualizarse programáticamente con `setValue(String)`.

```java
//Establecer el contenido inicial
textField.setValue("Contenido inicial");

//Recuperar el valor actual
String text = textField.getValue();
```

Si se utiliza el método `getValue()` en un campo sin un valor actual, devuelve una cadena vacía (`""`).

Este comportamiento es consistente con cómo el elemento HTML `<input type="text">` expone su valor a través de JavaScript.

:::tip Combina el manejo de valores con la validación
Aplica restricciones como un [patrón](#pattern-matching), [longitud mínima](#setminlength) o una [longitud máxima](#setmaxlength) para definir cuándo un valor se considera válido. 
:::

## Texto de marcador de posición {#placeholder-text}

Puedes establecer texto de marcador de posición para el `TextField` utilizando el método `setPlaceholder()`. El texto de marcador de posición se muestra cuando el campo está vacío, ayudando a indicar al usuario que ingrese una entrada apropiada en el `TextField`.

## Texto seleccionado {#selected-text}

Es posible interactuar con la clase `TextField` para recuperar el texto seleccionado por el usuario y obtener información sobre la selección del usuario. Puedes recuperar el texto seleccionado en el `TextField` utilizando el método `getSelectedText()`. Este comportamiento se utilizaría comúnmente junto con un evento.

```java
TextField textField = new TextField("Ingresa algo...");
Button getSelectionBtn = new Button("Obtener Texto Seleccionado");

getSelectionBtn.onClick(e -> {
  String selected = textField.getSelectedText();
  System.out.println("Texto seleccionado: " + selected);
});
```

De manera similar, es posible recuperar el rango de selección actual del `TextField` utilizando el método `getSelectionRange()`. Esto devuelve un objeto `SelectionRange` que representa los índices de inicio y fin del texto seleccionado.

:::tip Usando `getSelectedText()` vs carga del evento
Si bien puedes llamar a `getSelectedText()` manualmente dentro de un manejador de eventos, es más eficiente utilizar los datos de selección proporcionados en la carga del evento, como en un `SelectionChangeEvent`, para evitar búsquedas adicionales.
:::

## Coincidencia de patrones {#pattern-matching}

Puedes usar el método `setPattern()` para definir una regla de validación para el `TextField` utilizando una expresión regular. Establecer un patrón añade una validación de restricción que requiere que el valor de entrada coincida con el patrón especificado.

El patrón debe ser una [expresión regular de JavaScript válida](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions), tal como la interpreta el navegador. La bandera `u` (Unicode) se aplica internamente para garantizar una coincidencia precisa de los puntos de código Unicode. No envuelvas el patrón en barras diagonales (`/`), ya que no son necesarias y se tratarán como caracteres literales.

```java
textField.setPattern("[A-Za-z]{3}\\d{2}"); // por ejemplo ABC12
```

Si no se proporciona un patrón, o la sintaxis es inválida, la regla de validación se ignora.

:::tip Ayuda contextual
Al utilizar patrones complejos para un `TextField`, considera usar una combinación de los métodos `setLabel()`, `setHelperText()`, y `setTooltipText()`
para proporcionar pistas y orientaciones adicionales.
:::

## Longitud mínima y máxima {#minimum-and-maximum-length}

El componente `TextField` admite validación de restricciones basada en el número de caracteres ingresados por el usuario. Esto se puede controlar utilizando los métodos `setMinLength()` y `setMaxLength()`. Usa ambos métodos para definir un límite claro de longitudes de entrada aceptables.

:::info Requisitos de longitud
Por defecto, el campo muestra un mensaje cuando el valor de entrada está fuera de rango, indicando al usuario si debe acortar o alargar su entrada. Este mensaje se puede sobrescribir con el método `setInvalidMessage()`.
:::

### `setMinLength()` {#setminlength}

Este método establece el **número mínimo de unidades de código UTF-16** que deben ingresarse para que el campo se considere válido. El valor debe ser un número entero y no debe exceder la longitud máxima configurada.

```java
textField.setMinLength(5); // El usuario debe ingresar al menos 5 caracteres
```

Si la entrada contiene menos caracteres que los mínimos requeridos, la entrada fallará la validación de restricciones. Esta regla solo se aplica cuando el usuario cambia el valor del campo.

### `setMaxLength()` {#setmaxlength}

Este método establece el **número máximo de unidades de código UTF-16** permitidas en el campo de texto. El valor debe ser `0` o mayor. Si no se establece, o se establece en un valor inválido, no se aplica ninguna restricción máxima.

```java
textField.setMaxLength(20); // El usuario no puede ingresar más de 20 caracteres
```

El campo falla la validación de restricciones si la longitud de la entrada excede la longitud mínima. Al igual que con `setMinLength()`, esta validación solo se activa cuando el valor es cambiado por el usuario.

## Mejores prácticas {#best-practices}

La siguiente sección describe algunas mejores prácticas sugeridas para la utilización del `TextField`.

- **Proporcionar Etiquetas e Instrucciones Claras**: Etiqueta el `TextField` claramente para indicar el tipo de información que los usuarios deben ingresar. Proporciona texto adicional o valores de marcador de posición para guiar a los usuarios y establecer expectativas sobre el input requerido.

- **Corrección Ortográfica y Autocompletar**: Cuando sea aplicable, considera utilizar la corrección ortográfica con `setSpellCheck()` y/o usar autocompletar en un `TextField`. Estas características pueden ayudar a los usuarios a ingresar información más rápido y reducir errores al proporcionar valores sugeridos basados en entradas anteriores u opciones predefinidas.

- **Accesibilidad**: Utiliza el componente `TextField` con la accesibilidad en mente, cumpliendo con los estándares de accesibilidad como etiquetado adecuado, soporte para navegación con teclado y compatibilidad con tecnologías de asistencia. Asegúrate de que los usuarios con discapacidades puedan interactuar con el `TextField` de manera efectiva.
