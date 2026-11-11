---
sidebar_position: 35
title: TextField
slug: textfield
description: A single-line input component for entering and editing text data.
_i18n_hash: 2f01abb14f66d83dd6edd3cc18ac560e
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/TextField" top='true'/>

El componente `TextField` permite a los usuarios introducir y editar texto en una sola línea. Puedes configurar el campo para que muestre un teclado virtual específico, como un teclado numérico o uno para introducir direcciones de correo electrónico, números de teléfono o URL. El componente también incluye validación integrada para rechazar valores que no se ajusten al tipo especificado.

<!-- INTRO_END -->

## Usos {#usages}

<ParentLink parent="Field" />

`TextField` es adecuado para una amplia variedad de situaciones en las que se requiere introducir o editar texto. Estos son algunos ejemplos de cuándo usar `TextField`:

1. **Campos de formulario**: `TextField` se usa habitualmente en formularios para recopilar datos de los usuarios, como nombres, direcciones o comentarios. Es mejor usar `TextField` en una aplicación cuando la entrada generalmente sea lo suficientemente breve como para caber en una sola línea.

2. **Funcionalidad de búsqueda**: `TextField` se puede usar como cuadro de búsqueda para que los usuarios introduzcan sus consultas.

3. **Edición de texto**: `TextField` es ideal para aplicaciones que requieren editar texto o crear contenido, como editores de documentos, aplicaciones de chat o aplicaciones para tomar notas.

## Tipos {#types}

Puedes especificar el tipo de `TextField` mediante el método `setType()`. Del mismo modo, puedes obtener el tipo mediante el método `getType()`, que devuelve un valor de enumeración.

- `Type.TEXT`: Este es el tipo predeterminado de `TextField` y elimina automáticamente los saltos de línea del valor introducido.

- `Type.EMAIL`: Este tipo se usa para introducir direcciones de correo electrónico. Valida la entrada según la sintaxis estándar de las direcciones de correo electrónico. Además, en los navegadores y dispositivos compatibles, muestra un teclado dinámico que simplifica la escritura al incluir teclas de uso frecuente, como <kbd>.com</kbd> y <kbd>@</kbd>.

  :::note
  Esta validación confirma que el formato de la dirección de correo electrónico sea correcto, pero no garantiza que la dirección exista.
  :::

- `Type.TEL`: Este tipo se usa para introducir números de teléfono. En algunos dispositivos con teclados dinámicos, el campo muestra un teclado telefónico.

- `Type.URL`: Este tipo se usa para introducir URL. Valida que la URL introducida incluya un esquema y un nombre de dominio, por ejemplo: https://webforj.com. Además, en los navegadores y dispositivos compatibles, muestra un teclado dinámico que simplifica la escritura al incluir teclas de uso frecuente, como <kbd>:</kbd>, <kbd>/</kbd> y <kbd>.com</kbd>.

- `Type.SEARCH`: Campo de texto de una sola línea para introducir cadenas de búsqueda. Los saltos de línea se eliminan automáticamente del valor introducido.

<ComponentDemo
path='/webforj/textfield'
files={['src/main/java/com/webforj/samples/views/fields/textfield/TextFieldView.java']}
height='250px'
/>

## Valor del campo {#field-value}

El valor de un `TextField` representa la entrada actual del usuario como una cadena. En webforJ, puedes acceder a él mediante el método `getValue()` o actualizarlo mediante programación con `setValue(String)`.

```java
//Establecer el contenido inicial
textField.setValue("Contenido inicial");

//Obtener el valor actual
String text = textField.getValue();
```

Si se usa el método `getValue()` en un campo que no tiene un valor actual, devuelve una cadena vacía (`""`).

Este comportamiento coincide con el modo en que el elemento HTML `<input type="text">` expone su valor mediante JavaScript.

