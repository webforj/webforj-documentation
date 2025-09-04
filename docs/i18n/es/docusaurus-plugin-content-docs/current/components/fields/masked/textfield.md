---
title: MaskedTextField
sidebar_position: 15
_i18n_hash: 701dcaccf198fbf507d1cd19c4bd995d
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-textfield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedTextField" top='true'/>

El componente `MaskedTextField` tiene como objetivo proporcionar una entrada de texto configurable y fácilmente validable. Es ideal para aplicaciones que requieren entrada formateada, como aplicaciones financieras, de comercio electrónico y de atención médica.

## Básicos {#basics}

El `MaskedTextField` se puede instanciar con o sin parámetros. Puedes definir un valor inicial, una etiqueta, un texto de marcador de posición y un oyente en caso de que el valor cambie.

```java
MaskedTextField field = new MaskedTextField("ID de cuenta");
field.setMask("ZZZZ-0000")
  .setHelperText("Máscara: ZZZZ-0000 - por ejemplo: SAVE-2025")
```

## Reglas de máscara {#mask-rules}

El `MaskedTextField` formatea la entrada de texto utilizando una máscara: una cadena que define qué caracteres están permitidos en cada posición. Esto asegura una entrada consistente y estructurada para cosas como números de teléfono, códigos postales y formatos de ID.

### Caracteres de máscara compatibles {#supported-mask-characters}

| Caracter  | Descripción                                                                                 |
|-----------|---------------------------------------------------------------------------------------------|
| `X`       | Cualquier carácter imprimible                                                                |
| `a`       | Cualquier carácter alfabético (mayúscula o minúscula)                                        |
| `A`       | Cualquier carácter alfabético; las letras minúsculas se convierten a mayúsculas             |
| `0`       | Cualquier dígito (0–9)                                                                       |
| `z`       | Cualquier dígito o letra (mayúscula o minúscula)                                            |
| `Z`       | Cualquier dígito o letra; las letras minúsculas se convierten a mayúsculas                   |

Todos los demás caracteres en la máscara se tratan como literales y deben escribirse exactamente. 
Por ejemplo, una máscara como `XX@XX` requiere que el usuario ingrese un `@` en el medio.

- **Los caracteres inválidos** son ignorados silenciosamente.
- **La entrada corta** se rellena con espacios.
- **La entrada larga** se trunca para ajustarse a la máscara.

### Ejemplos {#examples}

```java
field.setMask("(000) 000-0000");     // Ejemplo: (123) 456-7890
field.setMask("A00 000");            // Ejemplo: A1B 2C3 (código postal canadiense)
field.setMask("ZZZZ-0000");          // Ejemplo: ABCD-1234
field.setMask("0000-0000-0000-0000");// Ejemplo: 1234-5678-9012-3456
```

:::tip Se permite entrada completa
Si la máscara solo contiene `X`, el campo se comporta como un [`TextField`](../text-field.md) estándar, permitiendo cualquier entrada imprimible.
Esto es útil cuando deseas reservar la capacidad de formatear sin aplicar reglas de caracteres estrictas.
:::

<ComponentDemo 
path='/webforj/maskedtextfield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldView.java'
height='250px'
/>

## Patrones de validación {#validation-patterns}

Si bien las máscaras definen la estructura de la entrada, puedes combinarlas con patrones de validación para hacer cumplir reglas de entrada más específicas. Esto agrega una capa adicional de validación del lado del cliente usando expresiones regulares.

Usa el método `setPattern()` para aplicar una expresión regular personalizada:

```java
field.setPattern("[A-Za-z0-9]{10}"); // Impone un código alfanumérico de 10 caracteres
```

Esto asegura que la entrada no solo coincida con la máscara, sino que también se conforme a una estructura definida, como longitud o caracteres permitidos.

Esto es especialmente útil cuando:

- La máscara permite demasiada flexibilidad
- Quieres imponer una longitud exacta o un formato específico (por ejemplo, hex, Base64, UUID)

:::tip Formato de expresión regular
El patrón debe ser una expresión regular válida de [JavaScript](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions), como se utiliza por el tipo `RegExp`. Puedes encontrar más detalles en la [documentación del atributo de patrón HTML](https://developer.mozilla.org/en-US/docs/Web/HTML/Attributes/pattern#overview).
:::

## Restaurando el valor {#restoring-the-value}

El `MaskedTextField` incluye una función de restauración que restablece el valor del campo a un estado predeterminado u original. 
Esto puede ser útil para deshacer cambios del usuario o volver a una entrada predeterminada.

```java
field.setRestoreValue("ABC123");
field.restoreValue();
```

### Formas de restaurar el valor {#ways-to-restore-the-value}

- **Programáticamente**, llamando a `restoreValue()`
- **A través del teclado**, presionando <kbd>ESC</kbd> (esta es la tecla de restauración predeterminada a menos que se anule por un oyente de eventos)

Puedes establecer el valor a restaurar con `setRestoreValue()`. Si no se establece ningún valor de restauración, el campo volverá al valor inicial en el momento en que se renderizó.

<ComponentDemo 
path='/webforj/maskedtextfieldrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldRestoreView.java'
height='200px'
/>

## `MaskedTextFieldSpinner` {#maskedtextfieldspinner}

El `MaskedTextFieldSpinner` extiende [`MaskedTextField`](#basics) al agregar controles de spinner que permiten a los usuarios recorrer una lista de valores predefinidos. 
Esto mejora la experiencia del usuario en situaciones donde la entrada debe estar restringida a un conjunto fijo de opciones válidas.

<ComponentDemo 
path='/webforj/maskedtextfieldspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldSpinnerView.java'
height='120px'
/>

### Características clave {#key-features}

- **Soporte para lista de opciones**  
  Llena el spinner con una lista de valores de cadena válidos usando `setOptions()`:

  ```java
  spinner.setOptions(List.of("Opción A", "Opción B", "Opción C"));
  ```

- **Rotación programática**  
  Usa `spinUp()` y `spinDown()` para moverte entre opciones:

  ```java
  spinner.spinUp();   // Selecciona la siguiente opción
  spinner.spinDown(); // Selecciona la opción anterior
  ```

- **Control de índice**  
  Establece o recupera el índice de selección actual con:

  ```java
  spinner.setOptionIndex(1);
  int current = spinner.getOptionIndex();
  ```

- **Compatibilidad con máscara**  
  Hereda completamente todas las reglas de formato, de máscara y de validación de patrones de `MaskedTextField`.

## Estilo {#styling}

<TableBuilder name="MaskedTextField" />
