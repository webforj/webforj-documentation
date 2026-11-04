---
title: MaskedTextField
sidebar_position: 15
description: >-
  Enforce formatted text entry with the MaskedTextField, supporting mask
  characters for digits, letters, and literals for IDs and codes.
_i18n_hash: 9ee372191dc17b2510f020bc908f0060
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-textfield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedTextField" top='true'/>

El componente `MaskedTextField` proporciona una entrada de texto configurable que impone reglas de formato y validación. Es ideal para aplicaciones que requieren entrada estructurada, como sistemas financieros, de comercio electrónico y de salud.

Este componente se puede instanciar con o sin parámetros. Puedes definir un valor inicial, una etiqueta, un texto de marcador de posición y un oyente en caso de que el valor cambie.

<!-- INTRO_END -->

```java
MaskedTextField field = new MaskedTextField("ID de Cuenta");
field.setMask("ZZZZ-0000")
  .setHelperText("Máscara: ZZZZ-0000 - por ejemplo: SAVE-2025");
```

## Reglas de máscara {#mask-rules}

El `MaskedTextField` formatea la entrada de texto utilizando una máscara, que es una cadena que define qué caracteres son permitidos en cada posición. Esto garantiza una entrada consistente y estructurada para cosas como números de teléfono, códigos postales y formatos de identificación.

:::tip Aplicando máscaras programáticamente
Para formatear cadenas con la misma sintaxis de máscara fuera de un campo, por ejemplo, al renderizar datos en una [`Table`](/docs/components/table/overview), utiliza la clase de utilidad [`MaskDecorator`](/docs/advanced/mask-decorator).
:::

### Caracteres de máscara soportados {#supported-mask-characters}

| Carácter | Descripción                                                                                 |
|----------|---------------------------------------------------------------------------------------------|
| `X`      | Cualquier carácter imprimible                                                                |
| `a`      | Cualquier carácter alfabético (mayúsculas o minúsculas)                                     |
| `A`      | Cualquier carácter alfabético; las letras minúsculas se convierten a mayúsculas            |
| `0`      | Cualquier dígito (0–9)                                                                       |
| `z`      | Cualquier dígito o letra (mayúsculas o minúsculas)                                         |
| `Z`      | Cualquier dígito o letra; las letras minúsculas se convierten a mayúsculas                |

Todos los demás caracteres en la máscara se tratan como literales y deben escribirse exactamente. Por ejemplo, una máscara como `XX@XX` requiere que el usuario ingrese un `@` en el medio.

- **Los caracteres inválidos** se ignoran silenciosamente.
- **La entrada corta** se rellena con espacios.
- **La entrada larga** se trunca para ajustarse a la máscara.

### Ejemplos {#examples}

```java
field.setMask("(000) 000-0000");     // Ejemplo: (123) 456-7890
field.setMask("A0A 0A0");            // Ejemplo: A1B 2C3 (código postal canadiense)
field.setMask("ZZZZ-0000");          // Ejemplo: ABCD-1234
field.setMask("0000-0000-0000-0000");// Ejemplo: 1234-5678-9012-3456
```

:::tip Entrada completa permitida
Si la máscara solo contiene `X`, el campo se comporta como un [`TextField`](../textfield) estándar, permitiendo cualquier entrada imprimible. Esto es útil cuando deseas reservar la capacidad de formatear sin aplicar reglas estrictas de caracteres.
:::

<ComponentDemo
path='/webforj/maskedtextfield'
files={['src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldView.java']}
height='250px'
/>

## Patrones de validación {#validation-patterns}

Mientras que las máscaras definen la estructura de la entrada, puedes combinarlas con patrones de validación para imponer reglas de entrada más específicas. Esto agrega una capa adicional de validación del lado del cliente utilizando expresiones regulares.

Utiliza el método `setPattern()` para aplicar una expresión regular personalizada:

```java
field.setPattern("[A-Za-z0-9]{10}"); // Impone un código alfanumérico de 10 caracteres
```

Esto asegura que la entrada no solo coincida con la máscara, sino que también se ajuste a una estructura definida, como longitud o caracteres permitidos.

Esto es especialmente útil cuando:

- La máscara permite demasiada flexibilidad
- Quieres imponer una longitud exacta o un formato específico (por ejemplo, hex, Base64, UUID)

:::tip Formato de expresión regular
El patrón debe ser una expresión regular válida de [JavaScript](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions), como se utiliza en el tipo `RegExp`. Puedes encontrar más detalles en la [documentación del atributo de patrón de HTML](https://developer.mozilla.org/en-US/docs/Web/HTML/Attributes/pattern#overview).
:::

## Restaurando el valor {#restoring-the-value}

El `MaskedTextField` incluye una función de restauración que restablece el valor del campo a un estado predefinido u original. Esto puede ser útil para deshacer cambios realizados por el usuario o volver a una entrada predeterminada.

```java
field.setRestoreValue("ABC123");
field.restoreValue();
```

### Maneras de restaurar el valor {#ways-to-restore-the-value}

- **Programáticamente**, llamando a `restoreValue()`
- **Vía teclado**, presionando <kbd>ESC</kbd> (esta es la tecla de restauración predeterminada a menos que se sobreescriba mediante un oyente de eventos)

Puedes establecer el valor a restaurar con `setRestoreValue()`. Si no se establece un valor de restauración, tanto `restoreValue()` como <kbd>ESC</kbd> establecen el valor del campo en `null`.

<ComponentDemo
path='/webforj/maskedtextfieldrestore'
files={['src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldRestoreView.java']}
height='200px'
/>

## `MaskedTextFieldSpinner` {#maskedtextfieldspinner}

El `MaskedTextFieldSpinner` extiende el `MaskedTextField` añadiendo controles de spinner que permiten a los usuarios ciclar a través de una lista de valores predefinidos. Esto mejora la experiencia del usuario en situaciones donde la entrada debe estar restringida a un conjunto fijo de opciones válidas.

<ComponentDemo
path='/webforj/maskedtextfieldspinner'
files={['src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldSpinnerView.java']}
height='120px'
/>

### Características clave {#key-features}

- **Soporte para lista de opciones**
  Completa el spinner con una lista de valores de cadena válidos utilizando `setOptions()`:

  ```java
  spinner.setOptions(List.of("Opción A", "Opción B", "Opción C"));
  ```

- **Ciclado programático**
  Utiliza `spinUp()` y `spinDown()` para moverte a través de las opciones:

  ```java
  spinner.spinUp();   // Selecciona la siguiente opción
  spinner.spinDown(); // Selecciona la opción anterior
  ```

- **Control de índice**
  Establece o recupera el índice de la selección actual con:

  ```java
  spinner.setOptionIndex(1);
  int current = spinner.getOptionIndex();
  ```

- **Compatibilidad con la máscara**
  Hereda completamente todas las reglas de formato, máscara y validación de patrones del `MaskedTextField`.

## Estilo {#styling}

<TableBuilder name="MaskedTextField" />