:::tip Combinar la gestión del valor con la validación
Aplica restricciones como un [patrón](#pattern-matching), una [longitud mínima](#setminlength) o una [longitud máxima](#setmaxlength) para definir cuándo se considera válido un valor.
:::

## Texto de marcador de posición {#placeholder-text}

Puedes establecer un texto de marcador de posición para `TextField` mediante el método `setPlaceholder()`. El texto se muestra cuando el campo está vacío y ayuda a orientar al usuario para que introduzca el contenido adecuado en `TextField`.

## Texto seleccionado {#selected-text}

Puedes interactuar con la clase `TextField` para obtener el texto seleccionado por el usuario y consultar información sobre su selección. Para obtener el texto seleccionado en `TextField`, usa el método `getSelectedText()`. Este comportamiento suele utilizarse junto con un evento.

```java
TextField textField = new TextField("Introduce algo...");
Button getSelectionBtn = new Button("Obtener texto seleccionado");

getSelectionBtn.onClick(e -> {
  String selected = textField.getSelectedText();
  System.out.println("Texto seleccionado: " + selected);
});
```

También puedes obtener el rango de selección actual de `TextField` mediante el método `getSelectionRange()`. Este devuelve un objeto `SelectionRange` que representa los índices inicial y final del texto seleccionado.

:::tip Cambios en el valor y selección
Usa `onValueChange()` para gestionar los cambios en el valor del campo. Su `ValueChangeEvent<String>` proporciona el nuevo valor mediante `event.getValue()`, pero no contiene datos de selección. Para leer la selección actual, llama a `getSelectedText()` o `getSelectionRange()` en el campo.
:::

## Coincidencia de patrones {#pattern-matching}

Puedes usar el método `setPattern()` para definir una regla de validación para `TextField` mediante una expresión regular. Al establecer un patrón, se añade una restricción de validación que exige que el valor introducido coincida con el patrón especificado.

El patrón debe ser una [expresión regular válida de JavaScript](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions), según la interpretación del navegador. Internamente, se aplica la marca `u` (Unicode) para garantizar la coincidencia precisa de los puntos de código Unicode. No encierres el patrón entre barras diagonales (`/`), ya que no son necesarias y se tratarán como caracteres literales.

```java
textField.setPattern("[A-Za-z]{3}\\d{2}"); // p. ej., ABC12
```

Si no se proporciona un patrón o la sintaxis no es válida, se ignora la regla de validación.

:::tip Ayuda contextual
Al usar patrones complejos en `TextField`, considera combinar los métodos `setLabel()`, `setHelperText()` y `setTooltipText()`
para proporcionar indicaciones y orientación adicionales.
:::

## Longitud mínima y máxima {#minimum-and-maximum-length}

El componente `TextField` admite la validación de restricciones según la cantidad de caracteres introducidos por el usuario. Esto se puede controlar mediante los métodos `setMinLength()` y `setMaxLength()`. Usa ambos métodos para definir límites claros para las longitudes de entrada aceptables.

:::info Requisitos de longitud
De forma predeterminada, el campo muestra un mensaje cuando el valor introducido está fuera del rango e indica al usuario si debe acortar o alargar la entrada. Este mensaje se puede reemplazar con el método `setInvalidMessage()`.
:::

### `setMinLength()` {#setminlength}

Este método establece la **cantidad mínima de unidades de código UTF-16** que se deben introducir para que el campo se considere válido. El valor debe ser un número entero y no debe superar la longitud máxima configurada.

```java
textField.setMinLength(5); // El usuario debe introducir al menos 5 caracteres
```

Si la entrada contiene menos caracteres que el mínimo requerido, no superará la validación de restricciones. Esta regla solo se aplica cuando el usuario cambia el valor del campo.

### `setMaxLength()` {#setmaxlength}

Este método establece la **cantidad máxima de unidades de código UTF-16** permitidas en el campo de texto. El valor debe ser `0` o mayor. Si no se establece o se establece un valor no válido, no se aplica ningún máximo.

```java
textField.setMaxLength(20); // El usuario no puede introducir más de 20 caracteres
```

El campo no superará la validación de restricciones si la longitud de la entrada excede la longitud máxima. Al igual que con `setMinLength()`, esta validación solo se activa cuando el usuario cambia el valor.

## Prácticas recomendadas {#best-practices}

En la siguiente sección se describen algunas prácticas recomendadas para usar `TextField`.

- **Proporciona etiquetas e instrucciones claras**: Etiqueta `TextField` claramente para indicar qué tipo de información deben introducir los usuarios. Proporciona texto instructivo adicional o valores de marcador de posición para orientar a los usuarios y establecer expectativas sobre la entrada requerida.

- **Corrector ortográfico y autocompletado**: Cuando corresponda, considera habilitar la corrección ortográfica con `setSpellCheck()` o usar el autocompletado en un `TextField`. Estas funciones pueden ayudar a los usuarios a introducir información más rápido y reducir los errores al sugerir valores basados en entradas anteriores u opciones predefinidas.

- **Accesibilidad**: Proporciona etiquetas claras, navegación con teclado y compatibilidad con tecnologías de asistencia para que las personas con discapacidades puedan usar el campo.
